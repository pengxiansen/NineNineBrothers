package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/11/2 0002.
 * 2.2.7  查询债事文件列表
 */

public class ZsDataInfoFileById {


    /**
     * id : 3
     * createTime : 2017-10-11 14:44:06
     * path : /debtMatterFile/2017/10/11/15077042464218696.jpg
     * debtMatterId : 1
     * type : img
     * url : http://s.jq.com/upload/debtMatterFile/2017/10/11/15077042464218696.jpg
     */

    private String id;
    private String createTime;
    private String path;
    private String debtMatterId;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDebtMatterId() {
        return debtMatterId;
    }

    public void setDebtMatterId(String debtMatterId) {
        this.debtMatterId = debtMatterId;
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
