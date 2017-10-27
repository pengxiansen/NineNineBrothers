package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.MyFragmentPagerAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzPersonBeiAnBinding;

import java.util.ArrayList;

/**
 * @作者 Administrator
 * 债事人备案
 * @创建日期 2017/10/27 0027 15:24
 */

public class JzPersonBeiAnActivity extends BaseActivity<ActivityJzPersonBeiAnBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_person_bei_an);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("摘事人备案");
        showContentView();
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        bindingView.vp.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vp.setOffscreenPageLimit(1);
        myAdapter.notifyDataSetChanged();
        bindingView.tab.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tab.setupWithViewPager(bindingView.vp);
    }

    private void initListener() {

    }

    private void initFragmentList() {
        mTitleList.add("债事企业");
        mTitleList.add("债事自然人");
        //0已使用 1未使用 2已评价
        mFragments.add(new JzPersonBeianQiYeFragment());
        mFragments.add(new JzPersonBeianPersonFragment());
    }
}
