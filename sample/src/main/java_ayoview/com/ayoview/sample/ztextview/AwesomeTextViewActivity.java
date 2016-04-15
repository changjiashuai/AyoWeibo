package com.ayoview.sample.ztextview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.cowthan.sample.App;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.Display;
import org.ayo.view.textview.awesome.AwesomeTextHandler;

/**
 * Created by Administrator on 2016/3/28.
 */
public class AwesomeTextViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_demo_spannable);

        TextView tv_1 = findViewById(R.id.tv_1);
        TextView tv_2 = findViewById(R.id.tv_2);

        String content = "目前有{numHospital}家医院{numSeller}位咨询师";
        content = content.replace("{numHospital}", "28").replace("{numSeller}", "325");

        //普通TextView
        tv_1.setText(content);

        //AwesomeTextView处理过的TextView
        tv_2.setText(content);
        String color = "#79d2be";
        AwesomeTextHandler awesomeTextViewHandler = new AwesomeTextHandler();
        awesomeTextViewHandler
                .addViewSpanRenderer("医院", new CardAmountSpanRenderer())
                .addViewSpanRenderer("咨询师", new CardAmountSpanRenderer())
                .setView(tv_2);

    }

    public class CardAmountSpanRenderer implements AwesomeTextHandler.ViewSpanRenderer {

        @Override
        public View getView(String text, Context context) {
            TextView view = new TextView(context);
            view.setTextColor(Color.parseColor("#ff8d84"));
            view.setTextSize(Display.sp2px(App.app, 20));
            SpannableString ss = new SpannableString(text);
            view.setText(ss);
            return view;
        }
    }
}
