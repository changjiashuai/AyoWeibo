package com.ayoview.sample.ultra.pulltorefresh.ui.classic;


import com.cowthan.sample.R;

import org.ayo.view.pullrefresh.PtrClassicFrameLayout;

public class ReleaseToRefresh extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(PtrClassicFrameLayout ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_release_to_refresh);
        ptrFrame.setPullToRefresh(false);
    }
}
