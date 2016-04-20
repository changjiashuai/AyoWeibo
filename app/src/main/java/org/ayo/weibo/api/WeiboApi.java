package org.ayo.weibo.api;

import com.sina.weibo.sdk.demo.AccessTokenKeeper;

import org.ayo.http.AyoRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.worker.HttpWorkerUseOkhttp;

import org.ayo.weibo.App;
import sample.http.FastJsonParser;
import org.ayo.weibo.model.ResponseTimeline;

/**
 * 微博api接口
 *
 * app scret  fb11d1bde949b9234ac38946dedf2d68
 * app key  489733908
 *
 * Created by Administrator on 2016/4/13.
 */
public class WeiboApi {

    public static AyoRequest request(){
        AyoRequest r = AyoRequest.newInstance()
                .header("os", "android")      //可选
                .header("version", "1.0.0")   // 可选
                .myResponseClass(WeiboResponseModel.class)  //必须，且理论上应该项目唯一
                        //.worker(new HttpWorkerUseXUtils(true)); //必须
                        //.worker(new HttpWorkerUseOkhttp()); //必须
                .worker(new HttpWorkerUseOkhttp()); //必须
        return r;
    }

    /**
     * access_token	true	string	采用OAuth授权方式为必填参数，OAuth授权后获得。
     count	false	int	单页返回的记录条数，默认为50。
     page	false	int	返回结果的页码，默认为1。
     base_app	false	int	是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param flag
     * @param callback
     */
    public static void getPublicTimelines(String flag, BaseHttpCallback<ResponseTimeline> callback){
        WeiboApi.request().flag(flag)
                .url("https://api.weibo.com/2/statuses/public_timeline.json")
                .method("get")
                .param("access_token", AccessTokenKeeper.readAccessToken(App.app).getToken())
                .param("count", "50")
                .param("page", "1")
                .param("base_app", "0")
                .go(new WeiboJsonDispatcher(ResponseTimeline.class, new FastJsonParser()), callback);
    }

}
