package org.ayo.app.tmpl.pager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.ayo.app.tmpl.indicator.IPagerIndicator;

import java.util.List;

import genius.android.view.R;

/**
 * 把常用ViewPager布局和Adapter封装起来，适合使用ViewPager组织fragment的情况，
 * 如引导页
 *
 * 应该算是个简单版的PageGroupView
 *
 * Created by Administrator on 2016/4/20.
 */
public class FragmentPager extends FrameLayout{
    public FragmentPager(Context context) {
        super(context);
        init();
    }

    public FragmentPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FragmentPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FragmentPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    ScrollerViewPager viewpager;

    private void init(){
        View v = View.inflate(getContext(), R.layout.ayo_layout_fragment_pager, null);
        this.addView(v);


        viewpager = (ScrollerViewPager) findViewById(R.id.view_pager);
    }

    public void attach(FragmentManager fm, List<Fragment> fragments, String[] titles, IPagerIndicator pagerIndicator, ViewPager.OnPageChangeListener onPageChangeListener){
        viewpager.setOffscreenPageLimit(fragments.size());
        viewpager.fixScrollSpeed();

        MainPagerAdapter mAdapetr = new MainPagerAdapter(fm, fragments, titles);
        viewpager.setAdapter(mAdapetr);

        final MainBodyPageChangeListener bodyChangeListener = new MainBodyPageChangeListener();
        viewpager.addOnPageChangeListener(bodyChangeListener);

        if(pagerIndicator != null){
            pagerIndicator.setViewPager(viewpager);
        }

        if(onPageChangeListener != null){
            viewpager.addOnPageChangeListener(onPageChangeListener);
        }

    }

    class MainBodyPageChangeListener implements ViewPager.OnPageChangeListener {

        public MainBodyPageChangeListener(){

        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            if (arg1 != 0.0f) {
//                int right, left;
//                if (arg0 == customRadioGroup.getCheckedIndex()) {
//                    // 方向向右
//                    left = customRadioGroup.getCheckedIndex();
//                    right = customRadioGroup.getCheckedIndex() + 1;
//                } else {
//                    // 方向向左
//                    left = customRadioGroup.getCheckedIndex() - 1;
//                    right = customRadioGroup.getCheckedIndex();
//
//                }
//                customRadioGroup.itemChangeChecked(left, right, arg1);
//            } else {
//                customRadioGroup.setCheckedIndex(arg0);
//            }
        }

        public void onPageSelected(int arg0) {
            //currentPage = pages[arg0];
        }

    }

    class MainPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;
        private FragmentManager fm;
        private String[] titles;

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        public MainPagerAdapter(FragmentManager fm,
                                List<Fragment> fragments,
                                String[] titles) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
