package app.ui.main.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;

import java.util.List;

import app.model.data.main.HomePageArticleBean;
import app.ui.main.viewholder.HomePageViewHolder;

/**
 * Created by jxy on 2018/6/8.
 */

public class HomePageAdapter extends BaseQuickAdapter<HomePageArticleBean.DatasBean, HomePageViewHolder> {

    public HomePageAdapter(int layoutResId, @Nullable List<HomePageArticleBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HomePageViewHolder helper, HomePageArticleBean.DatasBean article) {
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.tv_content, article.getTitle())
            .addOnClickListener(R.id.tv_content);
        }
        if (!TextUtils.isEmpty(article.getAuthor())) {
            helper.setText(R.id.tv_author, article.getAuthor());
        }
        if (!TextUtils.isEmpty(article.getNiceDate())) {
            helper.setText(R.id.tv_time, article.getNiceDate());
        }
        if (!TextUtils.isEmpty(article.getChapterName())) {
            helper.setText(R.id.tv_type, article.getChapterName());
        }
    }

}

