package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.bean.Street;
import com.messoft.gzmy.nineninebrothers.http.BaseObserver;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.RxSchedulers;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/24 0024.
 * 登录注册模块
 */

public class LoginModel {

    /**
     * 登录
     */
    public void login(Context context, String account, String password, final RequestImpl listener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("account", account);
        map.put("password", password);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL_MEMBER + ConstantsUrl.LOGIN,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        DebugUtil.debug("okhttp:", "登录地址：" + url);
        HttpClient.Builder.getNineServer().login(url)
                .compose(RxSchedulers.<BaseBean<Login>>compose())
                .subscribe(new BaseObserver<Login>(context, false) {
                    @Override
                    protected void onSuccess(Login login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 注册
     */
    public void register(Context context,
                         String mobile,
                         String code,
                         String password,
                         String fatherReferralCode,
                         String province,
                         String provinceText,
                         String city,
                         String cityText,
                         String district,
                         String districtText,
                         String street, //街道非必填
                         String streetText,
                         final RequestImpl listener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("password", password);
        map.put("fatherReferralCode", fatherReferralCode);
        map.put("province", province);
        map.put("provinceText", provinceText);
        map.put("city", city);
        map.put("cityText", cityText);
        map.put("district", district);
        map.put("districtText", districtText);
        if (StringUtils.isNoEmpty(street) && StringUtils.isNoEmpty(streetText)) {
            map.put("street", street);
            map.put("streetText", streetText);
        }
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL_MEMBER + ConstantsUrl.REGISTER,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
//        DebugUtil.debug("okhttp:", "注册地址：" + url);
        HttpClient.Builder.getNineServer().register(url)
                .compose(RxSchedulers.<BaseBean<Login>>compose())
                .subscribe(new BaseObserver<Login>(context, true) {
                    @Override
                    protected void onSuccess(Login login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 根据区请求街道
     */
    public void searchStreetById(Context context, String parentId, final RequestImpl listener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("parentId", parentId);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL_COMMON + ConstantsUrl.SEARCH_STREET_BY_ID,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
//        DebugUtil.debug("okhttp:", "登录地址：" + url);
        HttpClient.Builder.getNineServer().searchStreetById(url)
                .compose(RxSchedulers.<BaseBean<List<Street>>>compose())
                .subscribe(new BaseObserver<List<Street>>(context, true) {
                    @Override
                    protected void onSuccess(List<Street> login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 获取验证码 0.注册、1.修改密码2.微信授权登录验证
     */
    public void getCode(Context context, String account, String type,final RequestImpl listener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("account", account);
        map.put("type", type);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL_MEMBER + ConstantsUrl.GET_CODE,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        DebugUtil.debug("okhttp:", "获取验证码地址：" + url);
        HttpClient.Builder.getNineServer().getCode(url)
                .compose(RxSchedulers.<BaseBean<Object>>compose())
                .subscribe(new BaseObserver<Object>(context, true) {
                    @Override
                    protected void onSuccess(Object login) {
                        listener.loadSuccess(login);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }
}
