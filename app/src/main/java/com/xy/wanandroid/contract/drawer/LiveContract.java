package com.xy.wanandroid.contract.drawer;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.drawer.LiveUrl;

/**
 * Created by jxy on 2018/7/23.
 */

public class LiveContract {

    public interface View extends AbstractView {

        void getLiveUrlOk(LiveUrl liveUrl);

        void getLiveUrlErr(String info);

    }

    public interface Presenter extends AbsPresenter<LiveContract.View> {

        void getLiveUrl(String roomId);

    }
}
