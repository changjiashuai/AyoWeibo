package org.ayo.weibo.api;

import org.ayo.weibo.model.LoginUserInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/21.
 */
public interface WBUserApi {
    @GET("users/show.json")
    Observable<LoginUserInfo> getUserInfo(
            @Query("access_token") String access_token,
            @Query("uid") String uid,
            @Query("screen_name") String screen_name
    );

}
