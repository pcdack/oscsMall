package com.pcdack.oscsmall.dao;

import com.pcdack.oscsmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectByNameAndId(@Param("productName") String productName,@Param("productId") Integer productId);

    List<Product> setectByNameAndCategoryId(@Param("keyword") String keyword, @Param("categoryIds") List<Integer> categoryIds);

    List<Product> selectByCategoryId(@Param("categoryId") Integer categoryId);

    List<Product> selectByKeyWord(@Param("keyword") String keyword);
}