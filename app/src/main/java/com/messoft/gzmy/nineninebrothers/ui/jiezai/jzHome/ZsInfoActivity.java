package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.JzInfo;
import com.messoft.gzmy.nineninebrothers.bean.QueryDebtOrderTradeList;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityZsInfoBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.List;

public class ZsInfoActivity extends BaseActivity<ActivityZsInfoBinding> {

    private String mType;// //type  0:解债师 1:高级合伙人
    private String mId;//债事id
    private JzModel mJzModel;
    private JzInfo mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zs_info);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("债事详情");

        mJzModel = new JzModel();

        if (getIntent() != null && getIntent().getBundleExtra("b") != null) {
            //type  0:解债师 1:高级合伙人
            mType = getIntent().getBundleExtra("b").getString("type");
            mId = getIntent().getBundleExtra("b").getString("id");

            if (mType != null && mType.equals("0")) {
                //解债师不能查看详情
                bindingView.tvZqInfo.setVisibility(View.VISIBLE);
                bindingView.tvZwInfo.setVisibility(View.VISIBLE);
                bindingView.tvNext.setVisibility(View.VISIBLE);
            } else if(mType != null && mType.equals("1")) {
                bindingView.tvNext.setVisibility(View.GONE);
            }

            if (StringUtils.isNoEmpty(mId)) {
                //查信息
                loadInfo();

                //查询债事单交易列表，用于查询指定债事是否在交易中，有数据返回说明正在交易
                queryDebtOrderTradeList(mId);
            }
        }

    }

    private void initListener() {
        //债(权)事人详情
        bindingView.tvZqInfo.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mData != null && mData.getCreditorId() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mData.getCreditorId());
                    SysUtils.startActivity(ZsInfoActivity.this, JzPersonInfoActivity.class, bundle);
                }
            }
        });
        //债(务)事人详情
        bindingView.tvZwInfo.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mData != null && mData.getDebtorId() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mData.getDebtorId());
                    SysUtils.startActivity(ZsInfoActivity.this, JzPersonInfoActivity.class, bundle);
                }
            }
        });
        //债事资料
        bindingView.llZsData.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mId != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mId);
                    SysUtils.startActivity(ZsInfoActivity.this, ZsDataInfoByIdActivity.class, bundle);
                }
            }
        });
        //债务人资产
        bindingView.rlZwAsset.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mData != null && mData.getDebtorAsset() != null &&
                        mData.getDebtorAsset().getId() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mData.getDebtorAsset().getId());
                    SysUtils.startActivity(ZsInfoActivity.this, ZwAssetInfoByIdActivity.class, bundle);
                }
            }
        });

        //接单
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (StringUtils.isNoEmpty(mId)) {
                    applyDebtMatterOrder();
                }
            }
        });
    }

    private void loadInfo() {
        mJzModel.getZsInfo(ZsInfoActivity.this, mId, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                mData = (JzInfo) object;
                setData(mData);
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                showContentView();
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    /**
     * 展示数据
     *
     * @param data
     */
    private void setData(JzInfo data) {

        bindingView.tvBianhao.setText("编号：" + data.getDebtNumber());

        bindingView.tvZq.setText("债权人：" + data.getCreditorName());
        bindingView.tvGeren.setText(BusinessUtils.debtProperty(data.getCreditorType()));
        bindingView.tvZw.setText("债务人：" + data.getDebtorName());
        bindingView.tvGeren.setText(BusinessUtils.debtProperty(data.getDebtorType()));

        bindingView.tvTime.setText("发生时间：" + data.getDebtDate());
        bindingView.tvZsType.setText("债事类型：" + BusinessUtils.debtorType(data.getDebtType()));
        bindingView.tvZsProperty.setText("债事性质：" + BusinessUtils.debtProperty(data.getDebtorType()));
        bindingView.tvZsMoney.setText("债事金额：" + data.getDebtAmount());
        bindingView.tvSusong.setText("诉讼情况：" + BusinessUtils.lawsuitState(data.getLawsuitState()));

        if (data.getDebtorAsset() != null) {
            JzInfo.DebtorAssetBean debtorAsset = data.getDebtorAsset();
            bindingView.tvZcType.setText("资产类型：" + BusinessUtils.assetType(debtorAsset.getAssetType()));
            bindingView.tvZcAddress.setText("资产地址：" + debtorAsset.getProvinceText() +
                    debtorAsset.getCityText() + debtorAsset.getDistrictText());
            bindingView.tvAssessPrice.setText("评估价格：" + debtorAsset.getEvaluatedPrice());
            bindingView.tvExpectPrice.setText("期望价格：" + debtorAsset.getExpectedPrice());
        }

        bindingView.tvZwNeed.setText(data.getCreditorDemandExplain());
    }

    private void queryDebtOrderTradeList(String id) {
        mJzModel.queryDebtOrderTradeList(ZsInfoActivity.this, id, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                List<QueryDebtOrderTradeList> data = (List<QueryDebtOrderTradeList>) object;
                if (data == null) {
                    return;
                }
                if (data.size() > 0) {
                    //有数据返回说明正在交易
                    bindingView.tvNext.setVisibility(View.VISIBLE);
                    bindingView.tvNext.setText("正在交易中");
                    bindingView.tvNext.setEnabled(false);
                } else {
                    bindingView.tvNext.setVisibility(View.VISIBLE);
                    bindingView.tvNext.setText("接单");
                    bindingView.tvNext.setEnabled(true);
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    /**
     * 接单
     */
    private void applyDebtMatterOrder() {
        mJzModel.applyDebtMatterOrder(ZsInfoActivity.this, mId, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                //接单成功
                bindingView.tvNext.setVisibility(View.VISIBLE);
                bindingView.tvNext.setText("正在交易中");
                bindingView.tvNext.setEnabled(false);
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
                bindingView.tvNext.setVisibility(View.VISIBLE);
                bindingView.tvNext.setText("接单");
                bindingView.tvNext.setEnabled(true);
            }
        });
    }
}
