package com.messoft.gzmy.nineninebrothers.http;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 用于数据请求的回调
 */

public interface RequestImpl {
    void loadSuccess(Object object);

    void loadFailed();

//    void loadCompleted();

    void addDisposable(Disposable disposable);
}
