package com.che58.ljb.rxjava.fragment.lesson;

import com.alibaba.fastjson.JSON;
import com.che58.ljb.rxjava.fragment.lesson.common.ResponseTimeline;
import com.che58.ljb.rxjava.fragment.lesson.common.TimelineListFragment2;
import com.che58.ljb.rxjava.fragment.lesson.common.U;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxJava课程3：样板代码的封装：just，from和Action
 */
/*
Observable<String> myObservable =
		    Observable.just("Hello, world!", "Fuck world!");
相当于：
public void call(Subscriber<? super String> sub) {
					sub.onNext("Hello, world!");
					sub.onCompleted();
				}
	改成：
	public void call(Subscriber<? super List<String>> sub) {
	        Throwable ex = null;
			try{
                for(){
                    sub.onNext("Hello, world!");
                }
			}catch(e){
				ex = e;
			}

			if(ex != null){
			    sub.onError(e);
			}else{
			    sub.onCompleted();
			}
	}
 */
public class Rxjava3 extends TimelineListFragment2 {

    protected void loadData() {


        // /一个订阅者，将和一个observerable绑定
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                //这里面对消息的处理，如果出现异常，会跑到onErrorAction
                System.out.println(s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable s) {
                System.out.println("onErrorAction");
                s.printStackTrace();
            }
        };

        Action0 onCompleteAction = new Action0() {

            @Override
            public void call() {
                System.out.println("onCompleteAction： 结束了！");
            }
        };

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseTimeline>() {
                               @Override
                               public void call(ResponseTimeline responseTimeline) {
                                   //responseTimeline = null;
                                   System.out.println("订阅者：线程--" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
                                   mAdapter.notifyDataSetChanged(responseTimeline.statuses);
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
                            }
                        }
                );



    }
}
