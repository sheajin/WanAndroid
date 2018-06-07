package app.util.normal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class VersionUtil {
    private static final String TAG = "ym";
    private static String PACKAGE = null;

    /**
     * 获取版本号
     */
    public static int getVerCode(Context context) {
        if (PACKAGE == null) {
            PACKAGE = getAppProcessName(context);
        }
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(PACKAGE, 0).versionCode;
            Log.e(TAG, "getVerCode: PACKAGE  "+PACKAGE+"   verCode   "+verCode );
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, "getVerCode: PACKAGE  "+PACKAGE+"   verCode   "+verCode );
        }
        return verCode;
    }

    /**
     * 获取版本名称
     */
    public static String getVerName(Context context) {
        if (PACKAGE == null) {
            PACKAGE = getAppProcessName(context);
        }
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(PACKAGE, 0).versionName;
            Log.e(TAG, "getVerName: PACKAGE  "+PACKAGE+"   verName   "+verName );
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, "getVerName: PACKAGE  "+PACKAGE+"   verName   "+verName );
        }
        return verName;
    }

    /**
     * 获取当前应用程序的包名
     *
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
}
