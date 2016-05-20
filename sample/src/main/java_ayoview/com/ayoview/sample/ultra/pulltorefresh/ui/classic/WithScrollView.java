package com.ayoview.sample.ultra.pulltorefresh.ui.classic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.ayoview.sample.ultra.pulltorefresh.TitleBaseFragment;
import com.cowthan.sample.R;

import org.ayo.view.pullrefresh.PtrClassicFrameLayout;
import org.ayo.view.pullrefresh.PtrDefaultHandler;
import org.ayo.view.pullrefresh.PtrFrameLayout;
import org.ayo.view.pullrefresh.PtrHandler;

public class WithScrollView extends TitleBaseFragment {

    private PtrClassicFrameLayout mPtrFrame;
    private ScrollView mScrollView;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHeaderTitle(R.string.ptr_demo_block_scroll_view);

        final View contentView = inflater.inflate(R.layout.fragment_classic_header_with_scroll_view, null);
        mScrollView = (ScrollView) contentView.findViewById(R.id.rotate_header_scroll_view);
        mPtrFrame = (PtrClassicFrameLayout) contentView.findViewById(R.id.rotate_header_web_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 100);
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
}