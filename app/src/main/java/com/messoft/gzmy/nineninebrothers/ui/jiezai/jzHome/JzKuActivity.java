package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.MyFragmentPagerAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzKuBinding;

import java.util.ArrayList;

/**
 * @作者 Administrator
 * 解债库，解债师才能看
 * @创建日期 2017/10/31 0031 9:54
 */

public class JzKuActivity extends BaseActivity<ActivityJzKuBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_ku);

        initSetting();
    }

    private void initSetting() {
        setTitle("解债库");
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
        mTitleList.add("待接单");
        mTitleList.add("解债中");
        mTitleList.add("已完成"); //已使用
        //债事状态(0:待审核,1:待接单,2:交易中,3:已完成)
        //type  0:解债师 1:高级合伙人
        mFragments.add(JzKuFragment.newInstance("0","1"));
        mFragments.add(JzKuFragment.newInstance("0","2"));
        mFragments.add(JzKuFragment.newInstance("0","3"));
    }
}
