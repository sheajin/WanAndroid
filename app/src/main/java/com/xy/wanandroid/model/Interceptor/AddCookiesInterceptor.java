package com.xy.wanandroid.model.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.xy.wanandroid.base.app.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jxy on 2018/8/15.
 */

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
        String cookie = sharedPreferences.getString("cookie", "");
        builder.addHeader("Cookie", cookie);
        return chain.proceed(builder.build());
    }
}
