package sample;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;

import org.ayo.http.AyoHttp;
import org.ayo.http.retrofit.RetrofitManager;

/**
 * Created by Administrator on 2016/4/11.
 */
public class App extends Application {

    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        AyoHttp.init(this);
        initFresco(this);
        final Thread.UncaughtExceptionHandler d = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
                d.uncaughtException(thread, throwable);
            }
        });

        RetrofitManager.init(this, "https://api.weibo.com/2/");

    }

    public static void initFresco(Context context) {
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
                                .setBaseDirectoryPath(Environment.getDataDirectory())
                                .setMaxCacheSize(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnLowDiskSpace(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnVeryLowDiskSpace(20 * ByteConstants.MB).build())
                .build();
        Fresco.initialize(context, config);
    }

}
