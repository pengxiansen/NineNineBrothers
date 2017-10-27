package com.messoft.gzmy.nineninebrothers.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class Login implements Serializable{

    /**
     * otherDataPvCreaterIdMap : {}
     * postIds : []
     * accessToken : 8ce820754e9330fc371bff8b5315232c
     * CNs : []
     * orgExpandMap : {}
     * expiresIn : 864000000
     * domainIds : []
     * OUs : []
     * id : 211
     * userExpandMap : {"idCard":"","email":"","referralCode":"ZjOtJK11211","fatherReferralCode":"","mobile":"17688700264"}
     * name :
     * postTypeIds : []
     * secret : T5XC1/Uw1BEnPLXLg91zn/M6t9i5jcxdaKgBfJu+YAGhy/SKHwFV44mHrN7VSP+S4Sj44as8/9KPk7SRK4BVaA==
     * account : 17688700264
     * apiRole :
     */

    private String accessToken;
    private String expiresIn;
    private String id;
    private UserExpandMapBean userExpandMap;
    private String name;
    private String secret;
    private String account;
    private String apiRole;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserExpandMapBean getUserExpandMap() {
        return userExpandMap;
    }

    public void setUserExpandMap(UserExpandMapBean userExpandMap) {
        this.userExpandMap = userExpandMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApiRole() {
        return apiRole;
    }

    public void setApiRole(String apiRole) {
        this.apiRole = apiRole;
    }

    public static class UserExpandMapBean implements Serializable{
        private String idCard;
        private String email;
        private String referralCode;
        private String fatherReferralCode;
        private String mobile;

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }

        public String getFatherReferralCode() {
            return fatherReferralCode;
        }

        public void setFatherReferralCode(String fatherReferralCode) {
            this.fatherReferralCode = fatherReferralCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
