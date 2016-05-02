package org.ayo.weibo.ui.fragment.qa;

import android.view.View;

import org.ayo.app.tmpl.AyoRecyclerViewFragment;
import org.ayo.app.tmpl.Condition;
import org.ayo.notify.Toaster;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.model.qa.QaSimple;
import org.ayo.weibo.ui.adapter.QaAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/2.
 */
public class QaListFragment extends AyoRecyclerViewFragment<QaSimple> {

    @Override
    protected void onCreateViewFinished(View root) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void loadData(){
        QaCondition cond = (QaCondition) getCondition();

        final List<QaSimple> list = new ArrayList<>();
        list.add(new QaSimple());
        list.add(new QaSimple());
        list.add(new QaSimple());
        list.add(new QaSimple());
        list.add(new QaSimple());
        list.add(new QaSimple());
        list.add(new QaSimple());list.add(new QaSimple());
        list.add(new QaSimple());
        list.add(new QaSimple());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadOk(list);
            }
        }, 2000);

    }

    public void refresh(){
        autoRefresh();
    }

    @Override
    protected SimpleRecyclerAdapter newAdapter() {
        return new QaAdapter(getActivity(), null);
    }

    @Override
    protected void onRefresh() {
        QaCondition cond = (QaCondition) getCondition();
        cond.onPullDown();
        loadData();
    }

    @Override
    protected void onLoadMore() {
        QaCondition cond = (QaCondition) getCondition();
        cond.onPullUp();
        loadData();
    }

    @Override
    public void onNotAnyMore() {
        Toaster.toastShort("没有下一页了");
    }

    @Override
    public Condition initCondition() {
        return new QaCondition();
    }

    private class QaCondition extends Condition{

        public int page = 0;

        @Override
        public void onPullDown() {
            page = 0;
        }

        @Override
        public void onPullUp() {
            page++;
        }

        @Override
        public void reset() {
            page = 0;
        }
    }
}
