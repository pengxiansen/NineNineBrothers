package com.messoft.gzmy.nineninebrothers.utils;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 避免在1秒内多次点击
 */

public abstract class PerfectClickListener implements View.OnClickListener{
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private int id = -1;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int mId = v.getId();
        if(id != mId){
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }
        if(currentTime - lastClickTime > MIN_CLICK_DELAY_TIME){
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}
