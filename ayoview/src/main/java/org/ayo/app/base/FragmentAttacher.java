package org.ayo.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/3/15.
 */
public abstract class FragmentAttacher extends Fragment{

    protected abstract int getLayoutId();

    protected View root;
    protected Handler mHandler;
    private ActivityAttacher attacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ///检查此Fragment对象的父Activity是否ActivityAttacher对象
        Activity a = getActivity();
        if(a instanceof TmplBaseActivity){
            attacher = ((TmplBaseActivity)a).getActivityAttacher();
        }else{
            throw new RuntimeException("FragmentAttacher对必须从属于ActivityAttacher");
        }
        //over

        root = inflater.inflate(getLayoutId(), null);
        mHandler = new Handler();
        onCreateView(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        mHandler = null;
        super.onDestroyView();
    }

    public abstract void onCreateView(View root);

    public View findViewById(int id){
        return root.findViewById(id);
    }

    public View findViewWithTag(Object o){
        return root.findViewWithTag(o);
    }
}
