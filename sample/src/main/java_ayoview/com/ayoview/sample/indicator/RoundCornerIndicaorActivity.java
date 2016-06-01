package com.ayoview.sample.indicator;

import android.os.Bundle;
import android.view.View;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.app.tmpl.indicator.flyco.RoundCornerIndicaor;

import java.util.ArrayList;

public class RoundCornerIndicaorActivity extends BaseActivity {
    private int[] resIds = {R.mipmap.item1, R.mipmap.item2,
            R.mipmap.item3, R.mipmap.item4};
    private ArrayList<Integer> resList;
    private View decorView;
    private SimpleImageBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indic_flyco_activity_rci);

        resList = new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            resList.add(resIds[i]);
        }

        decorView = getActivity().getWindow().getDecorView();
        banner = ViewFindUtils.find(decorView, R.id.banner_circle);
        banner.setSource(resList).startScroll();

        indicator(R.id.indicator_circle);
        indicator(R.id.indicator_square);
        indicator(R.id.indicator_round_rectangle);
        indicator(R.id.indicator_circle_stroke);
        indicator(R.id.indicator_square_stroke);
        indicator(R.id.indicator_round_rectangle_stroke);
    }

    private void indicator(int indicatorId) {
        final RoundCornerIndicaor indicator = ViewFindUtils.find(decorView, indicatorId);
        indicator.setViewPager(banner.getViewPager(), resList.size());
    }
}
