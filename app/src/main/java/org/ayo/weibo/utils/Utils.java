package org.ayo.weibo.utils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.demo.AccessTokenKeeper;

import org.ayo.weibo.App;

/**
 * Created by Administrator on 2016/4/20.
 */
public class Utils {

    public static int getColor(int colorId){
        return App.app.getResources().getColor(colorId);
    }


    /**
     * 获取授权微博用户的token
     * @return
     */
    public static String getWeiboToken(){
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(App.app);
        if(token != null){
            return token.getToken();
        }else{
            return "";
        }
    }

    /**
     * 获取授权微博用户的uid
     * @return
     */
    public static String getCurrentWeiboUserUid(){
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(App.app);
        if(token != null){
            return token.getUid();
        }else{
            return "";
        }
    }

}
