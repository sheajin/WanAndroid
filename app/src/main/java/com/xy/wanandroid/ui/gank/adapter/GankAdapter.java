package com.xy.wanandroid.ui.gank.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.gank.RecommendData;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;


/**
 * Created by jxy on 2018/8/8.
 */

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.GankViewHolder> {

    private List<RecommendData.ResultsBean> gankList;
    private Activity activity;
    private OnItemClickListener onItemClickListener;

    public GankAdapter(List<RecommendData.ResultsBean> gankList, Activity activity) {
        this.gankList = gankList;
        this.activity = activity;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return gankList.get(position).getItemType();
    }

    @NonNull
    @Override
    public GankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GankViewHolder holder = null;
        if (viewType == RecommendData.ResultsBean.ENTITY_ITEM_ONE) {
            holder = new GankViewHolder(View.inflate(activity, R.layout.item_gank, null));
        } else if (viewType == RecommendData.ResultsBean.ENTITY_ITEM_TWO) {
            holder = new GankViewHolder(View.inflate(activity, R.layout.item_gank_two, null));
        } else if (viewType == RecommendData.ResultsBean.ENTITY_ITEM_THREE) {
            holder = new GankViewHolder(View.inflate(activity, R.layout.item_gank_three, null));
        } else if (viewType == RecommendData.ResultsBean.ENTITY_TITLE) {
            holder = new GankViewHolder(View.inflate(activity, R.layout.item_gank_title, null));
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull GankViewHolder holder, int position) {
        holder.mTvContent.setText(gankList.get(position).getDesc());
        GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), holder.mImageView);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return gankList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class GankViewHolder extends RecyclerView.ViewHolder {

        TextView mTvContent;
        ImageView mImageView;

        public GankViewHolder(View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}
