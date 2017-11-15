package com.messoft.gzmy.nineninebrothers.http;

import android.content.Context;

import com.example.http.utils.CheckNetwork;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.InterfaceAuthenUtils;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.TimeUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class MyHttpInterceptor implements Interceptor {

    private Context context;

    public MyHttpInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;
        Request.Builder builder = null;

        //获取到第一个action
        String action = originalRequest.url().queryParameterValue(0);
        if (StringUtils.isNoEmpty(BusinessUtils.getToken())) {
            //签名
            String data = null;
            if (originalRequest.body() instanceof FormBody) {
                FormBody oldFormBody = (FormBody) originalRequest.body();
                for (int i = 0; i < oldFormBody.size(); i++) {
                    //拿到data的值
                    if (oldFormBody.name(i).equals("data")) {
                        //这里data直接getValue
                        data = oldFormBody.value(i);
                        DebugUtil.debug("MyHttpHeadInterceptor", "data参数：" + data);
                    }
                }
                //要签名
                String mSign = InterfaceAuthenUtils.signTopRequestNew(
                        action,
                        data,
                        TimeUtils.getCurrentTime(),
                        BusinessUtils.getToken(),
                        BusinessUtils.getSecret());
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("appKey", "")
                        .addQueryParameter("timestamp", TimeUtils.getCurrentTime())
                        .addQueryParameter("sign", mSign)
                        .addQueryParameter("token", BusinessUtils.getToken())
                        .build();
                DebugUtil.debug("MyHttpInterceptor","签名参数：timestamp："+TimeUtils.getCurrentTime()+"--sign"+
                        mSign+"--token:"+BusinessUtils.getToken()+"--data:"+data);

                request = originalRequest.newBuilder().url(modifiedUrl).build();
                builder = request.newBuilder();
            }
        } else {
            //不签名
            builder = originalRequest.newBuilder();
        }

        builder.addHeader("Accept", "application/json;versions=1");
        if (CheckNetwork.isNetworkConnected(context)) {
            int maxAge = 60;
            builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
        } else {
            int maxStale = 60 * 60 * 24 * 28;
            builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
        }
        return chain.proceed(builder.build());
    }
}
