package com.messoft.gzmy.nineninebrothers.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/27 0027.
 * 保存个人信息（暂时用来注册成功后通知登录页）
 */

public class PersonInfo extends BaseObservable implements Serializable{
    private String account;
    private String password;

    public PersonInfo(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
