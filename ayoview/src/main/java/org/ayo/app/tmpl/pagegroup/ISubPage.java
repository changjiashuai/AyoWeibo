package org.ayo.app.tmpl.pagegroup;

/**
 * 子页面接口
 * Created by Administrator on 2016/4/5.
 */
public interface ISubPage {
    void onPageVisibleChange(boolean isVisible);

    /**
     * 加载即显示
     *
     * 如果参数是true，则fragment被add上之后，立刻加载数据，
     * 注意，fragment刚被add之后，setUserVisibleHint在onCreateView之前调用
     * 所以仅在刚初始化完时，不能依赖于setUserVisibleHint里的逻辑
     *
     * 如果fragment需要被add进去，但不立刻加载数据（省资源，如模式ViewPager和TabHostHungry），则此处可以传入false
     *
     * @param isTheFirstPage
     */
    void setIsTheFirstPage(boolean isTheFirstPage);
}
