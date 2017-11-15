package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityForgetPswBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.interfae.TimeListener;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.utils.RegexUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.TimeCount;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

/**
 * @作者 Administrator
 * 获取验证码界面，这个界面可以通用，通过type区分
 * @创建日期 2017/10/24 0024 17:10
 */

public class ForgetPswActivity extends BaseActivity<ActivityForgetPswBinding> {

    private String mType;
    private LoginModel mLoginModel;
    private TimeCount mTimeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);

        initSetting();
        initListener();
    }

    private void initSetting() {
        showContentView();
        mLoginModel = new LoginModel();
        if (getIntent() != null && null != getIntent().getBundleExtra("b")) {
            mType = getIntent().getBundleExtra("b").getString("type");
            if (mType.equals("login")) {
                setTitle("忘记密码");
            }else if(mType.equals("PersonInfoActivity")){
                setTitle("修改手机号");
                bindingView.tvNext.setText("修改");
            }
        }

        //计时器
        mTimeCount = new TimeCount(60000, 1000, new TimeListener() {
            @Override
            public void timeFinish() {
                if (!isFinishing()) {
                    bindingView.tvGetCode.setText("重新获取");
                    bindingView.tvGetCode.setClickable(true);
                    mTimeCount.cancel();
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                //计时过程显示
                if (!isFinishing()) {
                    bindingView.tvGetCode.setClickable(false);
                    bindingView.tvGetCode.setText(millisUntilFinished / 1000 + "秒");
                }
                if (isFinishing()) {
                    mTimeCount.cancel();
                }
            }
        });
    }

    private void initListener() {
        //验证码
        bindingView.tvGetCode.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                getCode();
            }
        });
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mType.equals("login")) {
                    checkCode();
                }else if(mType.equals("PersonInfoActivity")){
                    //修改手机号
                    clickChangePhone();
                }
            }
        });

    }

    /**
     * 修改手机号
     */
    private void clickChangePhone() {
        final String etPhone = bindingView.etPhone.getText().toString().trim();
        final String etCode = bindingView.etCode.getText().toString().trim();
        if (!StringUtils.isNoEmpty(etPhone)) {
            ToastUtil.showToast("手机号码不能为空");
            return;
        }
        if (!RegexUtil.checkMobile(etPhone)) {
            ToastUtil.showToast("手机号码格式不正确");
            return;
        }
        if (!StringUtils.isNoEmpty(etCode)) {
            ToastUtil.showToast("验证码不能为空");
            return;
        }
        mLoginModel.modifyMobile(ForgetPswActivity.this, etPhone, etCode, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                ToastUtil.showToast("修改成功");
                finish();
                //TODO 通知个人中心页面刷新
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    private void checkCode() {
        final String etPhone = bindingView.etPhone.getText().toString().trim();
        final String etCode = bindingView.etCode.getText().toString().trim();
        if (!StringUtils.isNoEmpty(etPhone)) {
            ToastUtil.showToast("手机号码不能为空");
            return;
        }
        if (!RegexUtil.checkMobile(etPhone)) {
            ToastUtil.showToast("手机号码格式不正确");
            return;
        }
        if (!StringUtils.isNoEmpty(etCode)) {
            ToastUtil.showToast("验证码不能为空");
            return;
        }
        mLoginModel.checkCode(ForgetPswActivity.this, etPhone, "1",etCode, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mType.equals("login")) {
                    //去修改密码
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile",etPhone);
                    bundle.putString("code",etCode);
                    SysUtils.startActivity(ForgetPswActivity.this,ResetPswActivity.class,bundle);
                    finish();
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String etPhone = bindingView.etPhone.getText().toString().trim();
        if (!StringUtils.isNoEmpty(etPhone)) {
            ToastUtil.showToast("手机号码不能为空");
            return;
        }
        if (!RegexUtil.checkMobile(etPhone)) {
            ToastUtil.showToast("手机号码格式不正确");
            return;
        }
        mLoginModel.getCode(ForgetPswActivity.this, etPhone, "1", new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                ToastUtil.showToast("验证码已发送到您的手机");
                mTimeCount.start();
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mTimeCount.cancel();
        }
    }
}
