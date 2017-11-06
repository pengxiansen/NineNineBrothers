package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.JzKuAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.JzKuList;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJzKuBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpUtils;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31 0031.
 * 解债师---解债管理的页面
 */

public class JzKuJzsFragment extends BaseFragment<FragmentJzKuBinding> {

    // 初始化完成后加载数据
    private boolean mIsPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean mIsFirst = true;
    private int mPage = 0;

    private JzModel mJzModel;
    private JzKuAdapter mAdapter;
    private String mDebtState;
    private String mType; //type  0:解债师 1:高级合伙人

    @Override
    protected int setContent() {
        return R.layout.fragment_jz_ku;
    }

    public static JzKuJzsFragment newInstance(String type, String debtState) {
        JzKuJzsFragment fragment = new JzKuJzsFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("debtState", debtState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDebtState = getArguments().getString("debtState");
            mType = getArguments().getString("type");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSetting();
        initListener();
    }

    private void initSetting() {
        mAdapter = new JzKuAdapter(getActivity());
        mJzModel = new JzModel();

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

        mAdapter.setOnItemClickListener(new OnItemClickListener<JzKuList>() {
            @Override
            public void onClick(JzKuList jzKuList, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("type",mType);
                bundle.putString("id",jzKuList.getId());//债事id
                SysUtils.startActivity(getActivity(),JzManageSelectTypeActivity.class,bundle);
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
        mJzModel.getJzKuList(getActivity(), "", mDebtState, "1", mPage, HttpUtils.per_page, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                List<JzKuList> data = (List<JzKuList>) object;
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
        showNoData("暂无待接单订单");
    }

    private void setNewListAdapter(List<JzKuList> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        bindingView.xrcHome.refreshComplete();


        mIsFirst = false;
    }
}
