package app.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;

import java.util.List;

import app.base.MyApplication;
import app.model.data.main.HomePageArticleBean;
import app.ui.main.viewholder.HomePageViewHolder;
import app.util.app.LogUtil;
import app.util.normal.TextUtil;

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
            helper.setText(R.id.tv_content, article.getTitle());
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

