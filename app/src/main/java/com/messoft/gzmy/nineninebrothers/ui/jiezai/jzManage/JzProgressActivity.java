package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.JzProgressAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.JzProgress;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzProgressBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.List;

/**
 * @作者 Administrator
 * 解债进度
 * @创建日期 2017/11/6 0006 16:47
 */

public class JzProgressActivity extends BaseActivity<ActivityJzProgressBinding> {

    private String mType;// //type  0:解债师 1:高级合伙人
    private String mId;//债事id
    private JzModel mJzModel;
    private JzProgressAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_progress);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("解债进度");
        mJzModel = new JzModel();
        mAdapter = new JzProgressAdapter(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(JzProgressActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.xrcPro.setLayoutManager(mLayoutManager);
        bindingView.xrcPro.setAdapter(mAdapter);
        // 需加，不然滑动不流畅
        bindingView.xrcPro.setNestedScrollingEnabled(false);
        bindingView.xrcPro.setHasFixedSize(false);
        bindingView.xrcPro.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        bindingView.xrcPro.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        bindingView.xrcPro.setArrowImageView(R.drawable.iconfont_downgrey);
        if (getIntent() != null && getIntent().getBundleExtra("b") != null) {
            //type  0:解债师 1:高级合伙人
            mType = getIntent().getBundleExtra("b").getString("type");
            mId = getIntent().getBundleExtra("b").getString("id");

            if (StringUtils.isNoEmpty(mId)) {
                //查信息
                loadInfo();
            }
        }
    }

    private void initListener() {
        bindingView.xrcPro.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (StringUtils.isNoEmpty(mId)) {
                    //查信息
                    loadInfo();
                }
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void loadInfo() {
        mJzModel.queryDebtMatterProgressInfoList(JzProgressActivity.this, mId, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                List<JzProgress> list = (List<JzProgress>) object;
                if (list.size() > 0) {
                    bindingView.xrcPro.setVisibility(View.VISIBLE);
                    bindingView.llErrorRefresh.setVisibility(View.GONE);
                    mAdapter.clear();
                    mAdapter.addAll(list);
                    mAdapter.notifyDataSetChanged();
                } else {
                    //没有进度
                    bindingView.xrcPro.setVisibility(View.GONE);
                    bindingView.llErrorRefresh.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
                showError();
            }
        });
    }

    @Override
    protected void onRefresh() {
        loadInfo();
    }
}
