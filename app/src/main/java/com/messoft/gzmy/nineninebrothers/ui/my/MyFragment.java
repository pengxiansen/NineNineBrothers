package com.messoft.gzmy.nineninebrothers.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentMyBinding;

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentView();
            }
        }, 3000);

    }
}
