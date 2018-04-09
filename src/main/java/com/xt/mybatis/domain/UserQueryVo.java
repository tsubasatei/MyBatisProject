package com.xt.mybatis.domain;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/3/22
 * Time: 17:07
 * Description: 用户包装类型
 */
public class UserQueryVo {

    //在这里包装所需要的查询条件

    //用户查询条件
    private UserCustomer userCustomer;
    //用户id查询列表
    private List<Integer> ids;

    public UserCustomer getUserCustomer() {
        return userCustomer;
    }

    public void setUserCustomer(UserCustomer userCustomer) {
        this.userCustomer = userCustomer;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    //可以包装其他的查询条件，如订单、商品
}
