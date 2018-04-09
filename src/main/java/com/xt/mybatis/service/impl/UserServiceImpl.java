package com.xt.mybatis.service.impl;

import com.xt.mybatis.domain.User;
import com.xt.mybatis.domain.UserCustomer;
import com.xt.mybatis.domain.UserQueryVo;
import com.xt.mybatis.mapper.UserMapper;
import com.xt.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 17:47
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(int id) {
        User user = userMapper.findUserById(id);
        return user;
    }

    @Override
    public List<UserCustomer> findUserList1(UserQueryVo userQueryVo) {
        return userMapper.findUserList1(userQueryVo);
    }

    @Override
    public User findUserByIdResultMap(int id) {
        return userMapper.findUserByIdResultMap(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public List<User> findUserByName(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public List<UserCustomer> findUserList(UserQueryVo userQueryVo) throws Exception {
        return userMapper.findUserList(userQueryVo);
    }

    @Override
    public int findUserCount(UserQueryVo userQueryVo) throws Exception {
        return userMapper.findUserCount(userQueryVo);
    }

    @Override
    @Transactional
    public void testCache1() throws Exception {
        //第一次发起请求查询id为1的用户
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

         /* 如果sqlSession去执行commit操作（执行插入、更新、删除），清空sqlSession中的一级缓存，
            这样做的目的是为了让缓存中存储的是最新的信息，避免脏读*/

        //更新user1的信息
//        user1.setUsername("测试用户27");
//        userMapper.updateUser(user1);

        //第一次发起请求查询id为1的用户
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);
    }

    @Override
    //@Transactional
    public void testCache2() throws Exception{
        //第一次发起请求查询id为1的用户
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

         /* 如果sqlSession去执行commit操作（执行插入、更新、删除），清空sqlSession中的一级缓存，
            这样做的目的是为了让缓存中存储的是最新的信息，避免脏读*/

        //更新user1的信息
//        user1.setUsername("测试用户27");
//        userMapper.updateUser(user1);

        //第一次发起请求查询id为1的用户
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);
    }
}
