package com.xt.mybatis.service;

import com.xt.mybatis.domain.*;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 17:46
 * Description:商品信息service
 */
public interface ItemsService {
    /**
     * Created with xt.
     * Date:2018/4/9
     * Time:14:09
     * Params:
     * Description:根据商品名称查询商品信息列表
     */
    List<ItemsCustom> findItemsListByName(ItemsQueryVo itemsQueryVo) throws Exception;
}
