package org.ayo.weibo.ui.fragment.main;

import android.view.View;

import org.ayo.app.tmpl.AyoRecyclerViewFragment;
import org.ayo.app.tmpl.Condition;
import org.ayo.app.tmpl.ErrorReason;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.utils.HttpProblem;
import org.ayo.lang.Async;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.model.top.Top;
import org.ayo.weibo.ui.adapter.tops.TopAdapter;

import java.util.List;

import sample.http.HttpperTops;

/**
 * 资讯
 */
public class TopFragment extends AyoRecyclerViewFragment<Top> implements ISubPage{

    private boolean isFirstPage = false;
    private boolean isFirstCome = true;

    @Override
    protected SimpleRecyclerAdapter<Top> newAdapter() {
        return new TopAdapter(getActivity(), null);
    }

    private void loadData(){

        HttpperTops.getTops("获取新闻列表", new BaseHttpCallback<List<Top>>() {
            @Override
            public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, List<Top> tops) {
                if(isSuccess){
                    onLoadOk(tops);
                }else{
                    onLoadFail(ErrorReason.SERVER, true);
                }
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            if(isFirstPage && isFirstCome){
                isFirstCome = false;
            }else{
                autoRefresh();
            }
        }else{

        }
    }

    @Override
    protected void onRefresh() {
        loadData();
    }

    @Override
    protected void onLoadMore() {
        Async.post(new Runnable() {
            @Override
            public void run() {
                stopRefreshOrLoadMore();
            }
        }, 1000);
    }

    @Override
    protected void onCreateViewFinished(View root) {

    }

    @Override
    public void onNotAnyMore() {

    }

    @Override
    public Condition initCondition() {
        return null;
    }

    @Override
    public void onPageVisibleChange(boolean isVisible) {

    }

    @Override
    public void setIsTheFirstPage(boolean isTheFirstPage) {
        isFirstPage = isTheFirstPage;
    }

    public static class TopCondition extends Condition{

        @Override
        public void onPullDown() {

        }

        @Override
        public void onPullUp() {

        }

        @Override
        public void reset() {

        }
    }
}
