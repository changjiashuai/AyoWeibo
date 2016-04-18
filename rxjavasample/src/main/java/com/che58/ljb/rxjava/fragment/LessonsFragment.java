package com.che58.ljb.rxjava.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.che58.ljb.rxjava.R;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Buffer操作符
 * Created by ljb on 2016/3/25.
 */
public class LessonsFragment extends RxFragment {

    @Bind(R.id.btn_click)
    Button btn_click;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notMoreClick();
    }

    /**
     * 3秒内不允许按钮多次点击
     * */
    private void notMoreClick() {
        RxView.clicks(btn_click)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(getActivity(), R.string.des_demo_not_more_click, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 不适用Rxjava，就得这么写
     */
    private void runOldWay(){
        /*
        遍历指定文件夹，找到所有图片
         */
        final File[] folders = {

        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (File folder : folders) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".png")) {
                            final Bitmap bitmap = getBitmapFromFile(file);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //在主线程显示
                                    //imageCollectorView.addImage(bitmap);
                                }
                            });
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 使用Rxjava，就可以这么写
     */
    private void runFashionWay(){
        /*
        遍历指定文件夹，找到所有图片
         */
        final File[] folders = {

        };
        Observable.from(folders)   //--folders现在成了事件队列，里面每一个folder都作为一个事件发出，可以直接订阅
                .flatMap(new Func1<File, Observable<File>>() { //--但直接订阅的话，里面又要解析，回到主线程，还是会有嵌套，所以用flatMap转换，意思就是展开，把folders里每一个File再展开成一个事件，进去的是File，出来的是Observable<File>
                    @Override
                    public Observable<File> call(File file) {   //--所以flatMap是把事件再转为Observable，让业务流得以继续
                        return Observable.from(file.listFiles());  //--遍历文件夹，又产生一个事件队列
                    }
                })
                .filter(new Func1<File, Boolean>() {   //--Observable<File>发出一个事件，即File，在这里被过滤
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {  //--进去的是File，出来的是Bitmap，这里不会再产生observable，所以业务流就终止了，Bitmap成为了最后发出的事件
                    @Override
                    public Bitmap call(File file) {  //--所以map是把事件转为另一种事件，主要看订阅者希望要什么
                        return getBitmapFromFile(file);
                    }
                })
                .subscribeOn(Schedulers.io())  //--在io线程上订阅,io线程是io密集型，大多数事件再等待，可以让出CPU，computation()是计算密集型，占用大量CPU事件
                .observeOn(AndroidSchedulers.mainThread())  //--在主线程上观察，这个怎么理解？我在北京订阅了一个杂志，在山东观察？
                .subscribe(new Action1<Bitmap>() {   //--订阅，注册一个订阅者，即Action1
                    @Override
                    public void call(Bitmap bitmap) {
                        //显示图片，在主线程
                        //imageCollectorView.addImage(bitmap);
                    }
                });
    }




















    private Bitmap getBitmapFromFile(File file) {
        return null;
    }

}
