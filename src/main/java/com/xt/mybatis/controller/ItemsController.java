package com.xt.mybatis.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.xt.mybatis.domain.ItemsCustom;
import com.xt.mybatis.domain.ItemsQueryVo;
import com.xt.mybatis.exception.CustomerException;
import com.xt.mybatis.service.ItemsService;
import com.xt.mybatis.validation.ValidGroup1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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

    @RequestMapping("/findItemsListByName")
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
        if(itemsCustom == null){
            throw new CustomerException("修改的商品信息不存在！");
        }
        modelAndView.addObject("itemsCustom", itemsCustom);
        modelAndView.setViewName("items/editItems");

        return modelAndView;
    }

    //商品信息修改提交
    //在需要校验的pojo前边添加@Validated，在需要校验的pojo后边添加BindingResult bindingResult接收校验出错信息
    //注意：@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）。
    //value = {ValidGroup1.class}指定使用ValidGroup1分组的校验
    //springmvc自动将pojo数据放到request域，key等于pojo类型（首字母小写）
    //@ModelAttribute指定pojo回显到页面在request中的key
    //@PostMapping("/updateItems")
    @RequestMapping(value="/updateItems", method = RequestMethod.POST)
    public String updateItems(Model model, Integer id,
                              @ModelAttribute("itemsCustom") @Validated(value = {ValidGroup1.class}) ItemsCustom itemsCustom,
                              BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList){
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("errorList", errorList);
            //可以直接使用model将提交数据回显
            model.addAttribute("itemsCustom", itemsCustom);
            return "items/editItems";
        }
        itemsService.updateItems(id, itemsCustom);
        return "redirect:findItemsListByName";
    }

    //商品信息批量删除
    @PostMapping("/batchDeleteItems")
    public String batchDeleteItems(Integer[] items_id){
        //调用service的批量删除方法

        //return "redirect:findItemsListByName";
        return "success";
    }

    //批量修改商品页面
    @RequestMapping("/batchEditItems")
    public String batchEditItems(ItemsQueryVo itemsQueryVo, Model model) throws Exception {
        List<ItemsCustom> itemsList = itemsService.findItemsListByName(itemsQueryVo);
        model.addAttribute("itemsList", itemsList);

        return "items/editBatchItems";
    }

    //批量提交商品列表
    @PostMapping("/batchUpdateItems")
    public String batchUpdateItems(ItemsQueryVo itemsQueryVo) throws Exception {
        //调用service批量更新商品信息方法

        return "success";
    }
}
