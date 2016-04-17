package com.ayoview.sample.tmpl_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ayoview.sample.tmpl_listview.MyHttpResponse;
import com.ayoview.sample.tmpl_listview.TmplBean;
import com.ayoview.sample.tmpl_listview.http.TestHttper;
import com.ayoview.sample.tmpl_listview.http.TestOrder;
import com.ayoview.sample.tmpl_listview.http.TestOrderList;
import com.cowthan.sample.Utils;

import org.ayo.app.tmpl.AyoRecyclerViewFragment;
import org.ayo.app.tmpl.Condition;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.utils.HttpProblem;
import org.ayo.view.recycler.SimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public class DemoRecyclerFragment extends AyoRecyclerViewFragment {

    @Override
    protected SimpleRecyclerAdapter newAdapter() {
        return new ArticleAdapter2(getActivity(), list);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }


    @Override
    protected void onRefresh() {
        getCondition().onPullDown();
        loadData();
    }

    @Override
    protected void onLoadMore() {
        getCondition().onPullUp();
        loadData();
    }

    @Override
    protected void onCreateViewFinished(View root) {
        autoRefresh();
    }

    @Override
    public void onNotAnyMore() {

    }

    @Override
    public Condition initCondition() {
        MyCondition cond = new MyCondition();
        cond.reset();
        return cond;
    }

    private void loadData(){
        final MyCondition cond = (MyCondition) getCondition();
        BaseHttpCallback<TestOrderList> callback = new BaseHttpCallback<TestOrderList>() {

            @Override
            public void onFinish(boolean isSuccess, HttpProblem problem,
                                 ResponseModel resp, TestOrderList t) {
                if(isSuccess){

                    //--
                    if(t == null || t.artlist == null || t.artlist.size() == 0){
                        onLoadOk(null);
                        return;
                    }

                    //--
                    List<TmplBean> list = new ArrayList<TmplBean>();
                    for(TestOrder o : t.artlist){
                        TmplBean b = new TmplBean();
                        b.title = o.title;
                        b.cover_url = o.cover_url;
                        list.add(b);
                    }
                    onLoadOk(list);

                }else{
                    onLoadFail(Utils.parseErrorType(problem), true);
                }

            }

        };

        TestHttper.getArticle("haha", cond.pageNow, callback, MyHttpResponse.class);
    }
}
