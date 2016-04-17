package org.ayo.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.File;
import java.io.FileOutputStream;


/**
 * This class provided an interface which would be implemented by any third party image library.
 * Now the implementation is UniveralImageDownloader
 *
 */
public class ImageLoaderUseUIL implements AyoImageLoader{

	DisplayImageOptions options;
	DisplayImageOptions.Builder optionsBuilder;

	public ImageLoaderUseUIL(Context context, String cacheDir){
		optionsBuilder = new DisplayImageOptions.Builder();
		optionsBuilder.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(1500));

		//DisplayImageOptions.Builder opt = new DisplayImageOptions.Builder();
		//opt.bitmapConfig(Bitmap.Config.ALPHA_8);
//		opt.cacheInMemory(true);
//		opt.cacheOnDisk(true);
//		opt.considerExifParams(true);
		//opt.decodingOptions();- //?????
		//opt.delayBeforeLoading(delayInMillis);
		//opt.delayBeforeLoading(300);
		//opt.displayer(new FadeInBitmapDisplayer(500));//CircleBitmapDisplayer, RoundedBitmapDisplayer, RoundedVignetteBitmapDisplayer, SimpleBitmapDisplayer
		//opt.extraForDownloader(Object);
		//opt.handler(Handler);
		//opt.imageScaleType(ImageView.ScaleType)

		//opt.preProcessor(BitmapProcessor);
		//opt.postProcessor(BitmapProcessor);
		//opt.resetViewBeforeLoading(true);
		//opt.showImageOnLoading(int|Drawable);
		//opt.showImageOnFail(int|Drawable);
		//opt.showImageForEmptyUri(int|Drawable);

		ImageLoaderConfiguration config  = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCache(new UnlimitedDiskCache(new File(cacheDir)))
						//.diskCacheFileNameGenerator(new SimpleFileNameGenerator())
				.memoryCacheExtraOptions(480, 800)
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
						//.diskCacheSize(50 * 1024 * 1024) // 100
						//.diskCacheFileNameGenerator(new Md5FileNameGenerator())
						//.diskCacheFileCount(500)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove
				.threadPoolSize(2)
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout-5s, readTimeout-30s
				.defaultDisplayImageOptions(optionsBuilder.build())
				.build();
		ImageLoader.getInstance().init(config);
	}


	private DisplayImageOptions.Builder getDisplayImageOptionsBuilder(){
		DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder();
		optionsBuilder.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(1500));
		return optionsBuilder;
	}

	////---------------------------------------------------
	public void loadImage(String url, final String savePath, final ImageLoaderCallback callback){

		if(VanGogh.isValidUri(url)){
			File localFile = null;
			if(VanGogh.isLocalPath(url)){
				localFile = new File(url);
			}else{
				localFile = ImageLoader.getInstance().getDiskCache().get(url);
			}

			if(localFile != null && localFile.exists()){
				localFile.renameTo(new File(savePath));
				if(callback != null) callback.onFinish(true, url, savePath, null);
				return;
			}else{
			}

			//在这才真正去下载
			ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
				}

				@Override
				public void onLoadingFailed(String imageUri, View view,
											FailReason failReason) {
					if(callback != null){
						//boolean isSuccess, String uri, String savedPath, String failInfo
						String errorInfo = "";
						if(failReason != null){
							if(failReason.getCause() != null){
								errorInfo = failReason.getCause().getLocalizedMessage();
								failReason.getCause().printStackTrace();
							}
						}
						callback.onFinish(false, imageUri, null, failReason.getCause().getLocalizedMessage());
					}else{
						if(failReason != null){
							if(failReason.getCause() != null){
								failReason.getCause().printStackTrace();
							}
						}
					}
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					try {
						File f = new File(savePath);
						if(!f.exists()){
							f.createNewFile();
						}
						FileOutputStream out = new FileOutputStream(f);

						loadedImage.compress(CompressFormat.PNG, 100, out);
						out.flush();
						out.close();
						if(callback != null) {
							callback.onFinish(true, imageUri, savePath, null);
						}
					} catch (Throwable e) {
						e.printStackTrace();
						if(callback != null) {
							callback.onFinish(false, imageUri, null, e.getLocalizedMessage());
						}
					}

				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					if(callback != null) {
						callback.onFinish(false, imageUri, null, "被取消: " + imageUri);
					}
				}
			});

		}else{
			throw new IllegalArgumentException("非法图片路径：" + url);
		}


	}

	@Override
	public String getCachePath(String url) {
		try {
			if(DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache()) == null){
				return "";
			}else{
				return DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache()).getAbsolutePath();
			}
			//return ImageLoader.getInstance().getDiskCache().get(url).getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public void showImage(View iv, String uri, String thumbUri, int placeHolderLoading, int placeHolderFail, ImageLoaderCallback callback) {

		if(!(iv instanceof ImageView)){
			throw new RuntimeException("参数1必须是ImageView");
		}
		Log.i("aaa", "1111");
		if(VanGogh.isValidUri(thumbUri)){
			//先加载先行缩略图，并且不用等待结束
			showImage(iv, thumbUri, null, 0, 0, null);
			placeHolderLoading = 0;
			placeHolderFail = 0;
		}
		Log.i("aaa", "2222");
		String url = "";
		if(VanGogh.isValidUri(uri)){
			if(VanGogh.isLocalPath(uri)){
				url = VanGogh.getUri(uri);
			}else{
				url = uri;
			}
			if(url == null){
				throw new IllegalArgumentException("非法本地图片路径：" + uri);
			}

			DisplayImageOptions.Builder db = getDisplayImageOptionsBuilder();
			if(VanGogh.isValidResourceId(placeHolderLoading)){
				db.showImageOnLoading(placeHolderLoading);
			}

			if(VanGogh.isValidResourceId(placeHolderFail)){
				db.showImageOnFail(placeHolderFail);
			}
			Log.i("aaa", "3333");
			ImageLoader.getInstance().displayImage(url, (ImageView)iv, db.build(), new MyImageLoadingListener(callback), new MyImageLoadingProgressListener(callback));
		}else{
			throw new IllegalArgumentException("非法图片路径：" + uri);
		}

	}

	@Override
	public void showImage(View iv, int resId, ImageLoaderCallback callback) {
		if(!(iv instanceof ImageView)){
			throw new RuntimeException("参数1必须是ImageView");
		}
		DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder();
		String uri = "drawable://" + resId;
		ImageLoader.getInstance().displayImage(uri, (ImageView)iv, optionsBuilder.build(), null, null);
	}

	class MyImageLoadingListener implements ImageLoadingListener{

		private ImageLoaderCallback callback;

		public MyImageLoadingListener(ImageLoaderCallback callback){
			this.callback = callback;
		}

		@Override
		public void onLoadingStarted(String imageUri, View view) {

		}

		@Override
		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			if(callback != null){
				//boolean isSuccess, String uri, String savedPath, String failInfo
				String errorInfo = "";
				if(failReason != null){
					if(failReason.getCause() != null){
						errorInfo = failReason.getCause().getLocalizedMessage();
						failReason.getCause().printStackTrace();
					}
				}
				callback.onFinish(false, imageUri, null, failReason.getCause().getLocalizedMessage());
			}else{
				if(failReason != null){
					if(failReason.getCause() != null){
						failReason.getCause().printStackTrace();
					}
				}
			}
		}

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if(callback != null){
				callback.onFinish(true, imageUri, null, null);
			}
		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			if(callback != null){
				callback.onFinish(false, imageUri, null, "被取消：" + imageUri);
			}
		}
	};

	class MyImageLoadingProgressListener implements ImageLoadingProgressListener{

		private ImageLoaderCallback callback;

		public MyImageLoadingProgressListener(ImageLoaderCallback callback){
			this.callback = callback;
		}

		@Override
		public void onProgressUpdate(String imageUri, View view, int current, int total) {
			if(callback != null){
				callback.onLoading(imageUri, current, total);
			}
		}
	}

}
