package com.xy.wanandroid.model.api;


import com.xy.wanandroid.model.constant.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jxy on 2018/1/25.
 */

public class BaseUrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原始的originalRequest
        Request originalRequest = chain.request();
        //获取老的url
        HttpUrl oldUrl = originalRequest.url();
        //获取originalRequest的创建者builder
        Request.Builder builder = originalRequest.newBuilder();
        //获取头信息的集合
        List<String> urlNameList = originalRequest.headers("baseUrl");
        if (urlNameList != null && urlNameList.size() > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader("baseUrl");
            //获取头信息中配置的value
            String urlName = urlNameList.get(0);
            HttpUrl baseURL = null;
            //根据头信息中配置的value,来匹配新的base_url地址
            if (urlName.equals(Constant.WANANDROID)) {
                baseURL = HttpUrl.parse(AppConfig.BASE_URL);
            } else if (urlName.equals(Constant.DOUYU)) {
                baseURL = HttpUrl.parse(AppConfig.DOUYU_URL);
            } else if (urlName.equals(Constant.GETLIVE)) {
                baseURL = HttpUrl.parse(AppConfig.GETLIVE_URL);
            }
            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .build();
            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        } else {
            return chain.proceed(originalRequest);
        }
    }


}
