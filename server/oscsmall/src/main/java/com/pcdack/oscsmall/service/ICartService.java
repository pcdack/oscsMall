package com.pcdack.oscsmall.service;


import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.vo.CartVo;

/**
 * Created by pcdack on 17-9-13.
 *
 */
public interface ICartService {

    ServerResponse<CartVo> getList(Integer userId);

    ServerResponse<CartVo> add(Integer id, Integer count, Integer productId);

    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer>getCartProductCount(Integer userId);
}
