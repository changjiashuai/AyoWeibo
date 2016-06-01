package com.ayoview.sample.tmpl_pagegroup;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cowthan.sample.R;

import org.ayo.app.tmpl.pagegroup.ISubPage;


/**
 * Created by Administrator on 2016/4/5.
 */
public class SampleFragment extends Fragment implements ISubPage {


    /**
     * 是否第一次进这个页面
     */
    public boolean isFirstCome = true;

    /**
     * 本页面是否首页，即是否需要直接呈现给用户
     */
    public boolean isTheFirstPage = false;

    ListView lv_list;
    ProgressBar progress_bar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("pagegroup", title + "--onCreateView");
        View root = View.inflate(getActivity(), R.layout.frag_demo_page_group_sample_fragment_list, null);
        lv_list = (ListView) root.findViewById(R.id.lv_list);
        progress_bar = (ProgressBar) root.findViewById(R.id.progress_bar);
        TextView tv_title = (TextView) root.findViewById(R.id.tv_title);
        tv_title.setText(title);

        if(isTheFirstPage){
            loadData();
        }

        return root;
    }

    private String title = "no";
    public void setTitle(String t){
        this.title = t;
    }

    @Override
    public void setIsTheFirstPage(boolean isTheFirstPage){
        this.isTheFirstPage = isTheFirstPage;
    }

    private void loadData() {

        Log.i("pagegroup", title + "------------------loadData------------------");
        progress_bar.setVisibility(View.VISIBLE);
        lv_list.setVisibility(View.GONE);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                lv_list.setAdapter(new SampleAdapter());
                progress_bar.setVisibility(View.GONE);
                lv_list.setVisibility(View.VISIBLE);
            }
        }.execute();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        Log.i("pagegroup", title + "--setUserVisibleHint: " + isVisibleToUser);
        if(isVisibleToUser){
            if(isTheFirstPage && isFirstCome){
                //首页，第一次进来会在onCreateView中加载数据，这里不用
                isFirstCome = false;
            }else{
                if(isFirstCome){
                    //非首页，第一次进入，加载数据
                    isFirstCome = false;
                    loadData();
                }else{
                    //非首页，也不是第一次进来
                    //这里就得按照业务需求来了
                }

            }
        }

    }

    @Override
    public void onPageVisibleChange(boolean isVisible) {

        //在这里进行友盟统计
        Log.i("pagegroup", title + "--onPageVisibleChange: " + isVisible);

        if(isVisible){

        }else{

        }

    }


    private class SampleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
            TextView text1 = (TextView) v.findViewById(android.R.id.text1);
            text1.setText("item--" + i);
            return v;
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.i("pagegroup", title + "--onAttach");
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("pagegroup", title + "--onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("pagegroup", title + "--onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i("pagegroup", title + "--onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i("pagegroup", title + "--onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.i("pagegroup", title + "--onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("pagegroup", title + "--onPause");
        super.onPause();
    }

    @Override
    public void onDetach() {
        Log.i("pagegroup", title + "--onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.i("pagegroup", title + "--onDestroyView");
        super.onDestroyView();
    }


}
