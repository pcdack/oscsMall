package com.pcdack.oscsmall.dao;

import com.pcdack.oscsmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    void batchInsert(@Param("orderItems")List<OrderItem> orderItems);

    List<OrderItem> getByOrderNo(Long orderNo);

    List<OrderItem> getByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    List<OrderItem> getByOrderNoUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer id);
}