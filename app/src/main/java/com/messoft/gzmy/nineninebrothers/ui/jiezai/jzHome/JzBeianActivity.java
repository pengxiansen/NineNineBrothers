package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzBeianBinding;

/**
 * @作者 Administrator
 * 解债备案
 * @创建日期 2017/10/27 0027 17:49
 */

public class JzBeianActivity extends BaseActivity<ActivityJzBeianBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_beian);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("解债备案");
        showContentView();
    }

    private void initListener() {
    }
}
