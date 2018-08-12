package com.xy.wanandroid.contract.drawer;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.drawer.RecommendEntity;

/**
 * Created by jxy on 2018/7/23.
 */

public class RecommendContract {

    public interface View extends AbstractView {

        void getRecommendOk(RecommendEntity recommendEntity, boolean isRefresh);

        void getRecommendErr(String info);

    }

    public interface Presenter extends AbsPresenter<RecommendContract.View> {

        void getRecommendList(String type, int offset, int page);

        void autoRefresh(String type, int page);

        void loadMore(String type, int page);
    }
}
