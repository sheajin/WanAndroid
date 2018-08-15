package com.xy.wanandroid.model.api;

import com.xy.wanandroid.data.gank.CategoryTitle;
import com.xy.wanandroid.data.gank.EverydayData;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.data.gank.LiveList;
import com.xy.wanandroid.data.gank.LiveUrl;
import com.xy.wanandroid.data.gank.MovieDetailBean;
import com.xy.wanandroid.data.gank.MusicBanner;
import com.xy.wanandroid.data.gank.RecommendData;
import com.xy.wanandroid.data.gank.RecommendEntity;
import com.xy.wanandroid.data.knowledge.KnowledgeClassifyListBean;
import com.xy.wanandroid.data.knowledge.KnowledgeListBean;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.data.main.BannerBean;
import com.xy.wanandroid.data.main.HomePageArticleBean;
import com.xy.wanandroid.data.main.SearchHot;
import com.xy.wanandroid.data.project.ProjectBean;
import com.xy.wanandroid.data.project.ProjectListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public interface ApiService {
    /**
     * 主页
     */
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<BaseResp<HomePageArticleBean>> getArticleList(@Path("page") int num);

    /**
     * banner
     */
    @Headers({"baseUrl:normal"})
    @GET("banner/json")
    Observable<BaseResp<List<BannerBean>>> getBanner();

    /**
     * 知识体系列表
     */
    @Headers({"baseUrl:normal"})
    @GET("tree/json")
    Observable<BaseResp<List<KnowledgeListBean>>> getKnowledgeList();

    /**
     * 项目分类列表
     */
    @Headers({"baseUrl:normal"})
    @GET("project/tree/json")
    Observable<BaseResp<List<ProjectBean>>> getProjectTitle();

    /**
     * 单个项目列表
     */
    @Headers({"baseUrl:normal"})
    @GET("project/list/{page}/json")
    Observable<BaseResp<ProjectListBean>> getProjectList(@Path("page") int page, @Query("cid") int id);

    /**
     * 单个知识体系列表
     */
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<BaseResp<KnowledgeClassifyListBean>> getKnowledgeClassifyList(@Path("page") int page, @Query("cid") int id);

    /**
     * 搜索热词
     */
    @Headers({"baseUrl:normal"})
    @GET("hotkey/json")
    Observable<BaseResp<List<SearchHot>>> getSearchHot();

    /**
     * 获取搜索结果
     */
    @Headers({"baseUrl:normal"})
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseResp<HomePageArticleBean>> getSearchResult(@Path("page") int page, @Field("k") String key);

    /**
     * 常用网站
     */
    @Headers({"baseUrl:normal"})
    @GET("friend/json")
    Observable<BaseResp<List<SearchHot>>> getHotWeb();

    /**
     * 注册
     */
    @Headers({"baseUrl:normal"})
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResp<UserInfo>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 登录
     */
    @Headers({"baseUrl:normal"})
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResp<UserInfo>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 收藏文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/collect/{id}/json")
    Observable<BaseResp> collectArticle(@Path("id") int id);

    /**
     * 取消收藏文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResp> cancelCollectArticle(@Path("id") int id);

    /**
     * 收藏文章列表
     */
    @Headers({"baseUrl:normal"})
    @GET("lg/collect/list/{id}/json")
    Observable<BaseResp<HomePageArticleBean>> getCollectList(@Path("id") int id);

    /**
     * 取消收藏 收藏列表的文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResp<HomePageArticleBean>> cancelCollectArticleList(@Path("id") int id, @Field("originId") int originId);

    /**
     * 斗鱼 直播标题列表
     */
    @Headers({"baseUrl:douyu"})
    @GET("/api/v1/getColumnDetail")
    Observable<BaseResp<List<CategoryTitle>>> getLiveTitle(@QueryMap Map<String, String> params);

    /**
     * 斗鱼 直播列表
     */
    @Headers({"baseUrl:douyu"})
    @GET("/api/v1/live/{tag_id}")
    Observable<BaseResp<List<LiveList>>> getLiveList(@Path("tag_id") String tagId, @QueryMap Map<String, String> params);

    /**
     * 斗鱼 获取直播流
     */
    @Headers({"baseUrl:live"})
    @GET("/html5/live")
    Observable<BaseResp<LiveUrl>> getLiveUrl(@Query("roomId") String roomId);

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @Headers({"baseUrl:gank"})
    @GET("api/data/{type}/{offset}/{page}")
    Observable<RecommendEntity> getRecommendList(@Path("type") String type, @Path("offset") int offset, @Path("page") int page);

    /**
     * 获取音乐界面banner
     */
    @Headers({"baseUrl:music"})
    @GET("v1/restserver/ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Observable<MusicBanner> getMusicBanner();

    /**
     * 获取所有数据
     */
    @Headers({"baseUrl:gank"})
    @GET("api/data/all/8/1")
    Observable<RecommendData> getEveryDayData();

    /**
     * 获取每日推荐数据
     */
    @Headers({"baseUrl:gank"})
    @GET("api/today")
    Observable<EverydayData> getToday();

    /**
     * 下载图片
     */
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * 豆瓣热映电影，每日更新
     */
    @Headers({"baseUrl:douban"})
    @POST("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @Headers({"baseUrl:douban"})
    @GET("v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @Headers({"baseUrl:douban"})
    @GET("v2/movie/top250")
    Observable<HotMovieBean> getMovieTop(@Query("start") int start, @Query("count") int count);
}
