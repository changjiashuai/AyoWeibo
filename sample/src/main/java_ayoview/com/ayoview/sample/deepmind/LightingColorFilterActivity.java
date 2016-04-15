package com.ayoview.sample.deepmind;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.ayoview.sample.deepmind.custom.ImageColorMatrixFilterView;
import com.ayoview.sample.deepmind.custom.ImageLightingFilterView;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * LightingColorFilter的demo
 */
public class LightingColorFilterActivity extends BaseActivity {

    private LinearLayout llRoot;// 界面的根布局
    private ImageLightingFilterView customView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dm_ac_custom_view_lighting_color_filter);

        llRoot = findViewById(R.id.main_root_ll);
        customView2 = findViewById(R.id.customView1);

    }
}