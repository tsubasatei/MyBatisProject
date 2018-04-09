package com.xt.mybatis.domain;

/**
 * Created with xt.
 * Date: 2018/3/26
 * Time: 14:35
 * Description: 订单扩展信息
 */
public class OrdersCustomer extends Orders {

    private String username;
    private String sex;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
