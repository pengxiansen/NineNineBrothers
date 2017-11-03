package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.bean.AssetKuList;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.JzInfo;
import com.messoft.gzmy.nineninebrothers.bean.JzKuList;
import com.messoft.gzmy.nineninebrothers.bean.QueryDebtOrderTradeList;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonInfo;
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
 * Created by Administrator on 2017/10/31 0031.
 * 解债模块
 */

public class JzModel {

    /**
     * 解债库列表
     */
    public void getJzKuList(Context context, String id, String debtState, String verifyState, int pageNo, int pageSize, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNoEmpty(id)) {
            map.put("id", id);
        }
        if (StringUtils.isNoEmpty(debtState)) {
            //债事状态(0:待审核,1:待接单,2:交易中,3:已完成)
            map.put("debtState", debtState);
        }
        if (StringUtils.isNoEmpty(verifyState)) {
            //债事审核状态(0:待审核,1:审核通过,2:审核驳回)
            map.put("verifyState", verifyState);
        }
        String url = BusinessUtils.getUrl(ConstantsUrl.MASTER_URL + ConstantsUrl.GET_JZ_KU_LIST,
                map, pageNo, pageSize);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().getJzKuList(url)
                .compose(RxSchedulers.<BaseBean<List<JzKuList>>>compose())
                .subscribe(new BaseObserver<List<JzKuList>>(context, false) {
                    @Override
                    protected void onSuccess(List<JzKuList> getNewsLists) {
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
     * 2.2.6  查询债事详细资料
     */
    public void getZsInfo(Context context, String id, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.GET_ZS_INFO,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().getJzInfo(url)
                .compose(RxSchedulers.<BaseBean<JzInfo>>compose())
                .subscribe(new BaseObserver<JzInfo>(context, false) {
                    @Override
                    protected void onSuccess(JzInfo getNewsLists) {
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
     * 2.1.4  查询债事人详情
     */
    public void getZsPersonInfo(Context context, String id, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.GET_ZS_PERSON_INFO,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().getZsPersonInfo(url)
                .compose(RxSchedulers.<BaseBean<ZsPersonInfo>>compose())
                .subscribe(new BaseObserver<ZsPersonInfo>(context, false) {
                    @Override
                    protected void onSuccess(ZsPersonInfo getNewsLists) {
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
     * 2.4.3  查询债事单交易列表
     */
    public void queryDebtOrderTradeList(Context context, String id, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        map.put("debtMatterId", id);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.QUERY_DEBT_ORDER_TRADE_LIST,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().queryDebtOrderTradeList(url)
                .compose(RxSchedulers.<BaseBean<List<QueryDebtOrderTradeList>>>compose())
                .subscribe(new BaseObserver<List<QueryDebtOrderTradeList>>(context, false) {
                    @Override
                    protected void onSuccess(List<QueryDebtOrderTradeList> getNewsLists) {
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
     * 2.4.1  债事接单申请
     */
    public void applyDebtMatterOrder(Context context, String id, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        map.put("debtMatterId", id);
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.APPLY_DEBT_MATTER_ORDER,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().applyDebtMatterOrder(url)
                .compose(RxSchedulers.<BaseBean<Object>>compose())
                .subscribe(new BaseObserver<Object>(context, false) {
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
     * 2.3.4  查询资产库列表
     */
    public void getAssetList(Context context, final RequestImpl listener) {
        Map<String, String> map = new HashMap<>();
        map.put("dealState", "0");
        map.put("verifyState", "1");
        map.put("putawayState", "0");
        String url = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.GET＿ASSET＿LIST,
                map);
        if (!StringUtils.isNoEmpty(url)) {
            return;
        }
        HttpClient.Builder.getNineServer().getAssetList(url)
                .compose(RxSchedulers.<BaseBean<List<AssetKuList>>>compose())
                .subscribe(new BaseObserver<List<AssetKuList>>(context, false) {
                    @Override
                    protected void onSuccess(List<AssetKuList> getNewsLists) {
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
