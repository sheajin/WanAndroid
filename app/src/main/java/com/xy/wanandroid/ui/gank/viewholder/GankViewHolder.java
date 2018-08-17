package com.xy.wanandroid.ui.gank.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;

/**
 * Created by jxy on 2018/8/13.
 */

public class GankViewHolder extends BaseViewHolder {

    public View viewType;
    public ImageView imageView;

    public GankViewHolder(View view) {
        super(view);
//        viewType = view.findViewById(R.id.view_type);
        imageView = view.findViewById(R.id.image_view);
    }
}
