package org.ayo.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * This class provided an interface which would be implemented by any third party image library.
 * Now the implementation is UniveralImageDownloader
 *
 */
public class VanGogh {

	private static int BIG_LOADING = 0;
	private static int BIG_ERROR = 0;
	private static int BIG_EMPTY = 0;
	private static int MIDDLE_LOADING = 0;
	private static int MIDDLE_ERROR = 0;
	private static int MIDDLE_EMPTY = 0;
	private static int SMALL_LOADING = 0;
	private static int SMALL_ERROR = 0;
	private static int SMALL_EMPTY = 0;

	public static Context context;
	private static AyoImageLoader imageLoader;

	/**
	 * init the module with some default base parameters
	 * @param context
	 */
	public static void init(Context context, AyoImageLoader imageLoader){
		VanGogh.context = context;
		VanGogh.imageLoader = imageLoader;
	}

	/**
	 * init the replace image for big image
	 */
	public static void initImageBig(int loading, int error, int empty){
		BIG_LOADING = loading;
		BIG_ERROR = error;
		BIG_EMPTY = empty;
	}

	/**
	 * init the replace image for middle image
	 */
	public static void initImageMiddle(int loading, int error, int empty){
		MIDDLE_LOADING = loading;
		MIDDLE_ERROR = error;
		MIDDLE_EMPTY = empty;
	}

	/**
	 * init the replace image for small image
	 */
	public static void initImageSmall(int loading, int error, int empty){
		SMALL_LOADING = loading;
		SMALL_ERROR = error;
		SMALL_EMPTY = empty;
	}

	/**
	 * init the replace image for big image
	 */
	public static void initImageBig(int img){
		initImageBig(img, img, img);
	}

	/**
	 * init the replace image for middle image
	 */
	public static void initImageMiddle(int img){
		initImageMiddle(img, img, img);
	}

	/**
	 * init the replace image for small image
	 */
	public static void initImageSmall(int img){
		initImageSmall(img, img, img);
	}

	ImageView iv;
	private VanGogh(ImageView iv){
		this.iv = iv;
	}

	public VanGogh imageLoading(int resId){
		b.showImageOnLoading(resId);
		return this;
	}

	public VanGogh imageError(int resId){
		b.showImageOnFail(resId);
		return this;
	}

	public VanGogh imageEmpty(int resId){
		b.showImageForEmptyUri(resId);
		return this;
	}

	public static VanGogh paper(ImageView iv){
		return new VanGogh(iv);
	}

	public void paint(String url, ImageLoadingListener listener, ImageLoadingProgressListener progressListener){

		options = b.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(1500))
				.build();

		if(iv == null || b == null){
			//throw new RuntimeException("Illegal Url");
		}

		if(url == null || url.equals("")){
			//throw new RuntimeException("Url is null);
			return;
		}

		if(isNotLocalPathOrRemotePath(url)){
			//throw new RuntimeException("Url is either local path nor remote url, cannot load image：" + url);
			return;
		}

		if(isLocalPath(url)){
			url = Uri.fromFile(new File(url)).toString();
			url = URLDecoder.decode(url);  /// Uri.fromFile would encdoe the chinese charators in url, should be decoded back
		}
		ImageLoader.getInstance().displayImage(url, iv, options, listener, progressListener);
	}

	public void paintBigImage(String url, ImageLoadingListener listener){

		this.imageEmpty(BIG_EMPTY)
				.imageError(BIG_ERROR)
				.imageLoading(BIG_LOADING)
				.paint(url, listener, null);
	}

	public void paintMiddleImage(String url, ImageLoadingListener listener){

		this.imageEmpty(MIDDLE_EMPTY)
				.imageError(MIDDLE_ERROR)
				.imageLoading(MIDDLE_LOADING)
				.paint(url, listener,null);
	}

	public void paintSmallImage(String url, ImageLoadingListener listener){

		this.imageEmpty(SMALL_EMPTY)
				.imageError(SMALL_ERROR)
				.imageLoading(SMALL_LOADING)
				.paint(url, listener,null);
	}





	public static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				switch(orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
						break;
				}
			}
		}
		return degree;
	}

	/**
	 * rotate bitmap
	 *
	 * @param bm
	 * @param degree
	 * @return new bitmap which is rotate from bm
	 */
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		if(degree == 0 || degree == -1){
			return bm;
		}
		Bitmap returnBm = null;

		// generate the matrix
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}



	public static String getUri(String path){
		File f = new File(path);
		if(f.exists()){
			String s = Uri.fromFile(f).toString();
			try {
				s = URLDecoder.decode(s, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return s;
		}else{
			return null;
		}
	}

	public static boolean isLocalPath(String uri){
		if(uri != null && uri.startsWith("/")){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isLocalUri(String uri){
		if(uri != null && uri.startsWith("file")){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isHttpUrl(String uri){
		if(uri != null && uri.startsWith("http")){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isValidUri(String uri){
		return isHttpUrl(uri) || isLocalPath(uri) || isLocalUri(uri);
	}

	public static boolean isValidResourceId(int id){
		return id > 0;
	}

}
