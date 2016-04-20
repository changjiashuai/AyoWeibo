package org.ayo.weibo.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sina.weibo.sdk.demo.WBDemoMainActivity;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.common.AyoSwipeBackActivityAttacher;
import org.ayo.http.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/20.
 */
public class LoginActivity extends AyoSwipeBackActivityAttacher {

    public static void start(Context c){
        ActivityAttacher.startActivity(c, LoginActivity.class, null, false, ActivityAttacher.LAUNCH_MODE_STANDARD);
    }

    @Bind(R.id.btn_login)
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_ac_login);
        ButterKnife.bind(this, findViewById(R.id.root));
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WBDemoMainActivity.start(getActivity());
            }
        });
    }

//    @OnClick(R.id.btn_login)
//    public void login(){
//        WBDemoMainActivity.start(getActivity());
//    }
}
