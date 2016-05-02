package org.ayo.weibo.topic;

/**
 * Created by Administrator on 2016/4/30.
 */
public class Topic{

    /**
     * 手机sd卡里文件夹名字，也是七牛里数据json文件的前缀之一
     */
    public String dirName;

    /**
     * 在App界面上选择时显示的名字
     */
    public String showName;

    public Topic(String dirName, String showName) {
        this.dirName = dirName;
        this.showName = showName;
    }

    public Topic(){

    }
}
