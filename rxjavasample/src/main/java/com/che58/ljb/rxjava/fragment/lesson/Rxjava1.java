package com.che58.ljb.rxjava.fragment.lesson;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.che58.ljb.rxjava.fragment.lesson.common.ResponseTimeline;
import com.che58.ljb.rxjava.fragment.lesson.common.TimelineListFragment2;
import com.che58.ljb.rxjava.fragment.lesson.common.U;

/**
 * 不使用RxJava
 */
public class Rxjava1 extends TimelineListFragment2 {

    ResponseTimeline responseTimeline;

    protected void loadData() {

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                //这个应该在io线程，这里是读文件，模拟的是http，这里会出http的错，超时会拿不到json，404,500会拿到另一种格式的json
                String json = U.getContentFromAssets(getActivity(), "weibo.json");

                //这个是json解析，应该在CPU线程，这里json解析会出错，比如服务器给的字段不合规范
                responseTimeline = JSON.parseObject(json, ResponseTimeline.class);

                //模拟一些变换，又有可能需要在子线程，如处理返回的数据，根据拿到的文章，从本地取是否收藏，是否点赞，是否读过等，或者给图片加水印之类的
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //刷新页面，这个应该在主线程
                mAdapter.notifyDataSetChanged(responseTimeline.statuses);
            }
        }.execute();




    }
}
