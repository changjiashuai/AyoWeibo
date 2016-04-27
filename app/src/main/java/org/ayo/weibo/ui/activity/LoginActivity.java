package org.ayo.weibo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sina.weibo.sdk.demo.WBDemoMainActivity;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.common.AyoSwipeBackActivityAttacher;
import org.ayo.file.Files;
import org.ayo.http.R;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.retrofit.RetrofitManager;
import org.ayo.http.utils.HttpProblem;
import org.ayo.http.utils.NetWorkUtils;
import org.ayo.lang.Async;
import org.ayo.lang.Lang;
import org.ayo.notify.Toaster;
import org.ayo.view.progress.ProgressView;
import org.ayo.weibo.Config;
import org.ayo.weibo.api.WBUserApi;
import org.ayo.weibo.api2.ApiUser;
import org.ayo.weibo.api3.ApiStatus;
import org.ayo.weibo.model.LoginUserInfo;
import org.ayo.weibo.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/20.
 */
public class LoginActivity extends AyoSwipeBackActivityAttacher {

    public static void start(Context c){
        ActivityAttacher.startActivity(c, LoginActivity.class, null, false, ActivityAttacher.LAUNCH_MODE_STANDARD);
    }

    @Bind(R.id.pv_loading)
    ProgressView pv_loading;

    @Bind(R.id.rl_content)
    RelativeLayout rl_content;

    @Bind(R.id.btn_login)
    Button btn_login;

    @Bind(R.id.btn_in)
    Button btn_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_ac_login);
        View r = findViewById(R.id.root);
        ButterKnife.bind(this, r);

        rl_content.setVisibility(View.GONE);

        if(NetWorkUtils.isConnected(getActivity())){
            checkWeiboAuthExpired();
        }else{
            Toaster.toastShort("5月20之前还不支持离线使用");
        }

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    /**
     * 检查微博授权是否过时
     */
    private void checkWeiboAuthExpired() {
        if(Config.API.USE_RETROFIT){
            WBUserApi api = RetrofitManager.getRetrofit().create(WBUserApi.class);
            api.getUserInfo(Utils.getWeiboToken(), Utils.getCurrentWeiboUserUid(), "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<LoginUserInfo>() {
                        @Override
                        public void call(LoginUserInfo loginUserInfo) {
                            Toaster.toastShort("欢迎回来，" + loginUserInfo.screen_name);
                            postJumpToMain();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            rl_content.setVisibility(View.VISIBLE);
                            pv_loading.setVisibility(View.GONE);
                            Toaster.toastShort("请先通过微博登录授权");
                        }
                    });
        }else{
            ApiUser.getLoginUserInfo("获取当前登录用户信息",
                    Utils.getWeiboToken(),
                    Utils.getCurrentWeiboUserUid(),
                    "",
                    new BaseHttpCallback<LoginUserInfo>() {
                        @Override
                        public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, LoginUserInfo loginUserInfo) {
                            if (isSuccess) {
                                Toaster.toastShort("欢迎回来，" + loginUserInfo.screen_name);
                                postJumpToMain();
                            } else {
                                rl_content.setVisibility(View.VISIBLE);
                                pv_loading.setVisibility(View.GONE);
                                Toaster.toastShort("请先通过微博登录授权");
                            }
                        }
                    }
            );
        }
    }

    private void postJumpToMain(){

        Async.newTask(new Runnable() {
            @Override
            public void run() {
                if(!ApiStatus.checkAppDataDir()){
                    Toaster.toastShort("sd卡不正常，无法继续工作1");
                    return;
                }

                if(!ApiStatus.checkUserDataDir()){
                    Toaster.toastShort("sd卡不正常，无法继续工作2");
                    return;
                }
                Integer[] pages1 = ApiStatus.getCurrentPages(Config.DIR.APP_DIR, Config.DIR.DEFAULT_TIMELINE_COLOR);
                Integer[] pages2 = ApiStatus.getCurrentPages(Config.DIR.APP_DIR, Config.DIR.DEFAULT_TIMELINE_IT);

                if(Lang.isEmpty(pages1)){
                    Files.fileop.moveFromAssetToSD("timeline_color/1.json",
                            ApiStatus.getJsonFilePath(Config.DIR.APP_DIR, Config.DIR.DEFAULT_TIMELINE_COLOR, "1.json"));
                }
                if(Lang.isEmpty(pages2)){
                    Files.fileop.moveFromAssetToSD("timeline_it/1.json",
                            ApiStatus.getJsonFilePath(Config.DIR.APP_DIR, Config.DIR.DEFAULT_TIMELINE_IT, "1.json"));
                }

                Lang.sleep(1000);
            }
        }).post(new Runnable() {
            @Override
            public void run() {
                MainFrameActivity.start(getActivity());
                finish();
            }
        }).go();

    }

    public void login(){
        WBDemoMainActivity.start(getActivity());
        finish();
    }

    @OnClick(R.id.btn_in)
    public void in(){
        MainFrameActivity.start(getActivity());
        finish();
    }
}
