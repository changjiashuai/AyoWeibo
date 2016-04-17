package org.ayo.app.tmpl.pagegroup.handler;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import genius.android.view.R;
import org.ayo.app.tmpl.pagegroup.CustomRadioGroup;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pagegroup.PageIndicatorInfo;


/**
 * TabHost懒加载模式，只有显示时，才加载
 */
public class TabHostLazyHandler implements SubPageHandler {

    private boolean[] pageIsAdded;
    private ISubPage[] pages;
    private ISubPage currentPage = null;  // 子页面，用来控制生命周期
    private int currentPosition = -1;

    private Context context;
    private FrameLayout main_root;
    private CustomRadioGroup main_footer;

    @Override
    public void handleSubPages(Context context, final FragmentManager fragmentManager, final FrameLayout main_root, CustomRadioGroup main_footer, PageIndicatorInfo[] indicatorInfos, final ISubPage[] pages) {

        this.context = context;
        this.main_footer = main_footer;
        this.main_root = main_root;
        this.pages = pages;
        int pageCount = indicatorInfos.length;

        //添加底部导航
        for(int i = 0; i < pageCount; i++){
            PageIndicatorInfo info = indicatorInfos[i];
            main_footer.addItem(info.resNormal, info.resPressed, info.title);
        }

        //初始化状态
        pageIsAdded = new boolean[pageCount];
        for(int i = 0; i < pageCount; i++){
            pageIsAdded[i] = false;
        }

        //添加所有SubPage，此处是lazy模式，所以此处不会添加fragment
//        for(int i = 0; i < pageCount; i++){
//            FrameLayout fl = pageContainers[i];
//            fragmentManager.beginTransaction().replace(fl.getId(), (Fragment) pages[i]).commit();
//        }

        //处理事件
        main_footer.setOnItemChangedListener(new CustomRadioGroup.OnItemChangedListener() {
            public void onItemChanged(int index) {

                //控制显示和生命周期，更新current状态
                if (index == currentPosition) {
                    return;
                } else {
                    //hide the old
                    if (currentPage != null) {
                        Fragment f = ((Fragment) currentPage);
                        //fragmentManager.beginTransaction().hide(f).commit();
                        currentPage.onPageVisibleChange(false);
                        fragmentManager.beginTransaction().hide(f).commit();
                        f.setMenuVisibility(false);
                        f.setUserVisibleHint(false);
                    }

                    //show the new
                    currentPosition = index;
                    currentPage = pages[index];

                    if (currentPage != null) {
                        Fragment f = ((Fragment) currentPage);

                        ////====
                        //f在这里可能还没add进去
                        boolean isTheFirstPage = false;
                        if (pageIsAdded[index]) {
                            fragmentManager.beginTransaction().show(f).commit();
                        } else {
                            isTheFirstPage = true;
                            fragmentManager.beginTransaction().add(main_root.getId(), f).commit();
                            pageIsAdded[index] = true;
                            fragmentManager.beginTransaction().show(f).commit();
                        }
                        ////---over

                        //fragmentManager.beginTransaction().show(f).commit();
                        //fragmentManager.beginTransaction().attach(f).commit();
                        currentPage.setIsTheFirstPage(isTheFirstPage);
                        currentPage.onPageVisibleChange(true);
                        f.setMenuVisibility(true);
                        f.setUserVisibleHint(true);
                    }
                }


            }
        });

        //设置默认显示的page
//        pages[1].setIsTheFirstPage(true);
//        setChecked(1);
    }

    @Override
    public ISubPage getCurrentSubPage() {
        return currentPage;
    }

    private FrameLayout generateFragmentContainer(Context context){
        FrameLayout fl = new FrameLayout(context);
        return fl;
    }

    @Override
    public void setCheck(int position, boolean animateScroll, boolean isFirstPage) {
        pages[position].setIsTheFirstPage(isFirstPage);
        main_footer.setCheckedIndex(position);
    }
}
