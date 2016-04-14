package org.ayo.http;

import android.app.Application;
import android.content.Context;


/**
 * Created by Administrator on 2016/4/11.
 */
public class AyoHttp {

    public static Context context = null;

    public static void init(Application c){
        context = c;
//        x.Ext.init(c);
//        x.Ext.setDebug(false);
    }

//    public static void startService(Context context, Intent intent){
//        String methodName = "startServiceAsUser";
//        // ComponentName startServiceAsUser(Intent service, UserHandle user)
//
//        try {
//            Method m = context.getClass().getDeclaredMethod(methodName, Intent.class, UserHandle.class);
//            m.invoke(context, intent, Process.)
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//    }

}
