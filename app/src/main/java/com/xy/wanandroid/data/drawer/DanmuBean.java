package com.xy.wanandroid.data.drawer;

/**
 * Created by jxy on 2018/7/26.
 */

public class DanmuBean {

    public DanmuBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String name;
    public String content;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
