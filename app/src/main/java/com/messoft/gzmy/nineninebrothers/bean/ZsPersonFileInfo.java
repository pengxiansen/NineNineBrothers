package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/11/1 0001.
 * 2.1.5  查询债事人文件列表
 */

public class ZsPersonFileInfo {

    /**
     * id : 26
     * createTime : 2017-10-12 09:45:03
     * debtMatterPersonId : 7
     * path : /debtMatterPersonFile/2017/10/12/15077727031481785.jpg
     * type : img
     * url : http://s.jq.com/upload/debtMatterPersonFile/2017/10/12/15077727031481785.jpg
     */

    private String id;
    private String createTime;
    private String debtMatterPersonId;
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

    public String getDebtMatterPersonId() {
        return debtMatterPersonId;
    }

    public void setDebtMatterPersonId(String debtMatterPersonId) {
        this.debtMatterPersonId = debtMatterPersonId;
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

    @Override
    public String toString() {
        return "ZsPersonFileInfo{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", debtMatterPersonId='" + debtMatterPersonId + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
