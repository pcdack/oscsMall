package com.pcdack.oscsmall.vo;

public class MyPageVo {
    private int pageNum;
    private boolean nextPage;
    private boolean prePage;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isPrePage() {
        return prePage;
    }

    public void setPrePage(boolean prePage) {
        this.prePage = prePage;
    }
}