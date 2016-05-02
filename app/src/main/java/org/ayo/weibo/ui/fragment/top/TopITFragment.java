package org.ayo.weibo.ui.fragment.top;

import android.view.View;

import org.ayo.app.tmpl.AyoRecyclerViewFragment;
import org.ayo.app.tmpl.Condition;
import org.ayo.app.tmpl.ErrorReason;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.utils.HttpProblem;
import org.ayo.lang.Async;
import org.ayo.lang.Lang;
import org.ayo.notify.Toaster;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.model.top.Top;
import org.ayo.weibo.ui.adapter.TopAdapter;

import java.util.List;

import sample.http.HttpperTops;

/**
 * 资讯
 */
public class TopITFragment extends AyoRecyclerViewFragment<Top> implements ISubPage{

    private boolean isFirstPage = false;
    private boolean isFirstCome = true;


    @Override
    protected void onCreateViewFinished(View root) {
        if(isFirstPage){
            //如果是第一个显示的页面
            autoRefresh();
        }else{
            //不干什么事
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            if(isFirstPage && isFirstCome){
                isFirstCome = false;
            }else{
                if(Lang.isEmpty(getList())){
                    autoRefresh();
                }else{
                    Toaster.toastShort("有数据，不主动刷新");
                }
            }
        }else{

        }
    }

    @Override
    public void onPageVisibleChange(boolean isVisible) {

    }

    private void loadData(){

        HttpperTops.getTops("获取新闻列表", new BaseHttpCallback<List<Top>>() {
            @Override
            public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, List<Top> tops) {
                if (isSuccess) {
                    onLoadOk(tops);
                } else {
                    onLoadFail(ErrorReason.SERVER, true);
                }
            }
        });

    }

    @Override
    protected SimpleRecyclerAdapter<Top> newAdapter() {
        return new TopAdapter(getActivity(), null);
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
    public void onNotAnyMore() {

    }

    @Override
    public Condition initCondition() {
        return null;
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
