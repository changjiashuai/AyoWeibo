package org.ayo.app.tmpl;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ayo.app.LocalDisplay;
import org.ayo.app.StatusUIMgmr;
import org.ayo.view.pullrefresh.PtrClassicDefaultFooter;
import org.ayo.view.pullrefresh.PtrDefaultHandler2;
import org.ayo.view.pullrefresh.PtrFrameLayout;
import org.ayo.view.pullrefresh.header.MaterialHeader;
import org.ayo.view.recycler.LinearLayoutManager;
import org.ayo.view.recycler.SimpleRecyclerAdapter;

import java.util.Collection;
import java.util.List;

import genius.android.view.R;

/**
 */
public abstract class AyoRecyclerViewFragment<T> extends Fragment {

    private View root = null;
    PtrFrameLayout mPtrFrameLayout;
    RecyclerView mRecyclerView;
    protected SimpleRecyclerAdapter<T> mAdapter;
    protected List<T> list;

    protected RecyclerView.LayoutManager getLayoutManager(){
        return new LinearLayoutManager(getActivity());
    }
    protected abstract SimpleRecyclerAdapter<T> newAdapter();
    protected abstract void onRefresh();
    protected abstract void onLoadMore();
    protected abstract void onCreateViewFinished(View root);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocalDisplay.init(getActivity());
        root = View.inflate(getActivity(), R.layout.ayo_tmpl_frag_recycler, null);
        mPtrFrameLayout = (PtrFrameLayout) root.findViewById(R.id.ptr_frame);

        mHandler = new Handler();
        initRecyclerView();
        initPtrFrameLayout();
        initStatusManager(mPtrFrameLayout, callback);
        condition = initCondition();

       onCreateViewFinished(root);

        return root;
    }

    protected <T extends View> View findViewById(int id){
        return root.findViewById(id);
    }

    protected List<T> getList(){
        return list;
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        //使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        mRecyclerView.setHasFixedSize(true);

        //设置LayoutManager
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);

        //设置Adapter
        mAdapter = newAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //adapter刷新列表的方法
        /*
        public final void notifyDataSetChanged()
        public final void notifyItemChanged(int position)
        public final void notifyItemRangeChanged(int positionStart, int itemCount)
        public final void notifyItemInserted(int position)
        public final void notifyItemMoved(int fromPosition, int toPosition)
        public final void notifyItemRangeInserted(int positionStart, int itemCount)
        public final void notifyItemRemoved(int position)
        public final void notifyItemRangeRemoved(int positionStart, int itemCount)
         */
    }

    private void initPtrFrameLayout() {
        // header
        final MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

        final PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(getContext());
        mPtrFrameLayout.setFooterView(footer);
        mPtrFrameLayout.addPtrUIHandler(footer);

        //如果不设置这句，上拉加载完不会直接看到加载到的数据，而要有个下滑动作，再往上滑才能看到
        mPtrFrameLayout.setForceBackWhenComplete(true);

        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrameLayout.setPullToRefresh(false);

//        mPtrFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrameLayout.autoRefresh(false);
//            }
//        }, 100);

        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                isLoadMore = false;
                onRefresh();
            }

            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
                isLoadMore = true;
                onLoadMore();
            }


        });

    }


    public void stopRefreshOrLoadMore(){
        try{
            mPtrFrameLayout.refreshComplete();
        }catch (Exception e){

        }
    }

    ///----------状态相关
    protected boolean isLoadMore = false;
    private StatusUIMgmr statusMgmr;
    protected Handler mHandler;

    protected void initStatusManager(PtrFrameLayout ptrFrameLayout, StatusUIMgmr.OnStatusViewAddedCallback callback){
        ///状态切换
        if(callback == null){
            callback = this.callback;
        }
        statusMgmr = StatusUIMgmr.attach(mPtrFrameLayout, callback);
        statusMgmr.setEmptyLayout(R.layout.genius_view_empty);
        statusMgmr.setErrorLayout(R.layout.genius_view_error_local, R.layout.genius_view_error_server);
        statusMgmr.setLoadingLayout(R.layout.genius_view_loading);

    }

    private StatusUIMgmr.OnStatusViewAddedCallback callback = new StatusUIMgmr.OnStatusViewAddedCallback() {

        @Override
        public void onLoadingViewAdded(View loadingView) {

        }

        public void onErrorViewAdded(View errorOfLocalView, View errorOfServerView) {
        }

        @Override
        public void onEmptyViewAdded(View emptyView) {

        }
    };

    public void onLoadFinish(){
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                stopRefreshOrLoadMore();
            }
        }, 200);
    }

    /**
     * 错误提示：
     * 原则是：只要界面上有数据，就不会切换页面，
     * 但是如果页面上本来就没数据，那就得按照错误的分类来，
     * @param reason
     * @param forceChageUI  是否不管当前页面是什么，都强制切换到错误页，一般应该是false
     */
    public void onLoadFail(int reason, boolean forceChageUI){
        onLoadFinish();
        if((list == null || list.size() == 0 )&& !forceChageUI){
            //界面不是空，也不强制切换UI，则什么都不干
            //Toaster.toastLong(errorInfo);
        }else{
            if(reason == ErrorReason.LOCAL){
                statusMgmr.showErrorOfLocal();
            }else if(reason == ErrorReason.SERVER){
                statusMgmr.showErrorOfServer();
            }else{
                statusMgmr.showErrorOfServer();
            }
        }
    }

    public abstract void onNotAnyMore();

    public void onLoadOk(List<T> data){
        onLoadFinish();
        if(isLoadMore && isEmpty(data)){
            ///没有更多页了，并且这一页也是空
            //Toaster.toastLong("没有下一页了");
            onNotAnyMore();
            return;
        }

        if(isLoadMore){
            this.list = (List<T>) combine(this.list, data);
        }else{
            this.list = data;
        }

        if(isEmpty(this.list)){
            mAdapter.notifyDataSetChanged(null);
            statusMgmr.showEmpty();
        }else{
            statusMgmr.clearStatus();
            mAdapter.notifyDataSetChanged(list);
        }

    }

    protected void autoRefresh(){
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(false);
            }
        }, 100);
    }

    private Condition condition;

    public abstract Condition initCondition();

    public Condition getCondition(){
        return this.condition;
    }

    private boolean isEmpty(List<T> data) {
        return data == null || data.size() == 0;
    }

    private <T> Collection<T> combine(Collection<T> c1,
                                      Collection<T> c2) {
        if (c1 == null && c2 == null)
            return null;
        if (c1 == null)
            return c2;
        if (c2 == null)
            return c1;
        c1.addAll(c2);
        return c1;
    }
}
