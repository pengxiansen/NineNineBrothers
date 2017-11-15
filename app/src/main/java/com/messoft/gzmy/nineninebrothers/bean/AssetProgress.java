package com.messoft.gzmy.nineninebrothers.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/6 0006.
 * 资产进度
 */

public class AssetProgress extends BaseObservable implements Serializable {


    /**
     * id : 4
     * createTime : 2017-09-19 11:27:46
     * assetId : 2
     * assetDiscussId : 3
     * disposeState : 1
     * remarks : 222
     * memberId : 1
     * progressStage : 2
     */

    private String id;
    private String createTime;
    private String assetId;
    private String assetDiscussId;
    private String disposeState;
    private String remarks;
    private String memberId;
    private int progressStage;

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

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetDiscussId() {
        return assetDiscussId;
    }

    public void setAssetDiscussId(String assetDiscussId) {
        this.assetDiscussId = assetDiscussId;
    }

    public String getDisposeState() {
        return disposeState;
    }

    public void setDisposeState(String disposeState) {
        this.disposeState = disposeState;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getProgressStage() {
        return progressStage;
    }

    public void setProgressStage(int progressStage) {
        this.progressStage = progressStage;
    }
}
