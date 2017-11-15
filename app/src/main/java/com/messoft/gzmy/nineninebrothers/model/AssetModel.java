package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.bean.AssetProgress;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.GetAssetProgressFileList;
import com.messoft.gzmy.nineninebrothers.bean.QueryAssetDiscussApplyListWithAssetInfo;
import com.messoft.gzmy.nineninebrothers.bean.QueryMyAssetList;
import com.messoft.gzmy.nineninebrothers.bean.UploadDebtMatterProgressFile;
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
 * Created by Administrator on 2017/11/6 0006.
 * 资产
 */

public class AssetModel {
    /**
     * 2.5.1  资产洽谈申请
     */
    public void applyDebtMatterOrder(Context context, String assetId, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("assetId", assetId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().assetDiscussApply(StringUtils.toURLEncoderUTF8(map.toString()))
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
    public void queryAssetProgressListByAssetId(Context context, String assetId, int pageNo, int pageSize, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("assetId", assetId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().queryAssetProgressListByAssetId(StringUtils.toURLEncoderUTF8(map.toString()), pageNo, pageSize)
                .compose(RxSchedulers.<BaseBean<List<AssetProgress>>>compose())
                .subscribe(new BaseObserver<List<AssetProgress>>(context, false) {
                    @Override
                    protected void onSuccess(List<AssetProgress> jzProgresses) {
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
     * 2.6.2  查询资产交易进度文件列表
     */
    public void getAssetProgressFileList(Context context, String assetProgressId, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("assetProgressId", assetProgressId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().getAssetProgressFileList(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<List<GetAssetProgressFileList>>>compose())
                .subscribe(new BaseObserver<List<GetAssetProgressFileList>>(context, false) {
                    @Override
                    protected void onSuccess(List<GetAssetProgressFileList> jzProgresses) {
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
     * 2.6.3  录入资产交易进度
     */
    public void inputAssetProgress(Context context, String assetId, int progressStage, String remarks, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("assetId", assetId);
            map.put("progressStage", progressStage + "");
            if (StringUtils.isNoEmpty(remarks)) {
                map.put("remarks", remarks);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().inputAssetProgress(StringUtils.toURLEncoderUTF8(map.toString()))
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
    public void uploadAssetProgressFile(Context context, String assetProgressId,
                                        String base64Data, String fileSuffix, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("assetProgressId", assetProgressId);
            map.put("base64Data", base64Data + "");
            map.put("fileSuffix", fileSuffix + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().uploadAssetProgressFile(StringUtils.toURLEncoderUTF8(map.toString()))
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
     * 2.5.2  查询资产订单列表
     */
    public void queryAssetDiscussApplyListWithAssetInfo(Context context, String disposeState, int pageNo, int pageSize, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        //处理状态(0:待处理,1:同意,2:不同意,3:已结束)
        try {
            map.put("disposeState", disposeState);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().queryAssetDiscussApplyListWithAssetInfo(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<List<QueryAssetDiscussApplyListWithAssetInfo>>>compose())
                .subscribe(new BaseObserver<List<QueryAssetDiscussApplyListWithAssetInfo>>(context, false) {
                    @Override
                    protected void onSuccess(List<QueryAssetDiscussApplyListWithAssetInfo> jzProgresses) {
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
     * 2.3.6  查询资产管理列表
     */
    public void queryMyAssetList(Context context, String dealState, String verifyState, String putawayState, int pageNo, int pageSize, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("dealState", dealState);
            map.put("verifyState", verifyState);
            map.put("putawayState", putawayState);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().queryMyAssetList(StringUtils.toURLEncoderUTF8(map.toString()), pageNo, pageSize)
                .compose(RxSchedulers.<BaseBean<List<QueryMyAssetList>>>compose())
                .subscribe(new BaseObserver<List<QueryMyAssetList>>(context, false) {
                    @Override
                    protected void onSuccess(List<QueryMyAssetList> jzProgresses) {
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
     * 2.3.6  查询资产管理列表
     */
    public void assetRecord(Context context,
                            String memberId,
                            String type,
                            String assetType,
                            String province,
                            String provinceText,
                            String city,
                            String cityText,
                            String district,
                            String districtText,
                            String street,
                            String streetText,
                            String evaluatedPrice,
                            String expectedPrice,
                            String remarks,
                            final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("memberId", memberId);
            map.put("type", type); //资产性质(0:债事资产,1:代售资产)
            map.put("assetType", assetType); //资产类型(0:房产,1:专利,2:股权,3:货物)
            map.put("province", province);
            map.put("provinceText", provinceText);
            map.put("city", city);
            map.put("cityText", cityText);
            map.put("district", district);
            map.put("districtText", districtText);
            map.put("street", street);
            map.put("streetText", streetText);
            map.put("evaluatedPrice", evaluatedPrice);
            map.put("expectedPrice", expectedPrice);
            if (StringUtils.isNoEmpty(remarks)) {
                map.put("remarks", remarks);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().assetRecord(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserverNoData(context, false) {
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
}
