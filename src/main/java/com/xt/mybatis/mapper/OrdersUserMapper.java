package com.xt.mybatis.mapper;

import com.xt.mybatis.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 17:38
 * Description:
 */
@Mapper
public interface OrdersUserMapper {

    //查询订单信息，关联用户信息
    List<OrdersCustomer> findOrdersUser() throws Exception;

    List<Orders> findOrderUserResultMap() throws Exception;

    //查询订单关联用户和订单明细
    List<Orders> findOrderANDOrderDetailResultMap() throws Exception;

    //查询用户关联商品信息
    List<User> findUserAndItemsResultMap() throws Exception;

    //查询订单延迟查询用户
    List<Orders> findOrderUserLazyLoading() throws Exception;
}
