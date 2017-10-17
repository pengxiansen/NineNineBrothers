package com.messoft.gzmy.nineninebrothers.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentHomeBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity;

import static com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity.TYPE_KEY;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    @Override
    protected int setContent() {
        return R.layout.fragment_home;
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

        bindingView.tvGo.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startActivity(new Intent(getActivity(), WebCommonActivity.class).putExtra(TYPE_KEY, 12));
            }
        });
    }
}
