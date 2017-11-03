package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/11/2 0002.
 * 2.3.5  查询资产文件列表
 */

public class AssetInfoFile {

    /**
     * id : 6
     * createTime : 2017-10-12 15:35:07
     * isDel : 0
     * sort : 0
     * assetId : 7
     * path : /assetFile/2017/10/12/15077937073981719.jpg
     * type : img
     * url : http://s.jq.com/upload/assetFile/2017/10/12/15077937073981719.jpg
     */

    private String id;
    private String createTime;
    private String isDel;
    private String sort;
    private String assetId;
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

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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
