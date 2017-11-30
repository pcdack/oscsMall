package com.pcdack.oscsmall.dao;

import com.pcdack.oscsmall.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectOrderByIdandOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    List<Order> selectByUserId(Integer userId);

    Order selectByOrderNo(Long orderNo);

    List<Order> selectAllOrder();
}