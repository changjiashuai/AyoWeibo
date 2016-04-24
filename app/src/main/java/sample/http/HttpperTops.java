package sample.http;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.JsonResponseDispatcher;
import org.ayo.weibo.model.top.Top;

import java.util.List;

/**
 */
public class HttpperTops {

    public static void getTops(String flag, BaseHttpCallback<List<Top>> callback){
        Httper.request().flag(flag)
                .url("http://www.tngou.net/api/top/list")
                .method("get")
                .go(new JsonResponseDispatcher<List<Top>>(Top.class, new FastJsonParser()), callback);
    }
}
