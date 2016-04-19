package org.ayo.weibo.ui.prompt;

import org.ayo.http.R;
import org.ayo.view.widget.TitleBar;
import org.ayo.weibo.utils.Utils;

/**
 * Created by Administrator on 2016/4/20.
 */
public class TitleBarUtils {

    public static void setTitleBar(TitleBar titlebar, String title){
        titlebar.title(title).bgColor(Utils.getColor(R.color.main_color));
    }

}
