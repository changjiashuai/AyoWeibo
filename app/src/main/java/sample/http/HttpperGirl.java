package sample.http;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.JsonResponseDispatcher;

import java.util.List;

import sample.model.Gallery;
import sample.model.GalleryClass;

/**
 * 天狗API封装：美女图片版
 * Created by Administrator on 2016/4/11.
 */
public class HttpperGirl {



    /**
     * 天狗云，图片类别
     * @param flag
     * @param callback
     */
    public static void getGalleryClassList(String flag, BaseHttpCallback<List<GalleryClass>> callback){
        Httper.request().flag(flag)
                .url("http://www.tngou.net/tnfs/api/classify")
                .method("get")
                .go(new JsonResponseDispatcher<List<GalleryClass>>(GalleryClass.class, new FastJsonParser()), callback);
    }


    /**
     * 天狗云，按类别查图片列表
     * @param flag
     * @param classId  GalleryClass id
     * @param pageNow  从1开始
     * @param callback
     */
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
