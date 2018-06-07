package app.model.api;

import android.util.ArrayMap;

import app.model.data.UserInfo;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public interface ApiService {
    /**
     * 登录
     */
//    @Headers({"urlname:mdffx"})
    @FormUrlEncoded
    @POST(AppConfig.LOGIN)
    Observable<BaseResp<UserInfo>> login(@FieldMap ArrayMap<String, String> params);
}
