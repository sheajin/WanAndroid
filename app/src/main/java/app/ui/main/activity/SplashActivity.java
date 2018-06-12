package app.ui.main.activity;

import android.view.View;
import android.widget.ImageView;

import com.xy.wanandroid.R;

import java.util.concurrent.TimeUnit;

import app.base.activity.BaseActivity;
import app.base.app.MyApplication;
import app.util.app.JumpUtil;
import app.util.glide.GlideUtil;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.image_bg)
    ImageView imageBackground;
    private Disposable timer;
    private int time = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initUI() {
        GlideUtil.loadImage(MyApplication.getInstance(), R.drawable.splash, imageBackground);
    }

    @Override
    protected void initData() {
        timer = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (time - aLong == 1 && !timer.isDisposed()) {    //aLong -> 0,1,2
                        jump();
                    }
                });
    }

    @OnClick(R.id.tv_time)
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                if (!timer.isDisposed())
                    timer.dispose();
                jump();
                break;
        }
    }

    private void jump() {
        JumpUtil.overlay(context, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
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
