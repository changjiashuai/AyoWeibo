package org.ayo.weibo.api3;

import org.ayo.file.Files;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.download.SimpleDownloader;
import org.ayo.http.utils.HttpProblem;
import org.ayo.lang.Lang;
import org.ayo.lang.Lists;
import org.ayo.lang.OnWalk;
import org.ayo.weibo.Config;
import org.ayo.weibo.model.timeline.AyoResponseTimeline;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sample.http.FastJsonParser;


/**
 * 仿微博http请求
 */
public class ApiStatus {


    /**
     * 这个检查是保证app运行必须的，必填
     * 检查在sd卡工作目录下（ayoweibo/)：
     * 1 是否存在appData目录，无则创建，失败则返回false
     * 2 是否存在appData/timeline_color, timeline_it两个主题目录，无则创建，失败则返回false
     * 3 如果是空，不管是不是空，刷新列表时，都会从服务器下载json
     * @return
     */
    public static boolean checkAppDataDir(){
        // appData是否存在，无则创建
        String appDataDirPath = Files.path.getPathInRoot(Config.DIR.APP_DIR);
        File appDataDir = new File(appDataDirPath);
        if(appDataDir.exists() && appDataDir.isDirectory()){

        }else{
            if(appDataDir.mkdirs()){

            }else{
                return false;
            }
        }

        //timeline_color   timeline_it默认的，必须有
        File colorDir = new File(appDataDirPath, Config.DIR.DEFAULT_TIMELINE_COLOR);
        if(colorDir.exists() && colorDir.isDirectory()){

        }else{
            if(!colorDir.mkdir()) return false;
        }

        File itDir = new File(appDataDirPath, Config.DIR.DEFAULT_TIMELINE_IT);
        if(itDir.exists() && itDir.isDirectory()){

        }else{
            if(!itDir.mkdir()) return  false;
        }

        return true;
    }

    /**
     * 这个和上面一样，保证用户自己的工作目录存在
     * @return
     */
    public static boolean checkUserDataDir(){
        // appData是否存在，无则创建
        String appDataDirPath = Files.path.getPathInRoot(Config.DIR.USER_DIR);
        File appDataDir = new File(appDataDirPath);
        if(appDataDir.exists() && appDataDir.isDirectory()){

        }else{
            if(appDataDir.mkdirs()){

            }else{
                return false;
            }
        }

        //timeline_color 默认的，必须有
        File colorDir = new File(appDataDirPath, Config.DIR.DEFAULT_TIMELINE_COLOR);
        if(colorDir.exists() && colorDir.isDirectory()){

        }else{
            if(!colorDir.mkdir()) return false;
        }

        return true;
    }

    public static String getAppColorDir(){
        return Config.DIR.APP_DIR + "/" + Config.DIR.DEFAULT_TIMELINE_COLOR;
    }

    public static String getAppITDir(){
        return Config.DIR.APP_DIR + "/" + Config.DIR.DEFAULT_TIMELINE_IT;
    }

    public static String getAppDir(String topicName){
        return Config.DIR.APP_DIR + "/" + topicName;
    }

    public static String getUserDir(String topicName){
        return Config.DIR.USER_DIR + "/" + topicName;
    }

    public static String getJsonFilePath(String dataDir, String topicName, String filename){
        return Files.path.getFileInRoot(dataDir + "/" + topicName + "/" + filename);
    }

    /**
     * 获取指定目录下的当前所有页码，就是遍历文件
     * 倒序返回
     * @param dataDir   如appData
     * @param topicName  如timeline_color
     * @return
     */
    public static Integer[] getCurrentPages(String dataDir, String topicName){
        String subpath = dataDir + "/" + topicName;

        if(Config.DIR.jsonCache.get(subpath) == null){

        }else{
            return Config.DIR.jsonCache.get(subpath);
        }

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

                Config.DIR.jsonCache.put(subpath, pageArr);
                return pageArr;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public static String[] getTopics(String dataDir){
        String subpath = dataDir;
        String fullpath = Files.path.getPathInRoot(subpath);
        File dir = new File(fullpath);
        if(dir == null || !dir.exists() || !dir.isDirectory()){
            return null;
        }else{
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            });

            if(Lang.isEmpty(files)){
                return null;
            }else{
                final String[] arr = new String[files.length];
                Lists.each(files, new OnWalk<File>() {
                    @Override
                    public boolean process(int index, File s, int total) {
                        arr[index] = s.getName();
                        return false;
                    }
                });
                return arr;

            }


        }
    }


    public static void downloadTimeline(String flag, final String dataDir, final String topicName, final BaseHttpCallback<Boolean> callback){

        //取得当前最大页
        Observable.just("go")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Integer[] jsonPages = getCurrentPages(dataDir, topicName);
                        int maxPage = 0;
                        if(Lang.isEmpty(jsonPages)){
                            maxPage = 0;
                        }else{
                            maxPage = jsonPages[0];
                        }
                        maxPage++;
                        return Config.API.JSON_URL + dataDir + "_" + topicName + "_" + maxPage + ".json";
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        SimpleDownloader.download(s,
                                new File(Files.path.getPathInRoot(dataDir + "/" + topicName)),
                                s.replace(Config.API.JSON_URL + dataDir + "_" + topicName + "_", ""),
                                new SimpleDownloader.Callback(){

                                    @Override
                                    public void onOk(String savePath) {
                                        Config.DIR.jsonCache.remove(dataDir + "/" + topicName);
                                        callback.onFinish(true, HttpProblem.OK, null, null);
                                    }

                                    @Override
                                    public void onFuck(String fuckReason) {
                                        callback.onFinish(false, HttpProblem.SERVER_ERROR, null, null);
                                    }
                                });
                    }
                });



        //最大页+1 就是下一页，拼出七牛key，去七牛下载
        //下载成功：存到sd/ayoweibo/dataDir/topicName，更新Config.DIR.jsonCache
        //下载失败：就是没有最新，或者网不行


    }

    /**
     *
     * @param flag
     * @param dataDir app, user等
     * @param topicName   timeline_color(美女）， timeline_hh(闺女)，timeline_news(新闻）, timeline_it(技术文章），其他（sd工作目录下必须有对应目录，且里面必须有1.json等文件
     * @param page
     * @param callback
     */
    public static void getStatuses(String flag, final String dataDir, final String topicName, final int page, final BaseHttpCallback<AyoResponseTimeline> callback){

         Observable.just(page).map(new Func1<Integer, String>() {
             @Override
             public String call(Integer integer) {
                 String filePath = dataDir + "/" + topicName + "/" + page + ".json";
                 File f = new File(Files.path.getFileInRoot(filePath));
                 if(f.exists() && f.isFile()){
                     return Files.file.getContent(filePath);
                 }else{
                     return "{}";
                 }
             }
         }).subscribeOn(Schedulers.io())
         .map(new Func1<String, AyoResponseTimeline>() {
             @Override
             public AyoResponseTimeline call(String s) {
                 return new FastJsonParser().getBean(s, AyoResponseTimeline.class);
             }
         })
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(new Action1<AyoResponseTimeline>() {
             @Override
             public void call(AyoResponseTimeline ayoResponseTimeline) {
                 callback.onFinish(true, HttpProblem.OK, null, ayoResponseTimeline);
             }
         });


    }

}
