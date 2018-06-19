package app.model.api;

import java.util.List;

import app.data.knowledge.KnowledgeClassifyListBean;
import app.data.knowledge.KnowledgeListBean;
import app.data.main.BannerBean;
import app.data.main.HomePageArticleBean;
import app.data.project.ProjectBean;
import app.data.project.ProjectListBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    /**
     * 项目分类列表
     */
    @GET("project/tree/json")
    Observable<BaseResp<List<ProjectBean>>> getProjectTitle();

    /**
     * 单个项目列表
     */
    @GET("project/list/{page}/json")
    Observable<BaseResp<ProjectListBean>> getProjectList(@Path("page") int page, @Query("cid") int id);

    /**
     * 单个知识体系列表
     */
    @GET("article/list/{page}/json")
    Observable<BaseResp<KnowledgeClassifyListBean>> getKnowledgeClassifyList(@Path("page") int page, @Query("cid") int id);
}
