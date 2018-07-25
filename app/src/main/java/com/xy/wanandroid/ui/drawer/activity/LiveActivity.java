package com.xy.wanandroid.ui.drawer.activity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.contract.drawer.LiveContract;
import com.xy.wanandroid.data.drawer.LiveUrl;
import com.xy.wanandroid.media.IjkVideoView;
import com.xy.wanandroid.media.PlayerManager;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.drawer.LivePresenter;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class LiveActivity extends BaseActivity<LivePresenter> implements LiveContract.View {

    @BindView(R.id.video_view)
    IjkVideoView ijkVideoView;
    @BindView(R.id.live_loading)
    View viewLoading;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.view_back)
    View viewBack;
    @BindView(R.id.view_play)
    View viewPlay;
    @BindView(R.id.view_full_screen)
    View viewFullScreen;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.image_play)
    ImageView mImagePlay;
    @BindView(R.id.view_video)
    View viewContent;

    private boolean isPlaying = false;
    private String roomId, roomName;
    private PlayerManager playerManager;
    private RelativeLayout.LayoutParams params;
    private GestureDetector mGestureDetector;
    private GestureDetector.SimpleOnGestureListener mGestureListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        roomId = getIntent().getStringExtra(Constant.ROOMID);
        roomName = getIntent().getStringExtra(Constant.ROOMNAME);
        mTvTitle.setText(roomName);
        initLayoutParams();
        addTouchListener();
    }

    /**
     * 初始化视频宽高
     */
    private void initLayoutParams() {
        params = (RelativeLayout.LayoutParams) viewContent.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = 609;
        ijkVideoView.setLayoutParams(params);
        LogUtil.e("init orientation");
    }

    @Override
    protected void initData() {
        viewLoading.setVisibility(View.VISIBLE);
        mPresenter.getLiveUrl(roomId);
    }

    @OnClick({R.id.view_back, R.id.view_play, R.id.view_full_screen, R.id.view_share})
    void click(View view) {
        switch (view.getId()) {
            case R.id.view_back:
                if (isLandscape()) {
                    clickFullScreen();
                } else {
                    onBackPressedSupport();
                }
                break;
            case R.id.view_play:
                if (ijkVideoView != null && ijkVideoView.isPlaying()) {
                    mImagePlay.setImageResource(R.drawable.icon_pause);
                    ijkVideoView.pause();
                } else {
                    mImagePlay.setImageResource(R.drawable.icon_play);
                    ijkVideoView.start();
                }
                break;
            case R.id.view_full_screen:
                clickFullScreen();
                break;
            case R.id.view_share:
                break;
        }
    }

    @Override
    public void getLiveUrlOk(LiveUrl live) {
//        initVideo(live.getHls_url());
        initPlayer(live.getHls_url());
    }

    @Override
    public void getLiveUrlErr(String info) {
        ToastUtil.show(activity, info);
    }

    public boolean isLandscape() {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    /**
     * 设置全屏或取消全屏
     */
    public void clickFullScreen() {
        if (isLandscape()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            playerManager.setScaleType(PlayerManager.SCALETYPE_16_9);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            ijkVideoView.setLayoutParams(params);
            setVisible(viewBottom);
            LogUtil.e("orientation 竖屏");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            playerManager.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            ijkVideoView.setLayoutParams(params);
            setGone(viewBottom);
            LogUtil.e("orientation 横屏");
        }
    }

    /**
     * 手势事件
     */
    public void addTouchListener() {
        mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            //滑动事件  控制音量和屏幕亮度
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            //单击事件
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (viewTop.getVisibility() == View.VISIBLE && viewBottom.getVisibility() == View.VISIBLE) {
                    setGone(viewTop, viewBottom);
                } else {
                    setVisible(viewTop, viewBottom);
                }
                return true;
            }

            //双击事件 全屏或退出全屏
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                return super.onDoubleTap(e);
            }
        };
        mGestureDetector = new GestureDetector(this, mGestureListener);
    }

    /**
     * 触摸事件进行传递
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector != null)
            mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ijkVideoView != null && ijkVideoView.isPlaying()) {
            ijkVideoView.stopPlayback();
            ijkVideoView.release(true);
        }
        if (playerManager != null && playerManager.isPlaying()) {
            playerManager.stop();
            playerManager.onDestroy();
        }
    }

    /**
     * player播放
     *
     * @param liveUrl
     */
    private void initPlayer(String liveUrl) {
        ijkVideoView.setKeepScreenOn(true);
        playerManager = new PlayerManager(this);
        playerManager.live(true);
        playerManager.setScaleType(PlayerManager.SCALETYPE_16_9);
        playerManager.play(liveUrl);
        playerManager.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onLoading() {
                LogUtil.e("initPlayer onloading");
            }

            @Override
            public void onPlay() {
                isPlaying = true;
                setVisible(viewTop, viewBottom);
                setInVisible(viewLoading);
                if (viewTop.getVisibility() == View.VISIBLE && viewBottom.getVisibility() == View.VISIBLE) {
                    ijkVideoView.postDelayed(() -> setGone(viewTop, viewBottom), 2000);
                }
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError() {

            }
        });
    }

}
