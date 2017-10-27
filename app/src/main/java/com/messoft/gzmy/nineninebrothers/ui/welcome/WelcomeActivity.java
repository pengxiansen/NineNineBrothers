package com.messoft.gzmy.nineninebrothers.ui.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;

import com.messoft.gzmy.nineninebrothers.MainActivity;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityWelcomeBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.ui.login.LoginActivity;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding mBinding;
    private boolean isIn;
    private boolean animationEnd;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        //先显示默认图
        mBinding.ivDefultPic.setImageDrawable(getResources().getDrawable(R.drawable.img_transition_default));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 2000);

        mBinding.tvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                toMainActivity();
            }
        });

    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        //判断是否登陆，否则跳转登录页
        if (SPUtils.getBoolean("isLogin", false)) {
            mIntent = new Intent(this, MainActivity.class);
//            Object object = SPUtils.getObject("loginObject");
//            if (object != null) {
//
//            }

        } else {
            mIntent = new Intent(this, LoginActivity.class);
        }
        startActivity(mIntent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    /**
     * 实现监听跳转
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animationEnd();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void animationEnd() {
        synchronized (WelcomeActivity.this) {
            if (!animationEnd) {
                animationEnd = true;
                mBinding.ivPic.clearAnimation();
                toMainActivity();
            }
        }
    }
}
