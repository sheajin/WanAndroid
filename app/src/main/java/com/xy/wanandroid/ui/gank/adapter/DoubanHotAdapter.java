package com.xy.wanandroid.ui.gank.adapter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/8/15.
 */

public class DoubanHotAdapter extends BaseQuickAdapter<HotMovieBean.SubjectsBean, BaseViewHolder> {
    private StringBuilder stringBuilder = new StringBuilder();

    public DoubanHotAdapter(int layoutResId, @Nullable List<HotMovieBean.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotMovieBean.SubjectsBean item) {
        helper.setText(R.id.tv_name, item.getTitle());
        //导演
        stringBuilder.delete(0, stringBuilder.length());
        for (int i = 0; i < item.getDirectors().size(); i++) {
            if (item.getDirectors().size() > 0) {
                stringBuilder.append(item.getDirectors().get(i).getName()).append("、");
            }
        }
        helper.setText(R.id.tv_director, "导演: " + stringBuilder.substring(0, stringBuilder.lastIndexOf("、")));
        //主演
        stringBuilder.delete(0, stringBuilder.length());
        for (int i = 0; i < item.getCasts().size(); i++) {
            if (item.getCasts().size() > 0) {
                stringBuilder.append(item.getCasts().get(i).getName()).append("、");
            }
        }
        if (stringBuilder.toString().endsWith("、"))
            helper.setText(R.id.tv_actor, "主演: " + stringBuilder.toString().substring(0, stringBuilder.lastIndexOf("、")));
        //类型
        stringBuilder.delete(0, stringBuilder.length());
        for (int i = 0; i < item.getGenres().size(); i++) {
            if (item.getGenres().size() > 0) {
                stringBuilder.append(item.getGenres().get(i)).append("、");
            }
        }
        helper.setText(R.id.tv_type, "类型: " + stringBuilder.substring(0, stringBuilder.lastIndexOf("、")));
        //评分
        helper.setText(R.id.tv_score, "评分: " + String.valueOf(item.getRating().getAverage()));
        GlideUtil.loadImage(MyApplication.getInstance(), item.getImages().getMedium(), helper.getView(R.id.image_preview));
        //滑动 item动画
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(helper.itemView, scaleX, scaleY);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }
}
