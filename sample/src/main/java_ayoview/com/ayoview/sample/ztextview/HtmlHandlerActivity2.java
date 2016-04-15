package com.ayoview.sample.ztextview;

import android.os.Bundle;
import android.text.Spanned;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.view.textview.html.HtmlTagHandler;

/**
 * Created by Administrator on 2016/3/29.
 */
public class HtmlHandlerActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_demo_spannable);

        TextView tv_1 = findViewById(R.id.tv_1);
        TextView tv_2 = findViewById(R.id.tv_2);

        String content = "呵呵呵<span style=\"{color:#e60012}\">哈哈哈</span>嘿嘿嘿";

        //普通TextView
        tv_1.setText(content);

        //使用自定义标签
        content = "<html><body>" + content + "</body></html>";
        Spanned s = HtmlTagHandler.fromHtml(content, null, new com.ayoview.sample.ztextview.html.SpanTagHandler());
        tv_2.setText(s);
    }
}

