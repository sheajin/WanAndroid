package com.xy.wanandroid.util.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description:
 * Created by PangHaHa on 18-7-25.
 * Copyright (c) 2018 PangHaHa All rights reserved.
 * <p>
 * /**
 * <p>
 * 需要清楚：一个MIUI版本对应小米各种机型，基于不同的安卓版本，但是权限设置页跟MIUI版本有关
 * 测试TYPE_TOAST类型：
 * 7.0：
 * 小米      5        MIUI8         -------------------- 失败
 * 小米   Note2       MIUI9         -------------------- 失败
 * 6.0.1
 * 小米   5                         -------------------- 失败
 * 小米   红米note3                  -------------------- 失败
 * 6.0：
 * 小米   5                         -------------------- 成功
 * 小米   红米4A      MIUI8         -------------------- 成功
 * 小米   红米Pro     MIUI7         -------------------- 成功
 * 小米   红米Note4   MIUI8         -------------------- 失败
 * <p>
 * 经过各种横向纵向测试对比，得出一个结论，就是小米对TYPE_TOAST的处理机制毫无规律可言！
 * 跟Android版本无关，跟MIUI版本无关，addView方法也不报错
 * 所以最后对小米6.0以上的适配方法是：不使用 TYPE_TOAST 类型，统一申请权限
 */

public class MiUI {
    private static final String miui = "ro.miui.ui.version.name";
    private static final String miui5 = "V5";
    private static final String miui6 = "V6";
    private static final String miui7 = "V7";
    private static final String miui8 = "V8";
    private static final String miui9 = "V9";

    public static boolean rom() {
        return Build.MANUFACTURER.equals("Xiaomi");
    }

    private static String getProp() {
        return Rom.getProp(miui);
    }

    public static void req(final Context context) {
        switch (getProp()) {
            case miui5:
                reqForMiui5(context);
                break;
            case miui6:
            case miui7:
                reqForMiui67(context);
                break;
            case miui8:
            case miui9:
                reqForMiui89(context);
                break;
        }
    }

    private static void reqForMiui5(Context context) {
        String packageName = context.getPackageName();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Rom.isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }
    }

    private static void reqForMiui67(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Rom.isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }
    }

    private static void reqForMiui89(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Rom.isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setPackage("com.miui.securitycenter");
            intent.putExtra("extra_pkgname", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Rom.isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            }
        }
    }

    /**
     * 有些机型在添加TYPE-TOAST类型时会自动改为TYPE_SYSTEM_ALERT，通过此方法可以屏蔽修改
     * 但是...即使成功显示出悬浮窗，移动的话也会崩溃
     */
    private static void addViewToWindow(WindowManager wm, View view, WindowManager.LayoutParams params) {
        setMiUI_International(true);
        wm.addView(view, params);
        setMiUI_International(false);
    }

    private static void setMiUI_International(boolean flag) {
        try {
            Class BuildForMi = Class.forName("miui.os.Build");
            Field isInternational = BuildForMi.getDeclaredField("IS_INTERNATIONAL_BUILD");
            isInternational.setAccessible(true);
            isInternational.setBoolean(null, flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rom {

        static boolean isIntentAvailable(Intent intent, Context context) {
            return intent != null && context.getPackageManager().queryIntentActivities(
                    intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
        }

        static String getProp(String name) {
            BufferedReader input = null;
            try {
                Process p = Runtime.getRuntime().exec("getprop " + name);
                input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
                String line = input.readLine();
                input.close();
                return line;
            } catch (IOException ex) {
                return null;
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static boolean hasPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            return hasPermissionBelowMarshmallow(context);
        }
    }

    /**
     * 6.0以下判断是否有权限
     * 理论上6.0以上才需处理权限，但有的国内rom在6.0以下就添加了权限
     * 其实此方式也可以用于判断6.0以上版本，只不过有更简单的canDrawOverlays代替
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static boolean hasPermissionBelowMarshmallow(Context context) {
        try {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Method dispatchMethod = AppOpsManager.class.getMethod("checkOp", int.class, int.class, String.class);
            //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
            return AppOpsManager.MODE_ALLOWED == (Integer) dispatchMethod.invoke(
                    manager, 24, Binder.getCallingUid(), context.getApplicationContext().getPackageName());
        } catch (Exception e) {
            return false;
        }
    }

}
