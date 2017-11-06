package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.MyFragmentPagerAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJzManageBinding;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/13 0013.
 * 普通会员加载这个
 */

public class JzManageFragment extends BaseFragment<FragmentJzManageBinding> {


    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    @Override
    protected int setContent() {
        return R.layout.fragment_jz_manage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();

        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        bindingView.vpBook.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpBook.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        bindingView.tabBook.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabBook.setupWithViewPager(bindingView.vpBook);

    }

    private void initFragmentList() {
        mTitleList.add("待接单");
        mTitleList.add("解债中");
        mTitleList.add("已完成"); //已使用
        //债事状态(0:待审核,1:待接单,2:交易中,3:已完成)
        //type  0:解债师 1:高级合伙人
        //这里type传1 后面的详情页可以查看债市人详情
        mFragments.add(JzKuJzsFragment.newInstance("1","1"));
        mFragments.add(JzKuJzsFragment.newInstance("1","2"));
        mFragments.add(JzKuJzsFragment.newInstance("1","3"));
    }
}
