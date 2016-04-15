package com.ayoview.sample.deepmind.custom;

import android.graphics.PorterDuff;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PorterDuffModeMgmr {

    public static PorterDuff.Mode[] modes = {
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.XOR,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.LIGHTEN,
            PorterDuff.Mode.OVERLAY,
            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.DST,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.DST_OVER,
            PorterDuff.Mode.SRC,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.SRC_OUT,
            PorterDuff.Mode.SRC_OVER,
    };

    public static String[] modesStr = {
            "PorterDuff.Mode.ADD",
            "PorterDuff.Mode.XOR",
            "PorterDuff.Mode.MULTIPLY",
            "PorterDuff.Mode.CLEAR",
            "PorterDuff.Mode.DARKEN",
            "PorterDuff.Mode.LIGHTEN",
            "PorterDuff.Mode.OVERLAY",
            "PorterDuff.Mode.SCREEN",
            "PorterDuff.Mode.DST",
            "PorterDuff.Mode.DST_ATOP",
            "PorterDuff.Mode.DST_IN",
            "PorterDuff.Mode.DST_OUT",
            "PorterDuff.Mode.DST_OVER",
            "PorterDuff.Mode.SRC",
            "PorterDuff.Mode.SRC_ATOP",
            "PorterDuff.Mode.SRC_IN",
            "PorterDuff.Mode.SRC_OUT",
            "PorterDuff.Mode.SRC_OVER",
    };

    public static PorterDuff.Mode getMode(int i){
        return modes[i];
    }

    public static String getModeName(int i){
        return modesStr[i];
    }

    public static int getModeCount(){
        return modes.length;
    }
}
