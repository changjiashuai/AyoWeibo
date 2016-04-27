package org.ayo.weibo.ui.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.webkit.WebView;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.base.BundleManager;
import org.ayo.app.base.SimpleBundle;
import org.ayo.app.common.FragmentContainerActivityAttacher;
import org.ayo.app.tmpl.AyoWebViewFragment;
import org.ayo.weibo.ui.activity.TimeLineDetailActivity;

/**
 * Created by Administrator on 2016/4/24.
 */
public class TopDetailActivity extends FragmentContainerActivityAttacher {

    public static void start(Activity a, String url){
        SimpleBundle bundle = BundleManager.getDefault().fetch();
        bundle.putExtra("url", url);
        ActivityAttacher.startActivity(a, TopDetailActivity.class, bundle, false, ActivityAttacher.LAUNCH_MODE_STANDARD);
    }

    @Override
    protected void initFragment(FragmentManager fragmentManager, View root) {
        TopDetailFragment fragment = new TopDetailFragment();
        String url = getBundle().getExtra("url");
        fragment.setUrl(url, null);
        getSupportFragmentManager().beginTransaction().replace(root.getId(), fragment).commit();
    }

    public static class TopDetailFragment extends AyoWebViewFragment{

        @Override
        public void setJsInfomation(WebView webview) {

        }
    }
}
