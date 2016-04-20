package org.ayo.weibo.ui;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.common.FragmentContainerActivityAttacher;
import org.ayo.weibo.ui.fragment.TimelineListFragment2;


/**
 * Created by Administrator on 2016/4/13.
 */
public class TimeLineActivity extends FragmentContainerActivityAttacher {

    public static void start(Context c){
        ActivityAttacher.startActivity(c, TimeLineActivity.class, null, false, ActivityAttacher.LAUNCH_MODE_SINGLE_TASK);
    }

    @Override
    protected void initFragment(FragmentManager fragmentManager, View root) {
        TimelineListFragment2 frag = new TimelineListFragment2();
        frag.setTimeLineType("public");
        fragmentManager.beginTransaction().replace(root.getId(), frag).commit();
    }
}
