package com.xt.mybatis.controller;

import com.xt.mybatis.domain.*;
import com.xt.mybatis.service.OrdersService;
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
 * Description:订单
 */
@RestController
@RequestMapping("/order")
public class OrdersUserController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/findOrderUser")
    public List<OrdersCustomer> findOrderUser() throws Exception {
        return ordersService.findOrdersUser();
    }

    @GetMapping("/findOrderUserResultMap")
    public List<Orders> findOrderUserResultMap() throws Exception {
        return ordersService.findOrderUserResultMap();
    }

    @GetMapping("/findOrderANDOrderDetailResultMap")
    public List<Orders> findOrderANDOrderDetailResultMap() throws Exception {
        return ordersService.findOrderANDOrderDetailResultMap();
    }

    @GetMapping("/findUserAndItemsResultMap")
    public List<User> findUserAndItemsResultMap() throws Exception {
        return ordersService.findUserAndItemsResultMap();
    }

    //查询订单延迟查询用户
    @GetMapping("/findOrderUserLazyLoading")
    public void  findOrderUserLazyLoading() throws Exception {
        List<Orders> list = ordersService.findOrderUserLazyLoading();
        if(null != list && list.size() > 0){
            for(Orders orders : list){
                User user = orders.getUser();
                System.out.println(user);
            }
        }
    }
}
