package com.che58.ljb.rxjava.fragment.lesson;

import com.alibaba.fastjson.JSON;
import com.che58.ljb.rxjava.fragment.lesson.common.ResponseTimeline;
import com.che58.ljb.rxjava.fragment.lesson.common.Timeline;
import com.che58.ljb.rxjava.fragment.lesson.common.TimelineListFragment2;
import com.che58.ljb.rxjava.fragment.lesson.common.U;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxJava课程4：from构造器
 *
 * 注意和just的区别
 *
 * just接收多个参数，产生事件流，如果其中一个参数是List，只对应一个事件
 * from接收一个List或者数组，并将其每一个元素拆分成一个事件
 *
 */
public class Rxjava4 extends TimelineListFragment2 {

    List<Timeline> list = new ArrayList<Timeline>();

    protected void loadData() {

        /**
         * just可以接收多个参数，形成一个事件流
         */
        Observable.just("Hello, world!")
                .map(new Func1<String, ResponseTimeline>() {

                    @Override
                    public ResponseTimeline call(String s) {

                        System.out.println("发布者：线程--" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());

                        //这个应该在io线程，这里是读文件，模拟的是http，这里会出http的错，超时会拿不到json，404,500会拿到另一种格式的json
                        String json = U.getContentFromAssets(getActivity(), "weibo.json");
                        //这个是json解析，应该在CPU线程，这里json解析会出错，比如服务器给的字段不合规范
                        ResponseTimeline responseTimeline = JSON.parseObject(json, ResponseTimeline.class);

                        return responseTimeline;
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ResponseTimeline, Observable<Timeline>>() {
                    @Override
                    public Observable<Timeline> call(ResponseTimeline responseTimeline) {
                        System.out.println("发布者变换：线程--" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
                        return Observable.from(responseTimeline.statuses);
                    }
                })
                .subscribeOn(Schedulers.computation())
                .map(new Func1<Timeline, Timeline>() {
                    @Override
                    public Timeline call(Timeline timeline) {
                        timeline.text = "Rxjava处理过--" + timeline.text;
                        return timeline;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Timeline>() {
                               @Override
                               public void call(Timeline responseTimeline) {
                                   System.out.println("订阅者：线程--" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
                                   list.add(responseTimeline);
                                   //mAdapter.notifyDataSetChanged(responseTimeline.statuses);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                //出错了
                                throwable.printStackTrace();
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                //事件流结束了
                                mAdapter.notifyDataSetChanged(list);
                            }
                        }
                );


    }
}
