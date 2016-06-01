package org.ayo.weibo.initialize;

import org.ayo.Ayo;
import org.ayo.imageloader.Flesco;
import org.ayo.weibo.App;
import org.ayo.weibo.Initializer;

/**
 */
public class StepOfImageLoader implements Initializer.Step{
    @Override
    public boolean doSeriousWork() {
        Flesco.initFresco(App.app, Ayo.ROOT + "fresco");
        return true;
    }

    @Override
    public String getName() {
        return "图片加载库";
    }

    @Override
    public String getNotify() {
        return "图片加载库没有正确初始化";
    }

    @Override
    public boolean acceptFail() {
        return false;
    }
}
