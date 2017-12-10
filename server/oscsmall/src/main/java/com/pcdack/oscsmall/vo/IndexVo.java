package com.pcdack.oscsmall.vo;


import java.util.List;

public class IndexVo {
    private MyPageVo myPageVo;
    private List<CategoryVo> categoryVos;
    private List<CategoryWithProductVo> categoryWithProductVos;

    public MyPageVo getMyPageVo() {
        return myPageVo;
    }

    public void setMyPageVo(MyPageVo myPageVo) {
        this.myPageVo = myPageVo;
    }

    public List<CategoryVo> getCategoryVos() {
        return categoryVos;
    }

    public void setCategoryVos(List<CategoryVo> categoryVos) {
        this.categoryVos = categoryVos;
    }

    public List<CategoryWithProductVo> getCategoryWithProductVos() {
        return categoryWithProductVos;
    }

    public void setCategoryWithProductVos(List<CategoryWithProductVo> categoryWithProductVos) {
        this.categoryWithProductVos = categoryWithProductVos;
    }
}