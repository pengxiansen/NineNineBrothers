package com.messoft.gzmy.nineninebrothers.ui.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivitySettingBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.ui.login.LoginActivity;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("设置");
        showContentView();
    }

    private void initListener() {
        //退出登录
        bindingView.tvExit.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                exitLogin();
            }
        });
    }

    private void exitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("温馨提示");
        builder.setMessage("您确定退出登录吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPUtils.putBoolean("isLogin", false);
                SPUtils.putObject("loginObject", null);
//                PasswordHelp.savePassword(SettingActivity.this, "", "", true);
                SPUtils.putString("account","");
                SPUtils.putString("password","");
                Intent in = new Intent(SettingActivity.this, LoginActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
