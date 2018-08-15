package com.xy.wanandroid.contract.gank;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.gank.HotMovieBean;

/**
 * Created by jxy on 2018/8/15.
 */

public class TopContract {

    public interface View extends AbstractView {

        void getTopOk(HotMovieBean hotMovieBean,boolean isRefresh);

        void getTopErr(String info);

    }

    public interface Presenter extends AbsPresenter<TopContract.View> {

        void getTop();

        void autoRefresh();

        void loadMore();
    }

}
