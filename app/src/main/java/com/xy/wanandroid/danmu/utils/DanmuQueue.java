package com.xy.wanandroid.danmu.utils;

import com.xy.wanandroid.data.gank.DanmuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxy on 2018/7/26.
 */

public class DanmuQueue {

    private static DanmuQueue queue;
    private List<DanmuBean> danmuList = new ArrayList<>();

    public static DanmuQueue getInstance() {
        queue = new DanmuQueue();
        return queue;
    }

    public void addDanmu(String name, String content) {
        DanmuBean danmuBean = new DanmuBean(name, content);
        danmuList.add(danmuBean);
    }

    public void start() {
        if (danmuList.size() > 0) {
            DanmuBean danmuBean = danmuList.remove(0);
//            EventBus.getDefault().post(new DanmuEvent(EventConstant.SENDDANMU, danmuBean));
        }
    }

}
