package com.xy.wanandroid.model.constant;

/**
 * Created by jxy on 2018/3/12.
 */

public class MessageEvent {

    public int code;
    public String mess;
    private String name;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public MessageEvent(int code, String name, String mess) {
        this.code = code;
        this.name = name;
        this.mess = mess;
    }

}
