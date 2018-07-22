package com.xy.wanandroid.contract.drawer;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.drawer.LiveList;

import java.util.List;

/**
 * Created by jxy on 2018/7/21.
 */
public class LiveListContract {

    public interface View extends AbstractView {

        void getLiveListOk(List<LiveList> categoryTitle, boolean isRefresh);

        void getLiveListErr(String info);

    }

    public interface Presenter extends AbsPresenter<LiveListContract.View> {

        void autoRefresh(String tagId);

        void loadMore(String tagId);

        void getLiveList(String tagId);

    }
}
