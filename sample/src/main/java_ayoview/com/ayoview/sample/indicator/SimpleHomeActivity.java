package com.ayoview.sample.indicator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cowthan.sample.BaseActivity;

import org.ayo.app.base.ActivityAttacher;

public class SimpleHomeActivity extends BaseActivity {
    private Context context;
    private final String[] items = {"RoundCornerIndicaor", "FlycoPageIndicaor"};
    private final Class<? extends ActivityAttacher>[] classes = new Class[]{RoundCornerIndicaorActivity.class, FlycoPageIndicaorActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ListView lv = new ListView(context);
        lv.setCacheColorHint(Color.TRANSPARENT);
        lv.setFadingEdgeLength(0);
        lv.setAdapter(new SimpleHomeAdapter(context, items));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAttacher.startActivity(context, classes[position]);
            }
        });

        setContentView(lv);
    }
}
