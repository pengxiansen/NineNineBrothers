package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.base.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.HomeBanner;
import com.messoft.gzmy.nineninebrothers.bean.NewsList;
import com.messoft.gzmy.nineninebrothers.http.BaseObserver;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.RxSchedulers;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18 0018.
 * 资讯model 新闻，轮播图等
 */

public class NewsModel {

    /**
     * 新闻
     * @param context
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void getNewsList(Context context, int pageNo, int pageSize, final RequestImpl listener) {
        String url = BusinessUtils.getUrl(ConstantsUrl.MASTER_URL_COMMON + ConstantsUrl.GET_NEWS_LIST,
                null, pageNo, pageSize);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        DebugUtil.debug("okhttp:", "地址："+url);
        HttpClient.Builder.getNineServer().getNewsList(url)
                .compose(RxSchedulers.<BaseBean<List<NewsList>>>compose())
                .subscribe(new BaseObserver<List<NewsList>>(context,false) {
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
     * @param context
     * @param listener
     */
    public void getBanner(Context context, final RequestImpl listener) {
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL_COMMON + ConstantsUrl.GET_BANNER_LIST,
                null);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().getHomeBanner(url)
                .compose(RxSchedulers.<BaseBean<List<HomeBanner>>>compose())
                .subscribe(new BaseObserver<List<HomeBanner>>(context,false) {
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
