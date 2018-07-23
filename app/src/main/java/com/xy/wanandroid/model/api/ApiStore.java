package com.xy.wanandroid.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xy.wanandroid.model.cookie.CookiesManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public class ApiStore {

    private static Retrofit retrofit;
    private static String baseUrl = AppConfig.BASE_URL;

    static {
        createProxy();
    }

    public static <T> T createApi(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static void createProxy() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd HH:mm:ss").create();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .cookieJar(new CookiesManager());

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }


}
