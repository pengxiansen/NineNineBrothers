package com.messoft.gzmy.nineninebrothers.ui.jiezai.JzPropertyManage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.AssetProgressAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.AssetProgress;
import com.messoft.gzmy.nineninebrothers.bean.RxBusMessage;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityAssetProgressBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.rx.RxBus;
import com.messoft.gzmy.nineninebrothers.http.rx.RxCodeConstants;
import com.messoft.gzmy.nineninebrothers.model.AssetModel;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @作者 Administrator
 * 资产进度
 * @创建日期 2017/11/9 0009 10:09
 */

public class AssetProgressActivity extends BaseActivity<ActivityAssetProgressBinding> {

    private String mType;// //type  0:解债师 1:高级合伙人
    private String mId;//债事id
    private AssetModel mJzModel;
    private AssetProgressAdapter mAdapter;
    private RxBus mRxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_progress);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("资产交易进度");
        mJzModel = new AssetModel();
        mAdapter = new AssetProgressAdapter(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AssetProgressActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.xrcPro.setLayoutManager(mLayoutManager);
        bindingView.xrcPro.setAdapter(mAdapter);
        // 需加，不然滑动不流畅
        bindingView.xrcPro.setNestedScrollingEnabled(false);
        bindingView.xrcPro.setHasFixedSize(false);
        bindingView.xrcPro.setLoadingMoreEnabled(false);

        bindingView.xrcPro.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        bindingView.xrcPro.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        bindingView.xrcPro.setArrowImageView(R.drawable.iconfont_downgrey);
        if (getIntent() != null && getIntent().getBundleExtra("b") != null) {
            //type  0:解债师 1:高级合伙人
            mType = getIntent().getBundleExtra("b").getString("type");
            mId = getIntent().getBundleExtra("b").getString("id");
            //TODO 解债师可以在首页的进度列表录制资产或者债事的进度 会员或高级合伙人只能在个人中心已通过的订单里面去录制
            if (StringUtils.isNoEmpty(mId)) {
                //查信息
                loadInfo();
            }
        }

        initRxBus();
    }

    private void initListener() {
        bindingView.rlRecord.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (!StringUtils.isNoEmpty(mId)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", mId);
                SysUtils.startActivity(AssetProgressActivity.this, RecordAssetProgressActivity.class, bundle);
            }
        });
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
        //这里0，和10 是取所有数据，因为只有4条数据
        mJzModel.queryAssetProgressListByAssetId(AssetProgressActivity.this, mId,0,10, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                List<AssetProgress> list = (List<AssetProgress>) object;
                if (list.size() > 0) {
                    bindingView.xrcPro.setVisibility(View.VISIBLE);
                    bindingView.llErrorRefresh.setVisibility(View.GONE);
                    mAdapter.clear();
                    mAdapter.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    bindingView.xrcPro.refreshComplete();

                    //录满了就隐藏录制按钮
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getProgressStage() == 4) {
                            bindingView.rlRecord.setVisibility(View.GONE);
                            break;
                        }
                    }
                } else {
                    //没有进度
                    bindingView.xrcPro.refreshComplete();
                    bindingView.xrcPro.setVisibility(View.GONE);
                    bindingView.llErrorRefresh.setVisibility(View.VISIBLE);
                }
                showContentView();
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
                bindingView.xrcPro.refreshComplete();
                showError();
            }
        });
    }

    @Override
    protected void onRefresh() {
        loadInfo();
    }

    /**
     * 注册RxBus
     */
    private void initRxBus() {
        mRxBus = RxBus.getInstanceBus();
        mRxBus.registerRxBus(mRxBus, RxBusMessage.class, new Consumer<RxBusMessage>() {
            @Override
            public void accept(@NonNull RxBusMessage rxBusMessage) throws Exception {
                //根据事件类型进行处理
                if (null != rxBusMessage && rxBusMessage.getType() == RxCodeConstants.REFRESH_ASSET_PROGRESS_LIST) {
                    if (rxBusMessage.getI()==0) {
                        //刷新列表
                        loadInfo();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRxBus != null) {
            mRxBus.unSubscribe(this);
        }
    }
}
