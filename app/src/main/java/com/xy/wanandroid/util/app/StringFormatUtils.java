package com.xy.wanandroid.util.app;

import com.xy.wanandroid.data.gank.MovieDetailBean;

/**
 * Created by jxy on 2018/9/27
 */
public class StringFormatUtils {

    public static String getActor(MovieDetailBean bean) {
        StringBuilder sb = new StringBuilder();
        for (MovieDetailBean.CastsBean castsBean : bean.getCasts()) {
            sb.append(castsBean.getName());
            sb.append("/");
        }
        String text = sb.toString();
        if (text.endsWith("/")) {
            return text.substring(0, text.length() - 1);
        }
        return "";
    }

    public static String getType(MovieDetailBean bean) {
        StringBuilder sb = new StringBuilder();
        for (String s : bean.getGenres()) {
            sb.append(s);
            sb.append("/");
        }
        String text = sb.toString();
        if (text.endsWith("/")) {
            return text.substring(0, text.length() - 1);
        }
        return "";
    }

}
