package org.ayo.weibo.initialize;

import org.ayo.app.AyoViewLib;
import org.ayo.weibo.App;
import org.ayo.weibo.Initializer;

/**
 * Created by Administrator on 2016/4/30.
 */
public class StepOfAyoView implements Initializer.Step{
    @Override
    public boolean doSeriousWork() {
        AyoViewLib.init(App.app);
        return true;
    }

    @Override
    public String getName() {
        return "UI Framework";
    }

    @Override
    public String getNotify() {
        return "UI Framework初始化失败";
    }

    @Override
    public boolean acceptFail() {
        return false;
    }
}
