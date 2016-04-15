package org.ayo.imageloader;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/4/15.
 */
public class ImageLoaderUseFresco implements AyoImageLoader {
    @Override
    public void showImage(ImageView iv, String uri, String thumbUri, int placeHolderLoading, int placeHolderFail, ImageLoaderCallback callback) {
        
    }

    @Override
    public void showImage(ImageView iv, int resId, ImageLoaderCallback callback) {

    }

    @Override
    public void loadImage(String url, String savePath, ImageLoaderCallback callback) {

    }

    @Override
    public String getCachePath(String url) {
        return null;
    }
}
