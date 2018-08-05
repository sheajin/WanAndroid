package com.xy.wanandroid.data.drawer;

import java.util.List;

/**
 * Created by jxy on 2018/8/5.
 */
public class RecommendData {
    /**
     * _id : 5b62b9569d21225e0b0777f6
     * createdAt : 2018-08-02T15:57:10.293Z
     * desc : 一个通用的AndroidShapeButton。
     * images : ["https://ww1.sinaimg.cn/large/0073sXn7gy1ftwegqdb7jg30av0hmthu"]
     * publishedAt : 2018-08-03T00:00:00.0Z
     * source : api
     * type : Android
     * url : https://github.com/michaelxs/Android-CommonShapeButton
     * used : true
     * who : lijinshan
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
