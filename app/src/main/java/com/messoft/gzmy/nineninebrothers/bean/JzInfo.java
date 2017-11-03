package com.messoft.gzmy.nineninebrothers.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/1 0001.
 * 债事详情
 */

public class JzInfo extends BaseObservable implements Serializable{

    /**
     * creditorDemandType : 1
     * lawsuitState : 0
     * debtorName : 借钱的
     * creditorDemandExplain : 债权人需求详细说明
     * debtorId : 2
     * creditorId : 1
     * debtProperty : 0
     * creditorType : 0
     * debtDate : null
     * debtorType : 0
     * debtNumber : ZS0001
     * id : 1
     * creditorName : 放贷企业
     * debtAmount : 99990
     * debtType : 0
     * debtorAsset : {"provinceText":"1","districtText":"1","remarks":"房屋资产变卖","putawayState":0,"expectedPrice":110,"type":1,"verifyState":1,"evaluatedPrice":210,"city":1,"id":2,"assetType":1,"province":1,"dealState":0,"memberId":1,"memberName":"友缘","district":1,"cityText":"1"}
     */

    private String creditorDemandType;
    private String lawsuitState;
    private String debtorName;
    private String creditorDemandExplain;
    private String debtorId;
    private String creditorId;
    private String debtProperty;
    private String creditorType;
    private String debtDate;
    private String debtorType;
    private String debtNumber;
    private String id;
    private String creditorName;
    private String debtAmount;
    private String debtType;
    private DebtorAssetBean debtorAsset;

    public String getCreditorDemandType() {
        return creditorDemandType;
    }

    public void setCreditorDemandType(String creditorDemandType) {
        this.creditorDemandType = creditorDemandType;
    }

    public String getLawsuitState() {
        return lawsuitState;
    }

    public void setLawsuitState(String lawsuitState) {
        this.lawsuitState = lawsuitState;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getCreditorDemandExplain() {
        return creditorDemandExplain;
    }

    public void setCreditorDemandExplain(String creditorDemandExplain) {
        this.creditorDemandExplain = creditorDemandExplain;
    }

    public String getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(String debtorId) {
        this.debtorId = debtorId;
    }

    public String getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(String creditorId) {
        this.creditorId = creditorId;
    }

    public String getDebtProperty() {
        return debtProperty;
    }

    public void setDebtProperty(String debtProperty) {
        this.debtProperty = debtProperty;
    }

    public String getCreditorType() {
        return creditorType;
    }

    public void setCreditorType(String creditorType) {
        this.creditorType = creditorType;
    }

    public String getDebtDate() {
        return debtDate;
    }

    public void setDebtDate(String debtDate) {
        this.debtDate = debtDate;
    }

    public String getDebtorType() {
        return debtorType;
    }

    public void setDebtorType(String debtorType) {
        this.debtorType = debtorType;
    }

    public String getDebtNumber() {
        return debtNumber;
    }

    public void setDebtNumber(String debtNumber) {
        this.debtNumber = debtNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(String debtAmount) {
        this.debtAmount = debtAmount;
    }

    public String getDebtType() {
        return debtType;
    }

    public void setDebtType(String debtType) {
        this.debtType = debtType;
    }

    public DebtorAssetBean getDebtorAsset() {
        return debtorAsset;
    }

    public void setDebtorAsset(DebtorAssetBean debtorAsset) {
        this.debtorAsset = debtorAsset;
    }

    public static class DebtorAssetBean extends BaseObservable implements Serializable{
        /**
         * provinceText : 1
         * districtText : 1
         * remarks : 房屋资产变卖
         * putawayState : 0
         * expectedPrice : 110
         * type : 1
         * verifyState : 1
         * evaluatedPrice : 210
         * city : 1
         * id : 2
         * assetType : 1
         * province : 1
         * dealState : 0
         * memberId : 1
         * memberName : 友缘
         * district : 1
         * cityText : 1
         */

        private String provinceText;
        private String districtText;
        private String remarks;
        private String putawayState;
        private String expectedPrice;
        private String type;
        private String verifyState;
        private String evaluatedPrice;
        private String city;
        private String id;
        private String assetType;
        private String province;
        private String dealState;
        private String memberId;
        private String memberName;
        private String district;
        private String cityText;

        public String getProvinceText() {
            return provinceText;
        }

        public void setProvinceText(String provinceText) {
            this.provinceText = provinceText;
        }

        public String getDistrictText() {
            return districtText;
        }

        public void setDistrictText(String districtText) {
            this.districtText = districtText;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getPutawayState() {
            return putawayState;
        }

        public void setPutawayState(String putawayState) {
            this.putawayState = putawayState;
        }

        public String getExpectedPrice() {
            return expectedPrice;
        }

        public void setExpectedPrice(String expectedPrice) {
            this.expectedPrice = expectedPrice;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getDealState() {
            return dealState;
        }

        public void setDealState(String dealState) {
            this.dealState = dealState;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCityText() {
            return cityText;
        }

        public void setCityText(String cityText) {
            this.cityText = cityText;
        }
    }
}
