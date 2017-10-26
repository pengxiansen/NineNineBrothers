package com.messoft.gzmy.nineninebrothers.app;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class ConstantsUrl {

    //玖玖兄弟主域1
    public static final String MASTER_URL = "http://qz.jq.com/";

    //玖玖兄弟主域2
    public static final String MASTER_URL_COMMON = "http://common.jq.com/";

    //玖玖兄弟主域3
    public static final String MASTER_URL_MEMBER = "http://member.jq.com/";

    //资讯列表
    public static final String GET_NEWS_LIST = "/news/news.do?action=getNewsList&data=";

    //首页轮播图
    public static final String GET_BANNER_LIST = "/image/image.do?action=getImageList&data=";

    //登录
    public static final String LOGIN = "/account/account.do?action=login&data=";

    //注册
    public static final String REGISTER = "/account/account.do?action=reg&data=";

    //获取验证码
    public static final String GET_CODE = "/verificationCode/verificationCode.do?action=getVerificationCode&data=";

    //根据区请求街道
    public static final String SEARCH_STREET_BY_ID = "/area/area.do?action=getAreaList&data=";
}
