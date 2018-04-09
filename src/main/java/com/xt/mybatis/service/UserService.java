package com.xt.mybatis.service;

import com.xt.mybatis.domain.User;
import com.xt.mybatis.domain.UserCustomer;
import com.xt.mybatis.domain.UserQueryVo;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 17:46
 * Description:
 */
public interface UserService {
    User findUserById(int id);

    //foreach查询1
    List<UserCustomer> findUserList1(UserQueryVo userQueryVo);

    //根据id查询用户信息使用resultMap
    User findUserByIdResultMap(int id);

    //查询用户列表
    List<User> findAll();

    //根据用户名模糊查询用户信息
    List<User> findUserByName(String username);

    //新增
    int insertUser(User user);

    //删除
    void deleteUser(Integer id);

    //更新
    void updateUser(User user);

    //用户信息综合查询
    List<UserCustomer> findUserList(UserQueryVo userQueryVo) throws Exception;

    //用户信息综合查询条数
    int findUserCount(UserQueryVo userQueryVo) throws Exception;

    //测试一级缓存
    void testCache1() throws Exception;

    //测试二级缓存
    void testCache2() throws Exception;
}
