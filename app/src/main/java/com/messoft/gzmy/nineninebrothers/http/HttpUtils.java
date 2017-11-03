package com.messoft.gzmy.nineninebrothers.http;

import android.content.Context;

import com.example.http.HttpHead;
import com.example.http.IpmlTokenGetListener;
import com.example.http.NullOnEmptyConverterFactory;
import com.example.http.ParamNames;
import com.example.http.utils.CheckNetwork;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.InterfaceAuthenUtils;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.TimeUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类
 */

public class HttpUtils {
    private static HttpUtils instance;
    private Gson gson;
    private Context context;
    private Object ohHttps;
    private IpmlTokenGetListener listener;
    private boolean debug; //正式包关闭
    /**
     * 分页数据，每页的数量
     */
    public static int per_page = 10;
//    public static int per_page_more = 20;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context context, boolean debug) {
        this.context = context;
        this.debug = debug;
        HttpHead.init(context);
    }


    public <T> T getNineServer(Class<T> a) {
        if (ohHttps == null) {
            synchronized (HttpUtils.class) {
                if (ohHttps == null) {
                    ohHttps = getBuilder(ConstantsUrl.MASTER_URL).build().create(a);
                }
            }
        }
        return (T) ohHttps;
    }


    private Retrofit.Builder getBuilder(String apiUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient());
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(new NullOnEmptyConverterFactory());
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder;
    }


    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }


    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    public OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpHeadInterceptor());
            okBuilder.addInterceptor(getInterceptor());
            okBuilder.sslSocketFactory(sslSocketFactory);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
//                    Log.d("HttpUtils", "==come");
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public OkHttpClient getOkClient() {
        OkHttpClient client1;
        client1 = getUnsafeOkHttpClient();
        return client1;
    }

    public void setTokenListener(IpmlTokenGetListener listener) {
        this.listener = listener;
    }


    class HttpHeadInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request request;
            Request.Builder builder;

            //拿到url做处理
            String action = originalRequest.url().queryParameterValue(0);
            String data = originalRequest.url().queryParameterValue(1);
            if (!StringUtils.isNoEmpty(BusinessUtils.getToken())) {
                //不签名
                builder = originalRequest.newBuilder();
            } else {
                //要签名
                String sign = InterfaceAuthenUtils.signTopRequestNew(
                        action,
                        data,
                        TimeUtils.getCurrentTime(),
                        BusinessUtils.getToken(),
                        BusinessUtils.getSecret());
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        //appKey预留个空
                        .addQueryParameter("appKey", "")
                        .addQueryParameter("timestamp", TimeUtils.getCurrentTime())
                        .addQueryParameter("sign", sign)
                        .addQueryParameter("token", BusinessUtils.getToken())
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                builder = request.newBuilder();
            }
            DebugUtil.debug("HttpHeadInterceptor","请求地址："+originalRequest.url().toString());
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

    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (debug) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return interceptor;
    }
}
