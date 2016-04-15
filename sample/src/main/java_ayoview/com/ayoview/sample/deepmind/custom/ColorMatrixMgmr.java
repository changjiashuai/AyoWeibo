package com.ayoview.sample.deepmind.custom;

import android.graphics.ColorMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cowthan on 2016/4/7.
 */
public class ColorMatrixMgmr {

    public static Map<String, ColorMatrix> map = new HashMap<String, ColorMatrix>();

    static{

        ///原图
        map.put("原图", new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        }));

        ///色彩矩阵矩阵1：变暗
        map.put("变暗", new ColorMatrix(new float[]{
                0.5F, 0, 0, 0, 0,
                0, 0.5F, 0, 0, 0,
                0, 0, 0.5F, 0, 0,
                0, 0, 0, 1, 0,
        }));


        //色彩矩阵矩阵2：黑白
        map.put("黑白", new ColorMatrix(new float[]{
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0, 0, 0, 1, 0,
        }));

        //色彩矩阵矩阵3：锐化
        map.put("锐化", new ColorMatrix(new float[]{
                -1, 0, 0, 1, 1,
                0, -1, 0, 1, 1,
                0, 0, -1, 1, 1,
                0, 0, 0, 1, 0,
        }));

        //色彩矩阵4：红蓝翻转
        map.put("红蓝翻转", new ColorMatrix(new float[]{
                0, 0, 1, 0, 0,
                0, 1, 0, 0, 0,
                1, 0, 0, 0, 0,
                0, 0, 0, 1, 0,
        }));

        //色彩矩阵5：老照片
        map.put("老照片", new ColorMatrix(new float[]{
                0.393F, 0.769F, 0.189F, 0, 0,
                0.349F, 0.686F, 0.168F, 0, 0,
                0.272F, 0.534F, 0.131F, 0, 0,
                0, 0, 0, 1, 0,
        }));

        //色彩矩阵6：去色后高对比度
        map.put("去色后高对比度", new ColorMatrix(new float[]{
                1.5F, 1.5F, 1.5F, 0, -1,
                1.5F, 1.5F, 1.5F, 0, -1,
                1.5F, 1.5F, 1.5F, 0, -1,
                0, 0, 0, 1, 0,
        }));

        //色彩矩阵7：加强饱和度
        map.put("加强饱和度", new ColorMatrix(new float[]{
                1.438F, -0.122F, -0.016F, 0, -0.03F,
                -0.062F, 1.378F, -0.016F, 0, 0.05F,
                -0.062F, -0.122F, 1.483F, 0, -0.02F,
                0, 0, 0, 1, 0,
        }));

    }

    public static ColorMatrix getColorMatrix(String key){
        return map.get(key);
    }

    public static List<String> listEffects(){
        Set<String> set =  map.keySet();
        List<String> list = new ArrayList<String>();

        for(String s: set){
            list.add(s);
        }

        return list;
    }

    public static int effectCount(){
        return map.size();
    }

    public static String key(int i){
        List<String> keys = listEffects();
        return keys.get(i);
    }


}
