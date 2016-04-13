package org.ayo.http.worker;

import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.ayo.http.AyoRequest;
import org.ayo.http.AyoResponse;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseDispatcher;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.framed.Header;

/**
 */
public class HttpWorkerUseOkhttp implements HttpWorker {

    static{
        OkHttpUtils.getInstance().setConnectTimeout(30000, TimeUnit.MILLISECONDS); //连接超时，30秒
        OkHttpUtils.getInstance().setReadTimeout(30000, TimeUnit.MILLISECONDS); //读超时，30秒
        OkHttpUtils.getInstance().setWriteTimeout(30000, TimeUnit.MILLISECONDS); //写超时，30秒
        //使用https，但是默认信任全部证书
        OkHttpUtils.getInstance().setCertificates();
    }

//    @Override
//    public <T> AyoResponse performRequest(AyoRequest request) {
//        return null;
//    }

    @Override
    public <T> void performRequestAsync(AyoRequest request, BaseResponseDispatcher responseHandler, BaseHttpCallback<T> callback) {
        String url = request.url;
        //基于OkHttpUtils辅助类


        //1 method决定了OkHttpRequestBuilder的哪个子类
        if(request.method.equalsIgnoreCase("get")){
            OkHttpUtils
                    .get()
                    .headers(request.headers)
                    .url(url)
                    .tag(request.flag)
                    .build()
                    .execute(new MyStringCallback(request, responseHandler, callback));
        }else if(request.method.equalsIgnoreCase("post")){
            boolean hasStringEntity = (request.stringEntity != null && !request.stringEntity.equals(""));
            boolean postFileLikeForm = (request.files != null && request.files.size() > 0);
            boolean postFileLikeStream = (request.file != null);

            //情况1：postForm
            if(!hasStringEntity && !postFileLikeForm && !postFileLikeStream){
                OkHttpUtils
                        .post()//
                        .url(url)
                        .headers(request.headers)
                        .params(request.params)
                        .tag(request.flag)
                        .build()
                        .execute(new MyStringCallback(request, responseHandler, callback));
            }
            //情况2：postString
            //情况3：postFile--流形式，不带name，带mime
            //情况4：postFile--表单形式，带name，带filename
            if(hasStringEntity){
                OkHttpUtils
                        .postString()
                        .url(url)
                        .headers(request.headers)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .content(request.stringEntity)
                        .tag(request.flag)
                        .build()
                        .execute(new MyStringCallback(request, responseHandler, callback));
            }else if(postFileLikeStream){
                OkHttpUtils
                        .postFile()
                        .url(url)
                        .headers(request.headers)//
                        .file(request.file)
                        .build()
                        .execute(new MyStringCallback(request, responseHandler, callback));
            }else if(postFileLikeForm){
                PostFormBuilder b = OkHttpUtils.post();
                for(String key: request.files.keySet()){
                    File f = request.files.get(key);
                    b.addFile(key, f.getName(), f);
                }

                b.url(url)//
                    .params(request.params)//
                    .headers(request.headers)//
                    .build()//
                    .execute(new MyStringCallback(request, responseHandler, callback));
            }

        }else{
            throw new RuntimeException("使用了不支持的http谓词：" + request.method);
        }

    }

    private void addHeader(OkHttpRequestBuilder builder, Map<String, String> headers){

    }

    public class MyStringCallback<T> extends StringCallback
    {

        private BaseResponseDispatcher responseDispatcher;
        private BaseHttpCallback<T> callback;
        AyoRequest request;
        AyoResponse resp = new AyoResponse();

        public MyStringCallback(AyoRequest request, BaseResponseDispatcher responseDispatcher, BaseHttpCallback<T> callback) {
            this.responseDispatcher = responseDispatcher;
            this.callback = callback;
            this.request = request;
        }

        @Override
        public void onBefore(Request request)
        {
            Log.i("dddddd", "onBefore");
        }

        @Override
        public void onAfter()
        {
            Log.i("dddddd", "onAfter");
        }

        @Override
        public void onError(Call call, Exception e)
        {
            Log.e("dddddd", "aaaa", e);
            //Log.i("dddddd", "onError");
            if(resp.code == -999) resp.code = -1;
            resp.data = e.getLocalizedMessage();
            resp.exception = e;
            resp.inputStream = null;
            responseDispatcher.process(request.flag, resp, callback, request.myRespClazz);
        }

        @Override
        public void onResponse(String response)
        {
            //Log.i("dddddd", "onResponse--" + response);
            if(resp.code == -999)  resp.code = 200;
            resp.data = response;
            resp.inputStream = null;
            responseDispatcher.process(request.flag, resp, callback, request.myRespClazz);
        }

        @Override
        public void inProgress(float progress)
        {
            //Log.i("dddddd", "inProgress--" + progress);
            callback.onLoading((long) (progress*100), 100L);
        }

        @Override
        public String parseNetworkResponse(Response response) throws IOException {

            //Log.i("dddddd", "inProgress--" + response.code());

            resp.code = response.code();
            Headers header = response.headers();
            if(header != null){
                for(String name: header.names()){
                    resp.headers.put(name, header.get(name));
                }
            }
            return super.parseNetworkResponse(response);
        }
    }

//    @Override
//    public boolean enableAsync() {
//        return false;
//    }
}
