package com.messoft.gzmy.nineninebrothers.ui.jiezai.JzPropertyManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.JzPropertyJzsdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.QueryAssetDiscussApplyListWithAssetInfo;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJzPropertyBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpUtils;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.AssetModel;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13 0013.
 * 解债师
 */

public class JzPropertyJzsFragment extends BaseFragment<FragmentJzPropertyBinding> {

    // 初始化完成后加载数据
    private boolean mIsPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean mIsFirst = true;
    private int mPage = 0;

    private AssetModel mJzModel;
    private JzPropertyJzsdapter mAdapter;
    private String mType; //type  0:解债师 1:高级合伙人
    private String mDisposeState; //处理状态(0:待处理,1:同意,2:不同意,3:已结束)

    public static JzPropertyJzsFragment newInstance(String type, String disposeState) {
        JzPropertyJzsFragment fragment = new JzPropertyJzsFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("disposeState", disposeState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContent() {
        return R.layout.fragment_jz_property;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString("type");
            mDisposeState = getArguments().getString("disposeState");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSetting();
        initListener();
    }

    private void initSetting() {
        mAdapter = new JzPropertyJzsdapter(getActivity());
        mJzModel = new AssetModel();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.xrcHome.setLayoutManager(mLayoutManager);
        bindingView.xrcHome.setAdapter(mAdapter);
        // 需加，不然滑动不流畅
        bindingView.xrcHome.setNestedScrollingEnabled(false);
        bindingView.xrcHome.setHasFixedSize(false);

        bindingView.xrcHome.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        bindingView.xrcHome.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        bindingView.xrcHome.setArrowImageView(R.drawable.iconfont_downgrey);

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initListener() {
        bindingView.xrcHome.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                loadJzKuList();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadJzKuList();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<QueryAssetDiscussApplyListWithAssetInfo>() {
            @Override
            public void onClick(QueryAssetDiscussApplyListWithAssetInfo bean, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("type",mType);
                bundle.putString("id",bean.getAssetId());//资产id
                SysUtils.startActivity(getActivity(),AssetManageSelectTypeActivity.class,bundle);
            }
        });
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadJzKuList();
    }

    /**
     * 加载列表
     */
    private void loadJzKuList() {
        mJzModel.queryAssetDiscussApplyListWithAssetInfo(getActivity(), mDisposeState, mPage, HttpUtils.per_page, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                List<QueryAssetDiscussApplyListWithAssetInfo> data = (List<QueryAssetDiscussApplyListWithAssetInfo>) object;
                if (mPage == 0) {
                    if (data != null && data.size() > 0) {
                        setNewListAdapter(data);
                    } else {
                        //TODO 暂无新闻
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
    }

    private void refreshComplete() {
        if (bindingView.xrcHome != null) {
            bindingView.xrcHome.refreshComplete();
        }
        showNoData("暂无资产订单");
    }

    private void setNewListAdapter(List<QueryAssetDiscussApplyListWithAssetInfo> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        bindingView.xrcHome.refreshComplete();


        mIsFirst = false;
    }
}
