package sample.http;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.JsonResponseDispatcher;

import java.util.List;

import sample.model.Gallery;

/**
 */
public class HttpperTmpl {

    public static void getGalleryByClass(String flag, String classId, int pageNow, BaseHttpCallback<List<Gallery>> callback){
        Httper.request().flag(flag)
                .url("http://www.tngou.net/tnfs/api/list")
                .method("post")
                .param("page", pageNow + "")
                .param("rows", "20")
                .param("id", classId)
                .go(new JsonResponseDispatcher<List<Gallery>>(Gallery.class, new FastJsonParser()), callback);
    }
}
