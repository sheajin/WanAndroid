package com.xy.wanandroid.ui.drawer.activity;

import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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
    @BindView(R.id.image_back)
    ImageView mImageBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.image_play)
    ImageView mImagePlay;
    @BindView(R.id.image_danmu)
    ImageView mImageDanmu;
    @BindView(R.id.image_full_screen)
    ImageView mImageFullScreen;

    private String roomId, roomName;
    private PlayerManager playerManager;
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
        addTouchListener();
    }

    @Override
    protected void initData() {
        mPresenter.getLiveUrl(roomId);
    }

    @OnClick({R.id.image_back})
    void click(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                onBackPressedSupport();
                break;
        }
    }

    @Override
    public void getLiveUrlOk(LiveUrl live) {
        viewLoading.setVisibility(View.VISIBLE);
        initVideo(live.getHls_url());
//        initPlayer(live.getHls_url());
    }

    @Override
    public void getLiveUrlErr(String info) {
        ToastUtil.show(activity, info);
    }

    /**
     * video播放
     *
     * @param liveUrl
     */
    private void initVideo(String liveUrl) {
        ijkVideoView.setKeepScreenOn(true);
        viewLoading.setVisibility(View.VISIBLE);
        ijkVideoView.setVideoURI(Uri.parse(liveUrl));
        ijkVideoView.start();
        ijkVideoView.setOnInfoListener((iMediaPlayer, i, i1) -> {
            switch (i) {
                //开始播放
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    setVisible(viewTop, viewBottom);
                    setInVisible(viewLoading);
                    break;
            }
            return false;
        });
        ijkVideoView.setOnCompletionListener(iMediaPlayer -> {
            //加载失败 没有网络等回调

        });
    }

    /**
     * player播放
     *
     * @param liveUrl
     */
    private void initPlayer(String liveUrl) {
        playerManager = new PlayerManager(this);
        playerManager.live(true);
        playerManager.setScaleType(PlayerManager.SCALETYPE_16_9);
        playerManager.play(liveUrl);
        playerManager.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onLoading() {
                LogUtil.e("player onLoading ");
            }

            @Override
            public void onPlay() {
                viewLoading.setVisibility(View.INVISIBLE);
                LogUtil.e("player onPlay ");
            }

            @Override
            public void onComplete() {
                LogUtil.e("player onComplete ");
            }

            @Override
            public void onError() {
                LogUtil.e("player onError ");
            }
        });
    }

    /**
     * 手势事件
     */
    public void addTouchListener() {
        mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            //滑动事件
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

            //双击事件
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
        }
        if (playerManager != null && playerManager.isPlaying()) {
            playerManager.stop();
            playerManager.onDestroy();
        }
    }
}
