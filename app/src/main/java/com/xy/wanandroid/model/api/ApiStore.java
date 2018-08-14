package com.xy.wanandroid.model.api;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xy.wanandroid.model.cookie.CookiesManager;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
                .sslSocketFactory(getSSLSocketFactory())
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .hostnameVerifier((hostname, session) -> true)
                .cookieJar(new CookiesManager());

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
