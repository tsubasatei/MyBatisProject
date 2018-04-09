package com.xt.mybatis.domain;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 16:24
 * Description: 订单明细
 */
public class OrderDetail {

    private Integer id;
    private Integer ordersId;// 订单号
    private Integer itemsId;// 商品id
    private Integer itemsNum;// 商品数量

    private Items items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getItemsId() {
        return itemsId;
    }

    public void setItemsId(Integer itemsId) {
        this.itemsId = itemsId;
    }

    public Integer getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(Integer itemsNum) {
        this.itemsNum = itemsNum;
    }

    public Items getIterms() {
        return items;
    }

    public void setIterms(Items iterms) {
        this.items = iterms;
    }
}
