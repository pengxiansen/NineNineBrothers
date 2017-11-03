package com.messoft.gzmy.nineninebrothers.http;

import com.messoft.gzmy.nineninebrothers.bean.AssetInfoById;
import com.messoft.gzmy.nineninebrothers.bean.AssetInfoFile;
import com.messoft.gzmy.nineninebrothers.bean.AssetKuList;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.HomeBanner;
import com.messoft.gzmy.nineninebrothers.bean.JzInfo;
import com.messoft.gzmy.nineninebrothers.bean.JzKuList;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.bean.LoginPersonInfo;
import com.messoft.gzmy.nineninebrothers.bean.NewsList;
import com.messoft.gzmy.nineninebrothers.bean.QueryDebtOrderTradeList;
import com.messoft.gzmy.nineninebrothers.bean.Street;
import com.messoft.gzmy.nineninebrothers.bean.ZsDataInfoById;
import com.messoft.gzmy.nineninebrothers.bean.ZsDataInfoFileById;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonFileInfo;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 网络请求类，一个接口对应一个方法
 */

public interface HttpClient {

    class Builder {
        public static HttpClient getNineServer() {
            return HttpUtils.getInstance().getNineServer(HttpClient.class);
        }

    }

//    @POST()
//    Call<LoginBean> doLogin(@Url String url);
//
//    /**
//     * 首页轮播图(测试--网易云)
//     */
//    @GET("ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
//    Observable<FrontpageBean> getFrontpage();

    /**
     * 5.8 获取所有商品
     */
//    @FormUrlEncoded
//    @POST("apigetallgoods")
//    Observable<BaseBeans<AllGoodsBean>> getAllGoods(@FieldMap Map<String, String> params);

    /**
     * 首页资讯
     */
    @POST
    Observable<BaseBean<List<NewsList>>> getNewsList(@Url String url);

    /**
     * 首页轮播图
     */
    @POST
    Observable<BaseBean<List<HomeBanner>>> getHomeBanner(@Url String url);

    /**
     * 登录
     */
    @POST
    Observable<BaseBean<Login>> login(@Url String url);

    /**
     * 查询登录人信息
     */
    @POST
    Observable<BaseBean<LoginPersonInfo>> checkLoginPersonInfo(@Url String url);

    /**
     * 注册 (结果同登录一样)
     */
    @POST
    Observable<BaseBean<Login>> register(@Url String url);

    /**
     * 获取验证码
     */
    @POST
    Observable<BaseBean<Object>> getCode(@Url String url);

    /**
     * 校验验证码
     */
    @POST
    Observable<BaseBean<Object>> checkCode(@Url String url);

    /**
     * 修改密码
     */
    @POST
    Observable<BaseBean<Object>> changePsw(@Url String url);

    /**
     * 根据区请求街道
     */
    @POST
    Observable<BaseBean<List<Street>>> searchStreetById(@Url String url);

    /**
     * 解债库，解债师才能看
     */
    @POST
    Observable<BaseBean<List<JzKuList>>> getJzKuList(@Url String url);

    /**
     * 2.2.6  查询债事详细资料
     */
    @POST
    Observable<BaseBean<JzInfo>> getJzInfo(@Url String url);

    /**
     * 2.1.4  查询债事人详情
     */
    @POST
    Observable<BaseBean<ZsPersonInfo>> getZsPersonInfo(@Url String url);

    /**
     * 2.1.5  查询债事人文件列表
     */
    @POST
    Observable<BaseBean<List<ZsPersonFileInfo>>> getZsPersonFileInfo(@Url String url);

    /**
     * 2.2.5  根据id查询债事信息
     */
    @POST
    Observable<BaseBean<ZsDataInfoById>> getZsInfoById(@Url String url);

    /**
     * 2.2.7  查询债事文件列表
     */
    @POST
    Observable<BaseBean<List<ZsDataInfoFileById>>> getZsInfoFileById(@Url String url);

    /**
     * 2.3.3  查询资产详情
     */
    @POST
    Observable<BaseBean<AssetInfoById>> getAssetById(@Url String url);

    /**
     * 2.3.5  查询资产文件列表
     */
    @POST
    Observable<BaseBean<List<AssetInfoFile>>> getAssetInfo(@Url String url);

    /**
     * 2.4.3  查询债事单交易列表
     */
    @POST
    Observable<BaseBean<List<QueryDebtOrderTradeList>>> queryDebtOrderTradeList(@Url String url);

    /**
     * 2.4.1  债事接单申请
     */
    @POST
    Observable<BaseBean<Object>> applyDebtMatterOrder(@Url String url);

    /**
     * 2.4.1  债事接单申请
     */
    @POST
    Observable<BaseBean<List<AssetKuList>>> getAssetList(@Url String url);
}
