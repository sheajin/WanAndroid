package com.xy.wanandroid.ui.mine.activity;

import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.widget.ElasticOutInterpolator;

import java.io.FileInputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by jxy on 2018/6/17.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.about_us_mountain)
    MountainSceneView mAboutUsMountain;
    @BindView(R.id.about_us_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.about_us_toolbar_layout)
    CollapsingToolbarLayout mAboutUsToolbarLayout;
    @BindView(R.id.about_us_app_bar)
    AppBarLayout mAboutUsAppBar;
    @BindView(R.id.about_us_fly_refresh)
    FlyRefreshHeader mFlyRefreshHeader;
    @BindView(R.id.about_us_refresh_layout)
    SmartRefreshLayout mAboutUsRefreshLayout;
    @BindView(R.id.about_us_fab)
    FloatingActionButton mAboutUsFab;
    @BindView(R.id.about_us_fly_view)
    FlyView mAboutUsFlyView;
    @BindView(R.id.about_us_content)
    NestedScrollView mScrollView;
    @BindView(R.id.aboutContent)
    TextView mAboutContent;
    @BindView(R.id.aboutVersion)
    TextView mAboutVersion;
    private View.OnClickListener mThemeListener;
    private String path;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {
        showAboutContent();
        setSmartRefreshLayout();
        //进入界面时自动刷新
        mAboutUsRefreshLayout.autoRefresh();
        //点击悬浮按钮时自动刷新
        mAboutUsFab.setOnClickListener(v -> mAboutUsRefreshLayout.autoRefresh());
        //监听 AppBarLayout 的关闭和开启 给 FlyView（纸飞机） 和 ActionButton 设置关闭隐藏动画
        mAboutUsAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean misAppbarExpand = true;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
                double minFraction = 0.1;
                double maxFraction = 0.8;
                if (mScrollView == null || mAboutUsFab == null || mAboutUsFlyView == null) {
                    return;
                }
                if (fraction < minFraction && misAppbarExpand) {
                    misAppbarExpand = false;
                    mAboutUsFab.animate().scaleX(0).scaleY(0);
                    mAboutUsFlyView.animate().scaleX(0).scaleY(0);
                    ValueAnimator animator = ValueAnimator.ofInt(mScrollView.getPaddingTop(), 0);
                    animator.setDuration(300);
                    animator.addUpdateListener(animation ->
                            mScrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0));
                    animator.start();
                }
                if (fraction > maxFraction && !misAppbarExpand) {
                    misAppbarExpand = true;
                    mAboutUsFab.animate().scaleX(1).scaleY(1);
                    mAboutUsFlyView.animate().scaleX(1).scaleY(1);
                    ValueAnimator animator = ValueAnimator.ofInt(mScrollView.getPaddingTop(), DensityUtil.dp2px(25));
                    animator.setDuration(300);
                    animator.addUpdateListener(animation ->
                            mScrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0));
                    animator.start();
                }
            }
        });
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void setSmartRefreshLayout() {
        //绑定场景和纸飞机
        mFlyRefreshHeader.setUp(mAboutUsMountain, mAboutUsFlyView);
        mAboutUsRefreshLayout.setReboundInterpolator(new ElasticOutInterpolator());
        mAboutUsRefreshLayout.setReboundDuration(800);
        mAboutUsRefreshLayout.setOnRefreshListener(refreshLayout -> {
            updateTheme();
            refreshLayout.finishRefresh(1000);
        });

        //设置让Toolbar和AppBarLayout的滚动同步
        mAboutUsRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
//            @Override
//            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
//                if (mAboutUsAppBar == null || mToolbar == null) {
//                    return;
//                }
//                mAboutUsAppBar.setTranslationY(offset);
//                mToolbar.setTranslationY(-offset);
//            }

//            @Override
//            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int extendHeight) {
//                if (mAboutUsAppBar == null || mToolbar == null) {
//                    return;
//                }
//                mAboutUsAppBar.setTranslationY(offset);
//                mToolbar.setTranslationY(-offset);
//            }
        });
    }

    private void showAboutContent() {
        mAboutContent.setText(Html.fromHtml(getString(R.string.about_content)));
        mAboutContent.setMovementMethod(LinkMovementMethod.getInstance());
        try {
            String versionStr = getString(R.string.app_name)
                    + " V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            mAboutVersion.setText(versionStr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update appbar theme
     */
    private void updateTheme() {
        if (mThemeListener == null) {
            mThemeListener = new View.OnClickListener() {
                int index = 0;
                int[] ids = new int[]{
                        R.color.colorPrimary,
                        android.R.color.holo_green_light,
                        android.R.color.holo_red_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright,
                };

                @Override
                public void onClick(View v) {
                    int color = ContextCompat.getColor(getApplication(), ids[index % ids.length]);
                    mAboutUsRefreshLayout.setPrimaryColors(color);
                    mAboutUsFab.setBackgroundColor(color);
                    mAboutUsFab.setBackgroundTintList(ColorStateList.valueOf(color));
                    mAboutUsToolbarLayout.setContentScrimColor(color);
                    index++;
                }
            };
        }
        mThemeListener.onClick(null);
    }

    String url = "http://video.ahaschool.com/1a9389308d414a4882d31f037c2e96e4/a5abe6c200d64ff6a3aefc3655781cf4-4b6ffae84f2e1d243955ecaedcf11a3e.m3u8";
    String urls = "http://video.ahaschool.com/a5abe6c200d64ff6a3aefc3655781cf4-4b6ffae84f2e1d243955ecaedcf11a3e-00042.mp4";
//    String urls = "http://video.ahaschool.com/a5abe6c200d64ff6a3aefc36.ts";
    @OnClick(R.id.image)
    void click() {
        readFile();
        LogUtil.e("readFile \n" + readFile());
        getFile();
    }

    private void getFile() {
        ApiStore.createApi(ApiService.class)
                .download(urls)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {

//                        path = CommonUtil.getDownloadPath("/files");   //设置下载路径
//                        File file = new File(path, "file.txt");   //设置文件名
//                        if (!file.exists()) {
//                            InputStream is = null;
//                            FileOutputStream fos = null;
//                            byte[] buffer = new byte[2048];
//                            long total = responseBody.contentLength();
//                            try {
//                                int len = 0;
//                                long current = 0;
//                                int progress = 0;
//                                is = responseBody.byteStream();
//                                fos = new FileOutputStream(file);
//                                while ((len = is.read(buffer)) != -1) {
//                                    fos.write(buffer, 0, len);
//                                    current += len;
//                                    progress = (int) (current * 100 / total);
//                                    LogUtil.e("progress = " + progress + " %");
//                                }
//                                fos.flush();
//                                ToastUtil.show(MyApplication.getInstance(), "保存成功");
//                                readFile();
//                                LogUtil.e("readFile" + readFile());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } finally {
//                                try {
//                                    if (fos != null) {
//                                        fos.close();
//                                    }
//                                    if (is != null) {
//                                        is.close();
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
////                        else {
////                            ToastUtil.show(MyApplication.getInstance(), "文件已存在");
////                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private String readFile() {
        StringBuilder sb = new StringBuilder();
        try {
            path = CommonUtil.getDownloadPath("/files");
            String fileName = path + "/file.txt";
            LogUtil.e("readFile  =" + fileName);
            FileInputStream fis = new FileInputStream(fileName);
            byte[] temp = new byte[1024];
            int lines = 0;
            while ((lines = fis.read(temp)) > 0) {
                sb.append(new String(temp, 0, lines, "utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = sb.toString();
        str.lastIndexOf(",");
        return sb.toString();
    }


}























