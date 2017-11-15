package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/11/7 0007.
 * 2.7.4  债事交易进度文件上传
 */

public class UploadDebtMatterProgressFile {

    /**
     * fileName : 15083216220092297.jpg
     * path : /debtMatterProgressFile/2017/10/18/15083216220092297.jpg
     * type : img
     * url : http://s.jq.com/upload/debtMatterProgressFile/2017/10/18/15083216220092297.jpg
     */

    private String fileName;
    private String path;
    private String type;
    private String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
