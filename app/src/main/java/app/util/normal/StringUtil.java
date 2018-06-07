package app.util.normal;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/5/18.
 */

public class StringUtil {
    /**
     * 手机号隐藏中间四位数
     */
    public static String hidePhoneNumber(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() < 11)
            return "";
        return phoneNum.substring(0,3) + "****" + phoneNum.substring(7,11);
    }
}
