package org.ayo.app.tmpl.pagegroup.handler;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import org.ayo.app.tmpl.pagegroup.CustomRadioGroup;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pagegroup.PageIndicatorInfo;
import genius.android.view.R;

/**
 * 把子页面添加到main_root，并和main_footer绑定，处理所有事件
 *
 * 特别注意：
 * 1 要处理生命周期
 *
 * Created by Administrator on 2016/4/5.
 */
public interface SubPageHandler {

    /**
     * 加载页面，添加监听，处理子页面和导航的关系
     * @param context
     * @param fragmentManager
     * @param main_root
     * @param main_footer
     * @param indicatorInfos
     * @param pages
     */
    void handleSubPages(Context context, FragmentManager fragmentManager, FrameLayout main_root, CustomRadioGroup main_footer, PageIndicatorInfo[] indicatorInfos, ISubPage[] pages);

    /**
     * 获取当前正在显示的页面
     * @return
     */
    ISubPage getCurrentSubPage();

    /**
     * 选中
     * @param postion
     */
    void setCheck(int postion, boolean animateScroll, boolean isFirstPage);
}

