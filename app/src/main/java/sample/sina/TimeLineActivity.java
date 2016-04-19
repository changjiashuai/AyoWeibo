package sample.sina;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;

import sample.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/4/13.
 */
public class TimeLineActivity extends BaseFragmentActivity  {

    public static void start(Context c){
        Intent i = new Intent(c, TimeLineActivity.class);
        c.startActivity(i);
    }

    @Override
    protected void initFragment(FragmentManager fragmentManager, View root) {
        TimelineListFragment2 frag = new TimelineListFragment2();
        frag.setTimeLineType("public");
        fragmentManager.beginTransaction().replace(root.getId(), frag).commit();
    }
}
