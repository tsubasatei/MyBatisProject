package com.xt.mybatis.domain;

/**
 * Created with xt.
 * Date: 2018/4/2
 * Time: 17:56
 * Description: 分页插件pojo
 */
public class PageParams {
    private Integer page; //当前页码
    private Integer pageSize; //每页条数
    private Boolean useFlag; //是否启用插件
    private Boolean checkFlag; //是否检查当前页码的有效性
    private Integer total; //当前sql返回条数，插件回填
    private Integer totalPage; //当前sql返回的总页数，插件回填

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }

    public Boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
