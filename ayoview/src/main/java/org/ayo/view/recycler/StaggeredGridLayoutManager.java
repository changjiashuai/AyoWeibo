package org.ayo.view.recycler;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/4/17.
 */
public class StaggeredGridLayoutManager extends android.support.v7.widget.StaggeredGridLayoutManager {
    public StaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public StaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }
}
