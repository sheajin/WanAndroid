package com.xy.wanandroid.util.storage;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;


/**
 * Created by Yangmu on 17/1/22.
 */

public class CacheMannger {
    private static final String TAG = "ym";
    private static SdCardUtils sdCard = new SdCardUtils();
    private static int i = 0;
//    应用缓存目录
    private static String savePath = "";
//    private static String savePath = AppConfig.SAVEDFILE_LOCATION;
    //清除应用缓存（主要是图片）
    public static void clearCache(){
        File file = new File(savePath);
        if (!file.exists()){
            file.mkdirs();
        }
        clearFile(file);
    }
    //获取缓存文件夹内存大小
    public static String getCacheSize(Context c){

        File file = new File(savePath);
        if (!file.exists()){
            file.mkdirs();
        }
        Log.i(TAG, "getCacheSize: "+file.getAbsolutePath());
        long totalSize = sdCard.getFolderSize(file);
        String size = Formatter.formatFileSize(c, totalSize);
        return size;
    }

    public static void clearFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                clearFile(f);
            }
            file.delete();
        }
    }

}
