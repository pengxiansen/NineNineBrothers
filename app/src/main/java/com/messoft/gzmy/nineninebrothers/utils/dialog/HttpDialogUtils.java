package com.messoft.gzmy.nineninebrothers.utils.dialog;

import android.content.Context;
import android.text.TextUtils;

/**
 *
 * HttpDialog
 */
public class HttpDialogUtils {

	/**
	 * 显示网络请求的dialog
	 *
	 * @param context
	 * @param canceledOnTouchOutside
	 * @param messageText
	 */
	public static void showDialog(Context context, boolean canceledOnTouchOutside, String messageText) {
		if (context != null) {
			if (TextUtils.isEmpty(messageText)) {
				CustomWaitDialogUtil.showWaitDialog(context, canceledOnTouchOutside);
			} else {
				CustomWaitDialogUtil.showWaitDialog(context, messageText, canceledOnTouchOutside);
			}
		}
	}

	public static void dismissDialog() {
		CustomWaitDialogUtil.stopWaitDialog();
	}

}
