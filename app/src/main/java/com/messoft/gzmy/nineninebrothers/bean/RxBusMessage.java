package com.messoft.gzmy.nineninebrothers.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26 0026.
 * RxBus ben
 */

public class RxBusMessage<T> implements Serializable {

    private int type;
    private int i;
    private T data;

    public RxBusMessage(T data) {
        this.data = data;
    }

    public RxBusMessage(int type, T data) {
        this.type = type;
        this.data = data;
    }

    public RxBusMessage(int type, int i) {
        this.type = type;
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
