package com.xy.wanandroid.model.api;

import com.xy.wanandroid.data.knowledge.KnowledgeClassifyListBean;
import com.xy.wanandroid.data.knowledge.KnowledgeListBean;
import com.xy.wanandroid.data.main.BannerBean;
import com.xy.wanandroid.data.main.HomePageArticleBean;
import com.xy.wanandroid.data.main.SearchHot;
import com.xy.wanandroid.data.main.SearchResult;
import com.xy.wanandroid.data.project.ProjectBean;
import com.xy.wanandroid.data.project.ProjectListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public interface ApiService {
    /**
     * 主页
     */
    @GET("article/list/{page}/json")
    Observable<BaseResp<HomePageArticleBean>> getArticleList(@Path("page") int num);

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

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    Observable<BaseResp<List<SearchHot>>> getSearchHot();

    /**
     * 获取搜索结果
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseResp<SearchResult>> getSearchResult(@Path("page") int page, @Field("k") String key);

}
