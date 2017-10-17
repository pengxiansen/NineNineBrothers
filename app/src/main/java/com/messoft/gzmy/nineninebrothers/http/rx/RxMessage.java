package com.messoft.gzmy.nineninebrothers.http.rx;

/**
 * Created by Administrator on 2017/10/16 0016.
 */

public class RxMessage<T> {
    private T data;

    private String mMsg;
    private int type;
    private int request;

    public RxMessage(T data) {
        this.data = data;
    }

    public RxMessage(int request, int type, String msg) {
        this.type = type;
        this.mMsg = msg;
        this.request = request;
    }
    public String getMsg(){
        return mMsg;
    }
    public int getType(){
        return type;
    }
    public int getRequest(){ return request; }

    public T getData(){return data;}
}
