package com.xy.wanandroid.ui.gank.activity;

import android.annotation.SuppressLint;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.data.gank.MovieDetailBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.ui.gank.adapter.HotMovieDetailAdapter;
import com.xy.wanandroid.util.app.DisplayUtil;
import com.xy.wanandroid.util.app.StringFormatUtils;
import com.xy.wanandroid.util.app.ToastUtil;
import com.xy.wanandroid.util.glide.GlideUtil;
import com.xy.wanandroid.util.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_another_name)
    TextView mTvAnotherName;
    @BindView(R.id.tv_introduce)
    TextView mTvIntroduce;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_director)
    TextView mTvDirector;
    @BindView(R.id.tv_actor)
    TextView mTvActor;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.image_preview)
    ImageView mImagePreview;
    @BindView(R.id.image_blur)
    ImageView mViewBlur;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.view_title)
    View mViewTitle;
    @BindView(R.id.image_back)
    ImageView mImageBack;

    private List<MovieDetailBean.CastsBean> actorList;
    private float imageHeight;
    private HotMovieDetailAdapter mAdapter;
    private String movieId, movieTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void initUI() {
        StatusBarUtil.setImmeriseStatusBar(activity);
        mViewTitle.setPadding(0, StatusBarUtil.getStatusBarHeight(activity), 0, 0);
        movieId = getIntent().getStringExtra(Constant.MOVIE_ID);
        movieTitle = getIntent().getStringExtra(Constant.MOVIE_TITLE);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = DisplayUtil.dip2px(context, 12f);
        mRv.setLayoutParams(params);
        actorList = new ArrayList<>();
        mTvTitle.setText(movieTitle);
        imageHeight = DisplayUtil.px2dip(activity, getResources().getDimensionPixelSize(R.dimen.dp_240));
//        mAdapter = new HotMovieDetailAdapter(R.layout.item_movie_detail_actor, actorList);
//        mRv.setAdapter(mAdapter);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void initData() {
        setAlpha();
        if (movieId != null) {
            ApiStore.createApi(ApiService.class)
                    .getMovieDetail(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpObserver<MovieDetailBean>() {
                        @Override
                        public void onNext(MovieDetailBean movieDetailBean) {
                            if (movieDetailBean != null) {
                                //如果直接指定%f，默认保留小数点后6位。可以通过%.2f来指定保留几位小数
                                mTvScore.setText(getString(R.string.movie_score, movieDetailBean.getRating().getAverage()));
                                mTvDirector.setText(getString(R.string.movie_director, movieDetailBean.getDirectors().get(0).getName()));
                                mTvActor.setText(getString(R.string.movie_actor, StringFormatUtils.getActor(movieDetailBean)));
                                mTvType.setText(getString(R.string.movie_type, StringFormatUtils.getType(movieDetailBean)));
                                mTvTime.setText(getString(R.string.movie_time, movieDetailBean.getYear()));
                                GlideUtil.loadImage(activity, movieDetailBean.getImages().getMedium(), mImagePreview);
                                GlideUtil.loadImage(activity, movieDetailBean.getImages().getSmall(), mViewBlur);
                                GlideUtil.loadBlurImage(activity, movieDetailBean.getImages().getSmall(), mViewBlur);//高斯模糊
                                mTvAnotherName.setText(movieDetailBean.getOriginal_title());
                                mTvIntroduce.setText(movieDetailBean.getSummary());
                                actorList.addAll(movieDetailBean.getCasts());
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.show(activity, e.getMessage());
                        }
                    });
        }
    }

    /**
     * nestedScrollView滑动改变标题栏Alpha
     */
    private void setAlpha() {
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int realScrollY = DisplayUtil.px2dip(activity, scrollY);
            float alpha = realScrollY / imageHeight;
            if (scrollY - oldScrollY > 0) {
                //屏幕向上滚动
                mViewTitle.setAlpha(1 - alpha);
            } else {
                //屏幕向下滚动
                mViewTitle.setAlpha(realScrollY == 0 ? 1 : (1 - alpha));
            }
        });
    }

}
