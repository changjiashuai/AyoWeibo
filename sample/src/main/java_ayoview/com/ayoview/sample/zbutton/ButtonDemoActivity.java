package com.ayoview.sample.zbutton;

import com.cowthan.sample.BaseDemoMenuActivity;
import com.cowthan.sample.menu.Leaf;

/**
 * Created by Administrator on 2016/1/25.
 */
public class ButtonDemoActivity extends BaseDemoMenuActivity{

    @Override
    protected Leaf[] getMenus() {
        Leaf[] leaves = {
                new Leaf("复选框-1", "", DemoCheckBox1Activity.class),
        };
        return leaves;
    }
}
