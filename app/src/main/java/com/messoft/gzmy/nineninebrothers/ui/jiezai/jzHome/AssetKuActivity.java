package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.AssetKuLeftAdapter;
import com.messoft.gzmy.nineninebrothers.adapter.AssetKuRightAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.AssetKuList;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityAssetKuBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
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
    private AssetKuRightAdapter mRightAdapter;
    private JzModel mJzModel;

    private String mAssetType = ""; //资产类型(0:房产,1:专利,2:股权,3:货物)
    private String mRemarks = ""; //搜索关键词
    private String mProvinceId = "";
    private String mCityId = "";
    private String mDistrictId = "";

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
        mRightAdapter = new AssetKuRightAdapter(this);

        //设置左边
        initLeftXrc();

        //设置右边边
        initRightXrc();

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

        //assetType：资产类型(0:房产,1:专利,2:股权,3:货物)
        List<String> listLeft = new ArrayList<>();
        listLeft.add("推荐");
        listLeft.add("房产");
        listLeft.add("专利");
        listLeft.add("股权");
        listLeft.add("货物");

        mLeftAdapter.clear();
        mLeftAdapter.addAll(listLeft);
        mLeftAdapter.notifyDataSetChanged();

        //默认选中第一个
        mLeftAdapter.setSelectedItem(0);
    }

    private void initRightXrc() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AssetKuActivity.this, 2);
        bindingView.xrcRight.setLayoutManager(gridLayoutManager);
        bindingView.xrcRight.setAdapter(mRightAdapter);
        // 需加，不然滑动不流畅
        bindingView.xrcRight.setNestedScrollingEnabled(false);
        bindingView.xrcRight.setHasFixedSize(false);
        bindingView.xrcRight.setPullRefreshEnabled(false);
        bindingView.xrcRight.setLoadingMoreEnabled(false);
    }

    private void initListener() {
        //搜索按钮
        getRightTv().setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
            }
        });

        //左边点击
        mLeftAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(final String s, final int position) {
                mLeftAdapter.setSelectedItem(position);
                mLeftAdapter.notifyDataSetChanged();
                scrollY();

                switch (position) {
                    case 0://推荐
                        mAssetType = "";
                        loadRightInfo();
                        break;
                    case 1://房产
                        mAssetType = "0";
                        loadRightInfo();
                        break;
                    case 2://专利
                        mAssetType = "1";
                        loadRightInfo();
                        break;
                    case 3://股权
                        mAssetType = "2";
                        loadRightInfo();
                        break;
                    case 4://货物
                        mAssetType = "3";
                        loadRightInfo();
                        break;
                    default:
                        break;
                }
            }
        });

        //右边点击
        mRightAdapter.setOnItemClickListener(new OnItemClickListener<AssetKuList>() {
            @Override
            public void onClick(AssetKuList mData, int position) {
                if (mData != null && mData.getId() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mData.getId());
                    bundle.putString("type","1");
                    SysUtils.startActivity(AssetKuActivity.this, ZwAssetInfoByIdActivity.class, bundle);
                }
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
//        if (!keyword.equals(mRemarks)) {
//            mRemarks = keyword;
//            loadRightInfo();
//        }
        mRemarks = keyword;
        loadRightInfo();
    }


    private void loadRightInfo() {
        mJzModel.getAssetList(AssetKuActivity.this,
                mAssetType,
                mRemarks,
                mProvinceId,
                mCityId,
                mDistrictId,
                new RequestImpl() {
                    @Override
                    public void loadSuccess(Object object) {
                        bindingView.rlNoData.setVisibility(View.GONE);
                        List<AssetKuList> data = (List<AssetKuList>) object;
                        if (data.size() > 0) {
                            mRightAdapter.clear();
                            mRightAdapter.addAll(data);
                            mRightAdapter.notifyDataSetChanged();
                        } else {
                            noRightData();
                        }
                    }

                    @Override
                    public void loadFailed(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                        noRightData();
                    }
                });
    }

    private void noRightData() {
        //搜索不到将所有关键词置为空
        mAssetType = "";
        mRemarks = "";
        mProvinceId = "";
        mCityId = "";
        mDistrictId = "";
        mRightAdapter.clear();
        mRightAdapter.notifyDataSetChanged();
        bindingView.rlNoData.setVisibility(View.VISIBLE);
    }
}
