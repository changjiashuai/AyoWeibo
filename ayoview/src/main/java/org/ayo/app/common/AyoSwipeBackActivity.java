package org.ayo.app.common;

import android.os.Bundle;

import org.ayo.view.layout.swipeback.SwipeBackActivity;

public abstract class AyoSwipeBackActivity extends SwipeBackActivity{
	
	protected ActivityDelegate agent = new ActivityDelegate();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		agent.attach(this);
	}

	@Override
	protected void onDestroy() {
		agent.detach();
		super.onDestroy();
	}

	protected AyoSwipeBackActivity getActivity(){
		return this;
	}
	
}
