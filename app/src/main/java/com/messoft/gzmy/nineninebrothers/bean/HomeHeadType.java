package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/10/23 0023.
 * 首页头部分类
 */

public class HomeHeadType {
    private String title;
    private int imgId;

    public HomeHeadType(String title, int imgId) {
        this.title = title;
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
