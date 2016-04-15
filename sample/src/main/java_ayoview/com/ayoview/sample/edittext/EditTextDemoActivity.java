package com.ayoview.sample.edittext;

import com.cowthan.sample.BaseDemoMenuActivity;
import com.ayoview.sample.zbutton.DemoCheckBox1Activity;
import com.cowthan.sample.menu.Leaf;

/**
 * Created by Administrator on 2016/3/28.
 */
public class EditTextDemoActivity extends BaseDemoMenuActivity {

    @Override
    protected Leaf[] getMenus() {
        Leaf[] leaves = {
                new Leaf("常见背景样式", "", DemoCheckBox1Activity.class),
                new Leaf("TextArea样式", "", DemoCheckBox1Activity.class),
                new Leaf("TextWatcher监听", "", DemoCheckBox1Activity.class),
                new Leaf("InputType", "", DemoCheckBox1Activity.class),
                new Leaf("搜索框", "", DemoCheckBox1Activity.class),
                new Leaf("自动补全", "", DemoCheckBox1Activity.class),
                new Leaf("密码可见", "", DemoCheckBox1Activity.class),
                new Leaf("电话号码分组", "", DemoCheckBox1Activity.class),
        };
        return leaves;
    }
}
