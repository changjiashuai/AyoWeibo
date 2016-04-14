package org.ayo.imageloader;

/**
 * Created by Administrator on 2016/4/14.
 */
public interface ImageLoaderCallback {

    void onLoading(String uri, int current, int total);
    void onFinish(boolean isSuccess, String uri, String savedPath, String failInfo);
}
