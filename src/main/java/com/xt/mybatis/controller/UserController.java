package com.xt.mybatis.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xt.mybatis.domain.User;
import com.xt.mybatis.domain.UserCustomer;
import com.xt.mybatis.domain.UserQueryVo;
import com.xt.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 17:48
 * Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findUserById/{id}")
    public User findUserById(@PathVariable("id") int id){
        return userService.findUserById(id);
    }

    @GetMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/findUserByName")
    public List<User> findUserByName(@RequestParam String username){
        return userService.findUserByName(username);
    }

    @PostMapping("/insertUser")
    public void  insertUser(){
        User user = new User();
        user.setUsername("王小军");
        user.setBirthday(new Date());
        user.setSex("1");
        user.setAddress("河北");
        System.out.println(userService.insertUser(user));
        System.out.println(user.getId());
    }

    @DeleteMapping("/deleteUser")
    public void  deleteUser(){
        userService.deleteUser(33);
    }

    @PutMapping("/updateUser")
    public void  updateUser(){
        User user = userService.findUserById(34);
        user.setUsername("张大军");
        user.setBirthday(new Date());
        user.setSex("2");
        user.setAddress("北京");
        userService.updateUser(user);
    }

    //用户信息综合查询
    @RequestMapping("/findUserList")
    public List<UserCustomer> findUserList() throws Exception {
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustomer userCustomer = new UserCustomer();
        //userCustomer.setSex("1");
        //userCustomer.setUsername("小军");
        userQueryVo.setUserCustomer(userCustomer);

        return userService.findUserList(userQueryVo);
    }

    //用户信息综合查询条数
    @RequestMapping("/findUserCount")
    public int findUserCount() throws Exception {
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustomer userCustomer = new UserCustomer();
        userCustomer.setSex("1");
        userCustomer.setUsername("小军");
        userQueryVo.setUserCustomer(userCustomer);

        return userService.findUserCount(userQueryVo);
    }

    @GetMapping("/findUserByIdResultMap")
    public User findUserByIdResultMap() {
        return userService.findUserByIdResultMap(33);
    }

    //foreach查询1
    @GetMapping("/findUserList1")
    public List<UserCustomer> findUserList1() throws Exception {
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustomer userCustomer = new UserCustomer();
        userCustomer.setUsername("小明");
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(10);
        ids.add(16);
        userQueryVo.setUserCustomer(userCustomer);
        userQueryVo.setIds(ids);

        return userService.findUserList1(userQueryVo);
    }

    @GetMapping("/testCache1")
    public void testCache1() throws Exception {
        userService.testCache1();
    }

    @GetMapping("/testCache2")
    public void testCache2() throws Exception {
        userService.testCache2();
    }

    @GetMapping("/fingUserListPage")
    public PageInfo<User>  fingUserListPage(@RequestParam(required = false) String username,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) throws Exception {
        PageHelper.startPage(page, pageSize);
        List<User> userList = userService.findUserByName(username);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }
}
