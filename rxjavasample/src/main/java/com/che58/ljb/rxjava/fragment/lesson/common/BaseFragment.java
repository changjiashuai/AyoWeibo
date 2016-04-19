package com.che58.ljb.rxjava.fragment.lesson.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;


public abstract class BaseFragment extends RxFragment {

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

	
}
