package org.ayo.http.download;


import org.ayo.utils.SimpleAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 简单的下载工具类，使用HttpURLConnection，没有进度提示，没有断点续传，没有多线程下载，
 * 最初写这个类，是因为要配合热补丁的下载功能，此下载功能要求特别简单，补丁文件也不会很大
 *
 */
public class SimpleDownloader {

    public interface Callback{
        void onOk(String savePath);
        void onFuck(String fuckReason);
    }

    public static void download(final String downloadUrl, final File saveDir, final String filename, final Callback callback){
        new SimpleAsyncTask() {

            boolean isSuccess = false;
            String error = "";

            @Override
            protected void onRunning() {
                error = downloadSync(downloadUrl, saveDir, filename, callback);
            }

            @Override
            protected void onFinish() {
                if(error == null){
                    callback.onOk(new File(saveDir, filename).getAbsolutePath());
                }else{
                    callback.onFuck(error);
                }
            }
        }.go();
    }

    /**
     *
     * @param downloadUrl  下载链接
     * @param saveDir 下完存哪儿，此dir没有会创建
     * @param filename 以此文件名保存
     * @param callback
     */
    private static String downloadSync(String downloadUrl, File saveDir, String filename, Callback callback){
        if(!saveDir.exists()){
            if(saveDir.mkdirs()){

            }else{
               // callback.onFuck();
                return "创建保存目录失败：" + saveDir.getAbsolutePath();
            }
        }
        try{
            URL url = new URL(downloadUrl);
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
            InputStream is = con.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(new File(saveDir, filename));

            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

            os.close();
            is.close();

            return null;

        }catch (Exception e){
            e.printStackTrace();
            //callback.onFuck(e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }
    }

}
