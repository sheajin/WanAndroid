package com.xy.wanandroid.ui.gank.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.drawer.RecommendData;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankAdapter extends BaseMultiItemQuickAdapter<RecommendData.ResultsBean, BaseViewHolder> {

    private List<RecommendData.ResultsBean> gankList;
    private List<RecommendData.ResultsBean> js = new ArrayList<>();
    private List<RecommendData.ResultsBean> android = new ArrayList<>();
    private List<RecommendData.ResultsBean> ios = new ArrayList<>();
    private List<RecommendData.ResultsBean> recommend = new ArrayList<>();
    private List<RecommendData.ResultsBean> app = new ArrayList<>();
    private List<RecommendData.ResultsBean> rest = new ArrayList<>();
    private List<RecommendData.ResultsBean> welfare = new ArrayList<>();
    private List<RecommendData.ResultsBean> extra = new ArrayList<>();

    public void setGankList(List<RecommendData.ResultsBean> gankList) {
        this.gankList = gankList;
        for (RecommendData.ResultsBean resultsBean : gankList) {
            if (resultsBean.getType().equals("Android")) {
                android.add(resultsBean);
            } else if (resultsBean.getType().equals("iOS")) {
                ios.add(resultsBean);
            } else if (resultsBean.getType().equals("App")) {
                app.add(resultsBean);
            } else if (resultsBean.getType().equals("前端")) {
                js.add(resultsBean);
            } else if (resultsBean.getType().equals("拓展资源")) {
                extra.add(resultsBean);
            } else if (resultsBean.getType().equals("瞎推荐")) {
                recommend.add(resultsBean);
            } else if (resultsBean.getType().equals("福利")) {
                welfare.add(resultsBean);
            } else if (resultsBean.getType().equals("休息视频")) {
                rest.add(resultsBean);
            }
        }
    }

    public GankAdapter(List<RecommendData.ResultsBean> data) {
        super(data);
        addItemType(RecommendData.ResultsBean.ENTITY_ONE, R.layout.item_gank_one);
        addItemType(RecommendData.ResultsBean.ENTITY_TWO, R.layout.item_gank_two);
        addItemType(RecommendData.ResultsBean.ENTITY_THREE, R.layout.item_gank_three);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendData.ResultsBean item) {
        switch (helper.getItemViewType()) {
            case RecommendData.ResultsBean.ENTITY_ONE:
                if (!item.getType().equals(Constant.WELFARE))
                    helper.setText(R.id.tv_content_one, item.getDesc());
                helper.setText(R.id.tv_cate_one, item.getType());
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_one));
                break;
            case RecommendData.ResultsBean.ENTITY_TWO:
                helper.setText(R.id.tv_cate_two, item.getType());
                helper.setText(R.id.tv_two_cate_one, item.getDesc());
                helper.setText(R.id.tv_two_cate_two, item.getDesc());
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_content_one));
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_content_two));
                break;
            case RecommendData.ResultsBean.ENTITY_THREE:
                helper.setText(R.id.tv_cate_three, item.getType());
                helper.setText(R.id.tv_three_cate_one, item.getDesc());
                helper.setText(R.id.tv_three_cate_two, item.getDesc());
                helper.setText(R.id.tv_three_cate_three, item.getDesc());
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_three_content_one));
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_three_content_two));
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_three_content_three));
                break;
        }
    }
}
