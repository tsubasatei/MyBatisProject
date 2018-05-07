package com.xt.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by xt on 2018/5/7.
 *
 * @Description: 登录
 */
@Controller
public class LoginController {

    //登录
    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password) throws Exception {

        //调用service进行用户验证

        session.setAttribute("username", username);

        return "redirect:items/findItemsListByName";
    }

    //退出
    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception {

        //调用service进行用户验证

        session.invalidate();

        return "/login";
    }
}
