package com.cowthan.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.cowthan.sample.Utils;
import com.cowthan.sample.menu.Leaf;

import org.ayo.app.base.ActivityAttacher;

/**
 *
 * Created by Administrator on 2016/3/28.
 */
public abstract class BaseDemoMenuActivity extends BaseActivity {

    private LinearLayout ll_root;

    protected abstract Leaf[] getMenus();

    public void onClicked(String btnText){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_button_demos);

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        for(Leaf leaf: getMenus()){
            addButton(leaf);
        }
    }

    private void addButton(final Leaf leaf){
        Button btn = new Button(getActivity());
        btn.setText(leaf.name);
        btn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btn.setBackgroundResource(R.drawable.sel_menu3);
        btn.setTextSize(15);
        btn.setPadding(20, 0, 20, 0);
        btn.setTextColor(Color.WHITE);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(getActivity(), 40));
        lp.gravity = Gravity.CENTER;
        lp.topMargin = Utils.dip2px(getActivity(), 5);
        lp.bottomMargin = Utils.dip2px(getActivity(), 5);
        lp.leftMargin = Utils.dip2px(getActivity(), 5);
        lp.rightMargin = Utils.dip2px(getActivity(), 5);
        ll_root.addView(btn, lp);

        if(leaf.attacherClass == null){
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onClicked(leaf.name);
                }
            });
        }else{
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(leaf.attacherClass == null){
                        Toast.makeText(getActivity(), "还没写", Toast.LENGTH_SHORT).show();
                    }else{
                        ActivityAttacher.startActivity(getActivity(), leaf.attacherClass);
                    }
                }
            });
        }


    }
}
