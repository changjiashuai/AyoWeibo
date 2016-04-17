package com.ayoview.sample.tmpl_recyclerview;

import android.support.v4.app.FragmentManager;
import android.view.View;

import org.ayo.app.common.FragmentContainerActivityAttacher;

/**
 * Created by Administrator on 2016/4/17.
 */
public class DemoRecyclerActivity extends FragmentContainerActivityAttacher {

    /**
     * 这里能做的事：
     * 1 new fragment
     * 2 给fragment设置参数
     * 3 getSupportFragmentManager().beginTransaction().replace(fl_root.getId(), frag).commit();
     *
     * 千万不要直接调用fragment刷新界面等，在这里并没有执行完onCreateView
     *
     * @param fragmentManager
     * @param root
     */
    @Override
    protected void initFragment(FragmentManager fragmentManager, View root) {
        DemoRecyclerFragment recyclerFragment = new DemoRecyclerFragment();
        getSupportFragmentManager().beginTransaction().replace(root.getId(), recyclerFragment).commit();
    }
}
