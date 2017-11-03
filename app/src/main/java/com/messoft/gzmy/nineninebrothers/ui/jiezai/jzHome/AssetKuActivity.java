package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.AssetKuLeftAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.AssetKuList;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityAssetKuBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 Administrator
 * 资产库
 * @创建日期 2017/11/3 0003 10:15
 */

public class AssetKuActivity extends BaseActivity<ActivityAssetKuBinding> implements IOnSearchClickListener {

    private SearchFragment searchFragment;
    private AssetKuLeftAdapter mLeftAdapter;
    private JzModel mJzModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_ku);

        initSetting();
        initListener();
    }

    private void initSetting() {
        //设置title
        setTvTitle();
        showContentView();
        mJzModel = new JzModel();
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(this);
        mLeftAdapter = new AssetKuLeftAdapter(this);

        //设置左边
        initLeftXrc();

        //请求右边数据
        loadRightInfo();
    }

    private void initLeftXrc() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AssetKuActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.xrcLeft.setLayoutManager(mLayoutManager);
        bindingView.xrcLeft.setAdapter(mLeftAdapter);
        // 需加，不然滑动不流畅
        bindingView.xrcLeft.setNestedScrollingEnabled(false);
        bindingView.xrcLeft.setHasFixedSize(false);
        bindingView.xrcLeft.setPullRefreshEnabled(false);
        bindingView.xrcRight.setLoadingMoreEnabled(false);

        List<String> listLeft = new ArrayList<>();
        listLeft.add("推荐");
        listLeft.add("房产");
        listLeft.add("文化");
        listLeft.add("汽车");
        listLeft.add("字画");
        listLeft.add("实业");
        listLeft.add("古董");
        listLeft.add("工业用品");
        listLeft.add("机械设备");
        listLeft.add("狗子");
        listLeft.add("狗子你变了");
        listLeft.add("二狗");
        listLeft.add("三狗");

        mLeftAdapter.clear();
        mLeftAdapter.addAll(listLeft);
        mLeftAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        //搜索按钮
        getRightTv().setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
            }
        });

        mLeftAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(final String s, final int position) {
                mLeftAdapter.setSelectedItem(position);
                mLeftAdapter.notifyDataSetChanged();

                scrollY();
            }
        });
    }

    private void scrollY() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        clsfyLeft.smoothScrollToPositionFromTop(
//                                position, (ScreenSizeUtils.getHeight(getActivity()) -//屏幕高度
//                                        Px2BDpUtil.Dp2Px(getActivity(), 94) -//顶部标题栏和底部导航栏
//                                        ImmersedStatusbarUtils.getStatusBarHeight(getActivity())) / 2 -//状态栏
//                                        Px2BDpUtil.Dp2Px(getActivity(), 30));
//                    }
//                });
//            }
//        }).start();
    }

    private void setTvTitle() {
        //设置title
        Drawable drawable = getResources().getDrawable(R.drawable.sanjiao);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        getTvTitle().setCompoundDrawables(null, null, drawable, null);
        getTvTitle().setText("深圳");

        setRightImg(R.drawable.actionbar_search);
    }

    @Override
    public void OnSearchClick(String keyword) {
        ToastUtil.showToast(keyword);
    }


    private void loadRightInfo() {
        mJzModel.getAssetList(AssetKuActivity.this, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                List<AssetKuList> data = (List<AssetKuList>) object;
//                mHomeNewsAdapter.clear();
//                mHomeNewsAdapter.addAll(data);
//                mHomeNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {

            }
        });
    }
}
