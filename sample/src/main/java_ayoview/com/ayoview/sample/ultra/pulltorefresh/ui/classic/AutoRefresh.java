package com.ayoview.sample.ultra.pulltorefresh.ui.classic;


import com.cowthan.sample.R;

import org.ayo.view.pullrefresh.PtrClassicFrameLayout;

public class AutoRefresh extends WithGridView {

    @Override
    protected void setupViews(final PtrClassicFrameLayout ptrFrame) {
        ptrFrame.setLoadingMinTime(3000);
        setHeaderTitle(R.string.ptr_demo_block_auto_fresh);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh(true);
            }
        }, 150);
    }
}