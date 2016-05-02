package org.ayo.weibo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.common.AyoActivityAttacher;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pagegroup.PageGroupView;
import org.ayo.app.tmpl.pagegroup.PageIndicatorInfo;
import org.ayo.http.R;
import org.ayo.lang.Lang;
import org.ayo.notify.Toaster;
import org.ayo.notify.actionsheet.ActionSheetDialog;
import org.ayo.notify.actionsheet.ActionSheetUtils;
import org.ayo.view.widget.TitleBar;
import org.ayo.weibo.Config;
import org.ayo.weibo.topic.Topic;
import org.ayo.weibo.topic.TopicManager;
import org.ayo.weibo.ui.fragment.main.ConversationListFragment;
import org.ayo.weibo.ui.fragment.main.ProfileFragment;
import org.ayo.weibo.ui.fragment.main.QaFragment;
import org.ayo.weibo.ui.fragment.main.TimelineListFragment;
import org.ayo.weibo.ui.fragment.main.TopPagerFragment;
import org.ayo.weibo.ui.prompt.Poper;
import org.ayo.weibo.ui.prompt.TitleBarUtils;

import java.util.List;

/**
 */
public class MainFrameActivity extends AyoActivityAttacher {

    public static void start(Context c){
        ActivityAttacher.startActivity(c, MainFrameActivity.class, null, false, ActivityAttacher.LAUNCH_MODE_SINGLE_TASK);
    }


    PageGroupView pgv_pagers;
    TitleBar titlebar;

    TimelineListFragment appFragment;
    //TimelineListFragment userFragment;
    TopPagerFragment topFragment;
    QaFragment qaFragment;
    ConversationListFragment conversationListFragment;
    ProfileFragment profileFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_ac_main_frame);
        titlebar = findViewById(R.id.titlebar);


        pgv_pagers = (PageGroupView) findViewById(R.id.pgv_pagers);

        PageIndicatorInfo[] indicatorInfos = new PageIndicatorInfo[]{
                new PageIndicatorInfo(R.mipmap.ic_tab_timeline_normal, R.mipmap.ic_tab_timeline_pressed, "动态"),
                new PageIndicatorInfo(R.mipmap.ic_tab_article_normal, R.mipmap.ic_tab_article_pressed, "资讯"),
                new PageIndicatorInfo(R.mipmap.ic_tab_chat_normal, R.mipmap.ic_tab_chat_pressed, "问答"),
                new PageIndicatorInfo(R.mipmap.ic_tab_shop_normal, R.mipmap.ic_tab_shop_pressed, "咨询"),
                new PageIndicatorInfo(R.mipmap.ic_tab_profle_normal, R.mipmap.ic_tab_profle_pressed, "我"),
        };


        appFragment = new TimelineListFragment();
        appFragment.setIsTheFirstPage(true);
        appFragment.setDataDir(Config.DIR.APP_DIR);

//        userFragment = new TimelineListFragment();
//        userFragment.setDataDir(Config.DIR.USER_DIR);

        topFragment = new TopPagerFragment();

        qaFragment = new QaFragment();

        conversationListFragment = new ConversationListFragment();

        profileFragment = new ProfileFragment();

        ISubPage[] pages = new ISubPage[]{
                appFragment,
                topFragment,
                qaFragment,
                conversationListFragment,
                profileFragment,

        };
        //int uiMode = PageGroupView.MODE_TABHOST_HUNGRY;
        //int uiMode = PageGroupView.MODE_TABHOST_LAZY;
        int uiMode = PageGroupView.MODE_VIEWPAGER;
        pgv_pagers.attach(this.getActivity(), indicatorInfos, pages, uiMode, 0);
        pgv_pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTitlebar(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pgv_pagers.setMessageNotify(2, 999);


        changeTitlebar(0);
    }

    private void changeTitlebar(int index){
        int currentItem = index;
        if(currentItem == 0){
            changeTitleBarTo0Or1(0, "动态", Config.DIR.APP_DIR);
        }else if(currentItem == 1){
            changeTitleBarTo0Or1(1, "资讯", Config.DIR.USER_DIR);
        }else if(currentItem == 2){
            changeTitleBarTo2();
        }else if(currentItem == 3){
            changeTitleBarTo3();
        }else if(currentItem == 4){
            changeTitleBarTo4();
        }
    }

    private void changeTitleBarTo0Or1(final int item, String title, final String dataDir){
        TitleBarUtils.setTitleBar(titlebar, title);
        titlebar.leftButton(0)
                .clearRightButtons()
                .rightButton(1, R.drawable.ic_add)
                .rightButton(2, R.drawable.ic_more)
                .callback(new TitleBar.Callback() {

                    @Override
                    public void onLeftButtonClicked(View v) {

                    }

                    @Override
                    public void onRightButtonClicked(int index, View v) {
                        if(index == 1){
                            Poper.showCreateSheet(getActivity());
                        }else if(index == 2){
                            pickTopic(item, dataDir);
                        }
                    }
                });
    }

    private void changeTitleBarTo2(){
        TitleBarUtils.setTitleBar(titlebar, "问答");
        titlebar.leftButton(0)
                .clearRightButtons()
                .rightButton(1, R.drawable.sel_download)
                .callback(new TitleBar.Callback() {

                    @Override
                    public void onLeftButtonClicked(View v) {

                    }

                    @Override
                    public void onRightButtonClicked(int index, View v) {
                        if(index == 1){
                            Toaster.toastShort("资讯搜索");
                        }
                    }
                });
    }

    private void changeTitleBarTo3(){
        TitleBarUtils.setTitleBar(titlebar, "咨询");
        titlebar.leftButton(0)
                .clearRightButtons()
                .rightButton(1, R.drawable.sel_download)
                .callback(new TitleBar.Callback() {

                    @Override
                    public void onLeftButtonClicked(View v) {

                    }

                    @Override
                    public void onRightButtonClicked(int index, View v) {
                        if(index == 1){
                            Toaster.toastShort("好友列表");
                        }
                    }
                });
    }

    private void changeTitleBarTo4(){
        TitleBarUtils.setTitleBar(titlebar, "个人中心");
        titlebar.leftButton(0)
                .clearRightButtons();
    }

    private void pickTopic(final int item, String dataDir){

        final List<Topic> topics = TopicManager.getTopics(dataDir);
        String[] topic = new String[topics.size()]; //ApiStatus.getTopics(dataDir);
        if(Lang.isEmpty(topics)){
            Toaster.toastShort("没有可选的主题");
        }else{

            for(int i = 0; i < topics.size(); i++){
                topic[i] = topics.get(i).showName;
            }

            ActionSheetUtils.showActionSheet(getActivity(), topic, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    if(item == 0){
                        appFragment.setTopic(topics.get(which - 1));
                        appFragment.autoRefresh();
                    }else if(item == 1){
                        //userFragment.setTopic(topics.get(which - 1));
                        //userFragment.autoRefresh();
                    }
                }
            });

        }
    }

    @Override
    protected void onResume() {
        pgv_pagers.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        pgv_pagers.onPause();
        super.onPause();
    }

}
