package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzBeianBinding;
import com.messoft.gzmy.nineninebrothers.utils.KeybordUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

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
        //用于点击空白取消软键盘
        bindingView.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeybordUtils.inputClose(bindingView.scrollView, JzBeianActivity.this);
                return onTouchEvent(event);
            }
        });
        //下一步
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(JzBeianActivity.this, JzBeiAnNextOneActivity.class);
            }
        });
    }
}
