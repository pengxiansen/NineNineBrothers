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

    //图片主域
    public static final String MASTER_URL_IMG = "http://www.jq.com/upload/";

    //资讯列表
    public static final String GET_NEWS_LIST = "/news/news.do?action=getNewsList&data=";

    //首页轮播图
    public static final String GET_BANNER_LIST = "/image/image.do?action=getImageList&data=";

    //登录
    public static final String LOGIN = "/account/account.do?action=login&data=";

    //注册
    public static final String REGISTER = "/account/account.do?action=reg&data=";

    //查询登录人信息
    public static final String CHECK_LOGIN_PERSON_INFO = "/member/member.do?action=getLoginMemberInfo&data=";

    //获取验证码
    public static final String GET_CODE = "/verificationCode/verificationCode.do?action=getVerificationCode&data=";

    //获取验证码
    public static final String CHECK_CODE = "/verificationCode/verificationCode.do?action=validateVerificationCode&data=";

    //修改密码
    public static final String CHANGE_PSW = "/account/account.do?action=modifyPassword&data=";

    //根据区请求街道
    public static final String SEARCH_STREET_BY_ID = "/area/area.do?action=getAreaList&data=";

    //2.2.4  查询债事库列表
    public static final String GET_JZ_KU_LIST = "/debtMatter/debtMatter.do?action=queryDebtMatterListWithAsset&data=";

    //2.4.3  查询债事单交易列表
    public static final String QUERY_DEBT_ORDER_TRADE_LIST = "/debtMatterOrder/debtMatterOrder.do?action=queryDebtOrderTradeList&data=";

    //2.4.1  债事接单申请
    public static final String APPLY_DEBT_MATTER_ORDER = "/debtMatterOrder/debtMatterOrder.do?action=applyDebtMatterOrder&data=";

    //2.2.6  查询债事详细资料
    public static final String GET_ZS_INFO = "/debtMatter/debtMatter.do?action=queryDebtMatterInfo&data=";

    //2.1.4  查询债事人详情
    public static final String GET_ZS_PERSON_INFO = "/debtMatterPerson/debtMatterPerson.do?action=queryDebtMatterPersonInfoById&data=";

    //2.1.5  查询债事人文件列表
    public static final String GET_ZS_PERSON_FILE_INFO = "/debtMatterPersonFile/debtMatterPersonFile.do?action=getDebtMatterPersonFileList&data=";

    //2.2.5  根据id查询债事信息
    public static final String GET_ZS_INFO_BY_ID = "/debtMatter/debtMatter.do?action=getDebtMatterById&data=";

    //2.2.5  根据id查询债事信息
    public static final String GET_ZS_INFO_FIlE_BY_ID = "/debtMatterFile/debtMatterFile.do?action=getDebtMatterFileList&data=";

    //2.3.3  查询资产详情
    public static final String GET_ASSET_BY_ID = "/asset/asset.do?action=getAssetById&data=";

    //2.3.5  查询资产文件列表
    public static final String GET_ASSET_FILE_BY_ID = "/assetFile/assetFile.do?action=getAssetFileList&data=";

    //2.3.4  查询资产库列表
    public static final String GET＿ASSET＿LIST = "/asset/asset.do?action=getAssetList&data={";
}
