package com.xy.wanandroid.contract.gank;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.gank.HotMovieBean;

/**
 * Created by jxy on 2018/8/14.
 */
public class HotContract {

    public interface View extends AbstractView {

        void getHotMovieOk(HotMovieBean hotMovieBean);

        void getHotMovieErr(String info);

    }

    public interface Presenter extends AbsPresenter<HotContract.View> {

        void getHotMovie();

    }
}
