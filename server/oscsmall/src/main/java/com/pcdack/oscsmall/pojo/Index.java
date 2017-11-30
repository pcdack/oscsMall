package com.pcdack.oscsmall.pojo;

public class Index {
    private Integer id;

    private Integer userId;

    public Index(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Index() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}