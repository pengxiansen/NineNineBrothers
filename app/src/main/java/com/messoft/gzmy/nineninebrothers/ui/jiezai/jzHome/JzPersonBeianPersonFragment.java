package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJzPersonBeianPersonBinding;
import com.messoft.gzmy.nineninebrothers.utils.KeybordUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

/**
 * Created by Administrator on 2017/10/27 0027.
 * 债事人备案-自然人
 */

public class JzPersonBeianPersonFragment extends BaseFragment<FragmentJzPersonBeianPersonBinding> {

    @Override
    protected int setContent() {
        return R.layout.fragment_jz_person_beian_person;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSetting();
        initListener();

    }

    private void initSetting() {
        showContentView();
    }

    private void initListener() {
        //用于点击空白取消软键盘
        bindingView.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeybordUtils.inputClose(bindingView.scrollView, getActivity());
                return getActivity().onTouchEvent(event);
            }
        });
        //下一步
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(getActivity(), JzUploadImgsActivity.class);
            }
        });
    }
}
