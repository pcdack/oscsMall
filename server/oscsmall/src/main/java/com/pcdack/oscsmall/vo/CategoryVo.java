package com.pcdack.oscsmall.vo;

import java.util.List;

public class CategoryVo {
    private Integer id;
    private String name;
    private Boolean status;
    private String image;
    private List<CategoryChildVo> childVos;

    public List<CategoryChildVo> getChildVos() {
        return childVos;
    }

    public void setChildVos(List<CategoryChildVo> childVos) {
        this.childVos = childVos;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}