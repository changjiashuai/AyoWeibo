package org.ayo.utils;

import android.util.Log;

public class LogInner {
	
	
	public static void print(String s){
		System.out.println("Genius: " + s);
	}
	
	public static void debug(String msg){
		if(msg == null) msg = "null";
		Log.i("sb", msg);
	}
}
