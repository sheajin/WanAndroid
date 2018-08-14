package com.xy.wanandroid.contract.gank;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.gank.CategoryTitle;

import java.util.List;

/**
 * Created by jxy on 2018/7/20.
 */

public class VideoContract {

    public interface View extends AbstractView {

        void getLiveTitleOk(List<CategoryTitle> categoryTitle);

        void getLiveTitleErr(String info);

    }

    public interface Presenter extends AbsPresenter<VideoContract.View> {

        void getLiveTitle();

    }
}
