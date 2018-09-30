package com.xy.wanandroid.ui.gank.activity;

import android.annotation.SuppressLint;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.StringFormatUtils;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    private HotMovieBean.SubjectsBean hotMovieBean;
    private List<String> actorList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void initUI() {
        hotMovieBean = (HotMovieBean.SubjectsBean) getIntent().getSerializableExtra(Constant.MOVIE);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        actorList = new ArrayList<>();
        mTvTitle.setText(hotMovieBean.getTitle());
    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void initData() {
        if (hotMovieBean != null) {
            mTvScore.setText(getString(R.string.movie_score, hotMovieBean.getRating().getAverage()));
            mTvDirector.setText(getString(R.string.movie_director, hotMovieBean.getDirectors().get(0).getName()));
            mTvActor.setText(getString(R.string.movie_actor, StringFormatUtils.getActor(hotMovieBean)));
            mTvType.setText(getString(R.string.movie_type, StringFormatUtils.getType(hotMovieBean)));
            mTvTime.setText(getString(R.string.movie_time, hotMovieBean.getYear()));
            GlideUtil.loadImage(activity, hotMovieBean.getImages().getMedium(), mImagePreview);
            //高斯模糊
            GlideUtil.loadBlurImage(activity, hotMovieBean.getImages().getSmall(), mViewBlur);
        }
        //nestedScrollView滑动分析
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            LogUtil.e("scrollY - oldScrollY = " + (scrollY - oldScrollY));
            if (scrollY - oldScrollY > 0) {
//                mViewTitle.setAlpha(0.8f);
            }
        });
    }
}
