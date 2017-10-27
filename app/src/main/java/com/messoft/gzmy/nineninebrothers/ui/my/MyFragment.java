package com.messoft.gzmy.nineninebrothers.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentMyBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class MyFragment extends BaseFragment<FragmentMyBinding> {

    @Override
    protected int setContent() {
        return R.layout.fragment_my;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();

        initSetting();
        initListener();
    }

    private void initSetting() {

    }

    private void initListener() {
        //设置
        bindingView.ivSetting.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(getActivity(),SettingActivity.class);
            }
        });
    }
}
