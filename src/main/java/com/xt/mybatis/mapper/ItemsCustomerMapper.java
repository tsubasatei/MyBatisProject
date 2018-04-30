package com.xt.mybatis.mapper;

import com.xt.mybatis.domain.ItemsCustom;
import com.xt.mybatis.domain.ItemsQueryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with xt.
 * Date: 2018/4/9
 * Time: 14:06
 * Description: 商品Mapper
 */
@Mapper
public interface ItemsCustomerMapper {
    List<ItemsCustom> findItemsListByName(ItemsQueryVo itemsQueryVo) throws Exception;
}
