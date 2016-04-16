package org.ayo.imageloader;

import android.view.View;


/**
 * 图片加载器的接口
 */
public interface AyoImageLoader {

    /**
     * 显示
     * @param iv
     * @param uri 原图url，可以是http，也可以是本地路径（以file://开头），也可以是本地绝对路径（以/开头）
     * @param thumbUri 缩略图url，在这里当做先行图用，如果有效，则覆盖placeHolderLoading和placeHolderFail，可以是http，但暂时还不支持，只能是本地路径，以file://开头
     * @param placeHolderLoading 如果缩略图无效，即为空或者为null，则使用这个图作为loading占位图
     * @param placeHolderFail 如果缩略图无效，即为空或者为null，则使用这个图作为fail占位图
     * @param callback
     */
    void showImage(View iv, String uri, String thumbUri, int placeHolderLoading, int placeHolderFail, ImageLoaderCallback callback);

    /**
     * 显示res下的图片
     * @param iv
     * @param resId
     * @param callback
     */
    void showImage(View iv, int resId, ImageLoaderCallback callback);


    /**
     * 下载
     * @param url
     * @param savePath 全路径
     * @param callback
     */
    void loadImage(String url, final String savePath, final ImageLoaderCallback callback);

    /**
     * 获取本地缓存的路径，无则返回null
     * @param url
     * @return
     */
    String getCachePath(String url);
}
