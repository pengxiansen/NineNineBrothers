package com.messoft.gzmy.nineninebrothers.http;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 用于数据请求的回调
 */

public interface RequestImpl {
    void loadSuccess(Object object);

    void loadFailed(int errorCode,String errorMessage);

//    void loadCompleted();

//    void addDisposable(Disposable disposable);
}
