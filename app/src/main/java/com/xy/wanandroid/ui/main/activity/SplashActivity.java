package com.xy.wanandroid.ui.main.activity;

import android.view.View;
import android.widget.ImageView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * splash页
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.image_bg)
    ImageView imageBackground;
    private Disposable timer;
    private int time = 3;
    private boolean isIn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initUI() {
        GlideUtil.loadImage(MyApplication.getInstance(), R.drawable.splash_cat, imageBackground);
    }

    @Override
    protected void initData() {
        timer = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    // aLong -> 0,1,2
                    if (time - aLong == 1 && !timer.isDisposed()) {
                        jump();
                    }
                });
    }

    @OnClick(R.id.tv_time)
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                if (!timer.isDisposed()) {
                    timer.dispose();
                }
                jump();
                break;
            default:
                break;
        }
    }

    private void jump() {
        if (isIn) {
            return;
        }
        JumpUtil.overlay(context, MainActivity.class);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!timer.isDisposed() && timer != null) {
            timer.dispose();
            timer = null;
        }
    }
}
