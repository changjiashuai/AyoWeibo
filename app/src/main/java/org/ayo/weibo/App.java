package org.ayo.weibo;

import android.app.Application;

import org.ayo.Ayo;
import org.ayo.app.AyoViewLib;
import org.ayo.http.AyoHttp;
import org.ayo.http.retrofit.RetrofitManager;
import org.ayo.imageloader.Flesco;
import org.ayo.notify.Toaster;

/**
 * Created by Administrator on 2016/4/11.
 */
public class App extends Application {

    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        Ayo.init(this, "/ayoweibo/", true, false);
        AyoViewLib.init(this);
        AyoHttp.init(this);
        Flesco.initFresco(this, Ayo.ROOT + "fresco");
        RetrofitManager.init(this, "https://api.weibo.com/2/");

        final Thread.UncaughtExceptionHandler d = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
                d.uncaughtException(thread, throwable);
                Toaster.toastShort(throwable.getLocalizedMessage());
            }
        });



    }


}
