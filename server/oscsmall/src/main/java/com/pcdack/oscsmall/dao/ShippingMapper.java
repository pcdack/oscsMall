package com.pcdack.oscsmall.dao;

import com.pcdack.oscsmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByShippingIdUserId(@Param("userId") Integer userId, @Param("shipping") Integer shipping);

    int updateByShipping(Shipping shipping);

    Shipping selectShippingByUserIdAndShippingId(@Param("userId") Integer userId,@Param("shippingId") Integer shippingId);

    List<Shipping> selectByUserId(Integer userId);
}