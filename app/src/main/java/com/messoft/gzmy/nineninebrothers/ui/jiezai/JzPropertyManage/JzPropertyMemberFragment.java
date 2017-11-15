package com.messoft.gzmy.nineninebrothers.ui.jiezai.JzPropertyManage;

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
 * 会员
 */

public class JzPropertyMemberFragment extends BaseFragment<FragmentJzManageBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);

    @Override
    protected int setContent() {
        return R.layout.fragment_jz_manage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        bindingView.vpBook.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpBook.setOffscreenPageLimit(1);
        myAdapter.notifyDataSetChanged();
        bindingView.tabBook.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabBook.setupWithViewPager(bindingView.vpBook);

        showContentView();
    }

    private void initFragmentList() {
        mTitleList.add("我的资产");
        mTitleList.add("已售");
        //交易状态(0:待交易,1:交易中,2:已出售)
        //type  0:解债师 1:高级合伙人
        mFragments.add(JzPropertyMemberItemFragment.newInstance("1",""));
        mFragments.add(JzPropertyMemberItemFragment.newInstance("1","2"));
    }
}
