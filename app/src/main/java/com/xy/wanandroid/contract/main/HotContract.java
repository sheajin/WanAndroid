package com.xy.wanandroid.contract.main;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.main.SearchHot;

import java.util.List;

/**
 * Created by jxy on 2018/6/22.
 */

public class HotContract  {

    public interface View extends AbstractView {

        void getHotWebOk(List<SearchHot> dataBean);

        void getHotWebErr(String info);

    }

    public interface Presenter extends AbsPresenter<HotContract.View> {

        void getHotWeb();

    }
}
