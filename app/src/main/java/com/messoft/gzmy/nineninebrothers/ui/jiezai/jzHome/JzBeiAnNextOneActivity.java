package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.FlowTagAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzBeiAnNextOneBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @作者 Administrator
 * 解债备案下一步
 * @创建日期 2017/10/30 0030 14:52
 */

public class JzBeiAnNextOneActivity extends BaseActivity<ActivityJzBeiAnNextOneBinding> {

    private String mCreditorId;
    private String mDebtorId;
    private String mDebtType;
    private String mDebtProperty;
    private String mLawsuitState;
    private String mDebtAmount;
    private String mContainInterest;
    private String mDebtDate;

    private int question1 = -1;
    private int question2 = -1;
    private int question3 = -1;
    private int question4 = -1;
    private String question5;
    private String question6;
    private String question7;
    private List<String> mListId;
    private List<String> mListBill;
    private List<String> mListData;

    private JzModel mJzModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_bei_an_next_one);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("解债备案");
        showContentView();
        mJzModel = new JzModel();

        if (getIntent() != null && getIntent().getBundleExtra("b") != null) {
            Bundle bundle = getIntent().getBundleExtra("b");
            mCreditorId = bundle.getString("creditorId");
            mDebtorId = bundle.getString("debtorId");
            mDebtType = bundle.getString("debtType");
            mDebtProperty = bundle.getString("debtProperty");
            mLawsuitState = bundle.getString("lawsuitState");
            mDebtAmount = bundle.getString("debtAmount");
            mContainInterest = bundle.getString("containInterest");
            mDebtDate = bundle.getString("debtDate");
        }

        initFlow();
    }

    private void initListener() {
        //问题1
        bindingView.rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_11:
                        question1 = 0;
                        break;
                    case R.id.rb_12:
                        question1 = 1;
                        break;
                }
            }
        });

        //问题2
        bindingView.rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_21:
                        question2 = 0;
                        break;
                    case R.id.rb_22:
                        question2 = 1;
                        break;
                }
            }
        });

        //问题3
        bindingView.rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_31:
                        question3 = 0;
                        break;
                    case R.id.rb_32:
                        question3 = 1;
                        break;
                }
            }
        });

        //问题4
        bindingView.rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_41:
                        question4 = 0;
                        break;
                    case R.id.rb_42:
                        question4 = 1;
                        break;
                }
            }
        });


        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //下一步
                goNext();
            }
        });
    }

    private void goNext() {
        if (question1 == -1) {
            ToastUtil.showToast("是否有债务人偿还欠款及利息的证据");
            return;
        }
        if (question2 == -1) {
            ToastUtil.showToast("是否有担保或者抵押的证明材料");
            return;
        }
        if (question3 == -1) {
            ToastUtil.showToast("是否有债务转让证明资料");
            return;
        }
        if (question4 == -1) {
            ToastUtil.showToast("是否有担保或者抵押的证明材料");
            return;
        }


        Set<Integer> setIds = bindingView.flowId.getSelectedList();
        question5 = BusinessUtils.spliceQuestion(setIds, mListId);
        Set<Integer> setBills = bindingView.flowBill.getSelectedList();
        question6 = BusinessUtils.spliceQuestion(setBills, mListBill);
        Set<Integer> setDatas = bindingView.flowData.getSelectedList();
        question7 = BusinessUtils.spliceQuestion(setDatas, mListData);

        DebugUtil.debug("JzBeiAnNextOneActivity", "question5:" + question5 +
                "---question6:" + question6 + "---question7:" + question7);

        mJzModel.debtMatterRecord(JzBeiAnNextOneActivity.this,
                mCreditorId,
                mDebtorId,
                mDebtType,
                mDebtProperty,
                mLawsuitState,
                mDebtAmount,
                mContainInterest,
                mDebtDate,
                question1 + "",
                question2 + "",
                question3 + "",
                question4 + "",
                question5,
                question6,
                question7,
                new RequestImpl() {
                    @Override
                    public void loadSuccess(Object object) {
                        ToastUtil.showToast("备案成功");
                        BaseBean bean = (BaseBean) object;
                        if (bean != null) {
                            String id = bean.getId();
                            //下一步
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "1");
                            bundle.putString("id", id);
                            //把资产所有人id（债事备案录入的资产传递债务人的memberId）传过去
                            bundle.putString("debtorId",mDebtorId);
                            SysUtils.startActivity(JzBeiAnNextOneActivity.this, JzUploadImgsActivity.class, bundle);
                        }
                    }

                    @Override
                    public void loadFailed(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });

    }

    private void initFlow() {

        //身份证明
        mListId = new ArrayList<>();
        mListId.add("营业执照副本复印件");
        mListId.add("组织机构代码复印件");
        mListId.add("法人身份证复印件");
        FlowTagAdapter flowIdlAdapter = new FlowTagAdapter(JzBeiAnNextOneActivity.this, bindingView.flowId,
                mListId);
        bindingView.flowId.setAdapter(flowIdlAdapter);

        //票据证明
        mListBill = new ArrayList<>();
        mListBill.add("合同");
        mListBill.add("收据");
        mListBill.add("借据");
        mListBill.add("欠据");
        mListBill.add("协议");
        mListBill.add("电报");
        mListBill.add("提货单");
        mListBill.add("仓单发票");
        mListBill.add("其他");
        FlowTagAdapter flowBillAdapter = new FlowTagAdapter(JzBeiAnNextOneActivity.this, bindingView.flowBill,
                mListBill);
        bindingView.flowBill.setAdapter(flowBillAdapter);

        //电子发票证明
        mListData = new ArrayList<>();
        mListData.add("微信");
        mListData.add("QQ");
        mListData.add("短信");
        mListData.add("微博");
        mListData.add("其他");
        FlowTagAdapter flowDataAdapter = new FlowTagAdapter(JzBeiAnNextOneActivity.this, bindingView.flowData,
                mListData);
        bindingView.flowData.setAdapter(flowDataAdapter);
    }
}
