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

package com.learnncode.mediachooser.fragment;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.learncode.mediachooser.utils.PhotoUtils;
import com.learnncode.mediachooser.MediaChooser;
import com.learnncode.mediachooser.MediaChooserConstants;
import com.learnncode.mediachooser.MediaModel;
import com.learnncode.mediachooser.R;
import com.learnncode.mediachooser.activity.BucketHomeFragmentActivity;
import com.learnncode.mediachooser.activity.HomeFragmentActivity;
import com.learnncode.mediachooser.activity.PhotoPreviewActivity;
import com.learnncode.mediachooser.adapter.GridViewAdapter;

public class ImageFragment extends Fragment
{
	private ArrayList<String> mSelectedItems = new ArrayList<String>();
	private ArrayList<MediaModel> mGalleryModelList;
	private GridView mImageGridView;
	private View mView;
	private OnImageSelectedListener mCallback;
	private GridViewAdapter mImageAdapter;
	private Cursor mImageCursor;
	private TextView txtPreview, txtOk;

	// Container Activity must implement this interface
	public interface OnImageSelectedListener
	{
		public void onImageSelected(int count);
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnImageSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnImageSelectedListener");
		}
	}

	public ImageFragment()
	{
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		if (mView == null) {
			mView = inflater.inflate(R.layout.view_grid_layout_media_chooser, container, false);


			TextView txtCount = (TextView)mView.findViewById(R.id.txtCount);
			txtPreview = (TextView) mView.findViewById(R.id.txtPreview);
			txtOk = (TextView) mView.findViewById(R.id.txtOk);
			txtPreview.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					Intent selectImageIntent = new Intent(getActivity(), PhotoPreviewActivity.class);
					selectImageIntent.putStringArrayListExtra("key_img_list", mSelectedItems);
					getActivity().startActivity(selectImageIntent);

				}
			});
			txtOk.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					if (mSelectedItems.size() == 0) {
						Toast.makeText(getActivity(), getString(R.string.plaese_select_file), Toast.LENGTH_SHORT).show();

					} else {
						if (mSelectedItems.size() > 0) {
							Intent imageIntent = new Intent();
							imageIntent.setAction(MediaChooser.IMAGE_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
							imageIntent.putStringArrayListExtra("list", mSelectedItems);
							getActivity().sendBroadcast(imageIntent);
						}
						getActivity().finish();
					}

				}
			});

			mImageGridView = (GridView) mView.findViewById(R.id.gridViewFromMediaChooser);
			mImageGridView.setNumColumns(4);



			if (getArguments() != null) {
				initPhoneImages(getArguments().getString("name"));
				if (getArguments().getString("type").equals("1"))
				{
					txtCount.setBackgroundResource(R.drawable.tv_circle_bg_zhigu);
				}
			} else {
				initPhoneImages();
			}

		} else {
			((ViewGroup) mView.getParent()).removeView(mView);
			if (mImageAdapter == null || mImageAdapter.getCount() == 0) {
				Toast.makeText(getActivity(), getActivity().getString(R.string.no_media_file_available), Toast.LENGTH_SHORT).show();
			}
		}

		return mView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (MediaChooser.isNeedOnlyGPS()) {
			Toast.makeText(getActivity(), "没有GPS信息的图片已被过滤！", Toast.LENGTH_LONG).show();
		}
	}

	private void initPhoneImages(String bucketName)
	{
		try {
			final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
			String searchParams = null;
			String bucket = bucketName;
			searchParams = "bucket_display_name = \"" + bucket + "\"";

			final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
			mImageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, searchParams, null, orderBy + " DESC");

			setAdapter(mImageCursor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPhoneImages()
	{
		try {
			final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
			final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
			mImageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");

			setAdapter(mImageCursor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setAdapter(Cursor imagecursor)
	{

		if (imagecursor.getCount() > 0) {

			mGalleryModelList = new ArrayList<MediaModel>();

			for (int i = 0; i < imagecursor.getCount(); i++) {
				imagecursor.moveToPosition(i);
				int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
				MediaModel galleryModel = new MediaModel(imagecursor.getString(dataColumnIndex).toString(), false);

				if (MediaChooser.isNeedOnlyGPS() && PhotoUtils.hasGPSInfo(galleryModel.url)) {
					mGalleryModelList.add(galleryModel);
				} else if (!MediaChooser.isNeedOnlyGPS()) {
					mGalleryModelList.add(galleryModel);
				}
			}

			mImageAdapter = new GridViewAdapter(getActivity(), 0, mGalleryModelList, false,getArguments().getString("type"));
			mImageGridView.setAdapter(mImageAdapter);
		} else {
			Toast.makeText(getActivity(), getActivity().getString(R.string.no_media_file_available), Toast.LENGTH_SHORT).show();
		}

		mImageGridView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{

				GridViewAdapter adapter = (GridViewAdapter) parent.getAdapter();
				MediaModel galleryModel = (MediaModel) adapter.getItem(position);
				File file = new File(galleryModel.url);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "image/*");
				startActivity(intent);
				return true;
			}
		});

		mImageGridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
				// update the mStatus of each category in the adapter
				GridViewAdapter adapter = (GridViewAdapter) parent.getAdapter();
				MediaModel galleryModel = (MediaModel) adapter.getItem(position);

//				if (!galleryModel.status) {
//					long size = MediaChooserConstants.ChekcMediaFileSize(new File(galleryModel.url.toString()), false);
//					if (size != 0) {
//						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.file_size_exeeded) + "  " + MediaChooserConstants.SELECTED_IMAGE_SIZE_IN_MB + " " + getActivity().getResources().getString(R.string.mb), Toast.LENGTH_SHORT).show();
//						return;
//					}
//
//					if ((MediaChooserConstants.MAX_MEDIA_LIMIT == MediaChooserConstants.SELECTED_MEDIA_COUNT)) {
//						Toast.makeText(getActivity(), "最多选择" + MediaChooserConstants.MAX_MEDIA_LIMIT+"张, 当前" + MediaChooserConstants.SELECTED_MEDIA_COUNT+"张", Toast.LENGTH_SHORT).show();
//						if (MediaChooserConstants.SELECTED_MEDIA_COUNT < 2) {
//							//Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
//							return;
//						} else {
//							//Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
//							return;
//						}
//
//					}
//				}
				if(galleryModel.status){
					///本来是选中
					
				}else{
					if(mSelectedItems.size() >= MediaChooserConstants.MAX_MEDIA_LIMIT){
						Toast.makeText(getActivity(), "最多只能选择" + MediaChooserConstants.MAX_MEDIA_LIMIT + "张图", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				// inverse the status
				galleryModel.status = !galleryModel.status;
				adapter.notifyDataSetChanged();

				if (galleryModel.status) {
					mSelectedItems.add(galleryModel.url.toString());
					MediaChooserConstants.SELECTED_MEDIA_COUNT++;

				} else {
					mSelectedItems.remove(galleryModel.url.toString().trim());
					MediaChooserConstants.SELECTED_MEDIA_COUNT--;
				}

				if (mCallback != null) {
					onImageSelectedThis(mSelectedItems.size());
					Intent intent = new Intent();
					intent.putStringArrayListExtra("list", mSelectedItems);
					getActivity().setResult(Activity.RESULT_OK, intent);
				}

			}
		});
	}

	public void onImageSelectedThis(int count)
	{

		if (count != 0) {
			((TextView) mView.findViewById(R.id.txtCount)).setText(count + "");
		} else {
			((TextView) mView.findViewById(R.id.txtCount)).setText("");
		}

	}

	public ArrayList<String> getSelectedImageList()
	{
		return mSelectedItems;
	}

	public void addItem(String item)
	{
		if (mImageAdapter != null) {
			MediaModel model = new MediaModel(item, false);
			mGalleryModelList.add(0, model);
			mImageAdapter.notifyDataSetChanged();
		} else {
			initPhoneImages();
		}
	}
}
