package com.ayoview.sample.deepmind;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.ayoview.sample.deepmind.custom.CustomView1;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 *
 *
 */
public class ViewCustom1Activity extends BaseActivity {

    private LinearLayout llRoot;// 界面的根布局
    private CustomView1 customView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dm_ac_custom_view_1);

        llRoot = findViewById(R.id.main_root_ll);
        customView1 = findViewById(R.id.customView1);
        //llRoot.addView(new CustomView1(this));
    }
}
