package com.xy.wanandroid.ui.drawer.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;

/**
 * Created by jxy on 2018/7/21.
 */
public class LiveViewHolder extends BaseViewHolder {

    public ImageView imagePreview;

    public LiveViewHolder(View view) {
        super(view);
        imagePreview = view.findViewById(R.id.image_preview);
    }
}
