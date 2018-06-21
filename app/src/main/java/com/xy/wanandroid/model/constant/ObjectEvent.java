package com.xy.wanandroid.model.constant;

/**
 * Created by jxy on 2018/3/15.
 */

public class ObjectEvent {
    private Object obj;
    private int code;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjectEvent(Object obj, int code) {
        this.obj = obj;
        this.code = code;
    }
}
