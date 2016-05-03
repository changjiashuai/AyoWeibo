package org.ayo.weibo.ui.fragment.main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.ayo.app.common.AyoFragment;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pager.FragmentPager;
import org.ayo.http.R;
import org.ayo.weibo.ui.fragment.top.TopITFragment;
import org.ayo.weibo.ui.fragment.top.TopNewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/1.
 */
public class TopPagerFragment extends AyoFragment implements ISubPage{
    @Override
    protected int getLayoutId() {
        return R.layout.wb_frag_viewpager;
    }

    @Override
    protected void onCreateView(View root) {

        List<Fragment> fragments = new ArrayList<>();
        TopITFragment topITFragment = new TopITFragment();
        topITFragment.setIsTheFirstPage(true);

        TopNewsFragment topNewsFragment = new TopNewsFragment();
        fragments.add(topITFragment);
        fragments.add(topNewsFragment);

        String[] titles = {
                "技术",
                "时事"
        };

        FragmentPager view_pager = findViewById(R.id.view_pager);
        view_pager.attach(getChildFragmentManager(), fragments, titles, null, new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onPageVisibleChange(boolean isVisible) {

    }

    @Override
    public void setIsTheFirstPage(boolean isTheFirstPage) {

    }
}
