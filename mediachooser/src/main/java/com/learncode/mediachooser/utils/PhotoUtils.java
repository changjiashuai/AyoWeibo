package com.learncode.mediachooser.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.ExifInterface;

/**
 * 照片读取，解析，显示，下载，上传
 * @author seven
 *
 */
public class PhotoUtils {
	
	public static boolean hasGPSInfo(String path){
		
		try {
			ExifInterface exf = new ExifInterface(path);
			float[] latlnt = new float[2];
			exf.getLatLong(latlnt);
			if(latlnt[0] <= 0 || latlnt[1] <= 0){
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
