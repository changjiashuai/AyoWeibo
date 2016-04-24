package org.ayo.app.tmpl.pagegroup.handler;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.ayo.app.tmpl.pagegroup.CustomRadioGroup;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pagegroup.PageIndicatorInfo;
import java.util.ArrayList;

import genius.android.view.R;


/**
 * ViewPager模式
 *
 * setOffscreenPageLimit设置为页面个数，不合理
 *
 * 但是作为主框架，子页面初始化之后应该缓存，不应该dettach，所以ViewPager默认方式还不太合理：
 * ——ViewPager根据setOffscreenPageLimit的值来初始化指定个数的子页面，默认为1，即初始化当前显示的前后各一个子页面
 * ——但是离开当前页面，范围之外的子页面会被dettach
 *
 * 只能加载fragment，但不同时加载数据，根据标志位来判断
 * ——第一个显示的页面：在onCreateView之后，立刻加载数据（问题在于setUserVisibleHint被调用时，onCreateView没执行到）
 * ——其他页面：在onCreateView之后，不立刻加载数据
 *
 *
 */
public class ViewPagerHandler implements SubPageHandler {

    private ISubPage currentPage = null;  // 子页面，用来控制生命周期
    private int currentPosition = -1;

    private Context context;
    private FrameLayout main_root;
    private CustomRadioGroup main_footer;

    private ViewPager viewpager;
    private ISubPage[] pages;

    @Override
    public void handleSubPages(Context context, final FragmentManager fragmentManager, FrameLayout main_root, CustomRadioGroup main_footer, PageIndicatorInfo[] indicatorInfos, final ISubPage[] pages) {

        this.context = context;
        this.main_footer = main_footer;
        this.main_root = main_root;
        int pageCount = indicatorInfos.length;
        this.pages = pages;

        //添加底部导航
        for (int i = 0; i < pageCount; i++) {
            PageIndicatorInfo info = indicatorInfos[i];
            main_footer.addItem(info.resNormal, info.resPressed, info.title);
        }

        //inflate出个ViewPager，添加给main_root，设置监听
        viewpager = (ViewPager) View.inflate(context, R.layout.ayo_layout_page_group_viewpager, null);
        main_root.addView(viewpager);

        //设置ViewPager参数
        viewpager.setOffscreenPageLimit(pageCount);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for(int i = 0; i < pageCount; i++){
            Fragment f = (Fragment) pages[i];
            fragments.add(f);
        }
        MainPagerAdapter mAdapetr = new MainPagerAdapter(fragmentManager, fragments);
        viewpager.setAdapter(mAdapetr);

        //ViewPager监听indicator
        main_footer.setOnItemChangedListener(new CustomRadioGroup.OnItemChangedListener() {
            @Override
            public void onItemChanged(int index) {
                viewpager.setCurrentItem(index, false);
            }
        });

        //indicator监听viewpager
        final MainBodyPageChangeListener bodyChangeListener = new MainBodyPageChangeListener(
                main_footer);
        viewpager.addOnPageChangeListener(bodyChangeListener);

        //设置默认显示的page
//        pages[1].setIsTheFirstPage(true);
//        setChecked(1);
    }

    @Override
    public void setCheck(int position, boolean animateScroll, boolean isFirstPage) {
        pages[position].setIsTheFirstPage(isFirstPage);
        main_footer.setCheckedIndex(position);
    }

    class MainBodyPageChangeListener implements ViewPager.OnPageChangeListener {
        private CustomRadioGroup customRadioGroup;

        public MainBodyPageChangeListener(CustomRadioGroup c) {
            this.customRadioGroup = c;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (arg1 != 0.0f) {
                int right, left;
                if (arg0 == customRadioGroup.getCheckedIndex()) {
                    // 方向向右
                    left = customRadioGroup.getCheckedIndex();
                    right = customRadioGroup.getCheckedIndex() + 1;
                } else {
                    // 方向向左
                    left = customRadioGroup.getCheckedIndex() - 1;
                    right = customRadioGroup.getCheckedIndex();

                }
                customRadioGroup.itemChangeChecked(left, right, arg1);
            } else {
                customRadioGroup.setCheckedIndex(arg0);
            }
        }

        public void onPageSelected(int arg0) {
            currentPage = pages[arg0];
        }

    }

    class MainPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments;
        private FragmentManager fm;

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        public MainPagerAdapter(FragmentManager fm,
                                ArrayList<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
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

    }

    @Override
    public ISubPage getCurrentSubPage() {
        return currentPage;
    }

    @Override
    public int getCurrentItem() {
        return currentPosition;
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener op){
        viewpager.addOnPageChangeListener(op);
    }
}
