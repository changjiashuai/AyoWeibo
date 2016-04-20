package com.ayo.weibo.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import org.ayo.weibo.model.ResponseTimeline;

/**
 * Created by Administrator on 2016/4/17.
 */
public interface WeiboService {

    @GET("statuses/public_timeline.json")
    Observable<ResponseTimeline> getPublicTimelines(
            @Query("access_token") String access_token,
            @Query("count") String count,
            @Query("page") String page,
            @Query("base_app") String base_app
            );


}
