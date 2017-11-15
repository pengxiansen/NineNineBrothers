package com.messoft.gzmy.nineninebrothers.http;

import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/10/18 0018.
 *
 */

public abstract class BaseObserverNoData<T> implements Observer<BaseBean<T>> {

    private static final String TAG = "BaseObserver";
    public final static String Thread_Main = "main";
    private Context mContext;
    private Disposable disposable;

    private static Gson gson = new Gson();

    private int errorCode = -1111;
    private String errorMsg = "未知的错误！";

    private final int RESPONSE_CODE_OK = 0;       //自定义的业务逻辑，成功返回积极数据
    private final int RESPONSE_FATAL_EOR = -1;  //返回数据失败,严重的错误

    /**
     * @param mCtx
     */
    public BaseObserverNoData(Context mCtx) {
        this.mContext = mCtx.getApplicationContext();
        HttpUiTips.showDialog(mContext, true, "加载中...");
    }

    /**
     * @param showProgress 默认需要显示进程，不要的话请传 false
     * @param context
     */
    public BaseObserverNoData(Context context, boolean showProgress) {
        this.mContext = context.getApplicationContext();
        if (showProgress) {
            HttpUiTips.showDialog(mContext, false, null);
        }
    }

    /**
     * @param showProgress 默认需要显示进程，不要的话请传 false
     * @param context
     */
    public BaseObserverNoData(Context context, boolean showProgress, String tip) {
        this.mContext = context.getApplicationContext();
        if (showProgress) {
            HttpUiTips.showDialog(mContext, false, tip);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(BaseBean<T> value) {
        HttpUiTips.dismissDialog(mContext);

        if (!disposable.isDisposed()) {
            disposable.dispose();
        }

        if (value.isSuccess()) {
            T t = (T) value;
            onSuccess(t);
        } else {
            onFailure(value.getState(), value.getMessage());
        }
    }

    @Override
    public void onError(Throwable t) {
        HttpUiTips.dismissDialog(mContext);
        DebugUtil.error(TAG, "error:" + t.toString());
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
            getErrorMsg(httpException);
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof UnknownHostException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "没有网络，请检查网络连接";
        } else if (t instanceof NetworkOnMainThreadException) {//主线程不能网络请求，这个很容易发现
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "主线程不能网络请求";
        } else if (t instanceof RuntimeException) { //很多的错误都是extends RuntimeException
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "运行时错误";
        }
        // REFACTOR: 2017/10/31 0031 待重构 这里如果有数据返回data{},没数据返回data[]，就报错了，错误码就不回调了，返回state就行
        onFailure(errorCode, errorMsg);
    }

    @Override
    public void onComplete() {
        DebugUtil.debug(TAG, "onComplete");
    }


    protected abstract void onSuccess(T t);

    /**
     * TODO 对请求失败的统一处理，这里暂时toast
     *
     * @param errorCode
     * @param msg
     */
    protected abstract void onFailure(int errorCode, String msg);

    /**
     * 对通用问题的统一拦截处理
     *
     * @param code
     */
    private final void disposeEorCode(String message, int code) {
        switch (code) {
//            case 996: //签名验证失败
//            case 997: //秘钥不存在
//            case 998: //请求地址失效
            case 999: //TODO token已失效请重新登录
                //退回到登录页面
//                if (mContext != null) {  //Context 可以使Activity BroadCast Service !
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, Oauth_MVP_Activity.class);
//                    mContext.startActivity(intent);
//                }
                break;
        }

        if (mContext != null&& Thread.currentThread().getName().toString().equals(Thread_Main)) {
            Toast.makeText(mContext.getApplicationContext(), message + "   code=" + code, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取详细的错误的信息 errorCode,errorMsg
     * <p>
     * 以登录的时候的Grant_type 故意写错为例子,这个时候的http 应该是直接的返回401=httpException.code()
     * 但是是怎么导致401的？我们的服务器会在respose.errorBody 中的content 中说明
     */
    private final void getErrorMsg(HttpException httpException) {
        String errorBodyStr = "";
        try {      //我们的项目需要的UniCode转码
            errorBodyStr = BusinessUtils.toURLEncoderUTF8(httpException.response().errorBody().string());
        } catch (IOException ioe) {
            Log.e("errorBodyStr ioe:", ioe.toString());
        }
        try {
            BaseBean errorResponse = gson.fromJson(errorBodyStr, BaseBean.class);
            if (null != errorResponse) {
                errorCode = errorResponse.getState();
                errorMsg = errorResponse.getMessage();
            }
        } catch (Exception jsonException) {
            jsonException.printStackTrace();
        }
    }
}