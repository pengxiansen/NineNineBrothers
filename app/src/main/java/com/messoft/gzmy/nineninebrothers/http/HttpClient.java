package com.messoft.gzmy.nineninebrothers.http;

import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.bean.NewsList;
import com.messoft.gzmy.nineninebrothers.bean.HomeBanner;
import com.messoft.gzmy.nineninebrothers.bean.Street;

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
     * 根据区请求街道
     */
    @POST
    Observable<BaseBean<List<Street>>> searchStreetById(@Url String url);
}
