package org.ayo.weibo.initialize;

import org.ayo.http.AyoHttp;
import org.ayo.http.retrofit.RetrofitManager;
import org.ayo.weibo.App;
import org.ayo.weibo.Initializer;

/**
 * Created by Administrator on 2016/4/30.
 */
public class StepOfHttp implements Initializer.Step{
    @Override
    public boolean doSeriousWork() {
        AyoHttp.init(App.app);
        RetrofitManager.init(App.app, "https://api.weibo.com/2/");
        return true;
    }

    @Override
    public String getName() {
        return "http库";
    }

    @Override
    public String getNotify() {
        return "http库没有正确初始化";
    }

    @Override
    public boolean acceptFail() {
        return false;
    }
}
