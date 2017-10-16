package com.messoft.gzmy.nineninebrothers.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class SysUtils {

    /**
     * 无参数跳转
     *
     * @param activity
     * @param cla
     */
    public static <T> void startActivity(Activity activity, Class<T> cla) {
        //跳转前关闭activity打开的键盘
        BGAKeyboardUtil.closeKeyboard(activity);
        Intent intent = new Intent(activity, cla);
        activity.startActivity(intent);
        //style里面设置跳转动画，这里就不设置了
//        activity.overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out);
    }

    /**
     * 带参数跳转
     *
     * @param activity
     * @param cla
     * @param b        注意，接收Bundle的key为“b”
     */
    public static <T> void startActivity(Activity activity, Class<T> cla, Bundle b) {
        //跳转前关闭activity打开的键盘
        BGAKeyboardUtil.closeKeyboard(activity);
        Intent intent = new Intent(activity, cla);
        if (b != null) {
            intent.putExtra("b", b);
        }
        activity.startActivity(intent);
        //style里面设置跳转动画，这里就不设置了
//        activity.overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out);
    }

}
