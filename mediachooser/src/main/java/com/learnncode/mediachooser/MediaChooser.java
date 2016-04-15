/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.learnncode.mediachooser;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

import com.learnncode.mediachooser.activity.DirectoryListActivity;
import com.learnncode.mediachooser.activity.HomeFragmentActivity;


public class MediaChooser {

	public static final int RESULT_OK = 200;
	public static final int RESULT_CLOSE_ALL = 201;
	public static final int REQ_PICK_DIR = 50;
	public static final int REQ_PICK_IMAGE = 51;
	
	/**
	 * 打开图片选择：先进入目录
	 * 
	 * @param context
	 */
	public static void pickImageFromDirList(Activity context)
	{
		MediaChooser.setSelectionLimit(20);
		Intent intent = new Intent(context, DirectoryListActivity.class);
		context.startActivityForResult(intent, MediaChooser.REQ_PICK_IMAGE);
	}
	
	/**
	 * 打开图片选择：先进入指定目录
	 * @param context
	 * @param defaultPath
	 */
	public static void pickImageFromSpecifiedDir2(Activity context, String defaultPath){
		File f = new File(defaultPath);
		if(!f.exists()){
			throw new IllegalArgumentException("不存在的目录");
		}
		if(!f.isDirectory()){
			throw new IllegalArgumentException("指定路径不是个目录");
		}
		Intent selectImageIntent = new Intent(context,HomeFragmentActivity.class);
		selectImageIntent.putExtra("name", defaultPath);
		selectImageIntent.putExtra("image", true);
		selectImageIntent.putExtra("isFromBucket", false);
		context.startActivityForResult(selectImageIntent, MediaChooser.REQ_PICK_IMAGE);
	}
	
	public static void pickImageFromSpecifiedDir(Activity context, String defaultPath){
//		File f = new File(defaultPath);
//		if(!f.exists()){
//			throw new IllegalArgumentException("不存在的目录");
//		}
//		if(!f.isDirectory()){
//			throw new IllegalArgumentException("指定路径不是个目录");
//		}
		Intent intent = new Intent(context, DirectoryListActivity.class);
		intent.putExtra("name", defaultPath);
		context.startActivityForResult(intent, MediaChooser.REQ_PICK_IMAGE);
	}
	
	/**
	 * 不管是从目录页面，还是从图片页面返回，都使用这个解析
	 * @param intent
	 * @return
	 */
	public static ArrayList<String> getSelectedImages(Intent intent){
		if(intent != null && intent.getExtras() != null){
			return intent.getStringArrayListExtra("list");
		}else{
			return new ArrayList<String>();
		}
	}
	
	/**
	 * Video file selected broadcast action.
	 */
	public static final String VIDEO_SELECTED_ACTION_FROM_MEDIA_CHOOSER = "lNc_videoSelectedAction"; 

	/**
	 *  Image file selected broadcast action.
	 */
	public static final String IMAGE_SELECTED_ACTION_FROM_MEDIA_CHOOSER = "lNc_imageSelectedAction"; 

	/**
	 * Set folder Name  
	 * @param folderName
	 * 			 default folder name is learnNcode.
	 */
	public static void setFolderName(String folderName) {
		if(folderName != null){
			MediaChooserConstants.folderName = folderName;
		}
	}

	/**
	 * To set visibility of camera/video button.
	 * 
	 * @param showCameraVideo 
	 *           boolean value.To set visibility of camera/video button.
	 *           default its visible.
	 */
	public static void showCameraVideoView(boolean showCameraVideo) {
		MediaChooserConstants.showCameraVideo = showCameraVideo;
	}

	/**
	 * To set file size limit for image selection.
	 * 
	 * @param size
	 *  			int file size in mb.
	 *  			default is set to 20 mb.
	 */
	public static void setImageSize(int size) {
		MediaChooserConstants.SELECTED_IMAGE_SIZE_IN_MB = size;
	}

	/**
	 * To set file size limit for video selection.
	 * 
	 * @param size
	 *  			int file size in mb.
	 *  			default is set to 20 mb.
	 */
	public static void setVideoSize(int size) {
		MediaChooserConstants.SELECTED_VIDEO_SIZE_IN_MB = size;
	}

	/**
	 * To set number of items that can be selected. 
	 * 
	 * @param limit
	 *   			int value.
	 *   			Default is 100.
	 */
	public static void setSelectionLimit(int limit) {
		MediaChooserConstants.MAX_MEDIA_LIMIT = limit;
	}

	/**
	 * To set already selected file count.
	 * 
	 * @param count
	 * 				int value.
	 */
	public static void setSelectedMediaCount(int count) {
		MediaChooserConstants.SELECTED_MEDIA_COUNT = count;
	}

	/**
	 *  Get selected media file count.
	 *  
	 * @return
	 * 			count.
	 */
	public static int getSelectedMediaCount() {
		return MediaChooserConstants.SELECTED_MEDIA_COUNT ;
	}

	public static void setNeedOnlyGPS(boolean onlyGPS){
		MediaChooserConstants.NEED_ONLY_GPS = onlyGPS;
	}
	
	public static boolean isNeedOnlyGPS(){
		return MediaChooserConstants.NEED_ONLY_GPS;
	}
	
	/**
	 * To display images only.
	 */
	public static void showOnlyImageTab() {
		MediaChooserConstants.showImage = true;
		MediaChooserConstants.showVideo = false;
	}

	/**
	 * To display video and images.
	 */
	public static void showImageVideoTab() {
		MediaChooserConstants.showImage = true;
		MediaChooserConstants.showVideo = true;
	}

	/**
	 * To display videos only.
	 */
	public static void showOnlyVideoTab() {
		MediaChooserConstants.showImage = false;
		MediaChooserConstants.showVideo = true;
	}

}
