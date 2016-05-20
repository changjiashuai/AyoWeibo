package com.ayoview.sample.ultra.pulltorefresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ayoview.sample.ultra.pulltorefresh.ui.activity.PtrDemoHomeActivity;
import com.cowthan.sample.R;

import org.ayo.app.common.AyoSwipeBackActivityAttacher;

/**
 * Created by Administrator on 2016/5/20.
 */
public class UltraDemoActivityAttacher extends AyoSwipeBackActivityAttacher{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_ultra_pulltorefresh_demo);

        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PtrDemoHomeActivity.class);
                getActivity().startActivity(i);
            }
        });
    }
}
