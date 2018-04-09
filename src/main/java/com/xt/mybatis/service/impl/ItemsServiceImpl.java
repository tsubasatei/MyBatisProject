package com.xt.mybatis.service.impl;

import com.xt.mybatis.domain.ItemsCustom;
import com.xt.mybatis.domain.ItemsQueryVo;
import com.xt.mybatis.mapper.ItemsMapper;
import com.xt.mybatis.service.ItemsService;
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
    ItemsMapper itemsMapper;

    @Override
    public List<ItemsCustom> findItemsListByName(ItemsQueryVo itemsQueryVo) throws Exception {
        return itemsMapper.findItemsListByName(itemsQueryVo);
    }
}
