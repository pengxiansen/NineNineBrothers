package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJzPersonBeianPersonBinding;

/**
 * Created by Administrator on 2017/10/27 0027.
 * 债事人备案-自然人
 */

public class JzPersonBeianPersonFragment extends BaseFragment<FragmentJzPersonBeianPersonBinding>{

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

    }
}
