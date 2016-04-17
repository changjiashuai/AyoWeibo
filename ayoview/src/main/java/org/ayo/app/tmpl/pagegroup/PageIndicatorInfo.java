package org.ayo.app.tmpl.pagegroup;

/**
 * 指示器对应的entity
 *
 * Created by Administrator on 2016/4/5.
 */
public class PageIndicatorInfo {

    public int resNormal = 0;
    public int resPressed = 0;
    public String title = "";

    public PageIndicatorInfo(int resNormal, int resPressed, String title) {
        this.resNormal = resNormal;
        this.resPressed = resPressed;
        this.title = title;
    }
}
