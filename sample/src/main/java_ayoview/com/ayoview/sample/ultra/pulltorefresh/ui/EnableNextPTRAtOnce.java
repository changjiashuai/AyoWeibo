package com.ayoview.sample.ultra.pulltorefresh.ui;


import com.ayoview.sample.ultra.pulltorefresh.ui.classic.WithTextViewInFrameLayoutFragment;
import com.cowthan.sample.R;

import org.ayo.view.pullrefresh.PtrClassicFrameLayout;

public class EnableNextPTRAtOnce extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(PtrClassicFrameLayout ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_enable_next_ptr_at_once);
        ptrFrame.setEnabledNextPtrAtOnce(true);
        ptrFrame.setPullToRefresh(false);
    }
}