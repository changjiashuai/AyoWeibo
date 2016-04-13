package org.ayo.http;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * Created by Administrator on 2016/4/11.
 */
public class AyoHttp {

    public static Context context = null;

    public static void init(Application c){
        context = c;
        x.Ext.init(c);
        x.Ext.setDebug(false);
    }

}
