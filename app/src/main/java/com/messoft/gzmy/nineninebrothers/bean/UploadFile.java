package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class UploadFile {


    /**
     * url : http://127.0.0.1:8086/gz-info-web/upload/assess/20161212/20161212152502_782.jpg
     * dbFileName : assess/20161212/20161212152502_782.jpg
     * fileName : 20161212152502_782.jpg
     */

    private String url;
    private String dbFileName;
    private String fileName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbFileName() {
        return dbFileName;
    }

    public void setDbFileName(String dbFileName) {
        this.dbFileName = dbFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
