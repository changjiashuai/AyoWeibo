package org.ayo.weibo.ui;

import android.content.Context;
import android.os.Bundle;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.common.AyoActivityAttacher;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pagegroup.PageGroupView;
import org.ayo.app.tmpl.pagegroup.PageIndicatorInfo;
import org.ayo.http.R;
import org.ayo.weibo.ui.fragment.ArticleFragment;
import org.ayo.weibo.ui.fragment.ConversationListFragment;
import org.ayo.weibo.ui.fragment.ProfileFragment;
import org.ayo.weibo.ui.fragment.ShoppingFragment;
import org.ayo.weibo.ui.fragment.TimelineListFragment;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MainFrameActivity extends AyoActivityAttacher {

    public static void start(Context c){
        ActivityAttacher.startActivity(c, MainFrameActivity.class, null, false, ActivityAttacher.LAUNCH_MODE_SINGLE_TASK);
    }


    PageGroupView pgv_pagers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_ac_main_frame);


        pgv_pagers = (PageGroupView) findViewById(R.id.pgv_pagers);

        PageIndicatorInfo[] indicatorInfos = new PageIndicatorInfo[]{
                new PageIndicatorInfo(R.mipmap.ic_tab_timeline_normal, R.mipmap.ic_tab_timeline_pressed, "动态"),
                new PageIndicatorInfo(R.mipmap.ic_tab_article_normal, R.mipmap.ic_tab_article_pressed, "文章"),
                new PageIndicatorInfo(R.mipmap.ic_tab_chat_normal, R.mipmap.ic_tab_chat_pressed, "聊天"),
                new PageIndicatorInfo(R.mipmap.ic_tab_shop_normal, R.mipmap.ic_tab_shop_pressed, "商城"),
                new PageIndicatorInfo(R.mipmap.ic_tab_profle_normal, R.mipmap.ic_tab_profle_pressed, "我"),
        };

        TimelineListFragment s1 = new TimelineListFragment();
        s1.setIsTheFirstPage(true);

        ArticleFragment s2 = new ArticleFragment();

        ConversationListFragment s3 = new ConversationListFragment();

        ShoppingFragment s4 = new ShoppingFragment();

        ProfileFragment s5 = new ProfileFragment();


        ISubPage[] pages = new ISubPage[]{
                s1,s2,s3,s4,s5,

        };
        //int uiMode = PageGroupView.MODE_TABHOST_HUNGRY;
        //int uiMode = PageGroupView.MODE_TABHOST_LAZY;
        int uiMode = PageGroupView.MODE_VIEWPAGER;

        pgv_pagers.attach(this.getActivity(), indicatorInfos, pages, uiMode, 0);
        pgv_pagers.setMessageNotify(2, 999);
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
