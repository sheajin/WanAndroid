package app.util.normal;

/**
 * Created by Administrator on 2017/4/17.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 跟网络相关的工具类
 *
 *
 *
 */
public class NetUtil {
    private static final String TAG = "NetUtil";
    public static final int NETWORK_TYPE_NONE = -0x1; // 断网情况
    public static final int NETWORK_TYPE_WIFI = 0x1; // WIFI模式
    public static final int NETWOKR_TYPE_MOBILE = 0x2; // GPRS模式

    private NetUtil()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getCurrentNetType(Context mContext) {
        ConnectivityManager connManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI); // WIFI
        NetworkInfo gprs = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // GPRS

        if (wifi != null && wifi.getState() == NetworkInfo.State.CONNECTED) {
            Log.d(TAG, "Current net type:  WIFI");
            return NETWORK_TYPE_WIFI;
        } else if (gprs != null && gprs.getState() == NetworkInfo.State.CONNECTED) {
            Log.d(TAG, "Current net type:  GPRS");
            return NETWOKR_TYPE_MOBILE;
        }
        Log.e(TAG, "Current net type:  NONE");
        return NETWORK_TYPE_NONE;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}