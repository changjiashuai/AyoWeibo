package com.che58.ljb.rxjava.fragment.lesson.common;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/4/19.
 */
public class U {

    public static void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getContentFromAssets(Context c, String path){

        sleep();

        AssetManager am = c.getAssets();
        InputStream in = null;
        BufferedReader br = null;
        try {
            in = am.open(path);
            br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while(true){
                line = br.readLine();
                if(line == null){
                    break;
                }
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
