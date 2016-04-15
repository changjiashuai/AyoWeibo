package org.ayo.app.common;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;

import genius.android.view.R;


/**
 *  一个Activity装载一个Fragment
 *  Fragment管界面
 *
 */
public abstract class FragmentContainerActivity extends AyoActivity {

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
    protected abstract void  initFragment(FragmentManager fragmentManager, View root);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayo_tmpl_ac_fragment_container);

        FrameLayout fl_root = (FrameLayout) findViewById(R.id.fl_root);
        initFragment(getSupportFragmentManager(), fl_root);

    }
}
