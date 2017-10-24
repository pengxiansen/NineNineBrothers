package com.messoft.gzmy.nineninebrothers.http;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.utils.dialog.HttpDialogUtils;

/**
 *
 * Created by zenglb on 2017/3/24.
 */
public class HttpUiTips {
    /**
     * http 请求遇阻提示，比如没有网络不提示，再重试也无用
     */
    public static void alertTip(Context mContext, String message, int code) {
        if(!Thread.currentThread().getName().toString().equals(BaseObserver.Thread_Main)){
            return;
        }

        //TODO: 2017/3/14 这里的提示框需要做成单例的

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("获取数据失败");
        builder.setMessage(message);
        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dlg = builder.create();
        dlg.show();
        dlg.setCanceledOnTouchOutside(false);

        dlg.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        dlg.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }


    /**
     * showDialog & dismissDialog 在http 请求开始的时候显示，结束的时候消失
     * 当然不是必须需要显示的 !
     */
    public static void showDialog(final Context mContext, final boolean canceledOnTouchOutside, final String messageText) {
        if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing())
            return;
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HttpDialogUtils.showDialog(mContext, canceledOnTouchOutside, messageText);
            }
        });
    }

    public static void dismissDialog(final Context mContext) {
        if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing())
            return;             //maybe not good !
        if (mContext != null) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HttpDialogUtils.dismissDialog();
                }
            });
        }
    }

}
