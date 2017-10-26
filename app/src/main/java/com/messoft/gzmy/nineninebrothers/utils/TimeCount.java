package com.messoft.gzmy.nineninebrothers.utils;

import android.os.CountDownTimer;

import com.messoft.gzmy.nineninebrothers.interfae.TimeListener;

/**
 * Created by Administrator on 2017/10/25 0025.
 * 计时器
 */

public class TimeCount extends CountDownTimer {

    private TimeListener mTimeListener;

    public TimeCount(long millisInFuture, long countDownInterval, TimeListener timeListener) {
        super(millisInFuture, countDownInterval);
        this.mTimeListener = timeListener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mTimeListener != null) {
            mTimeListener.onTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (mTimeListener != null) {
            mTimeListener.timeFinish();
        }
    }
}
