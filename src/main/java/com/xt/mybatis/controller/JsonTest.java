package com.xt.mybatis.controller;

import com.xt.mybatis.domain.ItemsCustom;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xt on 2018/5/6.
 *
 * @Description: json交互测试
 */
@Controller
@RequestMapping("/json")
public class JsonTest {

    @RequestMapping("/jsonTest")
    public String jsonTest(){
        return "jsonTest/jsonTest";
    }

    //@RequestBody将请求的商品信息的json串转化为itemsCustom对象
    //@ResponseBody将itemsCustom对象转化为json串输出
    @RequestMapping("/requestBody")
    @ResponseBody
    public ItemsCustom requestBody(@RequestBody ItemsCustom itemsCustom){
        return itemsCustom;
    }

    @RequestMapping("/responseBody")
    @ResponseBody
    public ItemsCustom responseBody(ItemsCustom itemsCustom){
        return itemsCustom;
    }

}
