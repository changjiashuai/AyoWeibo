package org.ayo.weibo;

import android.app.Application;

/**
 * Created by Administrator on 2016/4/11.
 */
public class App extends Application {

    public static App app;
    public static boolean isInitialed = false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        final Thread.UncaughtExceptionHandler d = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
                d.uncaughtException(thread, throwable);
                //Toaster.toastShort(throwable.getLocalizedMessage());
            }
        });
    }


}
