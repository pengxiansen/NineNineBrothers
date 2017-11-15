package com.messoft.gzmy.nineninebrothers.ui.my;

import android.os.Bundle;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityMyOrderBinding;

/**
 * @作者 Administrator
 * 我的订单
 * @创建日期 2017/11/9 0009 14:42
 */

public class MyOrderActivity extends BaseActivity<ActivityMyOrderBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        showNoData("此模块暂未开放");
    }
}
