package com.ayoview.sample.tmpl_pagegroup;

import android.os.Bundle;

import com.cowthan.sample.R;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.app.tmpl.pagegroup.PageGroupView;
import org.ayo.app.tmpl.pagegroup.PageIndicatorInfo;


public class DemoMainActivity extends ActivityAttacher {

    PageGroupView pgv_pagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_demo_pagegroup_main);

        pgv_pagers = findViewById(R.id.pgv_pagers);

        PageIndicatorInfo[] indicatorInfos = new PageIndicatorInfo[]{
                new PageIndicatorInfo(R.mipmap.ic_tab_news_1, R.mipmap.ic_tab_news_2, "资讯"),
                new PageIndicatorInfo(R.mipmap.ic_tab_qa_1, R.mipmap.ic_tab_qa_2, "问答"),
                new PageIndicatorInfo(R.mipmap.ic_tab_car_1, R.mipmap.ic_tab_car_2, "找车"),
                new PageIndicatorInfo(R.mipmap.ic_tab_play_1, R.mipmap.ic_tab_play_2, "活动"),
                new PageIndicatorInfo(R.mipmap.ic_tab_me_1, R.mipmap.ic_tab_me_2, "我"),
        };

        SampleFragment s1 = new SampleFragment();
        s1.setTitle("1");

        SampleFragment s2 = new SampleFragment();
        s2.setTitle("2");

        SampleFragment s3 = new SampleFragment();
        s3.setTitle("3");

        SampleFragment s4 = new SampleFragment();
        s4.setTitle("4");

        SampleFragment s5 = new SampleFragment();
        s5.setTitle("5");

        ISubPage[] pages = new ISubPage[]{
                s1,s2,s3,s4,s5

        };
        //int uiMode = PageGroupView.MODE_TABHOST_HUNGRY;
        int uiMode = PageGroupView.MODE_TABHOST_LAZY;
        //int uiMode = PageGroupView.MODE_VIEWPAGER;

        pgv_pagers.attach(this.getActivity(), indicatorInfos, pages, uiMode, 1);

        pgv_pagers.setChecked(4);
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
