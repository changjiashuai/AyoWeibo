package com.ayoview.sample.deepmind;

import android.app.Activity;

import org.ayo.Display;

/**
 * Created by Administrator on 2016/4/8.
 */
public class MeasureUtil {

    public static int[] getScreenSize(Activity a){
        return new int[]{Display.screenWidth, Display.screenHeight};
    }
}
