package org.ayo.app.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.base.TmplBaseActivity;


public abstract class AyoFragment extends Fragment {

	private View rootView;

	protected abstract int getLayoutId();
	protected abstract void onCreateView(View root);

	/**
	 * 返回布局，就是加载布局
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		rootView = inflater.inflate(getLayoutId() , container, false);
		onCreateView(rootView);
		return rootView;
	}

	protected <T> T findViewById(int id){
		return (T) rootView.findViewById(id);
	}

	protected <T> T id(int id){
		return (T) rootView.findViewById(id);
	}

	protected View getRootView(){
		return rootView;
	}

	public ActivityAttacher getActivityAttacher(){
		if(getActivity() instanceof TmplBaseActivity){
			return ((TmplBaseActivity)getActivity()).getActivityAttacher();
		}else{
			throw new RuntimeException("父Activity不是基于TmplBaseActivity，也就是说你没使用ActivityAttacher框架");
		}
	}
	
}
