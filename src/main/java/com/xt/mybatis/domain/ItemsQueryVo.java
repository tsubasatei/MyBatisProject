package com.xt.mybatis.domain;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 16:24
 * Description: 商品信息扩展类
 */
public class ItemsQueryVo{

    //商品信息
    private Items items;
    //商品信息扩展类
    private ItemsCustom itemsCustom;

    public Items getIterms() {
        return items;
    }

    public void setIterms(Items items) {
        this.items = items;
    }

    public ItemsCustom getItemsCustom() {
        return itemsCustom;
    }

    public void setItemsCustom(ItemsCustom itemsCustom) {
        this.itemsCustom = itemsCustom;
    }
}
