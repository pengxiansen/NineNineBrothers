package com.messoft.gzmy.nineninebrothers.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class NewsList extends BaseObservable implements Serializable{

    /**
     * content : 矢口抵赖荆防颗粒时代峻峰恐龙当家三六九等冯老师<img src="http://www.jq.com/js/kindeditor-4.1.10/plugins/emoticons/images/10.gif" border="0" alt="" /><img src="http://api.map.baidu.com/staticimage?center=121.325376,31.245213&zoom=10&width=558&height=360&markers=121.325376,31.245213&markerStyles=l,A" alt="" />
     * id : 3
     * createTime : 2017-09-19 17:30:09
     * title : 新闻3
     * newsTypeId : 1
     */

    private String content;
    private String id;
    private String createTime;
    private String title;
    private String newsTypeId;

    @Override
    public String toString() {
        return "NewsList{" +
                "content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", title='" + title + '\'' +
                ", newsTypeId='" + newsTypeId + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(String newsTypeId) {
        this.newsTypeId = newsTypeId;
    }
}
