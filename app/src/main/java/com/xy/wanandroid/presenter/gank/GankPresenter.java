package com.xy.wanandroid.presenter.gank;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.GankContract;
import com.xy.wanandroid.data.gank.MusicBanner;
import com.xy.wanandroid.data.gank.RecommendData;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.util.app.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankPresenter extends BasePresenter<GankContract.View> implements GankContract.Presenter {

    private boolean isRefresh = true;
    private List<RecommendData.ResultsBean> js = new ArrayList<>();
    private List<RecommendData.ResultsBean> android = new ArrayList<>();
    private List<RecommendData.ResultsBean> ios = new ArrayList<>();
    private List<RecommendData.ResultsBean> recommend = new ArrayList<>();
    private List<RecommendData.ResultsBean> app = new ArrayList<>();
    private List<RecommendData.ResultsBean> rest = new ArrayList<>();
    private List<RecommendData.ResultsBean> extra = new ArrayList<>();

    @Inject
    public GankPresenter() {

    }

    @Override
    public void getMusicBanner() {
        ApiStore.createApi(ApiService.class)
                .getMusicBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<MusicBanner>() {
                    @Override
                    public void onNext(MusicBanner banner) {
                        mView.getMusicBannerOK(banner);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("获取Banner失败" + e.getMessage());
                        mView.getMusicBannerErr("获取Banner失败");
                    }
                });
    }

    @Override
    public void getEveryDayList() {
        ApiStore.createApi(ApiService.class)
                .getEveryDayData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RecommendData>() {
                    @Override
                    public void onNext(RecommendData data) {
                        mView.getEveryDayListOk(data, isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getEveryDayListErr(e.getMessage());
                    }
                });
    }

    /**
     * 把集合分类
     */
    private void sortList(List<RecommendData.ResultsBean> gankList) {
        for (RecommendData.ResultsBean bean : gankList) {
            if (bean.getType().equals("Android")) {
                android.add(bean);
            } else if (bean.getType().equals("iOS")) {
                ios.add(bean);
            } else if (bean.getType().equals("App")) {
                app.add(bean);
            } else if (bean.getType().equals("前端")) {
                js.add(bean);
            } else if (bean.getType().equals("拓展资源")) {
                extra.add(bean);
            } else if (bean.getType().equals("瞎推荐")) {
                recommend.add(bean);
            } else if (bean.getType().equals("休息视频")) {
                rest.add(bean);
            }
        }
        /**
         * 对allList中的每一个集合的size进行排序。
         * 尽可能确保第一个集合的数量大于3,否则item之间间距有问题。
         */
        List<List<RecommendData.ResultsBean>> allList = new ArrayList<>();
        allList.add(android);
        allList.add(ios);
        allList.add(app);
        allList.add(js);
        allList.add(extra);
        allList.add(recommend);
        allList.add(rest);
        Collections.sort(allList, (o1, o2) -> {
            if (o1.size() < o2.size()) {
                return 1;
            } else if (o1.size() == o2.size()) {
                return 0;
            }
            return -1;
        });
    }

    /**
     * 根据某个具体分类集合的数量判断布局
     *
     * @param list
     * @return
     */
    private List<RecommendData.ResultsBean> chooseLayout(List<RecommendData.ResultsBean> list) {
        RecommendData.ResultsBean resultsBean = null;
        List<RecommendData.ResultsBean> resultsBeanList = new ArrayList<>();
        int size = list.size();
        if (size > 0)
            for (RecommendData.ResultsBean bean : list) {
                //第一种方式(错误)
//                if (size > 3) {
//                    resultsBean = new RecommendData.ResultsBean(RecommendData.ResultsBean.ENTITY_ITEM_THREE, bean.getDesc(), bean.getPublishedAt(), bean.getType(), bean.getUrl(), bean.getWho(), bean.getImages());
//                    size--;
//                } else {
//                    if (size > 0)
//                        if (size % 2 == 0 || size % 3 == 2) {
//                            resultsBean = new RecommendData.ResultsBean(RecommendData.ResultsBean.ENTITY_ITEM_TWO, bean.getDesc(), bean.getPublishedAt(), bean.getType(), bean.getUrl(), bean.getWho(), bean.getImages());
//                        } else if (size % 3 == 1) {
//                            resultsBean = new RecommendData.ResultsBean(RecommendData.ResultsBean.ENTITY_ITEM_ONE, bean.getDesc(), bean.getPublishedAt(), bean.getType(), bean.getUrl(), bean.getWho(), bean.getImages());
//                        }
//                }
                //第二种方式
                if (size % 3 == 0) {
                    resultsBean = new RecommendData.ResultsBean(RecommendData.ResultsBean.ENTITY_ITEM_THREE, bean.getDesc(), bean.getPublishedAt(), bean.getType(), bean.getUrl(), bean.getWho(), bean.getImages());
                } else if (size % 2 == 0 || size % 3 == 2) {
                    resultsBean = new RecommendData.ResultsBean(RecommendData.ResultsBean.ENTITY_ITEM_TWO, bean.getDesc(), bean.getPublishedAt(), bean.getType(), bean.getUrl(), bean.getWho(), bean.getImages());
                } else if (size % 3 == 1) {
                    resultsBean = new RecommendData.ResultsBean(RecommendData.ResultsBean.ENTITY_ITEM_ONE, bean.getDesc(), bean.getPublishedAt(), bean.getType(), bean.getUrl(), bean.getWho(), bean.getImages());
                }
                resultsBeanList.add(resultsBean);
            }
        return resultsBeanList;
    }

    /**
     * 分类完成添加进新的集合
     */
    public List<RecommendData.ResultsBean> getList(List<RecommendData.ResultsBean> gankList) {
        List<RecommendData.ResultsBean> lists = new ArrayList<>();
        sortList(gankList);
        lists.addAll(chooseLayout(android));
        lists.addAll(chooseLayout(ios));
        lists.addAll(chooseLayout(js));
        lists.addAll(chooseLayout(app));
        lists.addAll(chooseLayout(extra));
        lists.addAll(chooseLayout(recommend));
        lists.addAll(chooseLayout(rest));
        return lists;
    }
}
