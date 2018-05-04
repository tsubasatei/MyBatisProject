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

    /**
     * @Description: 根据商品id查询商品信息
     * @Date: 2018/4/29 18:58
     * @param:
     */
    ItemsCustom findItemsById(Integer id) throws Exception;

    /**
     * @Description: 更新商品信息
     * @Date: 2018/4/29 19:01
     * @param: null
     */
    void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception;

    //批量删除商品信息
    void batchDeleteItems(Integer[] itemsId) throws Exception;
}
