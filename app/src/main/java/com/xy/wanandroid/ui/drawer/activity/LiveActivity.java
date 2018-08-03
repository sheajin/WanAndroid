package com.xy.wanandroid.ui.drawer.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.adapter.SimpleFragmentStateAdapter;
import com.xy.wanandroid.contract.drawer.LiveContract;
import com.xy.wanandroid.danmu.utils.DanmuProcess;
import com.xy.wanandroid.data.drawer.LiveList;
import com.xy.wanandroid.data.drawer.LiveUrl;
import com.xy.wanandroid.media.IjkVideoView;
import com.xy.wanandroid.media.PlayerManager;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.drawer.LivePresenter;
import com.xy.wanandroid.ui.drawer.fragment.LiveChatFragment;
import com.xy.wanandroid.ui.drawer.fragment.LiveInfoFragment;
import com.xy.wanandroid.ui.drawer.fragment.LiveVideoFragment;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.DisplayUtil;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import master.flame.danmaku.ui.widget.DanmakuView;
import tv.danmaku.ijk.media.player.IMediaPlayer;

@SuppressLint("SetTextI18n")
public class LiveActivity extends BaseActivity<LivePresenter> implements LiveContract.View {

    @BindView(R.id.video_view)
    IjkVideoView ijkVideoView;
    @BindView(R.id.live_loading)
    View viewLoading;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.view_portrait_bottom)
    View viewPortraitBottom;
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
    @BindView(R.id.image_land_play)
    ImageView mImageLandPlay;
    @BindView(R.id.view_land_play)
    RelativeLayout viewLandPlay;
    @BindView(R.id.view_refresh)
    RelativeLayout viewRefresh;
    @BindView(R.id.edit_danmu)
    EditText mEtDanmu;
    @BindView(R.id.view_send_danmu)
    View viewSendDanmu;
    @BindView(R.id.view_danmu)
    View viewDanmu;
    @BindView(R.id.image_danmu)
    ImageView mImageDanmu;
    @BindView(R.id.view_exit_full_screen)
    View viewExitFullScreen;
    @BindView(R.id.view_landscape_bottom)
    View viewLandscapeBottom;
    @BindView(R.id.image_control)
    ImageView mImageControl;
    @BindView(R.id.tv_control_name)
    TextView mTvControlName;
    @BindView(R.id.tv_control_num)
    TextView mTvControlNum;
    @BindView(R.id.view_control)
    View viewControl;
    @BindView(R.id.danmuku)
    DanmakuView danmukuView;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;

    private int mScreenWidth;
    private int mShowVolume;//声音
    private int mShowLightness;//亮度
    private int mMaxVolume;//最大声音
    private String roomId;
    private boolean isDanmuOpend = true;
    private LiveList liveList;
    private DanmuProcess mDanmuProcess;
    private AudioManager mAudioManager;
    private PlayerManager playerManager;
    private RelativeLayout.LayoutParams params;
    private GestureDetector mGestureDetector;

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
        liveList = (LiveList) getIntent().getSerializableExtra(Constant.ROOMINFO);
        roomId = liveList.getRoom_id();
        mTvTitle.setText(liveList.getRoom_name());
        mScreenWidth = DisplayUtil.getScreenWidth(context);
        initPlayer();
        initDanmu();
        initLayoutParams();
        initVolumeWithLight();
        addTouchListener();
    }

    @Override
    protected void initData() {
        initTab();
        viewLoading.setVisibility(View.VISIBLE);
        mPresenter.getLiveUrl(roomId);
    }

    @OnClick({R.id.view_back, R.id.view_play, R.id.view_full_screen, R.id.view_share, R.id.view_refresh, R.id.view_danmu})
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
                ToastUtil.show(activity, getString(R.string.share_title));
                break;
            case R.id.view_refresh:
                mPresenter.getLiveUrl(roomId);
                break;
            case R.id.view_danmu:
                if (isDanmuOpend) {
                    //隐藏弹幕
                    danmukuView.hide();
                    mImageDanmu.setImageResource(R.drawable.icon_danmu_close);
                    isDanmuOpend = false;
                } else {
                    //开启弹幕
                    danmukuView.show();
                    mImageDanmu.setImageResource(R.drawable.icon_danmu_open);
                    isDanmuOpend = true;
                }
                break;

        }
    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        playerManager = new PlayerManager(this);
        playerManager.live(true);
        playerManager.setScaleType(PlayerManager.SCALETYPE_16_9);
    }

    /**
     * 初始化弹幕
     */
    private void initDanmu() {
        mDanmuProcess = new DanmuProcess(context, danmukuView, Integer.parseInt(roomId));
        mDanmuProcess.start();
        danmukuView.setVisibility(isLandscape() ? View.VISIBLE : View.GONE);
    }

    /**
     * 初始化视频宽高
     */
    private void initLayoutParams() {
        params = (RelativeLayout.LayoutParams) viewContent.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = mScreenWidth * 9 / 16;
        ijkVideoView.setLayoutParams(params);
        LogUtil.e("init orientation");
    }

    /**
     * 初始化声音和亮度
     */
    private void initVolumeWithLight() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mShowVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / mMaxVolume;
        mShowLightness = CommonUtil.getScreenBrightness(activity);
    }

    /**
     * 设置TabLayout和Viewpager
     */
    private void initTab() {
        String[] title = {"聊天", "主播", "排行榜"};
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        titles.addAll(Arrays.asList(title).subList(0, 3));
        fragments.add(LiveChatFragment.getInstance());
        fragments.add(LiveInfoFragment.getInstance(liveList));
        fragments.add(LiveVideoFragment.getInstance());
        SimpleFragmentStateAdapter pagerAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), fragments);
        mPager.setAdapter(pagerAdapter);
        String[] titleArray = titles.toArray(new String[titles.size()]);
        mPager.setAdapter(pagerAdapter);
        mTabLayout.setViewPager(mPager, titleArray);
        pagerAdapter.notifyDataSetChanged();
        mPager.setOffscreenPageLimit(title.length);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        mDanmuProcess.start();
                        break;
                    case 1:
                    case 2:
                        mDanmuProcess.finish();
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mDanmuProcess.finish();
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mDanmuProcess.start();
                        break;
                    case 1:
                    case 2:
                        mDanmuProcess.finish();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void getLiveUrlOk(LiveUrl live) {
        startLive(live.getHls_url());
    }

    @Override
    public void getLiveUrlErr(String info) {
        ToastUtil.show(activity, info);
    }

    /**
     * player播放
     */
    private void startLive(String liveUrl) {
        playerManager.play(liveUrl);
        playerManager.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onPlay() {
                setVisible(viewTop, viewPortraitBottom);
                setInVisible(viewLoading);
                if (viewTop.getVisibility() == View.VISIBLE && viewPortraitBottom.getVisibility() == View.VISIBLE) {
                    ijkVideoView.postDelayed(() -> setGone(viewTop, viewPortraitBottom), Constant.VIEW_POST_DELAY);
                }
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError() {
                //播放失败
                ToastUtil.show(activity, getString(R.string.get_live_fail));
            }
        });
        ijkVideoView.setOnCompletionListener(iMediaPlayer -> {
            //加载失败 没有网络等回调
            ToastUtil.show(activity, getString(R.string.internet_error));
        });

    }

    /**
     * 是否竖屏
     */
    private boolean isLandscape() {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    /**
     * 设置全屏或取消全屏
     */
    private void clickFullScreen() {
        if (isLandscape()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            playerManager.setScaleType(PlayerManager.SCALETYPE_16_9);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = mScreenWidth * 9 / 16;
            ijkVideoView.setLayoutParams(params);
            setVisible(viewTop, viewLandscapeBottom);
            setGone(viewPortraitBottom);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            playerManager.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            ijkVideoView.setLayoutParams(params);
            setVisible(viewTop, viewPortraitBottom);
            setGone(viewLandscapeBottom);
        }
    }

    /**
     * 此方法表示在改变屏幕方向、弹出软件盘和隐藏软键盘时，不再去执行onCreate()方法
     * 而是直接执行onConfigurationChanged()
     * 我们需要需要判断屏幕方向并作出相应处理
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        danmukuView.setVisibility(isLandscape() ? View.VISIBLE : View.GONE);
        if (isLandscape()) {
            setVisible(viewTop, viewLandscapeBottom);
            setGone(viewPortraitBottom);
            if (viewTop.getVisibility() == View.VISIBLE && viewLandscapeBottom.getVisibility() == View.VISIBLE) {
                ijkVideoView.postDelayed(() -> setGone(viewTop, viewLandscapeBottom), Constant.VIEW_POST_DELAY);
            }
        } else {
            setVisible(viewTop, viewPortraitBottom);
            setGone(viewLandscapeBottom);
            if (viewTop.getVisibility() == View.VISIBLE && viewPortraitBottom.getVisibility() == View.VISIBLE) {
                ijkVideoView.postDelayed(() -> setGone(viewTop, viewPortraitBottom), Constant.VIEW_POST_DELAY);
            }
        }
    }

    /**
     * 手势事件
     */
    private void addTouchListener() {
        GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            //滑动事件  控制音量和屏幕亮度
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (isLandscape()) {
                    float x1 = e1.getX();
                    float absDistanceX = Math.abs(distanceX);// distanceX < 0 从左到右
                    float absDistanceY = Math.abs(distanceY);// distanceY < 0 从上到下
                    // Y方向的距离比X方向的大，即 上下 滑动
                    if (absDistanceX < absDistanceY) {
                        setVisible(viewControl);
                        if (distanceY > 0) {//向上滑动
                            if (x1 >= mScreenWidth * 0.65) {//右边调节声音
                                changeVolume(Constant.VOLUME_FLAG);
                            } else {//调节亮度
                                changeLightness(Constant.VOLUME_FLAG);
                            }
                        } else {//向下滑动
                            if (x1 >= mScreenWidth * 0.65) {
                                changeVolume(Constant.LIGHT_FLAG);
                            } else {
                                changeLightness(Constant.LIGHT_FLAG);
                            }
                        }
                    } else {
                        // X方向的距离比Y方向的大，即 左右 滑动
                    }
                    return false;
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            //单击事件
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //竖屏点击事件
                if (isLandscape()) {
                    if (viewTop.getVisibility() == View.VISIBLE && viewLandscapeBottom.getVisibility() == View.VISIBLE) {
                        setGone(viewTop, viewLandscapeBottom);
                    } else {
                        setVisible(viewTop, viewLandscapeBottom);
                    }
                } else {    //横屏点击事件
                    if (viewTop.getVisibility() == View.VISIBLE && viewPortraitBottom.getVisibility() == View.VISIBLE) {
                        setGone(viewTop, viewPortraitBottom);
                    } else {
                        setVisible(viewTop, viewPortraitBottom);
                    }
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
     * 改变声音
     */
    private void changeVolume(int flag) {
        mShowVolume += flag;
        if (mShowVolume > 100) {
            mShowVolume = 100;
        } else if (mShowVolume < 0) {
            mShowVolume = 0;
        }
        mTvControlName.setText("音量");
        mImageControl.setImageResource(R.drawable.icon_volume);
        mTvControlNum.setText(mShowVolume + "%");
        int tagVolume = mShowVolume * mMaxVolume / 100;
        //tagVolume:音量绝对值
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, tagVolume, 0);
        ijkVideoView.postDelayed(() -> setGone(viewControl), Constant.VIEW_POST_DELAY);
    }

    /**
     * 改变亮度
     */
    private void changeLightness(int flag) {
        mShowLightness += flag;
        if (mShowLightness > 255) {
            mShowLightness = 255;
        } else if (mShowLightness <= 0) {
            mShowLightness = 0;
        }
        mTvControlName.setText("亮度");
        mImageControl.setImageResource(R.drawable.icon_light);
        mTvControlNum.setText(mShowLightness * 100 / 255 + "%");
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = mShowLightness / 255f;
        getWindow().setAttributes(lp);
        ijkVideoView.postDelayed(() -> setGone(viewControl), Constant.VIEW_POST_DELAY);
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
    public void onBackPressedSupport() {
        if (isLandscape()) {
            clickFullScreen();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ijkVideoView != null && ijkVideoView.isPlaying()) {
            ijkVideoView.stopBackgroundPlay();
            ijkVideoView.stopPlayback();
            ijkVideoView.release(true);
        }
        if (playerManager != null && playerManager.isPlaying()) {
            playerManager.stop();
            playerManager.onDestroy();
        }
        mDanmuProcess.finish();
        mDanmuProcess.close();
        if (danmukuView != null) {
            danmukuView.release();
            danmukuView.clear();
        }
    }

}
