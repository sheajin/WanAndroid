package app.util.app;

import android.os.Bundle;

/**
 * Created by yangmu on 2017/7/13.
 */

public class BundleUtil {
    /***/
    public static Bundle putIntValue(String key, int value) {
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        return bundle;
    }

    public static Bundle putIntValue(String key, int value, Bundle bundle) {
        bundle.putInt(key, value);
        return bundle;
    }

    /***/
    public static Bundle putStringValue(String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        return bundle;
    }

    public static Bundle putStringValue(String key, String value, Bundle bundle) {
        bundle.putString(key, value);
        return bundle;
    }

    /***/
    public static Bundle putBoolenValue(String key, boolean value) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(key, value);
        return bundle;
    }

    public static Bundle putBoolenValue(String key, boolean value, Bundle bundle) {
        bundle.putBoolean(key, value);
        return bundle;
    }

    /***/
    public static Bundle putByteValue(String key, Byte value) {
        Bundle bundle = new Bundle();
        bundle.putByte(key, value);
        return bundle;
    }

    public static Bundle putByteValue(String key, Byte value, Bundle bundle) {
        bundle.putByte(key, value);
        return bundle;
    }

    /***/
    public static Bundle putByteArray(String key, byte[] value) {
        Bundle bundle = new Bundle();
        bundle.putByteArray(key, value);
        return bundle;
    }

    public static Bundle putByteArray(String key, byte[] value, Bundle bundle) {
        bundle.putByteArray(key, value);
        return bundle;
    }
}
