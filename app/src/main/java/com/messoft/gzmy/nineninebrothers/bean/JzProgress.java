package com.messoft.gzmy.nineninebrothers.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/6 0006.
 * 债事进度
 */

public class JzProgress implements Serializable{


    /**
     * debtMatterOrderId : 1
     * id : 2
     * createTime : 2017-09-29 10:08:21
     * orderState : 1
     * debtOrderNumber : ZSD100000001
     * remarks : 阶段2
     * debtMatterId : 1
     * debtStage : 1
     */

    private String debtMatterOrderId;
    private String id;
    private String createTime;
    private String orderState;
    private String debtOrderNumber;
    private String remarks;
    private String debtMatterId;
    private int debtStage;

    public String getDebtMatterOrderId() {
        return debtMatterOrderId;
    }

    public void setDebtMatterOrderId(String debtMatterOrderId) {
        this.debtMatterOrderId = debtMatterOrderId;
    }

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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getDebtOrderNumber() {
        return debtOrderNumber;
    }

    public void setDebtOrderNumber(String debtOrderNumber) {
        this.debtOrderNumber = debtOrderNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDebtMatterId() {
        return debtMatterId;
    }

    public void setDebtMatterId(String debtMatterId) {
        this.debtMatterId = debtMatterId;
    }

    public int getDebtStage() {
        return debtStage;
    }

    public void setDebtStage(int debtStage) {
        this.debtStage = debtStage;
    }
}
