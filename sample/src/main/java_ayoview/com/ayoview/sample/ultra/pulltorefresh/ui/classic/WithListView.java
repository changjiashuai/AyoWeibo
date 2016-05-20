package com.ayoview.sample.ultra.pulltorefresh.ui.classic;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ayoview.sample.ultra.pulltorefresh.TitleBaseFragment;
import com.ayoview.sample.ultra.pulltorefresh.data.Item;
import com.ayoview.sample.ultra.pulltorefresh.data.JsonData;
import com.ayoview.sample.ultra.pulltorefresh.ui.MaterialStyleFragment;
import com.cowthan.sample.R;

import org.ayo.app.SBSimpleAdapter;
import org.ayo.imageloader.VanGogh;
import org.ayo.view.pullrefresh.PtrClassicFrameLayout;
import org.ayo.view.pullrefresh.PtrDefaultHandler;
import org.ayo.view.pullrefresh.PtrFrameLayout;
import org.ayo.view.pullrefresh.PtrHandler;

public class WithListView extends TitleBaseFragment {

    private PtrClassicFrameLayout mPtrFrame;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHeaderTitle(R.string.ptr_demo_block_list_view);


        final View contentView = inflater.inflate(R.layout.fragment_classic_header_with_list_view, null);
        final ListView listView = (ListView) contentView.findViewById(R.id.rotate_header_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    final String url = mAdapter.getItem(position).optString("pic");
                    if (!TextUtils.isEmpty(url)) {
                        getContext().pushFragmentToBackStack(MaterialStyleFragment.class, url);
                    }
                }
            }
        });

        mAdapter = new SBSimpleAdapter<Item>(getContext(), JsonData.getList()){

            @Override
            protected int getLayoutId() {
                return R.layout.ultra_list_view_item;
            }

            @Override
            public boolean isConvertViewUseable(View convertView, int position) {
                return true;
            }

            @Override
            public void fillHolder(ViewHolder holder, View convertView, Item bean, int position) {
                ImageView mImageView = (ImageView) holder.findViewById(R.id.list_view_item_image_view);
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                VanGogh.paper(mImageView).paintSmallImage(bean.pic, null, null);
            }
        };
        listView.setAdapter(mAdapter);

        mPtrFrame = (PtrClassicFrameLayout) contentView.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
        return contentView;
    }

    SBSimpleAdapter<Item> mAdapter;


    protected void updateData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.refreshComplete();
                mAdapter.notifyDataSetChanged(JsonData.getList());
            }
        }, 3000);

    }
}