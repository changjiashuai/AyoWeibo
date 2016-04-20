package org.ayo.weibo.api2;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.weibo.Config;
import org.ayo.weibo.model.LoginUserInfo;

import sample.http.FastJsonParser;

/**
 * Created by Administrator on 2016/4/21.
 */
public class ApiUser {


    /**
     * 获取当前登录用户的信息
     * @param flag
     * @param access_token
     * @param uid
     * @param screen_name
     * @param callback
     */
    public static void getLoginUserInfo(String flag,
                                          String access_token,
                                          String uid,
                                          String screen_name,
                                          BaseHttpCallback<LoginUserInfo> callback) {
        WeiboApi.request().flag(flag)
                .url(Config.API.HOST_WEIBO + "users/show.json")
                .method("get")
                .param("access_token", access_token)
                .param("uid", uid)
                //.param("screen_name", screen_name)
                .go(new WeiboJsonDispatcher(LoginUserInfo.class, new FastJsonParser()), callback);
    }
}
