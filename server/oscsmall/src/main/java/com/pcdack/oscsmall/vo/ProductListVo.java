package com.pcdack.oscsmall.vo;

import java.math.BigDecimal;

public class ProductListVo {
    private Integer id;
    private String name;
    private String subtitle;
    private String main_image;
    private BigDecimal price;
    private Integer status;
    private String imageHost;
    private Integer span_size;
    private Integer index_kind;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getSpan_size() {
        return span_size;
    }

    public void setSpan_size(Integer span_size) {
        this.span_size = span_size;
    }

    public Integer getIndex_kind() {
        return index_kind;
    }

    public void setIndex_kind(Integer index_kind) {
        this.index_kind = index_kind;
    }
}