package com.xt.mybatis.controller;

import com.xt.mybatis.domain.ItemsCustom;
import com.xt.mybatis.domain.ItemsQueryVo;
import com.xt.mybatis.exception.CustomerException;
import com.xt.mybatis.service.ItemsService;
import com.xt.mybatis.validation.ValidGroup1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.UUID;

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
    //图片路径
    @Value("${web.upload-path}")
    private String path;

    @RequestMapping("/findItemsListByName")
    public String findItemsListByName(ItemsQueryVo itemsQueryVo, Model model) throws Exception {
        /*ItemsCustom itemsCustom = new ItemsCustom();
        itemsCustom.setName("笔记本");
        itemsQueryVo.setItemsCustom(itemsCustom);*/
        List<ItemsCustom> itemsList = itemsService.findItemsListByName(itemsQueryVo);

        model.addAttribute("itemsList", itemsList);

        return "items/itemsList";
    }

    //查询商品信息，输出json
    ///itemsView/{id}里边的{id}表示将这个位置上的参数传到@PathVariable指定的名称中
    @RequestMapping("/itemsView/{id}")
    public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception {

        ItemsCustom itemsCustom = itemsService.findItemsById(id);
       return itemsCustom;
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
                              BindingResult bindingResult,
                              MultipartFile items_pic) throws Exception {
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

        //原图片名称
        String originalFilename = items_pic.getOriginalFilename();

        if(items_pic != null && originalFilename != null && originalFilename.length() > 0){

            //新图片名称
            String newFilename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //新图片
            File newFile = new File(path + newFilename);
            //将内存中的数据写入磁盘
            items_pic.transferTo(newFile);

            //将新图片名称写入itemsCustom
            itemsCustom.setPic(newFilename);
        }

        itemsService.updateItems(id, itemsCustom);
        return "redirect:findItemsListByName";
    }

    //商品信息批量删除
    @PostMapping("/batchDeleteItems")
    public String batchDeleteItems(@RequestParam Integer[] items_id) throws Exception {
        //调用service的批量删除方法,表有关联无法直接删
        /*try {
            itemsService.batchDeleteItems(items_id);
        }catch (Exception ex){
            throw new  CustomerException(ex.getMessage());
        }*/
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
