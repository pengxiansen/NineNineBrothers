package com.messoft.gzmy.nineninebrothers.ui.jiezai.JzPropertyManage;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentNewsBinding;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class JzPropertyFragment extends BaseFragment<FragmentNewsBinding> {

    @Override
    protected int setContent() {
        return R.layout.fragment_jz_property;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentView();
            }
        }, 3000);
    }
}
