package org.ayo.app.tmpl.pagegroup.handler;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import org.ayo.app.tmpl.pagegroup.CustomRadioGroup;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pagegroup.PageIndicatorInfo;


/**
 * TabHost全部加载模式
 *
 * fragment的commit不会直接调用onCreateView，导致onPageVisibilityChanged里的控件都没初始化，回去参考 viewPager吧
 *
 * setUserVisibleHint是为了让subpage兼容viewpager，可以作为一个回调使用
 *
 * fragmentTransaction.hide和show控制subpage的显示和隐藏
 *
 */
public class TabHostHungryHandler implements SubPageHandler {

    private ISubPage currentPage = null;  // 子页面，用来控制生命周期
    private ISubPage[] pages;
    private int currentPosition = -1;

    private Context context;
    private FrameLayout main_root;
    private CustomRadioGroup main_footer;

    @Override
    public void handleSubPages(Context context, final FragmentManager fragmentManager, FrameLayout main_root, CustomRadioGroup main_footer, PageIndicatorInfo[] indicatorInfos, final ISubPage[] pages) {

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

        //初始化subpage的container，在这里就是Fragment的Container
        for(int i = 0; i < pageCount; i++){
            //FrameLayout fragmentContainer = generateFragmentContainer(context, i);
            //main_root.addView(fragmentContainer);
            //Log.i("pagegroup", fragmentContainer.getId() + "");
            //pageContainers[i] = fragmentContainer;
        }

        //添加所有SubPage，此处是hungry模式，所以提前都加载上
        FragmentTransaction mCurTransaction = fragmentManager.beginTransaction();
        for(int i = 0; i < pageCount; i++){
            FrameLayout fl = main_root;
            //fragmentManager.beginTransaction().replace(fl.getId(), (Fragment) pages[i]).commit();
            mCurTransaction.add(fl.getId(), (Fragment) pages[i]);
        }

        //mCurTransaction.commit();


        for(int i = 0; i < pageCount; i++){
            Fragment f = ((Fragment) pages[i]);
            f.setMenuVisibility(false);
            f.setUserVisibleHint(false);

            mCurTransaction.hide(f);
        }

        mCurTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();

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
                        //fragmentManager.beginTransaction().show(f).commit();
                        //fragmentManager.beginTransaction().attach(f).commit();
                        fragmentManager.beginTransaction().show(f).commit();
                        currentPage.onPageVisibleChange(true);
                        f.setMenuVisibility(true);
                        f.setUserVisibleHint(true);
                    }
                }
                if(onPageChangeListener != null){
                    onPageChangeListener.onPageSelected(index);
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

    @Override
    public void setCheck(int position, boolean animateScroll, boolean isFirstPage) {
        pages[position].setIsTheFirstPage(isFirstPage);
        main_footer.setCheckedIndex(position);
    }

    @Override
    public int getCurrentItem() {
        return currentPosition;
    }

    private FrameLayout generateFragmentContainer(Context context, int i){
        FrameLayout fl = new FrameLayout(context);
        fl.setId((int) (System.currentTimeMillis()%1000 - i));
        return fl;
    }

    ViewPager.OnPageChangeListener onPageChangeListener;

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener op){
        this.onPageChangeListener = op;
    }
}
