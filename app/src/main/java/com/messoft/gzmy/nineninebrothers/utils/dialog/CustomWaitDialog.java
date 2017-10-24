package com.messoft.gzmy.nineninebrothers.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.messoft.gzmy.nineninebrothers.R;


/**
 * 自定义Dialog
 */
public class CustomWaitDialog extends Dialog {
    private Context context;
    private String msg; //跟随Dialog 一起显示的message 信息！

    public CustomWaitDialog(Context context, int theme, String msg) {
        super(context, theme);
        this.context = context;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = null;
        TextView tv_wait = null;
        ImageView iv_wait = null;
        if (!TextUtils.isEmpty(msg)) {
            view = View.inflate(context, R.layout.custom_wait_dialog_have_message, null);
            iv_wait = (ImageView) view.findViewById(R.id.iv_wait);
            tv_wait = (TextView) view.findViewById(R.id.tv_MyDialog);
            tv_wait.setText(msg);
        } else {
            view = View.inflate(context, R.layout.custom_wait_dialog_no_message, null);
            iv_wait = (ImageView) view.findViewById(R.id.iv_wait);
        }

        AnimationDrawable animationDrawable = (AnimationDrawable) iv_wait.getDrawable();
        animationDrawable.start();
        setContentView(view);
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

}
