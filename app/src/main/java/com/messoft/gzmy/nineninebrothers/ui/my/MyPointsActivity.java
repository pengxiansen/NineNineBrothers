package com.messoft.gzmy.nineninebrothers.ui.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.PointsListdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.PointsInfo;
import com.messoft.gzmy.nineninebrothers.bean.PointsList;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityMyPointsBinding;
import com.messoft.gzmy.nineninebrothers.databinding.HeadPointsListBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpUtils;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.List;

/**
 * @作者 Administrator
 * 我的积分
 * @创建日期 2017/11/9 0009 14:46
 */

public class MyPointsActivity extends BaseActivity<ActivityMyPointsBinding> {

    private PointsListdapter mAdapter;
    private LoginModel mLoginModel;
    private int mPage = 0;

    private View mHeaderView;
    private HeadPointsListBinding mHeadBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_points);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("积分详情");
        mAdapter = new PointsListdapter(this);
        mLoginModel = new LoginModel();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.xrcHome.setLayoutManager(mLayoutManager);
        bindingView.xrcHome.setAdapter(mAdapter);
        // 需加，不然滑动不流畅
        bindingView.xrcHome.setNestedScrollingEnabled(false);
        bindingView.xrcHome.setHasFixedSize(false);
        bindingView.xrcHome.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        bindingView.xrcHome.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        bindingView.xrcHome.setArrowImageView(R.drawable.iconfont_downgrey);

        //设置头
        mHeadBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.head_points_list, null, false);
        if (mHeaderView == null) {
            mHeaderView = mHeadBinding.getRoot();
            mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            bindingView.xrcHome.addHeaderView(mHeaderView);
            Glide.with(mHeadBinding.ivBg.getContext())
                    .load(R.drawable.bg_monkey_king)
                    .crossFade(500)
                    .into(mHeadBinding.ivBg);
        }

        loadInfo();
    }

    private void initListener() {
        bindingView.xrcHome.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                loadInfo();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadInfo();
            }
        });
    }

    private void loadInfo() {
        if (BusinessUtils.getLoginInfo() == null) {
            ToastUtil.showToast("登录人信息为空，请重新登录");
            return;
        }
        //请求列表
        mLoginModel.getPointLogList(MyPointsActivity.this, BusinessUtils.getLoginInfo().getId(), mPage, HttpUtils.per_page, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                List<PointsList> data = (List<PointsList>) object;
                if (mPage == 0) {
                    if (data != null && data.size() > 0) {
                        setNewListAdapter(data);
                    } else {
                        refreshComplete();
                    }
                } else {
                    if (data != null && data.size() > 0) {
                        bindingView.xrcHome.refreshComplete();
                        mAdapter.addAll(data);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        bindingView.xrcHome.loadMoreComplete();
                    }
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                showContentView();
                refreshComplete();
                ToastUtil.showToast(errorMessage);
            }
        });

        //请求头
        mLoginModel.getPointByAccount(MyPointsActivity.this, BusinessUtils.getLoginInfo().getId(), new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                PointsInfo bean = (PointsInfo) object;
                mHeadBinding.tvPoint.setText(bean.getCapital());
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });

    }

    private void refreshComplete(){
        if (bindingView.xrcHome != null) {
            bindingView.xrcHome.refreshComplete();
        }
    }

    private void setNewListAdapter(List<PointsList> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        bindingView.xrcHome.refreshComplete();

    }

    @Override
    protected void onRefresh() {
        loadInfo();
    }
}
