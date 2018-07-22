package com.xy.wanandroid.model.api;

import android.util.ArrayMap;

import com.xy.wanandroid.model.constant.Constant;


/**
 * Created by jxy on 2018/7/20.
 */
public class ParamsUtil {

    public static ArrayMap<String, String> getCommonParams() {
        ArrayMap<String, String> commonMap = new ArrayMap<>();
        commonMap.put(Constant.PARAMS1, Constant.VALUE1);
        commonMap.put(Constant.PARAMS2, Constant.VALUE2);
        commonMap.put(Constant.PARAMS3, String.valueOf(System.currentTimeMillis()));
        return commonMap;
    }
}
