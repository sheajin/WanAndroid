package com.xy.wanandroid.danmu.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

import com.xy.wanandroid.danmu.client.DyBulletScreenClient;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.util.app.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;

public class DanmuProcess {
    private Context mContext;
    private IDanmakuView mDanmakuView;
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mParser;
    private DyBulletScreenClient mDanmuClient;
    private int mRoomId;

    /**
     * 弹幕  随机颜色
     */
    private Random random;
    private int[] ranColor = {
            0xe0ffffff,
            0xe0F0E68C,
            0xe0F08080,
            0xe0FFC0CB,
            0xe000FA9A,
            0xe000FF7F,
            0xe0FFD700,
            0xe07FFFD4,
            0xe0FF7F50,
            0xe0DC143C,
            0xe0FFC0CB,
            0xe0DB7093,
            0xe87CEEB};

    public DanmuProcess(Context context, IDanmakuView danmakuView, int roomId) {
        this.mContext = context;
        this.mDanmakuView = danmakuView;
        this.mRoomId = roomId;
        random = new Random();
    }

    public void start() {
        initDanmaku();
        getAndAddDanmu();
    }

    private void initDanmaku() {
        mDanmakuContext = DanmakuContext.create();
        try {
            mParser = createParser(null);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_LR, 6);
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                .setCacheStuffer(new BackgroundCacheStuffer(), mCacheStufferAdapter)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);

        if (mDanmakuView != null) {
            mDanmakuView.setCallback(new DrawHandler.Callback() {
                @Override
                public void prepared() {
                    mDanmakuView.start();
                }

                @Override
                public void updateTimer(DanmakuTimer timer) {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {

                }

                @Override
                public void drawingFinished() {

                }
            });
            mDanmakuView.prepare(mParser, mDanmakuContext);
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }

    private BaseDanmakuParser createParser(InputStream stream) throws IllegalDataException {
        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }
        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
        loader.load(stream);
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;
    }

    private void getAndAddDanmu() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int groupId = -9999;
                mDanmuClient = DyBulletScreenClient.getInstance();
                //设置需要连接和访问的房间ID，以及弹幕池分组号
                mDanmuClient.start(mRoomId, groupId);
                /**
                 * 收到消息 用户昵称 + 弹幕消息
                 */
                mDanmuClient.setmHandleMsgListener(new DyBulletScreenClient.HandleMsgListener() {
                    @Override
                    public void handleMessage(String nickname, String txt) {
                        //发送弹幕
                        addDanmaku(true, txt);
                        //将信息保存起来
//                        if (nickname != null && txt != null) {
//                            LogUtil.e("LiveActivity getAndAddDanmu key = " + nickname + ",value = " + txt);
//                            EventBus.getDefault().post(new MessageEvent(EventConstant.SENDDANMU, nickname, txt));
//                        }
                    }
                });
            }
        });
        thread.start();
    }

    private void addDanmaku(boolean islive, String txt) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = txt;
        danmaku.padding = 10;
        danmaku.priority = 1;
        danmaku.isLive = islive;
        danmaku.textSize = 15f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.time = mDanmakuView.getCurrentTime();
        mDanmakuView.addDanmaku(danmaku);
    }

    public void finish() {
        //停止从服务器获取弹幕
        mDanmuClient.stop();
    }

    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {

        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            danmaku.text = null;
        }
    };

    class BackgroundCacheStuffer extends SpannedCacheStuffer {
        // 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
        final Paint paint = new Paint();
        final RectF rf = new RectF();

        @Override
        public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
            danmaku.padding = 15;  // 在背景绘制模式下增加padding
            super.measure(danmaku, paint, fromWorkerThread);
        }

        @Override
        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
            int ranNumber = random.nextInt(ranColor.length);
            int color = ranColor[ranNumber];
            paint.setAntiAlias(true);
            if (color != 0xe0ffffff && ranNumber % 2 == 0) {
                paint.setColor(color);  //弹幕背景颜色
                rf.left = left;
                rf.right = left + danmaku.paintWidth;
                rf.top = top;
                rf.bottom = top + danmaku.paintHeight;
                danmaku.textColor = 0xe0ffffff;
                paint.setStyle(Paint.Style.FILL);
//                canvas.drawRoundRect(rf, 40, 40, paint);
            } else {
                danmaku.textColor = color;
                paint.setColor(color);  //弹幕背景颜色
                rf.left = left;
                rf.right = left + danmaku.paintWidth;
                rf.top = top;
                rf.bottom = top + danmaku.paintHeight;
                paint.setStyle(Paint.Style.STROKE);
//                canvas.drawRoundRect(rf, 40, 40, paint);
            }
        }

        @Override
        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
            // 禁用描边绘制
        }
    }

    public void close() {
        if (mContext != null) {
            mContext = null;
        }
        if (mDanmakuView != null) {
            mDanmakuView = null;
        }
        if (mDanmakuContext != null) {
            mDanmakuContext = null;
        }
        if (mDanmuClient != null) {
            mDanmuClient = null;
        }
        if (mParser != null) {
            mParser = null;
        }

    }
}
