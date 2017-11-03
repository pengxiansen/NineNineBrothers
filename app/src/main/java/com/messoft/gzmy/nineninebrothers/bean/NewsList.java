package com.messoft.gzmy.nineninebrothers.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class NewsList extends BaseObservable implements Serializable{


    /**
     * content : 哈哈哈哈测试是的环境哦<span>哈哈哈哈测试是的环境哦</span><span>哈哈哈哈测试是的环境哦</span><span>哈哈哈哈测试是的环境哦</span>
     * id : 32
     * imgUrl : news/20171102/20171102094024_231.jpg
     * typeName : 通知
     * createTime : 2017-11-02 09:40:24
     * isDel : 0
     * title : 测试缩略图
     * projectType : project_type_info
     * createBy : 999999
     * createName : 系统管理员
     * typeId : 1
     */

    private String content;
    private String id;
    private String imgUrl;
    private String typeName;
    private String createTime;
    private String isDel;
    private String title;
    private String projectType;
    private String createBy;
    private String createName;
    private String typeId;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
