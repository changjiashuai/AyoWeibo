package org.ayo.weibo.ui.activity;


import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.ayo.http.R;
import org.ayo.lang.Async;
import org.ayo.notify.Toaster;
import org.ayo.permission.Ask;
import org.ayo.weibo.App;
import org.ayo.weibo.Initializer;
import org.ayo.weibo.initialize.StepOfAyoSdk;
import org.ayo.weibo.initialize.StepOfAyoView;
import org.ayo.weibo.initialize.StepOfCrash;
import org.ayo.weibo.initialize.StepOfHttp;
import org.ayo.weibo.initialize.StepOfImageLoader;
import org.ayo.weibo.initialize.StepOfLog;
import org.ayo.weibo.initialize.StepOfSdCard;
import org.ayo.weibo.topic.TopicManager;
import org.ayo.weibo.ui.base.WBBaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 闪屏页
 * @author Administrator
 *
 */
public class SplashActivity extends WBBaseActivity {

	@Bind(R.id.iv_logo)
	ImageView iv_logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wb_ac_splash);
		ButterKnife.bind(this);

		String uri = "http://7xicvb.com1.z0.glb.clouddn.com/girl_b_img-05ff9a30822bbd211590a8146903363f.jpg";
		//Flesco.setImageUri(iv_logo, "file:///android_asset//splash_placeholder.jpg", uri);

		///先别下载了，现在不管怎么说，都能下个文件下来
//		TopicManager.downloadTopicConfig(new SimpleDownloader.Callback() {
//			@Override
//			public void onOk(String savePath) {
//				onLoadFinish();
//			}
//
//			@Override
//			public void onFuck(String fuckReason) {
//				onLoadFinish();
//			}
//		});
	}

	private boolean isFirstCome = true;

	@Override
	protected void onStart() {
		super.onStart();
		if(isFirstCome && !App.isInitialed){
			initPermission();
			isFirstCome = false;
		}else{
			onLoadFinish();
		}
	}

	private void initPermission(){
		Ask.on(agent.getActivity())
				.forPermissions(Manifest.permission.ACCESS_COARSE_LOCATION
						, Manifest.permission.WRITE_EXTERNAL_STORAGE) //one or more permissions
				.withRationales("需要有定位权限功能才能正常工作",
						"需要有sd卡读写权限才能正常工作") //optional
				.when(new Ask.Permission() {
					@Override
					public void granted(List<String> permissions) {
						Log.i("aa", "granted :: " + permissions);
						initApp();
					}

					@Override
					public void denied(List<String> permissions) {
						Log.i("aa", "denied :: " + permissions);
					}
				}).go();
	}

	private void initApp(){

		Initializer.initailizer()
				.addStep(new StepOfCrash())
				.addStep(new StepOfAyoView())
				.addStep(new StepOfAyoSdk())
				.addStep(new StepOfSdCard(agent.getActivity()))
				.addStep(new StepOfLog())
				.addStep(new StepOfHttp())
				.addStep(new StepOfImageLoader())
				.setStepListener(new Initializer.StepListner() {
					@Override
					public boolean onSuffering(Initializer.Step step, boolean isSuccess, int currentStep, int total) {

						//统一判断
						if (!isSuccess && !step.acceptFail()) {
							//退出，提示错误
							Toaster.toastShort(step.getNotify());
							//finish();
							return false;
						}

						//单步逻辑
						if (step.getName().equals("UI Framework")) {
							//UI库加载完，才可以使用AyoViewLib
//							GifDrawable gif = null;
//							try {
//								gif = new GifDrawable(getResources(), R.drawable.gif_loading_fire);
//								iv_logo.setImageDrawable(gif);
//								gif.start();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
						}


						if(currentStep == total){
							App.isInitialed = true;
							onLoadFinish();
						}

						return true;
					}
				})
				.suffer();
	}

	private void onLoadFinish(){
		TopicManager.ensureAppTopicConfig();
		TopicManager.ensureUserTopicConfig();
		Async.post(new Runnable() {
			@Override
			public void run() {
				GuideActivity.start(agent.getActivity());
				finish();
			}
		}, 3000);
	}
}
