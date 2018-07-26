package com.xy.wanandroid.util.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.view.View;
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
        int red = random.nextInt(150);
        //0-190
        int green = random.nextInt(150);
        //0-190
        int blue = random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }

    /**
     * 计算当前观看人数
     */
    public static String getOnlineNum(int num) {
        String online = "";
        if (num > 10000) {
            StringBuilder sb = new StringBuilder();
            sb.append(num / 10000);
            sb.append(".");
            if (num % 10000 == 0) {
                sb.append("0");
            } else {
                sb.append(num % 10000 / 1000);
            }
            sb.append("万");
            online = String.valueOf(sb);
        } else if (num > 0 && num < 10000) {
            online = String.valueOf(num);
        }
        return online;
    }

    /**
     * 获得当前屏幕亮度值 0--255
     */
    public static int getScreenBrightness(Activity activity) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示虚拟按键
     */
    public static void showBottomUIMenu(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    /**
     * 控制虚拟按键的显示或隐藏
     */
    public static void showNavigationBar(Activity activity, boolean show) {
        try {
            if (show) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
