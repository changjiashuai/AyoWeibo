package com.ayoview.sample.ztextview;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by Administrator on 2016/3/28.
 */
public class SpannableActivity extends BaseActivity {

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

        //spannable处理过的TextView

        Spanned s = Html.fromHtml(content);
        int index0 = content.indexOf("前有")+2;
        int index1 = content.indexOf("家医院") + 3;
        int index2 = content.indexOf("位咨");

        String color = "#79d2be";
        SpannableString msp = new SpannableString(s);
        msp.setSpan(new RelativeSizeSpan(1.25f), index0, index1-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        msp.setSpan(new RelativeSizeSpan(1.2f), index1,index2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        msp.setSpan(new ForegroundColorSpan(Color.parseColor(color)), index0, index1-3,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色
        msp.setSpan(new ForegroundColorSpan(Color.parseColor(color)),index1,index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色
        msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), index0, index1-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), index1,index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体
        msp.setSpan(new ScaleXSpan(0.9f), index0, index1-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ScaleXSpan(0.9f), index1,index2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_2.setText(msp);
    }
}
