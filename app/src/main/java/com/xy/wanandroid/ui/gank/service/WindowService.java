package com.xy.wanandroid.ui.gank.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.media.IjkVideoView;

/**
 * Created by jxy on 2018/10/30
 */
public class WindowService extends Service {

    private WindowManager manager;
    private View floatingWindowView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    private MyBinder myBinder = new MyBinder();

    public class MyBinder extends Binder {

        public WindowService getService() {
            return WindowService.this;
        }
    }

    /**
     * 展示悬浮窗View
     */
    private void showFloatingView() {
        manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        floatingWindowView = View.inflate(MyApplication.getInstance(), R.layout.view_floating_window, null);
        IjkVideoView videoView = floatingWindowView.findViewById(R.id.video_view);

    }
}
