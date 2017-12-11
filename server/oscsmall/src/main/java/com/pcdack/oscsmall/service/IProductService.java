package com.pcdack.oscsmall.service;

import com.github.pagehelper.PageInfo;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.Product;
import com.pcdack.oscsmall.vo.ProductDetailVo;

/**
 * Created by pcdack on 17-9-10.
 *
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);
    ServerResponse<PageInfo> searchProduct(Integer productId, String productName, Integer pageNum, Integer pageSize);
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductByKeyWordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);
    ServerResponse<PageInfo> getProductByCategory(Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);
    ServerResponse<PageInfo> getProductByKeyWord(String keyword, Integer pageNum, Integer pageSize, String orderBy);
}
