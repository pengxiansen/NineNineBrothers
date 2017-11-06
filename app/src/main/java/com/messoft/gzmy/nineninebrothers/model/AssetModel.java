package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.AssetProgress;
import com.messoft.gzmy.nineninebrothers.http.BaseObserver;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.RxSchedulers;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/6 0006.
 * 资产
 */

public class AssetModel {
    /**
     * 2.5.1  资产洽谈申请
     */
    public void applyDebtMatterOrder(Context context, String assetId, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        map.put("assetId", assetId);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.ASSET_DISCUSS_APPLY,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().assetDiscussApply(url)
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserver(context, false) {
                    @Override
                    protected void onSuccess(Object getNewsLists) {
                        listener.loadSuccess(getNewsLists);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.6.1  查询资产交易进度列表
     */
    public void queryAssetProgressListByAssetId(Context context, String id, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        map.put("assetId", id);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.QUERY_ASSET_PROGRESS_LIST_BY_ASSETID,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().queryAssetProgressListByAssetId(url)
                .compose(RxSchedulers.<BaseBean<List<AssetProgress>>>compose())
                .subscribe(new BaseObserver<List<AssetProgress>>(context, false) {
                    @Override
                    protected void onSuccess(List<AssetProgress> jzProgresses) {
                        if (jzProgresses!=null) {
                            listener.loadSuccess(jzProgresses);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }
}
