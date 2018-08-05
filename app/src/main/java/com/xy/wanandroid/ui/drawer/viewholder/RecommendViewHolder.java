package com.xy.wanandroid.ui.drawer.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;

/**
 * Created by jxy on 2018/7/21.
 */
public class RecommendViewHolder extends BaseViewHolder {

    public ImageView imageContent;

    public RecommendViewHolder(View view) {
        super(view);
        imageContent = view.findViewById(R.id.image_content);
    }
}
