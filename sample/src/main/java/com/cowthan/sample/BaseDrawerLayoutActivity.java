package com.cowthan.sample;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.widget.FrameLayout;


/**
 */
public abstract  class BaseDrawerLayoutActivity extends BaseActivity implements DrawerListener{

    DrawerLayoutManager drawerLayoutManager;

    /**
     * 获取content和drawer的layout
     * 第一个元素是content
     * 第二个元素是drawer
     * @return
     */
    protected abstract int[] getLayoutId();

    /**
     * 初始化内容页
     * @param viewContent
     */
    protected abstract void initContentView(View viewContent);

    /**
     * 初始化drawer页
     * @param viewDrawer
     */
    protected abstract void initDrawerView(View viewDrawer);

    protected DrawerLayoutManager getDrawerLayoutManager(){
        return drawerLayoutManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tmpl_drawerlayout);

        setSwipeBackEnable(false);

        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        FrameLayout content_frame = findViewById(R.id.content_frame);
        FrameLayout left_drawer = findViewById(R.id.left_drawer);

        View viewContent = View.inflate(getActivity(), getLayoutId()[0], null);
        View viewDrawer = View.inflate(getActivity(),getLayoutId()[1], null);

        content_frame.addView(viewContent);
        left_drawer.addView(viewDrawer);

        initContentView(viewContent);
        initDrawerView(viewDrawer);

        ///drawer控制
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerLayoutManager = DrawerLayoutManager.attachLeft(mDrawerLayout, this);
    }




}
