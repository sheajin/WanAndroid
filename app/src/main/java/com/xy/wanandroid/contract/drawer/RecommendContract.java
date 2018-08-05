package com.xy.wanandroid.contract.drawer;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.drawer.LiveUrl;
import com.xy.wanandroid.data.drawer.RecommendData;

import java.util.List;

/**
 * Created by jxy on 2018/7/23.
 */

public class RecommendContract {

    public interface View extends AbstractView {

        void getRecommendOk(List<RecommendData> recommendData, boolean isRefresh);

        void getRecommendErr(String info);

    }

    public interface Presenter extends AbsPresenter<RecommendContract.View> {

        void getRecommendList(String id,int page,int pre_page);

        void autoRefresh(String id,int page,int pre_page);

        void loadMore(String id,int page,int pre_page);
    }
}
