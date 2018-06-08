package app.ui.main.viewholder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;

import butterknife.BindView;

/**
 * Created by jxy on 2018/6/8.
 */

public class HomePageViewHolder extends BaseViewHolder {

    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_collect)
    TextView mTvCollect;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_new_tag)
    TextView mTvNewTag;
    @BindView(R.id.item_card_homepage)
    CardView cardHomepage;

    public HomePageViewHolder(View view) {
        super(view);
    }
}
