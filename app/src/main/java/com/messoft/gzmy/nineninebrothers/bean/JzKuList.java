package com.messoft.gzmy.nineninebrothers.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/31 0031.
 * 解债库，解债师才能看
 */

public class JzKuList extends BaseObservable implements Serializable{

    /**
     * debtState : 1
     * provinceText : 河北
     * debtDate : 2017-10-31 00:00:00
     * expectedPrice : 422442
     * verifyState : 1
     * evaluatedPrice : 4554
     * debtNumber : ZS201710310938375817447
     * city : 602
     * id : 13
     * assetId : 39
     * assetType : 0
     * province : 113
     * cityText : 张家口市
     * debtAmount : 44444144
     */

    private String debtState;
    private String provinceText;
    private String debtDate;
    private String expectedPrice;
    private String verifyState;
    private String evaluatedPrice;
    private String debtNumber;
    private String city;
    private String id;
    private String assetId;
    private String assetType;
    private String province;
    private String cityText;
    private String debtAmount;

    public String getDebtState() {
        return debtState;
    }

    public void setDebtState(String debtState) {
        this.debtState = debtState;
    }

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public String getDebtDate() {
        return debtDate;
    }

    public void setDebtDate(String debtDate) {
        this.debtDate = debtDate;
    }

    public String getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(String expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public String getEvaluatedPrice() {
        return evaluatedPrice;
    }

    public void setEvaluatedPrice(String evaluatedPrice) {
        this.evaluatedPrice = evaluatedPrice;
    }

    public String getDebtNumber() {
        return debtNumber;
    }

    public void setDebtNumber(String debtNumber) {
        this.debtNumber = debtNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(String debtAmount) {
        this.debtAmount = debtAmount;
    }
}
