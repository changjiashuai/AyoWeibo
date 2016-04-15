package com.iwomedia.statictis;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

public class StatisticsUtils {
	
	public static void onPause(Context context){
		MobclickAgent.onPause(context);
	}
	
	public static void onResume(Context context){
		MobclickAgent.onResume(context);
	}
	
}
