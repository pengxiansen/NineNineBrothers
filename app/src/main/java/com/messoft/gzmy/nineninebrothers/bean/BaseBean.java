package com.messoft.gzmy.nineninebrothers.bean;

/**
 * Created by Administrator on 2017/9/22 0022.
 * json返回数据的封装 单个数据
 */

public class BaseBean<T> {
    private int state;
    private String message;
    private T data;    //泛型T来表示object，可能是数组，也可能是对象

    public int getState() {
        return state;
    }

    public boolean isSuccess() {
        if(state==0){
            return true;
        }else {
            return false;
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String error) {
        this.message = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }

    @Override
    public String toString() {
        return "httpResponse{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
