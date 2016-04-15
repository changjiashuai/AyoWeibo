package com.ayoview.sample.deepmind;

import com.ayoview.sample.deepmind.xfermode.DemoXfermodePorterDuffActivity;
import com.ayoview.sample.deepmind2.PaintDemoMenuActivity;
import com.cowthan.sample.BaseDemoMenuActivity;
import com.cowthan.sample.BaseDrawerLayoutActivity;
import com.cowthan.sample.menu.Leaf;

/**
 */
public class DemoCustomMenuActivity extends BaseDemoMenuActivity {
    @Override
    protected Leaf[] getMenus() {
        Leaf[] leaves = {
                new Leaf("Drawable自定义", "", DemoDrawableCustomActivity.class),
                new Leaf("自定义控件-1：onDraw基本套路", "", ViewCustom1Activity.class),
                new Leaf("自定义控件-2：onDraw重绘", "", ViewCustom2Activity.class),
                new Leaf("ColorFlter：ColorMatrixColorFilter--图片滤镜demo", "", ColorMatrixFilterActivity.class),
                new Leaf("ColorFlter：LightingColorFilter--光照demo", "", LightingColorFilterActivity.class),
                new Leaf("ColorFlter：PorterDuffColorFilter--混合模式过滤", "", PorterDuffColorFilterActivity.class),
                new Leaf("Xfermode：PorterDuff", "", DemoXfermodePorterDuffActivity.class),
                new Leaf("侧滑demo", "", PaintDemoMenuActivity.class),

        };
        return leaves;
    }
}
