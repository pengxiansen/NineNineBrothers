package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.MainActivity;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.bean.PersonInfo;
import com.messoft.gzmy.nineninebrothers.bean.RxBusMessage;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityLoginBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.rx.RxBus;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;
import com.messoft.gzmy.nineninebrothers.utils.StatusBarUtil;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private LoginModel mLoginModel;
    private RxBus mRxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        showContentView();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 255);

        initSetting();
        initListener();
    }

    private void initSetting() {
        mLoginModel = new LoginModel();
        initRxBus();
    }

    private void initListener() {
        //忘记密码
        bindingView.tvForgetPsw.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "login");
                SysUtils.startActivity(LoginActivity.this, ForgetPswActivity.class, bundle);
            }
        });

        //注册
        bindingView.tvRegister.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(LoginActivity.this, RegisterActivity.class);
            }
        });

        //登录
        bindingView.tvLogin.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String account = bindingView.etAccount.getText().toString().trim();
                String password = bindingView.etPsw.getText().toString().trim();
                clickLogin(account, password);
            }
        });
    }

    private void clickLogin(final String account, final String password) {
//        if (!StringUtils.isNoEmpty(account)) {
//            ToastUtil.showToast("请输入账号");
//            return;
//        }
//        if (!StringUtils.isNoEmpty(password)) {
//            ToastUtil.showToast("请输入密码");
//            return;
//        }
        mLoginModel.login(LoginActivity.this, account, password, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                ToastUtil.showToast("登录成功");
                if (object != null) {
                    Login login = (Login) object;
                    saveUserData(account,password,login);
                    SysUtils.startActivity(LoginActivity.this, MainActivity.class);
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
     * 保存用户信息
     *
     * @param account
     * @param password
     * @param login
     */
    private void saveUserData(String account, String password, Login login) {
        DebugUtil.debug("okhttp","okhttp:保存的登录人信息"+login.toString());
        SPUtils.putObject("loginObject", login);
        //将是否登录置为true
        SPUtils.putBoolean("isLogin", true);
        //保存用户名和密码
//        PasswordHelp.savePassword(LoginActivity.this,account,password,true);
        SPUtils.putString("account",account);
        SPUtils.putString("password",password);
        SPUtils.putString("accessToken",login.getAccessToken());
        SPUtils.putString("secret",login.getSecret());
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    /**
     * 注册RxBus
     */
    private void initRxBus() {
        mRxBus = RxBus.getInstanceBus();
        mRxBus.registerRxBus(mRxBus, RxBusMessage.class, new Consumer<RxBusMessage>() {
            @Override
            public void accept(@NonNull RxBusMessage rxBusMessage) throws Exception {
                //根据事件类型进行处理
                if (null != rxBusMessage && null != rxBusMessage.getData() && rxBusMessage.getData() instanceof PersonInfo) {
                    //收到登录信息
//                    ToastUtil.showToast("收到登录信息");
                    PersonInfo data = (PersonInfo) rxBusMessage.getData();
                    clickLogin(data.getAccount(), data.getPassword());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRxBus != null) {
            mRxBus.unSubscribe(this);
        }
    }
}
