package org.ayo.app.tmpl.pagegroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.ayo.app.tmpl.pagegroup.handler.SubPageHandler;
import org.ayo.app.tmpl.pagegroup.handler.TabHostHungryHandler;
import org.ayo.app.tmpl.pagegroup.handler.TabHostLazyHandler;
import org.ayo.app.tmpl.pagegroup.handler.ViewPagerHandler;

import genius.android.view.R;


/**
 * 主界面，不依赖于Activity或者Fragment，可以直接放到一个activity或fragment中，管理多个ISubPage子页面
 *
 * 但必须提供一个接口，接收一个FragmentManager
 *
 * 对外公开：
 * 1 界面变化
 * 2 界面滑动
 *
 * ISubPage的实现类要注意：
 * 1 加载数据的工作，请放在onShow里做，不要放在onCreateView中，因为本框架不依赖于onCreateView等系统级的生命周期
 * 2 在加载数据过程中，最好有个进度提示
 * 3 能缓存的就缓存，不能缓存的就算了
 * 4 TabHost模式，其实是把Fragment都添加进来了，都调用了onCreateView
 *   TabHost_2模式，其实是一次最多加载两个Fragment，直到都加载完，具体个数可以配置
 *
 *
 *  关于生命周期
 *  ISubPage的onPageVisibleChanged会跟随主Activity的onResume和onPause被调用，但fragment的setUserVisibleHint不会
 *  ——所以onPageVisibleChanged是系统级生命周期，适合数据统计，在框架层面的子页面切换，和系统级的主Activity生命周期，都会被调起
 *  ——setUserVisibleHint是框架级的生命周期，适合加载数据刷新UI，只有在框架层面的子页面切换时，才会调起
 *
 *  4.6上午10点  问题：
 *  在主Activity里调起了当前subpage的onPageVisibleChanged，但是对于首页来说，调用了两次，参数都是true
 *
 *  第一次在主Activity初始化PageGroup时调，第二次在onResume里调
 *  ——主要原因是框架层面和系统级别的生命周期有重复，就在首页第一次显示时
 *  ——在PageGroupView的onResume里处理第一次进来的逻辑
 *
 *
 *  关于加载数据
 *  ——应该在但fragment的setUserVisibleHint中加载，而不是onPageVisibleChanged
 *  ——大多数情况下，在setUserVisibleHint里也不应该每次都加载数据，还是得有个状态记录，如果已经加载过一次数据，更好的做法应该是提供个load或者refresh接口，由用户决定是否需要重新加载
 *  ——页面没数据时，加载数据叫加载
 *      ——从无到有，如果是加载本地缓存（文件或者数据库），则是否需要进度框还需要再议
 *      ——从无到有，如果是网络请求，肯定要进度框
 *  ——页面有数据时，叫刷新
 *  所以进度框的显示方式，不依赖于第几次加载数据，而依赖于当前UI是否有数据填充
 *
 *  关于状态存储
 *  PageGroupView里记录了太多当前子页面的状态，在onSaveInstance里得存起来，要不有麻烦
 *
 *
 * 剩下的问题：
 * 1 SampleFragment只是一个不完美的示例，本框架只提供生命周期，至于怎么在各个子页面里配合这些生命周期加载数据，本框架不做干预
 * 2 无论怎么加载数据，本框架的宗旨需要时刻记住，单纯是为了不一次性加载完所有fragment，但是取了个折中，可以预先加载所有fragment，但不要预先加载所有数据
 * 3 activity状态存储
 * 4 sample.src这个包下是ViewPager和两个Adapter的源码，留作学习用，暂时不删
 * 5 DataProxy是个不成功的尝试，因为很难考虑所有情况，所以放弃，这个应该不是本框架需要考虑的问题了，需要各位定义自己的UI模板和Presenter，
 *  可以参考本人的ListView模板，主要就是解决SampleFrament的数据逻辑的封装，和页面加载状态的问题
 * 6 是否首页的问题可以通过在setUserVisibleHint里做一个延时处理来解决，但延时多少呢，200毫秒能保证大多数情况下不出错，但保障力度不太够
 * 7 indicator的封装和可配置
 */
public class PageGroupView extends FrameLayout implements IPageGroup {


    public static final int MODE_TABHOST_HUNGRY = 1;
    public static final int MODE_TABHOST_LAZY = 2;
    public static final int MODE_VIEWPAGER = 3;

    private boolean isFirstCome = true;


    public PageGroupView(Context context) {
        super(context);
    }

    public PageGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PageGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PageGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private PageIndicatorInfo[] indicatorInfos;
    private ISubPage[] pages;
    private FragmentManager fragmentManager;
    private int mode = MODE_TABHOST_HUNGRY;

    private FrameLayout main_root;
    private CustomRadioGroup main_footer;

    SubPageHandler subPageHandler = null;

    public void attach(AppCompatActivity activity, PageIndicatorInfo[] indicatorInfos, ISubPage[] pages, int uiMode, int firstPage){
        this.fragmentManager = activity.getSupportFragmentManager();
        init(indicatorInfos, pages, uiMode, firstPage);
    }

    public void attach(Fragment fragment, PageIndicatorInfo[] indicatorInfos, ISubPage[] pages, int uiMode, int firstPage){
        this.fragmentManager = fragment.getChildFragmentManager();
        init(indicatorInfos, pages, uiMode, firstPage);
    }

    public void attach(FragmentActivity activity, PageIndicatorInfo[] indicatorInfos, ISubPage[] pages, int uiMode, int firstPage){
        this.fragmentManager = activity.getSupportFragmentManager();
        init(indicatorInfos, pages, uiMode, firstPage);
    }

    private void init(PageIndicatorInfo[] indicatorInfos, ISubPage[] pages, int uiMode, int firstPage){
        this.indicatorInfos = indicatorInfos;
        this.pages = pages;
        this.mode = uiMode;

        View view = View.inflate(getContext(), R.layout.ayo_layout_page_group_view, null);
        this.addView(view);

        main_root = (FrameLayout) findViewById(R.id.main_root);
        main_footer = (CustomRadioGroup) findViewById(R.id.main_footer);

        //初始化所有组件，并显示第一个界面

        if(uiMode == MODE_TABHOST_HUNGRY){
            subPageHandler = new TabHostHungryHandler();
        }else if(uiMode == MODE_TABHOST_LAZY){
            subPageHandler = new TabHostLazyHandler();
        }else if(uiMode == MODE_VIEWPAGER){
            subPageHandler = new ViewPagerHandler();
        }else{
            throw new RuntimeException("无效的mode");
        }

        subPageHandler.handleSubPages(getContext(), fragmentManager, main_root, main_footer, indicatorInfos, pages);
        subPageHandler.setCheck(firstPage, false, true);
    }

    public void onResume() {
        if(isFirstCome){
            //第一次进来，当前页面就是主页，onPageVisibleChange在这里会被重复调用
            isFirstCome = false;
        }else{
            if(subPageHandler.getCurrentSubPage() != null){
                subPageHandler.getCurrentSubPage().onPageVisibleChange(true);
            }
        }

    }

    public void onPause() {
        if(subPageHandler.getCurrentSubPage() != null){
            subPageHandler.getCurrentSubPage().onPageVisibleChange(false);
        }
    }

    public void setChecked(int position){
        subPageHandler.setCheck(position, false, false);
    }


    public void setMessageNotify(int postion, int num){
        main_footer.setItemNewsCount(postion, num);
    }
}
