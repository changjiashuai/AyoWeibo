package sample.http;

import org.ayo.http.AyoRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.JsonResponseDispatcher;
import org.ayo.http.worker.HttpWorkerUseOkhttp;

import java.util.List;

import sample.model.Gallery;
import sample.model.GalleryClass;

/**
 * 天狗API封装：美女图片版
 * Created by Administrator on 2016/4/11.
 */
public class Httpper {

    public static AyoRequest request(){
        AyoRequest r = AyoRequest.newInstance()
                .header("os", "android")      //可选
                .header("version", "1.0.0")   // 可选
                .myResponseClass(MyResponseMode.class)  //必须，且理论上应该项目唯一
                //.worker(new HttpWorkerUseXUtils(true)); //必须
                .worker(new HttpWorkerUseOkhttp()); //必须
                //.worker(new HttpWorkerUseXUtils()); //必须
        return r;
    }

    /**
     * 天狗云，图片类别
     * @param flag
     * @param callback
     */
    public static void getGalleryClassList(String flag, BaseHttpCallback<List<GalleryClass>> callback){
        Httpper.request().flag(flag)
                .url("http://www.tngou.net/tnfs/api/classify")
                .method("get")
                .go(new JsonResponseDispatcher<List<GalleryClass>>(GalleryClass.class), callback);
    }


    /**
     * 天狗云，按类别查图片列表
     * @param flag
     * @param classId  GalleryClass id
     * @param pageNow  从1开始
     * @param callback
     */
    public static void getGalleryByClass(String flag, String classId, int pageNow, BaseHttpCallback<List<Gallery>> callback){
        Httpper.request().flag(flag)
                .url("http://www.tngou.net/tnfs/api/list")
                .method("post")
                .param("page", pageNow + "")
                .param("rows", "20")
                .param("id", classId)
                .go(new JsonResponseDispatcher<List<Gallery>>(Gallery.class), callback);
    }
}
