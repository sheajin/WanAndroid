package com.xy.wanandroid.model.api;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public class BaseResp<T> {
    public T data;
    public T results;
    private int errorCode;
    private String errorMsg;
    private int error;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
