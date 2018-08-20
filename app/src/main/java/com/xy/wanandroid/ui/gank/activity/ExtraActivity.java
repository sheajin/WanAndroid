package com.xy.wanandroid.ui.gank.activity;

import android.content.Context;
import android.os.Environment;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.util.cache.DiskLruCache;

import java.io.File;
import java.io.IOException;

public class ExtraActivity extends BaseActivity {

    private String url = "http://img.my.csdn.NET/uploads/201309/01/1378037235_7476.jpg";
    private DiskLruCache diskLruCache;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_extra;
    }

    @Override
    protected void initUI() {
        File cacheDir = getDiskCacheDir(activity, "bitmap");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        try {
            diskLruCache = DiskLruCache.open(cacheDir, 1, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {

    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
