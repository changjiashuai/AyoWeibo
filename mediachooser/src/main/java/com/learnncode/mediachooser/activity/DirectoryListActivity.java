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


package com.learnncode.mediachooser.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learnncode.mediachooser.BucketEntry;
import com.learnncode.mediachooser.MediaChooser;
import com.learnncode.mediachooser.MediaChooserConstants;
import com.learnncode.mediachooser.R;
import com.learnncode.mediachooser.fragment.BucketImageFragment;

public class DirectoryListActivity extends FragmentActivity {

	///------------------------
	public static ArrayList<String> getSelectedPaths(Intent intent){
		if(intent != null && intent.getExtras() != null){
			return intent.getStringArrayListExtra("list");
		}else{
			return new ArrayList<String>();
		}
	}
	
	///-------------------------
	private static final String[] PROJECTION_BUCKET = {
		ImageColumns.BUCKET_ID,
		ImageColumns.BUCKET_DISPLAY_NAME,
		ImageColumns.DATA};
	private ArrayList<String> mSelectedImage = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_directory_chooser);
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
		if(getIntent() != null && getIntent().hasExtra("name")){
			///传入了一个默认的目录，直接打开
			String defaultPath = getIntent().getStringExtra("name");
			int type = getIntent().getIntExtra("type",0);
			TextView tv_title = (TextView) findViewById(R.id.tv_title);
			TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);

			if (type==1)
			{
				tv_title.setTextColor(0xff018442);
				tv_cancel.setTextColor(0xff018442);
			}

			
			 Cursor mCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION_BUCKET, null, null, orderBy + " DESC");
			try {
				while (mCursor.moveToNext()) {
					String name = mCursor.getString(1);
					if(name.equals("Camera")){
						Intent selectImageIntent = new Intent(this,HomeFragmentActivity.class);
						selectImageIntent.putExtra("name", "Camera");
						selectImageIntent.putExtra("image", true);
						selectImageIntent.putExtra("isFromBucket", true);
						selectImageIntent.putExtra("type",type);
						this.startActivityForResult(selectImageIntent, MediaChooser.REQ_PICK_IMAGE);
						break;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
//			File f = new File(defaultPath);
//			if(f.exists() && f.isDirectory()){
//				
//			}
		}
		
		this.getSupportFragmentManager().beginTransaction().replace(R.id.root, new BucketImageFragment()).commit();
		
		this.findViewById(R.id.left_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		this.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MediaChooser.REQ_PICK_IMAGE){
			if(resultCode == MediaChooser.RESULT_CLOSE_ALL){
				setResult(resultCode, null);
				finish();
				return;
			}else if(resultCode == MediaChooser.RESULT_OK){
				
			}
			if(data == null || data.getStringArrayListExtra("list") == null || data.getStringArrayListExtra("list").size() == 0){
				System.out.println("------------目录界面，得到null------------------");
			}else{
				////返回选好的图片们
				System.out.println("-----------到了目录界面，有数据-------");
				setResult(resultCode, data);
				finish();
			}
		}
	}
	
//	private void addMedia(ArrayList<String> list, ArrayList<String> input ) {
//		for (String string : input) {
//			list.add(string);
//		}
//	}
}
