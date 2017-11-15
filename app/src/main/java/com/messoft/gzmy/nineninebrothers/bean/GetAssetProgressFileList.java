package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class GetAssetProgressFileList {


    /**
     * id : 6
     * createTime : 2017-10-12 15:35:07
     * isDel : 0
     * sort : 0
     *  assetProgressId  : 7
     * path : /assetProgressFile/2017/10/12/15077937073981719.jpg
     * type : img
     * url : http://s.jq.com/upload/assetProgressFile/2017/10/12/15077937073981719.jpg
     */

    private String id;
    private String createTime;
    private String isDel;
    private String sort;
    private String assetProgressId;
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
}
