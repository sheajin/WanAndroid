package com.xy.wanandroid.model.api;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.model.Interceptor.AddCookiesInterceptor;
import com.xy.wanandroid.model.Interceptor.BaseUrlInterceptor;
import com.xy.wanandroid.model.Interceptor.HttpLoggingInterceptor;
import com.xy.wanandroid.model.Interceptor.ReceivedCookiesInterceptor;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
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

        //设置缓存目录
        File cacheFile = new File(MyApplication.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        //配置OkHttpBuilder
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                //信任证书
                .sslSocketFactory(getSSLSocketFactory())
                //设置超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .cache(cache)
                //错误重连
                .retryOnConnectionFailure(true)
                //(错误原因是验证证书时发现真正请求和服务器的证书域名不一致) 此代码为忽略hostname 的验证
                .hostnameVerifier((hostname, session) -> true)
                //Cookie 豆瓣API会无法调用,还没搞懂 使用下面的
                //.cookieJar(new CookiesManager())
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .client(builder.build())
                .build();
    }

    @SuppressLint("TrustAllX509TrustManager")
    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    try {
                        chain[0].checkValidity();
                    } catch (Exception e) {
                        throw new CertificateException("Certificate not valid or trusted.");
                    }
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            return null;
        }
    }

}
