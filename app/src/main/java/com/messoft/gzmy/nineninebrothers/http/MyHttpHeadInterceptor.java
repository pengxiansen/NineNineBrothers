package com.messoft.gzmy.nineninebrothers.http;

import android.content.Context;

import com.example.http.utils.CheckNetwork;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.InterfaceAuthenUtils;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.TimeUtils;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class MyHttpHeadInterceptor implements Interceptor {

    private Context context;

    public MyHttpHeadInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();

        //获取到第一个action
        String action = oldRequest.url().queryParameterValue(0);
        if (StringUtils.isNoEmpty(BusinessUtils.getToken())) {
            //要签名
            //拿到参数--封装成json
            String data = null;
            try {
                if (oldRequest.body() instanceof FormBody) {
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oldFormBody = (FormBody) oldRequest.body();
                    for (int i = 0; i < oldFormBody.size(); i++) {
                        //拿到data的值
                        if (oldFormBody.name(i).equals("data")) {
                            //这里data直接getValue
                            data = oldFormBody.value(i);
                            DebugUtil.debug("MyHttpHeadInterceptor","data参数："+data);
                        }
//                        newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
//                        newFormBody.addEncoded(oldFormBody.name(i), oldFormBody.value(i));
                        newFormBody.add(URLEncoder.encode(oldFormBody.name(i), "UTF-8"),URLEncoder.encode(oldFormBody.value(i),"UTF-8"));
                    }
                    //要签名
                    String sign = InterfaceAuthenUtils.signTopRequestNew(
                            action,
                            data,
                            TimeUtils.getCurrentTime(),
                            BusinessUtils.getToken(),
                            BusinessUtils.getSecret());
                    DebugUtil.debug("MyHttpHeadInterceptor", "action参数:"+action+"--data参数：" + data+"--sign参数："+sign);
                    newFormBody.add("appKey", "");
                    newFormBody.add("timestamp", TimeUtils.getCurrentTime());
                    newFormBody.add("sign", sign);
                    newFormBody.add("token", BusinessUtils.getToken());
                    builder.method(oldRequest.method(), newFormBody.build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //加头
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
