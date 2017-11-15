package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

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
    private JzPersonBeianQiYeFragment mJzPersonBeianQiYeFragment;
    private JzPersonBeianPersonFragment mJzPersonBeianPersonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_person_bei_an);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("债事人备案");
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
        mJzPersonBeianQiYeFragment = new JzPersonBeianQiYeFragment();
        mJzPersonBeianPersonFragment = new JzPersonBeianPersonFragment();
        mFragments.add(mJzPersonBeianQiYeFragment);
        mFragments.add(mJzPersonBeianPersonFragment);
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        if (mJzPersonBeianQiYeFragment.isChangeInfo() || mJzPersonBeianPersonFragment.isChangeInfo()) {
            //提示有信心尚未保存
            showExitPop();
            return;
        }
        mSwipeBackHelper.backward();
    }

    private void showExitPop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JzPersonBeiAnActivity.this);
        builder.setTitle("温馨提示");
        builder.setMessage("您有未保存的编辑信息，确定离开吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSwipeBackHelper.backward();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
