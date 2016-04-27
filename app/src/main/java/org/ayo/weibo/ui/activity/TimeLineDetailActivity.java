package org.ayo.weibo.ui.activity;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.base.BundleManager;
import org.ayo.app.base.SimpleBundle;
import org.ayo.app.common.FragmentContainerActivityAttacher;
import org.ayo.weibo.ui.fragment.TimeLineDetailFragment;

/**
 * Created by Administrator on 2016/4/21.
 */
public class TimeLineDetailActivity extends FragmentContainerActivityAttacher {

    public static void start(Activity a, String url){
        SimpleBundle bundle = BundleManager.getDefault().fetch();
        bundle.putExtra("url", url);
        ActivityAttacher.startActivity(a, TimeLineDetailActivity.class, bundle, false, ActivityAttacher.LAUNCH_MODE_STANDARD);
    }

    @Override
    protected void initFragment(FragmentManager fragmentManager, View root) {
        TimeLineDetailFragment fragment = new TimeLineDetailFragment();
        String url = getBundle().getExtra("url");
        fragment.setUrl(url, null);
        getSupportFragmentManager().beginTransaction().replace(root.getId(), fragment).commit();
    }
}
