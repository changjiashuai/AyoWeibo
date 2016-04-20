package org.ayo.app.common;

import android.os.Bundle;

import org.ayo.app.base.SwipeBackActivityAttacher;

public abstract class AyoSwipeBackActivityAttacher extends SwipeBackActivityAttacher{
	
	protected ActivityDelegate agent = new ActivityDelegate();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		agent.attach(getActivity());
	}

	@Override
	protected void onDestroy() {
		agent.detach();
		super.onDestroy();
	}

	protected AyoSwipeBackActivityAttacher getActivityAttacher(){
		return this;
	}

}
