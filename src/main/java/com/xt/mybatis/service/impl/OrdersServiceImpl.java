package com.xt.mybatis.service.impl;

import com.xt.mybatis.domain.Orders;
import com.xt.mybatis.domain.OrdersCustomer;
import com.xt.mybatis.domain.User;
import com.xt.mybatis.mapper.OrdersUserMapper;
import com.xt.mybatis.service.OrdersService;
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
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersUserMapper orderUserMapper;

    //查询订单信息，关联用户信息
    public List<OrdersCustomer> findOrdersUser() throws Exception {
        return orderUserMapper.findOrdersUser();
    }

    @Override
    public List<Orders> findOrderUserResultMap() throws Exception {
        return orderUserMapper.findOrderUserResultMap();
    }

    @Override
    public List<Orders> findOrderANDOrderDetailResultMap() throws Exception {
        return orderUserMapper.findOrderANDOrderDetailResultMap();
    }

    @Override
    public List<User> findUserAndItemsResultMap() throws Exception {
        return orderUserMapper.findUserAndItemsResultMap();
    }

    @Override
    public List<Orders> findOrderUserLazyLoading() throws Exception {
        return orderUserMapper.findOrderUserLazyLoading();
    }

}
