package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzMy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.MyFragmentPagerAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityAssetOrderBinding;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage.JzKuJzsFragment;

import java.util.ArrayList;

/**
 * @作者 Administrator
 * 资产订单
 * @创建日期 2017/11/10 0010 16:59
 */

public class AssetOrderActivity extends BaseActivity<ActivityAssetOrderBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_order);
        setTitle("资产订单");
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        bindingView.vpBook.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpBook.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        bindingView.tabBook.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabBook.setupWithViewPager(bindingView.vpBook);

        showContentView();
    }

    private void initFragmentList() {
        mTitleList.add("已预约");
        mTitleList.add("已通过");
        mTitleList.add("已驳回");
        //交易状态(0:已预约,1:已通过,2:已驳回)
        //type  0:解债师 1:高级合伙人
        //disposeState：处理状态(0:待处理,1:同意,2:不同意,3:已结束)
        //这里解债师跟合伙人用的接口一样
        mFragments.add(JzKuJzsFragment.newInstance("0","","0",""));
        mFragments.add(JzKuJzsFragment.newInstance("0","","1",""));
        mFragments.add(JzKuJzsFragment.newInstance("0","","2",""));

    }
}
