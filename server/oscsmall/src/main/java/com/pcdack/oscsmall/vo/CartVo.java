package com.pcdack.oscsmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pcdack on 17-9-13.
 *
 */
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal allPrices;
    private boolean allChecked;
    private String ImageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getAllPrices() {
        return allPrices;
    }

    public void setAllPrices(BigDecimal allPrices) {
        this.allPrices = allPrices;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return ImageHost;
    }

    public void setImageHost(String imageHost) {
        ImageHost = imageHost;
    }
}
