package com.cowthan.sample;

import android.app.Application;

import org.ayo.Ayo;
import org.ayo.CrashHandler;
import org.ayo.app.AyoViewLib;
import org.ayo.imageloader.ImageLoaderUseUIL;
import org.ayo.imageloader.VanGogh;
import org.xutils.db.x;

/**
 * Created by cowthan on 2016/1/24.
 */
public class App extends Application{

    public static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //初始化Ayo SDK
        Ayo.init(this, "ayo", true, true);
        Ayo.debug = true;
        AyoViewLib.init(this);

        x.Ext.init(this);

        //初始化日志类

        //初始化网络图片加载工具类
        VanGogh.initImageBig(R.mipmap.loading_big);
        VanGogh.initImageMiddle(R.mipmap.loading_big);
        VanGogh.initImageSmall(R.mipmap.loading_big);
        VanGogh.init(this, new ImageLoaderUseUIL(this, Ayo.ROOT));

        //初始化全局异常处理
        CrashHandler crashHandler = CrashHandler.getInstance();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }
}
