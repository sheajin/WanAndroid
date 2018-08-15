package com.xy.wanandroid.model.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.xy.wanandroid.base.app.MyApplication;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jxy on 2018/8/15.
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            List<String> d = originalResponse.headers("Set-Cookie");
//                Log.e("jing", "------------得到的 cookies:" + d.toString());

            // 返回cookie
            if (!TextUtils.isEmpty(d.toString())) {

                SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorConfig = sharedPreferences.edit();
                String oldCookie = sharedPreferences.getString("cookie", "");

                HashMap<String, String> stringStringHashMap = new HashMap<>();

                // 之前存过cookie
                if (!TextUtils.isEmpty(oldCookie)) {
                    String[] substring = oldCookie.split(";");
                    for (String aSubstring : substring) {
                        if (aSubstring.contains("=")) {
                            String[] split = aSubstring.split("=");
                            stringStringHashMap.put(split[0], split[1]);
                        } else {
                            stringStringHashMap.put(aSubstring, "");
                        }
                    }
                }
                String join = StringUtils.join(d, ";");
                String[] split = join.split(";");

                // 存到Map里
                for (String aSplit : split) {
                    String[] split1 = aSplit.split("=");
                    if (split1.length == 2) {
                        stringStringHashMap.put(split1[0], split1[1]);
                    } else {
                        stringStringHashMap.put(split1[0], "");
                    }
                }

                // 取出来
                StringBuilder stringBuilder = new StringBuilder();
                if (stringStringHashMap.size() > 0) {
                    for (String key : stringStringHashMap.keySet()) {
                        stringBuilder.append(key);
                        String value = stringStringHashMap.get(key);
                        if (!TextUtils.isEmpty(value)) {
                            stringBuilder.append("=");
                            stringBuilder.append(value);
                        }
                        stringBuilder.append(";");
                    }
                }

                editorConfig.putString("cookie", stringBuilder.toString());
                editorConfig.apply();
//                    Log.e("jing", "------------处理后的 cookies:" + stringBuilder.toString());
            }
        }

        return originalResponse;
    }
}
