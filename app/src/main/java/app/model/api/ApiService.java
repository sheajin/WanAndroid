package app.model.api;

import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;

import java.util.List;

import app.model.data.UserInfo;
import app.model.data.main.HomePageArticleBean;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public interface ApiService {
    /**
     * 主页
     */
    @GET(AppConfig.ARTICLE + "list/{num}/json")
    Observable<BaseResp<HomePageArticleBean>> getArticleList(@Path("num") int num);
}
