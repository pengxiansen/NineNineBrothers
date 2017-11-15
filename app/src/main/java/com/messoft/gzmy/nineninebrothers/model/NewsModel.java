package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.HomeBanner;
import com.messoft.gzmy.nineninebrothers.bean.NewsList;
import com.messoft.gzmy.nineninebrothers.http.BaseObserver;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.RxSchedulers;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18 0018.
 * 资讯model 新闻，轮播图等
 */

public class NewsModel {

    /**
     * 新闻
     *
     * @param context
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void getNewsList(Context context, int pageNo, int pageSize, final RequestImpl listener) {
        HttpClient.Builder.getNineServerCommon().getNewsList(pageNo, pageSize)
                .compose(RxSchedulers.<BaseBean<List<NewsList>>>compose())
                .subscribe(new BaseObserver<List<NewsList>>(context, false) {
                    @Override
                    protected void onSuccess(List<NewsList> getNewsLists) {
                        if (getNewsLists != null) {
                            listener.loadSuccess(getNewsLists);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 轮播图
     *
     * @param context
     * @param listener
     */
    public void getBanner(Context context, final RequestImpl listener) {
        //加个参数防止报错
        HttpClient.Builder.getNineServerCommon().getHomeBanner("")
                .compose(RxSchedulers.<BaseBean<List<HomeBanner>>>compose())
                .subscribe(new BaseObserver<List<HomeBanner>>(context, false) {
                    @Override
                    protected void onSuccess(List<HomeBanner> getNewsLists) {
                        if (getNewsLists != null) {
                            listener.loadSuccess(getNewsLists);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }
}
