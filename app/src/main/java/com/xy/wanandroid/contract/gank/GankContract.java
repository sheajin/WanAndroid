package com.xy.wanandroid.contract.gank;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.gank.EverydayData;
import com.xy.wanandroid.data.gank.MusicBanner;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankContract {

    public interface View extends AbstractView {

        void getEveryDayListOk(EverydayData data, boolean isRefresh);

        void getEveryDayListErr(String info);

        void getMusicBannerOK(MusicBanner banner);

        void getMusicBannerErr(String info);

    }

    public interface Presenter extends AbsPresenter<GankContract.View> {

        void getMusicBanner();

        void getEveryDayList();

    }
}
