package com.xy.wanandroid.model.api;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public class BaseResp<T> {
    public T data;
    public T result;
    private int errorCode;
    private int error_code;
    private String errorMsg;
    private int error;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getError_code() {
        return error_code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
