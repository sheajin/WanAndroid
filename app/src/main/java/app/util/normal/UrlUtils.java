package app.util.normal;

import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/6/2.
 */

public class UrlUtils {
    public static String urlEncode(String url){
        String str = null;
        try {
            str = new String(url.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
        } catch (Exception localException) {
            Log.e("ym", "onNoDoubleClick: "+localException.getMessage() );
        }
        return str;
    }
}
