package com.xy.wanandroid.ui.drawer.activity;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.media.IjkVideoView;
import com.xy.wanandroid.media.PlayerManager;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class LiveActivity extends BaseActivity {

    @BindView(R.id.video_view)
    IjkVideoView ijkVideoView;

    private PlayerManager player;
    private String source1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    protected void initUI() {
//        source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//        source1 = "http://c.brightcove.com/services/mobile/streaming/index/rendition.m3u8?assetId=5330721253001&pubId=4938530621001&videoId=5330694577001";

    }

    //使用滑动控制的话解开注释
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (player.gestureDetector.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }

    @Override
    protected void initData() {
        initVideo();
    }

    private void initVideo() {
        player = new PlayerManager(this);
        player.setFullScreenOnly(false);
        player.live(false);
        player.setScaleType(PlayerManager.SCALETYPE_WRAPCONTENT);
        player.playInFullScreen(false);
        player.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onComplete() {
                Log.e("   player  status    :", "complete");
            }

            @Override
            public void onError() {
                Log.e("   player  status    :", "error");
            }

            @Override
            public void onLoading() {
                Log.e("   player  status    :", "loading");
            }

            @Override
            public void onPlay() {
                Log.e("   player  status    :", "play");
            }
        });
        player.play(source1);
        IjkVideoView videoView = player.getVideoView();
        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                switch (i) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        break;
                }
                return false;

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()) {
            player.onDestroy();

        }
    }
}
