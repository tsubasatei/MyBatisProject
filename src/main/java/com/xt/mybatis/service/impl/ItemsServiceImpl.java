package com.xt.mybatis.service.impl;

import com.xt.mybatis.domain.Items;
import com.xt.mybatis.domain.ItemsCustom;
import com.xt.mybatis.domain.ItemsQueryVo;
import com.xt.mybatis.exception.CustomerException;
import com.xt.mybatis.mapper.ItemsCustomerMapper;
import com.xt.mybatis.mapper.ItemsMapper;
import com.xt.mybatis.service.ItemsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/4/9
 * Time: 14:09
 * Description:
 */
@Service
public class ItemsServiceImpl implements ItemsService{
    @Autowired
    ItemsCustomerMapper itemsCustomerMapper;
    @Autowired
    ItemsMapper itemsMapper;

    @Override
    public List<ItemsCustom> findItemsListByName(ItemsQueryVo itemsQueryVo) throws Exception {
        return itemsCustomerMapper.findItemsListByName(itemsQueryVo);
    }

    @Override
    public ItemsCustom findItemsById(Integer id) throws Exception {
        Items items = itemsMapper.selectByPrimaryKey(id);

        /*如果与业务功能相关的异常，建议在service中抛出异常。
          与业务功能没有关系的异常，建议在controller中抛出。*/
        if(null == items){
            throw new CustomerException("修改的商品信息不存在！");
        }
        //中间对商品信息进行业务处理
        ItemsCustom itemsCustom = null;
        if (items != null){
            itemsCustom = new ItemsCustom();
            BeanUtils.copyProperties(items, itemsCustom);
        }
        return itemsCustom;
    }

    @Override
    public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
        //添加业务校验。通常在service层对关键参数进行校验
        //校验id是否为空，如果为空抛出异常

        //更新商品信息使用updateByExampleWithBLOBs,根据id更新items表中所有字段，包括大文本类型字段
        //更新商品信息使用updateByExampleWithBLOBs要求必须传入id
        itemsCustom.setId(id);
        itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
    }
}
