package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/11/6 0006.
 * 2.7.2  查询债事交易进度文件列表
 */

public class GetDebtMatterProgressFileList {

    /**
     * id : 2
     * createTime : 2017-10-18 18:13:42
     * isDel : 0
     * sort : 0
     * debtMatterProgressId : 1
     * path : /debtMatterProgressFile/2017/10/18/15083216220092297.jpg
     * type : img
     * url : http://s.jq.com/upload/debtMatterProgressFile/2017/10/18/15083216220092297.jpg
     */

    private String id;
    private String createTime;
    private String isDel;
    private String sort;
    private String debtMatterProgressId;
    private String path;
    private String type;
    private String url;

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

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDebtMatterProgressId() {
        return debtMatterProgressId;
    }

    public void setDebtMatterProgressId(String debtMatterProgressId) {
        this.debtMatterProgressId = debtMatterProgressId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
