package org.ayo.view.recycler;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/4/17.
 */
public class GridLayoutManager extends android.support.v7.widget.GridLayoutManager {
    public GridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
}
