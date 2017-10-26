package com.messoft.gzmy.nineninebrothers.interfae;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public interface TimeListener {
    //计时结束
    void timeFinish();

    //及时过程显示
    void onTick(long millisUntilFinished);
}
