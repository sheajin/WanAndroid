package com.xy.wanandroid.contract;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.mine.CollectBean;

/**
 * Created by jxy on 2018/6/25.
 */

public class CollectContract {

    public interface View extends AbstractView {

        void getCollectListOk(CollectBean dataBean, boolean isRefresh);

        void getCollectListErr(String info);

//        void cancelCollectOk(String info);
//
//        void cancelCollectErr(String info);

    }

    public interface Presenter extends AbsPresenter<CollectContract.View> {

        void autoRefresh();

        void loadMore();

        void getCollectList(int page);

//        void cancelCollect(int id);
    }
}
