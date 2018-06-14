package app.model.api;

import java.util.List;

import app.model.data.knowledge.KnowledgeListBean;
import app.model.data.main.BannerBean;
import app.model.data.main.HomePageArticleBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public interface ApiService {
    /**
     * 主页
     */
    @GET("article/list/{num}/json")
    Observable<BaseResp<HomePageArticleBean>> getArticleList(@Path("num") int num);

    /**
     * banner
     */
    @GET("banner/json")
    Observable<BaseResp<List<BannerBean>>> getBanner();

    /**
     * 知识体系列表
     */
    @GET("tree/json")
    Observable<BaseResp<List<KnowledgeListBean>>> getKnowledgeList();
}
