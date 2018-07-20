package com.xy.wanandroid.contract.drawer;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.drawer.SortList;

import java.util.List;
import java.util.Map;

/**
 * Created by jxy on 2018/7/20.
 */

public class VideoContract {

    public interface View extends AbstractView {

        void getLiveTitleOk(List<SortList> dataBean);

        void getLiveTitleErr(String info);

    }

    public interface Presenter extends AbsPresenter<VideoContract.View> {

        void getLiveTitle(Map<String, String> params);

    }
}
