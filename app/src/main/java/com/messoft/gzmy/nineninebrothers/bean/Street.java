package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/10/25 0025.
 * 街道
 */

public class Street {

    /**
     * id : 1651500
     * createTime : 2017-10-10 13:40:52
     * parentId : 1440203
     * isDel : 0
     * title : 学苑大道
     * level : 4
     * sort : 1
     */

    private String id;
    private String createTime;
    private String parentId;
    private String isDel;
    private String title;
    private String level;
    private String sort;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Street{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", parentId='" + parentId + '\'' +
                ", isDel='" + isDel + '\'' +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
