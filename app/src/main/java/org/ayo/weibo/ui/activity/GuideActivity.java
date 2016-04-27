package org.ayo.weibo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.common.AyoActivityAttacher;
import org.ayo.app.tmpl.indicator.spring.SpringIndicator;
import org.ayo.app.tmpl.pager.FragmentPager;
import org.ayo.http.R;
import org.ayo.weibo.ui.fragment.mess.GuidePageVedioFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 应该仿慕课网做一个
 */
public class GuideActivity extends AyoActivityAttacher{

    public static void start(Context c){
        ActivityAttacher.startActivity(c, GuideActivity.class, null, false, ActivityAttacher.LAUNCH_MODE_STANDARD);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_ac_guide);

        FragmentPager pager = findViewById(R.id.pager);
        SpringIndicator indicator = findViewById(R.id.indicator);
        final Button btn_we_are_in = findViewById(R.id.btn_we_are_in);
        btn_we_are_in.setVisibility(View.GONE);
        btn_we_are_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LoginActivity.start(getActivity());
                finish();
            }
        });

        List<Fragment> fragments = new ArrayList<>();

        GuidePageVedioFragment f1 = new GuidePageVedioFragment();
        f1.setIsTheFirstPage(true);
        f1.setAssetVedioPath("guideVedio/guide_1.mp4");

        GuidePageVedioFragment f2 = new GuidePageVedioFragment();
        f2.setAssetVedioPath("guideVedio/guide_2.mp4");

        GuidePageVedioFragment f3 = new GuidePageVedioFragment();
        f3.setAssetVedioPath("guideVedio/guide_3.mp4");

        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);

        String[] titles = {
                1+"", 2+"", 3+""
        };


        pager.attach(getSupportFragmentManager(), fragments, titles, indicator, new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    btn_we_are_in.setVisibility(View.VISIBLE);
                }else{
                    btn_we_are_in.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
