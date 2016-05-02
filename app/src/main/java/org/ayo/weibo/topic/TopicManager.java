package org.ayo.weibo.topic;

import org.ayo.Ayo;
import org.ayo.Configer;
import org.ayo.file.AyoFiles;
import org.ayo.file.Files;
import org.ayo.http.download.SimpleDownloader;
import org.ayo.jlog.JLog;
import org.ayo.lang.Async;
import org.ayo.lang.JsonUtils;
import org.ayo.lang.Lang;
import org.ayo.lang.Lists;
import org.ayo.lang.OnWalk;
import org.ayo.notify.Toaster;
import org.ayo.weibo.Config;
import org.ayo.weibo.api3.ApiStatus;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 管理在本地存的topic配置文件，和本地目录的创建，也管下载topic配置文件
 */
public class TopicManager {

    public static void ensureAppTopicConfig(){
        ensureTopicConfig(Config.DIR.APP_DIR, Files.path.getFileInRoot("topic.json"));
    }

    public static void ensureUserTopicConfig(){
        ensureTopicConfig(Config.DIR.USER_DIR, Files.path.getFileInRoot("user_topic.json"));
    }

    /**
     * 保证sd卡下有数据相关的文件夹，前提是topic.json已经被下载下来了，如果没有，会加载assets里带的default/topic.json
     *
     * 并设置默认的系统动态页的主题
     *
     * 并且，如果appDir目录为空，则把默认的拷进来
     */
    public static boolean ensureTopicConfig(String dataDir, String configPath){

        //String topicPath = Files.path.getFileInRoot("topic.json");
        File topicFile = new File(configPath);

        if(!(topicFile.exists() && topicFile.isFile())){
            String src = dataDir.equals(Config.DIR.APP_DIR) ? "default/topic.json" : "default/user_topic.json";
            String dest = dataDir.equals(Config.DIR.APP_DIR) ? "topic.json" : "user_topic.json";
            Files.fileop.moveFromAssetToSD(src, Files.path.getFileInRoot(dest));
        }

        String topicConfigJson = AyoFiles.steam.string(topicFile);
        JLog.i("topic config of " + dataDir + " is: " + topicConfigJson);
        List<Topic> topics = JsonUtils.getBeanList(topicConfigJson, Topic.class);
        if(dataDir.equals(Config.DIR.APP_DIR)){
            saveDefaultAppTopic(topics.get(0));
        }else{
            saveDefaultUserTopic(topics.get(0));
        }
        for(Topic topic: topics){
            String dirPath = Files.path.getFileInRoot(dataDir + "/" + topic.dirName);
            File dir = new File(dirPath);
            if(!dir.exists()){
                if(dir.mkdirs()){
                    //do nth
                }else{
                    //
                    JLog.i("fail-7");
                    Toaster.toastShort("创建目录失败，可能没有sd卡权限");
                    return false;
                }
            }else{
                //啥也不干
            }
        }

        ///如果目录创建成功了，就把默认的拷进来
        Async.newTask(new Runnable() {
            @Override
            public void run() {
                if (!ApiStatus.checkAppDataDir()) {
                    Toaster.toastShort("sd卡不正常，无法继续工作1");
                    return;
                }

                if (!ApiStatus.checkUserDataDir()) {
                    Toaster.toastShort("sd卡不正常，无法继续工作2");
                    return;
                }
                Integer[] pages1 = ApiStatus.getCurrentPages(Config.DIR.APP_DIR, Config.DIR.APP_DEFAULT_TOPIC);
                Integer[] pages2 = ApiStatus.getCurrentPages(Config.DIR.USER_DIR, Config.DIR.USER_DEFAULT_TOPIC);

                if (Lang.isEmpty(pages1)) {
                    Files.fileop.moveFromAssetToSD("default/appDir/1.json",
                            ApiStatus.getJsonFilePath(Config.DIR.APP_DIR, Config.DIR.APP_DEFAULT_TOPIC, "1.json"));

                    Files.fileop.moveFromAssetToSD("default/appDir/2.json",
                            ApiStatus.getJsonFilePath(Config.DIR.APP_DIR, Config.DIR.APP_DEFAULT_TOPIC, "2.json"));
                }
                if (Lang.isEmpty(pages2)) {
                    Files.fileop.moveFromAssetToSD("default/default_user/1.json",
                            ApiStatus.getJsonFilePath(Config.DIR.USER_DIR, Config.DIR.USER_DEFAULT_TOPIC, "1.json"));

                    Files.fileop.moveFromAssetToSD("default/default_user/2.json",
                            ApiStatus.getJsonFilePath(Config.DIR.USER_DIR, Config.DIR.USER_DEFAULT_TOPIC, "21.json"));
                }

                Lang.sleep(1000);
            }
        }).post(new Runnable() {
            @Override
            public void run() {

            }
        }).go();

        return true;
    }

    /**
     * 获取系统的topic列表
     * @return 无则null
     */
    public static List<Topic> getTopics(String dataDir){

        String topicPath = null;
        if(dataDir.equals(Config.DIR.APP_DIR)){
            topicPath = Files.path.getFileInRoot("topic.json");
        }else{
            topicPath = Files.path.getFileInRoot("user_topic.json");
        }

        File topicFile = new File(topicPath);
        if(topicFile.exists() && topicFile.isFile()){
            String topicConfigJson = AyoFiles.steam.string(topicFile);
            List<Topic> topics = JsonUtils.getBeanList(topicConfigJson, Topic.class);
            return topics;
        }else{
            return null;
        }
    }


    /**
     * 下载主题配置文件
     * @param c
     */
    public static void downloadTopicConfig(SimpleDownloader.Callback c){
        SimpleDownloader.download("http://7xo0ny.com1.z0.glb.clouddn.com/topic.json",
                new File(Ayo.ROOT),
                "topic.json",
                c);
    }

    /**
     * 去七牛下载最新的数据，只有系统数据才需要下载，所以不用特意指定dataDir
     * @param topic
     * @param c
     */
    public static void downloadTopData(Topic topic, SimpleDownloader.Callback c){

        int maxPage = getMaxPage(Config.DIR.APP_DIR, topic);
        maxPage = maxPage + 1;
        String url = Config.API.JSON_URL + topic.dirName + "_" + maxPage + ".json";
        JLog.i("下载--" + url);
        SimpleDownloader.download(url,
                new File(Ayo.ROOT + Config.DIR.APP_DIR + "/" + topic.dirName),
                maxPage + ".json",
                c);
    }

    /**
     * 得到指定主题的当前最大页
     * @param topic
     * @return
     */
    public static int getMaxPage(String dataDir, Topic topic){
        Integer[] pages = getCurrentPages(dataDir, topic);
        if (Lang.isEmpty(pages)) {
            return 0;
        } else {
            return pages[0];
        }
    }

    public static void saveDefaultAppTopic(Topic topic){
        Configer.putObject("default-app-topic", topic);
    }

    public static Topic getDefaultAppTopic(){
        return Configer.getObject("default-app-topic", Topic.class);
    }

    public static void saveDefaultUserTopic(Topic topic){
        Configer.putObject("default-user-topic", topic);
    }

    public static Topic getDefaultUserTopic(){
        return Configer.getObject("default-user-topic", Topic.class);
    }

    /**
     * 得到指定主题的所有json文件对应的页数值，并倒序输出
     * @param topic
     * @return
     */
    private static Integer[] getCurrentPages(String dataDir, Topic topic){
        String subpath = dataDir + "/" + topic.dirName;

//        if(Config.DIR.jsonCache.get(subpath) == null){
//
//        }else{
//            return Config.DIR.jsonCache.get(subpath);
//        }

        String fullpath = Files.path.getFileInRoot(subpath);
        File dir = new File(fullpath);
        if(dir.exists() && dir.isDirectory()){

            File[] jsonFiles = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith(".json");
                }
            });
            if(Lang.isNotEmpty(jsonFiles)){
                final Integer[] pageArr = new Integer[jsonFiles.length];
                Lists.each(jsonFiles, new OnWalk<File>() {
                    @Override
                    public boolean process(int index, File file, int total) {
                        String s = file.getName().replace(".json", "");
                        pageArr[index] = Lang.toInt(s);
                        return false;
                    }
                });
                Arrays.sort(pageArr, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer integer, Integer t1) {
                        if (integer == null) return -1;
                        if (t1 == null) return 1;
                        return t1 - integer;
                    }
                });

                //Config.DIR.jsonCache.put(subpath, pageArr);
                return pageArr;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
