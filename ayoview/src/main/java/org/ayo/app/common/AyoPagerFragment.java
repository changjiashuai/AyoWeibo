package org.ayo.app.common;

import android.view.View;

import org.ayo.app.tmpl.pager.FragmentPager;

import genius.android.view.R;

/**
 * ViewPager模板
 */
public abstract class AyoPagerFragment extends AyoFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.ayo_tmpl_frag_viewpager;
    }

    @Override
    protected void onCreateView(View root) {
        FragmentPager pager = id(R.id.pager);
        attach(pager);
    }

    protected abstract void attach(FragmentPager pager);
}
