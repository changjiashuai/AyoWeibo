package org.ayo.weibo.ui.fragment.main;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.ayo.app.common.AyoFragment;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.http.R;

/**
 * 个人中心
 */
public class ProfileFragment extends AyoFragment implements ISubPage, View.OnClickListener{

    private Activity ctx;

    @Override
    protected int getLayoutId() {
        return R.layout.wb_frag_profile;
    }

    @Override
    protected void onCreateView(View root) {
        ctx = this.getActivity();
        TextView txt_pengyouquan = findViewById(R.id.txt_pengyouquan); //朋友圈
        TextView txt_friend = findViewById(R.id.txt_friend); //通讯录
        TextView txt_saoyisao = findViewById(R.id.txt_saoyisao);//扫一扫
        TextView txt_yaoyiyao = findViewById(R.id.txt_yaoyiyao);//摇一摇
        TextView txt_nearby = findViewById(R.id.txt_nearby);//附件的人
        TextView txt_piaoliuping = findViewById(R.id.txt_piaoliuping);//漂流瓶
        TextView txt_vedio = findViewById(R.id.txt_vedio);//录视频
        TextView txt_game = findViewById(R.id.txt_game);//游戏

        txt_pengyouquan.setOnClickListener(this);
        txt_friend.setOnClickListener(this);
        txt_saoyisao.setOnClickListener(this);
        txt_yaoyiyao.setOnClickListener(this);
        txt_nearby.setOnClickListener(this);
        txt_piaoliuping.setOnClickListener(this);
        txt_vedio.setOnClickListener(this);
        txt_game.setOnClickListener(this);
    }

    @Override
    public void onPageVisibleChange(boolean isVisible) {

    }

    @Override
    public void setIsTheFirstPage(boolean isTheFirstPage) {

    }

    @Override
    public void onClick(View view) {

    }
}
