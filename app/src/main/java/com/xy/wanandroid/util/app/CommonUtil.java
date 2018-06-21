package com.xy.wanandroid.util.app;

import android.content.Context;
import android.graphics.Color;
import android.view.inputmethod.InputMethodManager;

import com.xy.wanandroid.base.app.MyApplication;

import java.util.Random;

/**
 * Created by jxy on 2018/6/21.
 */

public class CommonUtil {

    /**
     * 隐藏键盘
     */
    public static void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }
}
