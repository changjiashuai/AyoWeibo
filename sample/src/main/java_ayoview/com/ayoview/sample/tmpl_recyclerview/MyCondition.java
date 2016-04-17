package com.ayoview.sample.tmpl_recyclerview;

import org.ayo.app.tmpl.Condition;

/**
 * Created by Administrator on 2016/4/17.
 */
public class MyCondition extends Condition {

    public int pageNow = 0;

    @Override
    public void onPullDown() {
        pageNow = 0;
    }

    @Override
    public void onPullUp() {
        pageNow++;
    }

    @Override
    public void reset() {
        pageNow = 0;
    }
}
