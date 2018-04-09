package com.xt.mybatis.domain;

import java.util.Date;
import java.util.List;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 16:24
 * Description: 商品信息
 */
public class Items {

    private int id;
    private String name;//
    private Float price;//
    private String detail;//
    private String pic;//
    private Date createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
