package org.ayo.imageloader;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;

/**
 *
 */
public class ImageLoaderUseFresco implements AyoImageLoader {

    public ImageLoaderUseFresco(Context context, String cacheDir){
        initFresco(context, cacheDir);
    }

    public static void initFresco(Context context, String cacheDir) {
        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };

        final ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(context)
                .setProgressiveJpegConfig(pjpegConfig)
                .setNetworkFetcher(new HttpUrlConnectionNetworkFetcher())
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context).setBaseDirectoryName("image_cache")
                                .setBaseDirectoryPath(new File(cacheDir))
                                .setMaxCacheSize(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnLowDiskSpace(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnVeryLowDiskSpace(20 * ByteConstants.MB).build())
                .build();
        Fresco.initialize(context, config);
    }

    @Override
    public void showImage(View iv, String uri, String thumbUri, int placeHolderLoading, int placeHolderFail, ImageLoaderCallback callback) {
        if(!(iv instanceof SimpleDraweeView)){
            throw new RuntimeException("参数1必须是SimpleDraweeView");
        }
        SimpleDraweeView sdv = (SimpleDraweeView) iv;
        sdv.setImageURI(VanGogh.toUri(uri));
    }

    @Override
    public void showImage(View iv, int resId, ImageLoaderCallback callback) {

    }

    @Override
    public void loadImage(String url, String savePath, ImageLoaderCallback callback) {

    }

    @Override
    public String getCachePath(String uri) {

        if(VanGogh.isHttpUrl(uri)){
            ///自己没摸索出来
//            ImageRequest r = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri)).build();
//            DiskStorageCache cache = Fresco.getImagePipelineFactory().getMainDiskStorageCache();
//            CacheKey key = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(r);
//            if(cache.hasKey(key)){
//                return cache.getResource().
//            }

            ///网上找的方法，说是不推荐
            FileBinaryResource resource = (FileBinaryResource)Fresco.getImagePipelineFactory()
                    .getMainDiskStorageCache().getResource(new SimpleCacheKey(uri.toString()));
            File file = resource.getFile();
            return file.getAbsolutePath();
        }else{
            return uri;
        }

    }

    private boolean isDownloaded(Uri loadUri) {

        if (loadUri == null) {
            return false;
        }
        ImageRequest imageRequest = ImageRequest.fromUri(loadUri);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest);
        return ImagePipelineFactory.getInstance()
                .getMainDiskStorageCache().hasKey(cacheKey);
    }
}
