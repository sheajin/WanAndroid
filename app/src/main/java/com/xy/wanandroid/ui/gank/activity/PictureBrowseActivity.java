package com.xy.wanandroid.ui.gank.activity;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.model.api.AppConfig;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.gank.PicturePresenter;
import com.xy.wanandroid.util.app.ToastUtil;
import com.xy.wanandroid.util.statusbar.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class PictureBrowseActivity extends BaseActivity<PicturePresenter> {

    @BindView(R.id.image_view)
    PhotoView mPhotoView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.viewBackground)
    View viewBackground;

    private PicturePresenter presenter;
    private String url;
    private RxPermissions rxPermissions;
    private boolean isLoadSuccess = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_browse;
    }

    @Override
    protected void initUI() {
        StatusBarUtil.hideStatusBar(activity);
        presenter = new PicturePresenter();
        rxPermissions = new RxPermissions(activity);
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra(Constant.BIGPICTURE);
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop();
        Glide.with(activity)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ToastUtil.show(context, getString(R.string.resource_fail));
                        isLoadSuccess = false;
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        setGone(viewBackground);
                        isLoadSuccess = true;
                        return false;
                    }
                })
                .apply(options)
                .into(mPhotoView);
    }

    @OnClick(R.id.tv_save)
    void click() {
        if (isLoadSuccess) {
            if (!rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new RxPermissions(activity)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(isGranted -> {
                            if (isGranted) {
                                presenter.downloadFile(url);
                            } else {
                                ToastUtil.show(context, getString(R.string.open_storage));
                            }
                        });
            } else {
                presenter.downloadFile(url);
            }
        }
    }

    @Override
    public void onBackPressedSupport() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressbar = null;
    }
}
