package com.ayoview.sample.ztextview;

import com.ayoview.sample.ztextview.badge.TabActivity;
import com.ayoview.sample.ztextview.verticalbanner.VerticalBannerActivity;
import com.cowthan.sample.BaseDemoMenuActivity;
import com.cowthan.sample.menu.Leaf;

/**
 * Created by Administrator on 2016/1/25.
 */
public class TextViewDemoActivity extends BaseDemoMenuActivity{

    @Override
    protected Leaf[] getMenus() {
        Leaf[] leaves = {
                new Leaf("Spannable使用", "", SpannableActivity.class),
                new Leaf("自定义html标签：超链接", "", HtmlHandlerActivity.class),
                new Leaf("自定义html标签：处理span标签", "", HtmlHandlerActivity2.class),
                new Leaf("AwesomeTextView使用", "", AwesomeTextViewActivity.class),
                new Leaf("BadgeView:简单用法", "", BadgeViewActivity.class),
                new Leaf("BadgeView:官方demo", "", TabActivity.class),
                new Leaf("VerticalBannerView:垂直切换广告条", "", VerticalBannerActivity.class),
        };
        return leaves;
    }

}
