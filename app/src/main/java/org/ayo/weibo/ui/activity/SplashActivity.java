package org.ayo.weibo.ui.activity;


import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ayo.http.R;
import org.ayo.imageloader.Flesco;
import org.ayo.lang.Async;
import org.ayo.weibo.ui.base.WBBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 闪屏页
 * @author Administrator
 *
 */
public class SplashActivity extends WBBaseActivity {

	@Bind(R.id.iv_logo)
	SimpleDraweeView iv_logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wb_ac_splash);
		ButterKnife.bind(this);

		String uri = "http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-05ff9a30822bbd211590a8146903363f.jpg";
		Flesco.setImageUri(iv_logo, "file:///android_asset//splash_placeholder.jpg", uri);

		Async.post(new Runnable() {
			@Override
			public void run() {
				GuideActivity.start(agent.getActivity());
				finish();
			}
		}, 3000);
	}
}
