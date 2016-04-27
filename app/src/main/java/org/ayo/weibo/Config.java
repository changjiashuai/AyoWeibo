package org.ayo.weibo;

import android.util.LruCache;

/**
 * Created by Administrator on 2016/4/21.
 */
public class Config {

    public static class API{
        public static final boolean USE_RETROFIT = false;
        public static final String HOST_WEIBO = "https://api.weibo.com/2/";

        /**
         * 存放json文件的七牛域名
         */
        public static final String JSON_URL = "";

    }


    public static class DIR{
        public static final String APP_DIR = "appData";
        public static final String USER_DIR = "userData";

        public static final String DEFAULT_TIMELINE_COLOR = "timeline_color";
        public static final String DEFAULT_TIMELINE_IT = "timeline_it";

        /**
         * key是：dataDir + "/" + topicName
         * 值是：Integer[]  就是json文件转出来的页码数组
         */
        public static LruCache<String, Integer[]> jsonCache = new LruCache<>(10*1000*1000);
    }


}
