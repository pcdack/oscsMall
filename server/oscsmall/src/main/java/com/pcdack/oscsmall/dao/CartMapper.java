package com.pcdack.oscsmall.dao;

import com.pcdack.oscsmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    Cart selectByProductIdAndUserId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int deleteByUserIdAndProductIds(@Param("userId") Integer userId,@Param("productIds") List<String> productIds);

    int checkedOrUncheckedProduct(@Param("userId") Integer userId,@Param("productId")Integer productId,@Param("checked")Integer checked);

    int selectCartProductCount(@Param("userId") Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}