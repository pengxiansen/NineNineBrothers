package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.bean.AssetKuList;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.GetDebtMatterProgressFileList;
import com.messoft.gzmy.nineninebrothers.bean.JzInfo;
import com.messoft.gzmy.nineninebrothers.bean.JzKuList;
import com.messoft.gzmy.nineninebrothers.bean.JzPersonList;
import com.messoft.gzmy.nineninebrothers.bean.JzProgress;
import com.messoft.gzmy.nineninebrothers.bean.QueryDebtOrderTradeList;
import com.messoft.gzmy.nineninebrothers.bean.UploadDebtMatterProgressFile;
import com.messoft.gzmy.nineninebrothers.bean.UploadFileHasData;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonInfo;
import com.messoft.gzmy.nineninebrothers.http.BaseObserver;
import com.messoft.gzmy.nineninebrothers.http.BaseObserverNoData;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.RxSchedulers;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31 0031.
 * 解债模块
 */

public class JzModel {

    /**
     * 解债库列表
     */
    public void getJzKuList(Context context, String id, String debtState, String verifyState, int pageNo, int pageSize, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getJzKuList(StringUtils.toURLEncoderUTF8(map.toString()), pageNo, pageSize)
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
     * 2.4.2  查询当前用户角色的债事单列表
     */
    public void queryRoleDebtOrderList(Context context, String state, String verifyState, String debtState, int pageNo, int pageSize, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            if (StringUtils.isNoEmpty(state)) {
                map.put("state", state);
            }
            if (StringUtils.isNoEmpty(verifyState)) {
                //债事审核状态(0:待审核,1:审核通过,2:审核驳回)
                map.put("verifyState", verifyState);
            }

            if (StringUtils.isNoEmpty(debtState)) {
                map.put("debtState", debtState);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getJzKuList(StringUtils.toURLEncoderUTF8(map.toString()), pageNo, pageSize)
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
        JSONObject map = new JSONObject();
        try {
            map.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getJzInfo(StringUtils.toURLEncoderUTF8(map.toString()))
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
        JSONObject map = new JSONObject();
        try {
            map.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getZsPersonInfo(StringUtils.toURLEncoderUTF8(map.toString()))
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
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().queryDebtOrderTradeList(StringUtils.toURLEncoderUTF8(map.toString()))
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
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().applyDebtMatterOrder(StringUtils.toURLEncoderUTF8(map.toString()))
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
     * 2.3.4  查询资产库列表
     */
    public void getAssetList(Context context, String assetType, String remarks,
                             String province, String city, String district, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("dealState", "0");
            map.put("verifyState", "1");
            map.put("putawayState", "0");
            if (StringUtils.isNoEmpty(assetType)) {
                map.put("assetType", assetType);
            }
            if (StringUtils.isNoEmpty(remarks)) {
                map.put("remarks", remarks);
            }
            if (StringUtils.isNoEmpty(province)) {
                map.put("province", province);
            }
            if (StringUtils.isNoEmpty(city)) {
                map.put("city", city);
            }
            if (StringUtils.isNoEmpty(district)) {
                map.put("district", district);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getAssetList(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<List<AssetKuList>>>compose())
                .subscribe(new BaseObserver<List<AssetKuList>>(context, true) {
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

    /**
     * 2.7.1  查询债事交易进度列表
     */
    public void queryDebtMatterProgressInfoList(Context context, String id, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().queryDebtMatterProgressInfoList(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<List<JzProgress>>>compose())
                .subscribe(new BaseObserver<List<JzProgress>>(context, false) {
                    @Override
                    protected void onSuccess(List<JzProgress> jzProgresses) {
                        if (jzProgresses != null) {
                            listener.loadSuccess(jzProgresses);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.7.1  查询债事交易进度列表
     */
    public void queryDebtMatterProgressInfoList(Context context, String id, int pageNo, int pageSize, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().queryDebtMatterProgressInfoList(StringUtils.toURLEncoderUTF8(map.toString()), pageNo, pageSize)
                .compose(RxSchedulers.<BaseBean<List<JzProgress>>>compose())
                .subscribe(new BaseObserver<List<JzProgress>>(context, false) {
                    @Override
                    protected void onSuccess(List<JzProgress> jzProgresses) {
                        if (jzProgresses != null) {
                            listener.loadSuccess(jzProgresses);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.7.2  查询债事交易进度文件列表
     */
    public void getDebtMatterProgressFileList(Context context, String id, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterProgressId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getDebtMatterProgressFileList(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<List<GetDebtMatterProgressFileList>>>compose())
                .subscribe(new BaseObserver<List<GetDebtMatterProgressFileList>>(context, false) {
                    @Override
                    protected void onSuccess(List<GetDebtMatterProgressFileList> jzProgresses) {
                        if (jzProgresses != null) {
                            listener.loadSuccess(jzProgresses);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.7.3  录入债事交易进度
     */
    public void inputDebtMatterProgress(Context context, String debtMatterId, int debtStage, String remarks, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterId", debtMatterId);
            map.put("debtStage", debtStage + "");
            if (StringUtils.isNoEmpty(remarks)) {
                map.put("remarks", remarks);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().inputDebtMatterProgress(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserver(context, true) {
                    @Override
                    protected void onSuccess(Object jzProgresses) {
                        if (jzProgresses != null) {
                            listener.loadSuccess(jzProgresses);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.7.4  债事交易进度文件上传
     */
    public void uploadDebtMatterProgressFile(Context context, String debtMatterProgressId,
                                             String base64Data, String fileSuffix, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterProgressId", debtMatterProgressId);
            map.put("base64Data", base64Data + "");
            map.put("fileSuffix", fileSuffix + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().uploadDebtMatterProgressFile(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<UploadDebtMatterProgressFile>>compose())
                .subscribe(new BaseObserver<UploadDebtMatterProgressFile>(context, false) {
                    @Override
                    protected void onSuccess(UploadDebtMatterProgressFile uploadDebtMatterProgressFile) {
                        if (uploadDebtMatterProgressFile != null) {
                            listener.loadSuccess(uploadDebtMatterProgressFile);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });

    }

    /**
     * 2.1.1  债事人备案
     */
    public void debtMatterPersonRecord(Context context,
                                       int type,
                                       String organizationCode,
                                       String name,
                                       String legalperson,
                                       String idCard,
                                       String province,
                                       String provinceText,
                                       String city,
                                       String cityText,
                                       String district,
                                       String districtText,
                                       String street,
                                       String streetText,
                                       String industry,
                                       String mobile,
                                       String registeredCapital,
                                       String email,
                                       String wechat,
                                       String qq,
                                       final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("type", type);//债事人类型(0:企业,1:个人)
            if (StringUtils.isNoEmpty(organizationCode)) {
                map.put("organizationCode", organizationCode);
            }
            map.put("name", name);
            if (StringUtils.isNoEmpty(legalperson)) {
                map.put("legalperson", legalperson);
            }
            map.put("idCard", idCard);
            map.put("province", province);
            map.put("provinceText", provinceText);
            map.put("city", city);
            map.put("cityText", cityText);
            map.put("district", district);
            map.put("districtText", districtText);
            map.put("street", street);
            map.put("streetText", streetText);
            if (StringUtils.isNoEmpty(industry)) {
                map.put("industry", industry);
            }
            map.put("mobile", mobile);
            if (StringUtils.isNoEmpty(registeredCapital)) {
                map.put("registeredCapital", registeredCapital);
            }
            map.put("email", email);
            map.put("wechat", wechat);
            map.put("qq", qq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().debtMatterPersonRecord(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserverNoData(context, true) {
                    @Override
                    protected void onSuccess(Object uploadDebtMatterProgressFile) {
                        if (uploadDebtMatterProgressFile != null) {
                            listener.loadSuccess(uploadDebtMatterProgressFile);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });

    }

    /**
     * 2.1.2  债事人文件上传
     */
    public void uploadDebtMatterPersonFile(Context context, String debtMatterPersonId,
                                           String base64Data, String fileSuffix, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterPersonId", debtMatterPersonId);
            map.put("base64Data", base64Data);
            map.put("fileSuffix", fileSuffix);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().uploadDebtMatterPersonFile(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<UploadFileHasData>>compose())
                .subscribe(new BaseObserver<UploadFileHasData>(context, false) {
                    @Override
                    protected void onSuccess(UploadFileHasData uploadDebtMatterProgressFile) {
                        if (uploadDebtMatterProgressFile != null) {
                            listener.loadSuccess(uploadDebtMatterProgressFile);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });

    }

    /**
     * 2.2.2  债事文件上传
     */
    public void uploadDebtMatterFile(Context context, String debtMatterId,
                                           String base64Data, String fileSuffix, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("debtMatterId", debtMatterId);
            map.put("base64Data", base64Data);
            map.put("fileSuffix", fileSuffix);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().uploadDebtMatterFile(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<UploadFileHasData>>compose())
                .subscribe(new BaseObserver<UploadFileHasData>(context, false) {
                    @Override
                    protected void onSuccess(UploadFileHasData uploadDebtMatterProgressFile) {
                        if (uploadDebtMatterProgressFile != null) {
                            listener.loadSuccess(uploadDebtMatterProgressFile);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });

    }

    /**
     * 2.1.3  查询债事人列表
     */
    public void getDebtMatterPersonList(Context context, String idCard, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("idCard", idCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getDebtMatterPersonList(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<List<JzPersonList>>>compose())
                .subscribe(new BaseObserver<List<JzPersonList>>(context, true, "查询中") {
                    @Override
                    protected void onSuccess(List<JzPersonList> uploadDebtMatterProgressFile) {
                        if (uploadDebtMatterProgressFile != null) {
                            listener.loadSuccess(uploadDebtMatterProgressFile);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });

    }

    /**
     * 2.1.3  查询债事人列表
     */
    public void debtMatterRecord(Context context,
                                 String creditorId,
                                 String debtorId,
                                 String debtType,
                                 String debtProperty,
                                 String lawsuitState,
                                 String debtAmount,
                                 String containInterest,
                                 String debtDate,
                                 String question1,
                                 String question2,
                                 String question3,
                                 String question4,
                                 String question5,
                                 String question6,
                                 String question7,
                                 final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("creditorId", creditorId);
            map.put("debtorId", debtorId);
            map.put("debtType", debtType);
            map.put("debtProperty", debtProperty);
            map.put("lawsuitState", lawsuitState);
            map.put("debtAmount", debtAmount);
            map.put("containInterest", containInterest);
            map.put("debtDate", debtDate);
            map.put("question1", question1);
            map.put("question2", question2);
            map.put("question3", question3);
            map.put("question4", question4);
            map.put("question5", question5);
            map.put("question6", question6);
            map.put("question7", question7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().debtMatterRecord(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserverNoData(context, true, "备案中...") {
                    @Override
                    protected void onSuccess(Object uploadDebtMatterProgressFile) {
                        if (uploadDebtMatterProgressFile != null) {
                            listener.loadSuccess(uploadDebtMatterProgressFile);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });

    }
}
