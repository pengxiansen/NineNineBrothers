package com.messoft.gzmy.nineninebrothers.utils.dialog;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.R;

import java.lang.ref.WeakReference;


/**
 * 这里有内存泄漏
 *
 */
public class CustomWaitDialogUtil {
//    public static CustomWaitDialog waitDialog = null;

    private static WeakReference<Context> mThreadActivityRef;//弱引用
    private static WeakReference<CustomWaitDialog> waitDialog;//弱引用

    /**
     * 自定义用于等待的dialog,有动画和message提示
     * 调用stopWaitDialog()方法来取消
     *
     * @param mContext
     * @param message
     * @param canceledOnTouchOutside
     */
    public static void showWaitDialog(Context mContext, String message, boolean canceledOnTouchOutside) {

        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }

        mThreadActivityRef = new WeakReference<>(mContext);
        waitDialog = new WeakReference<>(new CustomWaitDialog(mThreadActivityRef.get(), R.style.CustomHttpWaitDialog, message));

        waitDialog.get().setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (waitDialog != null && waitDialog.get() != null && !waitDialog.get().isShowing()) {
            waitDialog.get().show();
        }

    }

    /**
     * 自定义用于等待的dialog,有动画无message
     * 调用stopWaitDialog()方法来取消
     *
     * @param context
     * @param canceledOnTouchOutside
     */
    public static void showWaitDialog(Context context, boolean canceledOnTouchOutside) {
        showWaitDialog(context, null, canceledOnTouchOutside);
    }

    /**
     * 自定义用于等待的dialog,有动画和message提示
     * 调用stopWaitDialog()方法来取消
     *
     * @param context
     * @param canceledOnTouchOutside
     */
    public static void showWaitDialog(Context context, int messageId, boolean canceledOnTouchOutside) {
        showWaitDialog(context, context.getResources().getString(messageId), canceledOnTouchOutside);
    }

    /**
     * 取消等待dialog
     */
    public static void stopWaitDialog() {
        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }
    }
}
