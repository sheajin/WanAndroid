package com.xy.wanandroid.model.api;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public class GankBaseResp<T> {
    public T results;
    public boolean error;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
