package com.xy.wanandroid.model.constant;

/**
 * Created by jxy on 2018/3/12.
 */

public class MessageEvent {

    public int code;
    public String mess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public MessageEvent(int code, String mess) {
        this.code = code;
        this.mess = mess;
    }
}
