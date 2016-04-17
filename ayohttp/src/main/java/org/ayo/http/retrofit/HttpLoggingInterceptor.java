package org.ayo.http.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/4/17.
 */
public class HttpLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Request customization: add request headers
//        Request.Builder requestBuilder = original.newBuilder()
//                .header("log", "log")
//                .method(original.method(), original.body());
//
//        Request request = requestBuilder.build();

        return chain.proceed(original);
    }
}
