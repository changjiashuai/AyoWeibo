package com.ayoview.sample.ztmpl_listview.http;


import org.ayo.http.AyoRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.JsonResponseDispatcher;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.worker.HttpWorkerUseOkhttp;

public class TestHttper {
	
	public static AyoRequest getAyoRequest(Class<? extends ResponseModel> clazz) {

        return AyoRequest.newInstance()
				.myResponseClass(clazz)
				.header("os", "android")
                .header("sid", "")
                .header("version", "1");
    }
	
	public static void getArticle(String flag, int pageNow,  final BaseHttpCallback<TestOrderList> callback, Class<? extends ResponseModel> clazz){
		TestHttper.getAyoRequest(clazz)
				.flag(flag)
				.url("http://api.daogou.bjzzcb.com/v3/channel/home2_2_1")//
				.worker(new HttpWorkerUseOkhttp())
				//.worker(new HttpWorkerUseVolly(true))
				.method("post")
				.param("startId", "0")
				.param("tord", "up")
				.param("cate", "0")
				.param("topicId", "0")
				.param("count", "20")
				.param("tagId", "0")
				.go(new JsonResponseDispatcher<>(TestOrderList.class, new FastJsonParser()), callback);
	}
	
}
