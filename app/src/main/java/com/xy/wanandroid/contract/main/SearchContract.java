package com.xy.wanandroid.contract.main;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.main.SearchHot;

import java.util.List;

/**
 * Created by jxy on 2018/6/21.
 */

public class SearchContract {

    public interface View extends AbstractView {

        void getSearchHotOk(List<SearchHot> dataBean);

    }

    public interface Presenter extends AbsPresenter<SearchContract.View> {

        void getSearchHot();

    }
}
