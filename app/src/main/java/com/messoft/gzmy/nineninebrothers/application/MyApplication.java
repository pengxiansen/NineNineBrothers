package com.messoft.gzmy.nineninebrothers.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * 新增LeakCanary
 * Created by Administrator on 2017/10/12 0012.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
