package com.xt.mybatis.controller;

import com.xt.mybatis.domain.ItemsCustom;
import com.xt.mybatis.domain.ItemsQueryVo;
import com.xt.mybatis.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/4/9
 * Time: 14:12
 * Description:商品信息控制器
 */
//@RestController
@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    ItemsService itemsService;

    @GetMapping("/findItemsListByName")
    public String findItemsListByName(ItemsQueryVo itemsQueryVo, Model model) throws Exception {
        /*ItemsCustom itemsCustom = new ItemsCustom();
        itemsCustom.setName("笔记本");
        itemsQueryVo.setItemsCustom(itemsCustom);*/
        List<ItemsCustom> itemsList = itemsService.findItemsListByName(itemsQueryVo);

        model.addAttribute("itemsList", itemsList);

        return "items/itemsList";
    }

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "world");

        return "items/hello";
    }

    //商品信息修改页面显示
    @GetMapping("/editItems")
    public ModelAndView editItems(@RequestParam(value="id") Integer iterm_id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        ItemsCustom itemsCustom = itemsService.findItemsById(iterm_id);
        modelAndView.addObject("itemsCustom", itemsCustom);
        modelAndView.setViewName("items/editItems");

        return modelAndView;
    }

    //商品信息修改提交
    //@PostMapping("/updateItems")
    @RequestMapping(value="/updateItems", method = RequestMethod.POST)
    public String updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
        itemsService.updateItems(id, itemsCustom);
        return "redirect:findItemsListByName";
    }
}
