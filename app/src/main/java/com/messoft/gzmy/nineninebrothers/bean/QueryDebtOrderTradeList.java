package com.messoft.gzmy.nineninebrothers.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/2 0002.
 * 2.4.3  查询债事单交易列表
 */

public class QueryDebtOrderTradeList implements Serializable{


    /**
     * id : 14
     * debtReliefEngineerId : 775
     * debtOrderNumber : ZSD201711011358256149413
     * state : 1
     * debtMatterId : 23
     */

    private String id;
    private String debtReliefEngineerId;
    private String debtOrderNumber;
    private String state;
    private String debtMatterId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDebtReliefEngineerId() {
        return debtReliefEngineerId;
    }

    public void setDebtReliefEngineerId(String debtReliefEngineerId) {
        this.debtReliefEngineerId = debtReliefEngineerId;
    }

    public String getDebtOrderNumber() {
        return debtOrderNumber;
    }

    public void setDebtOrderNumber(String debtOrderNumber) {
        this.debtOrderNumber = debtOrderNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDebtMatterId() {
        return debtMatterId;
    }

    public void setDebtMatterId(String debtMatterId) {
        this.debtMatterId = debtMatterId;
    }
}
