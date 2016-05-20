package com.ayoview.sample.ultra.pulltorefresh.ui.storehouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ayoview.sample.ultra.pulltorefresh.LocalDisplay;
import com.ayoview.sample.ultra.pulltorefresh.TitleBaseFragment;
import com.cowthan.sample.R;

import org.ayo.imageloader.VanGogh;
import org.ayo.view.pullrefresh.PtrFrameLayout;
import org.ayo.view.pullrefresh.PtrHandler;
import org.ayo.view.pullrefresh.PtrUIHandler;
import org.ayo.view.pullrefresh.header.StoreHouseHeader;
import org.ayo.view.pullrefresh.indicator.PtrIndicator;

public class StoreHouseUsingString extends TitleBaseFragment {

    final String[] mStringList = {"Alibaba", "TMALL 11-11"};
    private String mTitlePre;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_storehouse_header, null);
        mTitlePre = getString(R.string.ptr_demo_storehouse_header_using_string_in_title);

        ImageView imageView = (ImageView) view.findViewById(R.id.store_house_ptr_image);
        String pic = "http://img5.duitang.com/uploads/item/201406/28/20140628122218_fLQyP.thumb.jpeg";
        VanGogh.paper(imageView).paintBigImage(pic, null, null);

        final PtrFrameLayout frame = (PtrFrameLayout) view.findViewById(R.id.store_house_ptr_frame);

        // header
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, LocalDisplay.dp2px(15), 0, 0);

        /**
         * using a string, support: A-Z 0-9 - .
         * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
         */
        header.initWithString(mStringList[0]);
        setHeaderTitle(mTitlePre + mStringList[0]);

        // for changing string
        frame.addPtrUIHandler(new PtrUIHandler() {

            private int mLoadTime = 0;

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                header.initWithString(string);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                String string = mStringList[mLoadTime % mStringList.length];
                setHeaderTitle(mTitlePre + string);
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });

        frame.setDurationToCloseHeader(3000);
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.autoRefresh(false);
            }
        }, 100);

        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 2000);
            }
        });
        return view;
    }
}
