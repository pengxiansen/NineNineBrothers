package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzMy;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.app.Constants;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.bean.LoginPersonInfo;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJzMyBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.ui.my.MyOrderActivity;
import com.messoft.gzmy.nineninebrothers.ui.my.MyPointsActivity;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class JzMyFragment extends BaseFragment<FragmentJzMyBinding> {

    // 初始化完成后加载数据
    private boolean mIsPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean mIsFirst = true;

    private LoginModel mLoginModel;
    private String mRoleId;

    @Override
    protected int setContent() {
        return R.layout.fragment_jz_my;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSetting();
        initListener();
    }

    private void initSetting() {
        showContentView();
        mLoginModel = new LoginModel();
        Glide.with(bindingView.ivHeadBg.getContext())
                .load(R.drawable.bg_monkey_king)
                .crossFade(500)
                .into(bindingView.ivHeadBg);
        if (BusinessUtils.getLoginPersonInfo() != null) {
            mRoleId = BusinessUtils.getLoginPersonInfo().getRoleId();
            if (mRoleId.equals("0") || mRoleId.equals("1")) {
                //普通会员 跳转跟高级合伙人一样
                bindingView.rlMyPoints.setVisibility(View.VISIBLE);
                bindingView.rlZsOrder.setVisibility(View.GONE);
                bindingView.lineZs.setVisibility(View.GONE);
            } else if (mRoleId.equals("2")) {
                //解债师
                bindingView.rlMyPoints.setVisibility(View.GONE);
                bindingView.rlZsOrder.setVisibility(View.VISIBLE);
                bindingView.linePoints.setVisibility(View.GONE);
            }
        }
        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initListener() {
        bindingView.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysUtils.startActivity(getActivity(), PersonInfoActivity.class);
            }
        });
        bindingView.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadInfo();
                        bindingView.refreshLayout.finishRefresh();
                    }
                }, 1000);
            }

        });
        //我的订单
        bindingView.rlMyOrder.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(getActivity(), MyOrderActivity.class);
            }
        });
        //积分
        bindingView.rlMyPoints.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(getActivity(), MyPointsActivity.class);
            }
        });

        //资产订单
        bindingView.rlAssetOrder.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(getActivity(), AssetOrderActivity.class);
            }
        });
        //债事订单
        bindingView.rlZsOrder.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(getActivity(), AssetOrderActivity.class);
            }
        });
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadInfo();
    }

    private void loadInfo() {
        mLoginModel.checkLoginPersonInfo(getActivity(), new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                LoginPersonInfo data = (LoginPersonInfo) object;
                if (data != null) {
                    mIsFirst = false;
                    SPUtils.putObject("loginPersonInfo", data);
                    SPUtils.putString("roleId", data.getRoleId());

                    bindingView.tvName.setText(data.getName());
                    //0.普通会员 1.高级合伙人 2.解债师
                    if (data.getRoleId().equals("0")) {
                        bindingView.tvMemberLevel.setText("普通会员");
                    } else if (data.getRoleId().equals("1")) {
                        bindingView.tvMemberLevel.setText("高级合伙人");
                    } else if (data.getRoleId().equals("2")) {
                        bindingView.tvMemberLevel.setText("解债师");
                    }
                    Glide.with(bindingView.ivHead.getContext())
                            .load(Constants.MASTER_URL_IMG + data.getImgName())
                            .crossFade(500)
                            .into(bindingView.ivHead);
                    bindingView.tvRecommendCode.setText("推荐编码：" + data.getReferralCode());
                }

            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }
}
