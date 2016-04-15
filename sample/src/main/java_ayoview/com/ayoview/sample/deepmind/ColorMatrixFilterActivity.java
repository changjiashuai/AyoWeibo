package com.ayoview.sample.deepmind;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.ayoview.sample.deepmind.custom.ImageColorMatrixFilterView;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 *
 *
 */
public class ColorMatrixFilterActivity extends BaseActivity {

    private LinearLayout llRoot;// 界面的根布局
    private ImageColorMatrixFilterView customView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dm_ac_custom_view_color_matrix);

        llRoot = findViewById(R.id.main_root_ll);
        customView2 = findViewById(R.id.customView1);



    }
}
