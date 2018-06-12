package app.ui.main.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

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
        helper.getView(R.id.view_tag).setVisibility(View.GONE);
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.tv_content, article.getTitle());
        }
        if (!TextUtils.isEmpty(article.getAuthor())) {
            helper.setText(R.id.tv_author, article.getAuthor());
        }
        if (!TextUtils.isEmpty(article.getNiceDate())) {
            helper.setText(R.id.tv_time, article.getNiceDate());
        }
        if (!TextUtils.isEmpty(article.getChapterName())) {
            String classifyName = article.getSuperChapterName() + " / " + article.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
        if (article.getSuperChapterName().contains(mContext.getString(R.string.project))) {
            helper.getView(R.id.view_tag).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_project_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_project_tag, mContext.getString(R.string.project));
        } else if (article.getSuperChapterName().contains(mContext.getString(R.string.hot))) {
            helper.getView(R.id.view_tag).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_hot_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hot_tag, mContext.getString(R.string.hot));
        }
        helper.addOnClickListener(R.id.tv_type);
        helper.addOnClickListener(R.id.image_collect);
        helper.setImageResource(R.id.image_collect, article.isCollect() ? R.drawable.icon_collect : R.drawable.icon_no_collect);
    }
}

