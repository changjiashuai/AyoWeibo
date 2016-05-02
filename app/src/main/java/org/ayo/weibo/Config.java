package org.ayo.weibo;

/**
 * Created by Administrator on 2016/4/21.
 */
public class Config {

    public static class Build{
        public static final boolean OPEN_DEBUG_LOG = true;
        public static final String APP_WORK_DIR = "/ayoweibo/";
    }

    public static class API{
        public static final boolean USE_RETROFIT = false;
        public static final String HOST_WEIBO = "https://api.weibo.com/2/";

        /**
         * 存放json文件的七牛域名
         */
        public static final String JSON_URL = "http://7xo0ny.com1.z0.glb.clouddn.com/";
    }


    public static class DIR{
        public static final String APP_DIR = "app";
        public static final String USER_DIR = "user";

        public static final String APP_DEFAULT_TOPIC = "appDir";
        public static final String USER_DEFAULT_TOPIC = "privateDir";

        /**
         * key是：dataDir + "/" + topicName
         * 值是：Integer[]  就是json文件转出来的页码数组
         */
        //public static LruCache<String, Integer[]> jsonCache = new LruCache<>(10*1000*1000);
    }



}
