package com.pcdack.oscsmall.vo;

import java.util.List;

public class CategoryWithProductVo {
    private Integer id;
    private String name;
    private Boolean status;
    private String categoryImg;
    private List<ProductListVo> productListVos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public List<ProductListVo> getProductListVos() {
        return productListVos;
    }

    public void setProductListVos(List<ProductListVo> productListVos) {
        this.productListVos = productListVos;
    }
}