package org.ayo.http.retrofit;

import android.content.Context;

import org.ayo.http.retrofit.adapter.rxjava.RxJavaCallAdapterFactory;
import org.ayo.http.retrofit.converter.fastjson.FastJsonConvertFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * 管理Retrofit，这个库没法被AyoHttp兼容，因为它本身是更高级的库，低级的东西不能兼容高级的东西
 *
 * 参考：http://www.zircon.me/01-25-2016/about-retrofit.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
 */
public class RetrofitManager {

    private static Retrofit retrofit = null;
    private static String BASE_URL = "";

    /**
     *
     * @param context
     * @param baseUrl  必须以/结尾
     */
    public static void init(Context context, String baseUrl){
        //"http://example.com/"
        BASE_URL = baseUrl;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("version", "1.0.0")
                                .addHeader("os", "android")
                                .addHeader("sid", "sid")
                                .build();
                        return chain.proceed(request);
                    }
                }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FastJsonConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static Retrofit getRetrofit(){
        return retrofit;
    }

}
