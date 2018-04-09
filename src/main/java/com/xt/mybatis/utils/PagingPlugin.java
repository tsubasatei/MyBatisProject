package com.xt.mybatis.utils;


import com.xt.mybatis.domain.PageParams;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created with xt.
 * Date: 2018/4/2
 * Time: 18:01
 * Description: 分页插件签名
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class}
        )
})
public class PagingPlugin implements Interceptor {

    private Integer defaultPage; //默认页码
    private Integer defaultPageSize; //默认每页条数
    private  Boolean defaultUseFlag; //默认是否启动插件
    private Boolean defaultCheckFlag; //默认是否检测当前页码的正确性

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = getUnProxyObject(invocation);
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        //不是select语句
        if(!checkSelect(sql)) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("deleget.boundSql");
        Object parameteObject = boundSql.getParameterObject();
        PageParams pageParams = getPageParams(parameteObject);
        if(null == pageParams) { //没有分页参数，不启用插件
            return invocation.proceed();
        }
        //获取分页参数，获取不到时候使用默认值
        Integer pageNum = pageParams.getPage() == null ? this.defaultPage : pageParams.getPage();
        Integer pageSize = pageParams.getPageSize() == null ? this.defaultPageSize : pageParams.getPageSize();
        Boolean userFlag = pageParams.getUseFlag() == null ? this.defaultUseFlag : pageParams.getUseFlag();
        Boolean checkFlag = pageParams.getCheckFlag() == null ? this.defaultCheckFlag : pageParams.getCheckFlag();
        if(!userFlag) { //不使用分页插件
            return invocation.proceed();
        }
        int total = getTotal(invocation, metaObject, boundSql);
        //回填总数到分页参数里
        setTotalToPageParams(pageParams, total, pageSize);
        //检出当前页码的有效性
        checkPage(checkFlag, pageNum, pageParams.getTotalPage());
        //修改sql
        return changeSQL(invocation, metaObject, boundSql, pageNum, pageSize);
    }

    @Override
    public Object plugin(Object statementHandler) {
        return Plugin.wrap(statementHandler, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String strDefaultPage = properties.getProperty("default.page", "1");
        String strDefaultPageSize = properties.getProperty("default.pageSize", "50");
        String strDefaultUseFlag = properties.getProperty("default.checkFlag", "false");
        String strDefaultCheckFlag = properties.getProperty("default.checkFlag", "false");

        this.defaultPage = Integer.parseInt(strDefaultPage);
        this.defaultPageSize = Integer.parseInt(strDefaultPageSize);
        this.defaultUseFlag = Boolean.parseBoolean(strDefaultUseFlag);
        this.defaultCheckFlag = Boolean.parseBoolean(strDefaultCheckFlag);
    }

    /**
     * Created with xt.
     * Date:2018/4/2
     * Time:19:03
     * Params:
     * Description:从代理对象中分离出真实对象
     */
    private StatementHandler getUnProxyObject(Invocation invocation){
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        //分离代理对象链（由于目标类可能被多个拦截器拦截，从而形成多次代理，通过循环可以分离出最原始的目标类）
        Object object = null;
        while(metaObject.hasGetter("h")){
            object = metaObject.getValue("h");
        }
        if(object == null){
            return statementHandler;
        }
        return (StatementHandler) object;
    }

    /**
     * Created with xt.
     * Date:2018/4/2
     * Time:19:09
     * Params:
     * Description: 判断是否select 语句
     */
    private boolean checkSelect(String sql) {
        String trimSql = sql.trim();
        int idx = trimSql.toLowerCase().indexOf("select");
        return idx == 0;
    }

    /**
     * Created with xt.
     * Date:2018/4/2
     * Time:19:13
     * Params:
     * Description:分解分页参数，这里支持使用map 和 @Param注解传递参数
     * 或者pojo继承PageParams
     */
    private PageParams getPageParams(Object parameterObject) {
        if (parameterObject == null) {
            return null;
        }
        PageParams pageParams = null;
        //支持map参数和mybatis的@param注解参数
        if (parameterObject instanceof Map){
            Map<String, Object> paramMap = (Map<String, Object>) parameterObject;
            Set<String> keySet = paramMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                Object value = paramMap.get(key);
                if (value instanceof  PageParams){
                    return (PageParams) value;
                }
            }
        } else if (parameterObject instanceof PageParams) { //继承方式
            pageParams = (PageParams) parameterObject;
        }
        return pageParams;
    }

    /**
     * Created with xt.
     * Date:2018/4/3
     * Time:8:54
     * Params:
     * Description: 获取总数
     */
    private int getTotal(Invocation invocation, MetaObject metaObject,
                         BoundSql boundSql) throws Exception {
        //获取当前的mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //配置对象
        Configuration cfg = mappedStatement.getConfiguration();
        //当前需要执行的sql
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        // 改写为统计总数的sql
        String countSql = "select count(*) as total from (" + sql + ") $_paging";
        //获取拦截方法参数，Connection对象
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement ps = null;
        int total = 0;
        try {
            //预编译统计总数sql
            ps = connection.prepareStatement(countSql);
            //构建统计总数BoundSql
            BoundSql countBoundSql = new BoundSql(cfg, countSql, boundSql.getParameterMappings(),
                    boundSql.getParameterObject());
            //构建MyBatis的ParameterHandler用来设置总数sql的参数
            ParameterHandler handler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);
            //设置总数sql参数
            handler.setParameters(ps);
            //执行查询
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                total = rs.getInt("total");
            }
        }finally {
            //这里不能关闭Connection，否则后续的sql没法继续
            if(ps != null) {
                ps.close();
            }
        }
        System.out.println("总条数：" + total);
        return total;
    }

    /**
     * Created with xt.
     * Date:2018/4/3
     * Time:9:12
     * Params:
     * Description: 回填总条数和总页数到分页参数
     */
    private void setTotalToPageParams(PageParams pageParams, int total,
                                      int pageSize) {
        pageParams.setTotal(total);
        //计算总页数
        int totalPage = total % pageSize == 0 ? pageSize : total/pageSize+1;
        pageParams.setTotalPage(totalPage);
    }

    /**
     * Created with xt.
     * Date:2018/4/3
     * Time:9:15
     * Params:
     * Description:检查当前页码是否大于最大页码
     */
    private void checkPage(Boolean checkFlag, Integer pageNum,
                           Integer pageTotal) throws Throwable {
        if (checkFlag) {
            //检查页码page是否合法
            if(pageNum > pageTotal) {
                throw new Exception("查询失败，查询页码【" + pageNum +"】大于" +
                        "总页数【" + pageTotal + "】!!");
            }
        }
    }

    /**
     * Created with xt.
     * Date:2018/4/3
     * Time:9:21
     * Params:
     * Description:修改当前查询的sql
     */
    private Object changeSQL(Invocation invocation, MetaObject metaObject,
                             BoundSql boundSql, int page, int pageSize) throws InvocationTargetException, IllegalAccessException, SQLException {
        //获取当前需要执行的sql
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        //修改sql
        String newSql = "select * from (" + sql + ") $_paging_table limit ?,?";
        //修改当前需要执行的sql
        metaObject.setValue("delegate.boundSql.sql", newSql);
        PreparedStatement ps = (PreparedStatement)invocation.proceed();
        //计算sql总参数个数
        int count = ps.getParameterMetaData().getParameterCount();
        ps.setInt(count-1, (page-1) * pageSize);
        ps.setInt(count, pageSize);
        return ps;

    }
}
