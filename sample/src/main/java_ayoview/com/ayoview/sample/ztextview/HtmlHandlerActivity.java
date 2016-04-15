package com.ayoview.sample.ztextview;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by Administrator on 2016/3/29.
 */
public class HtmlHandlerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_demo_spannable);

        TextView tv_1 = findViewById(R.id.tv_1);
        TextView tv_2 = findViewById(R.id.tv_2);

        String content = "<mxgsa>测试自定义标签</mxgsa>";

        //普通TextView
        tv_1.setText(content);

        //使用自定义标签
        tv_2.setText(Html.fromHtml(content, null, new MxgsaTagHandler(getActivity())));
        tv_2.setClickable(true);
        tv_2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

