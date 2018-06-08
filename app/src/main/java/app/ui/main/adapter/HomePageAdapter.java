package app.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import app.model.data.main.HomePageArticleBean;
import app.ui.main.viewholder.HomePageViewHolder;

/**
 * Created by jxy on 2018/6/8.
 */

public class HomePageAdapter extends BaseQuickAdapter<HomePageArticleBean, HomePageViewHolder> {

    public HomePageAdapter(int layoutResId, @Nullable List<HomePageArticleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HomePageViewHolder helper, HomePageArticleBean item) {

    }
}
