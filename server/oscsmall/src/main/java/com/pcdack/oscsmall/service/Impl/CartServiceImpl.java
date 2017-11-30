package com.pcdack.oscsmall.service.Impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.dao.CartMapper;
import com.pcdack.oscsmall.dao.ProductMapper;
import com.pcdack.oscsmall.pojo.Cart;
import com.pcdack.oscsmall.pojo.Product;
import com.pcdack.oscsmall.service.ICartService;
import com.pcdack.oscsmall.util.BigDecimalUtil;
import com.pcdack.oscsmall.util.PropertiesUtil;
import com.pcdack.oscsmall.vo.CartProductVo;
import com.pcdack.oscsmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pcdack on 17-9-13.
 *
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> getList(Integer userId) {
        CartVo cartVo=this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> add(Integer id, Integer count, Integer productId) {
        if (productId == null || id == null ){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Cart cart=cartMapper.selectByProductIdAndUserId(id,productId);
        if (cart == null){
//            这个产品不在了购物车里需要新增一个商品记录
            Cart cartItem=new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECK);
            cartItem.setProductId(productId);
            cartItem.setUserId(id);
            cartMapper.insert(cartItem);
        }else {
            count=cart.getQuantity()+count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }

        return this.getList(id);
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId,Integer count, Integer productId) {
        if (count == null || productId ==null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Cart cart=cartMapper.selectByProductIdAndUserId(userId, productId);
        if (cart!=null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKey(cart);
        return this.getList(userId);
    }

    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
        List<String> product_ids= Splitter.on(",").splitToList(productIds);
        if (product_ids.size()< 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        cartMapper.deleteByUserIdAndProductIds(userId,product_ids);
        return this.getList(userId);
    }


    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
        return this.getList(userId);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }


    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo=new CartVo();
        List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVos= Lists.newArrayList();
        BigDecimal cartTotalPrice=new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cart : cartList) {
                CartProductVo cartProductVo=new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setProductId(cart.getProductId());
                cartProductVo.setUserId(userId);
                Product product=productMapper.selectByPrimaryKey(cart.getProductId());
                if (product != null){
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    int buyLimitCount=0;
//                    如果商品总数大于需求量
                    if (product.getStock()>=cart.getQuantity())
                    {
                        buyLimitCount=cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount=product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
//                        购物车中更新有效的值
                        Cart cartForQuantity=new Cart();
                        cartForQuantity.setId(cart.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(cartProductVo.getQuantity(),product.getPrice().doubleValue()));
                    cartProductVo.setProductChecked(cart.getChecked());
                }
                if (cart.getChecked() == Const.Cart.CHECK){
                    cartTotalPrice=BigDecimalUtil.add(cartTotalPrice.doubleValue(),
                            cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVos.add(cartProductVo);
            }
        }
        cartVo.setCartProductVoList(cartProductVos);
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        cartVo.setAllPrices(cartTotalPrice);

        return cartVo;
    }
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;

    }
}
