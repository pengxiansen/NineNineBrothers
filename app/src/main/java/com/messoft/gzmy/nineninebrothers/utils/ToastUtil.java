package com.messoft.gzmy.nineninebrothers.utils;

import android.widget.Toast;

import com.messoft.gzmy.nineninebrothers.app.MyApplication;

/**
 * Created by peng on 2017/3/14.
 * 单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}
