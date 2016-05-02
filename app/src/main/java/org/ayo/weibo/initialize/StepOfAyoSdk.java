package org.ayo.weibo.initialize;

import org.ayo.Ayo;
import org.ayo.weibo.App;
import org.ayo.weibo.Config;
import org.ayo.weibo.Initializer;

/**
 */
public class StepOfAyoSdk implements Initializer.Step {
    @Override
    public boolean doSeriousWork() {
        Ayo.init(App.app, Config.Build.OPEN_DEBUG_LOG);
        return true;
    }

    @Override
    public String getName() {
        return "激活底层类库";
    }

    @Override
    public String getNotify() {
        return "底层类库激活失败";
    }

    @Override
    public boolean acceptFail() {
        return true;
    }
}
