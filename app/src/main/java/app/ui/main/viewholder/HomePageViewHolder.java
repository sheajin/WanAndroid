package app.ui.main.viewholder;

import android.view.View;
import android.widget.ImageView;
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
    @BindView(R.id.image_collect)
    ImageView mImageCollect;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_project_tag)
    TextView mTvProjectTag;
    @BindView(R.id.tv_hot_tag)
    TextView mTvHotTag;

    public HomePageViewHolder(View view) {
        super(view);
    }
}
