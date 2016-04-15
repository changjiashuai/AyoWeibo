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

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.learnncode.mediachooser.MediaChooser;
import com.learnncode.mediachooser.MediaChooserConstants;
import com.learnncode.mediachooser.R;
import com.learnncode.mediachooser.fragment.BucketImageFragment;
import com.learnncode.mediachooser.fragment.BucketImageFragmentForDirPicker;

public class DirectoryListActivityForDirPicker extends FragmentActivity {

	///------------------------
	public static ArrayList<String> getSelectedPaths(Intent intent){
		if(intent != null && intent.getExtras() != null){
			return intent.getStringArrayListExtra("list");
		}else{
			return new ArrayList<String>();
		}
	}
	
	///-------------------------
	
	private ArrayList<String> mSelectedImage = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_directory_chooser);
		this.getSupportFragmentManager().beginTransaction().replace(R.id.root, new BucketImageFragmentForDirPicker()).commit();
		
		this.findViewById(R.id.left_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}
	
}
