package org.ayo.weibo.initialize;

import android.app.Activity;

import org.ayo.Ayo;
import org.ayo.weibo.Config;
import org.ayo.weibo.Initializer;

/**
 */
public class StepOfSdCard implements Initializer.Step {

    private Activity activity;

    public StepOfSdCard(Activity a){
        this.activity = a;
    }
    
    @Override
    public boolean doSeriousWork() {
        return Ayo.setSDRoot(Config.Build.APP_WORK_DIR);
    }

    @Override
    public String getName() {
        return "sd card";
    }

    @Override
    public String getNotify() {
        return "sd卡没有正确初始化";
    }

    @Override
    public boolean acceptFail() {
        return false;
    }
}
