package com.ayoview.sample.deepmind2;

import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseDrawerLayoutActivity;
import com.cowthan.sample.R;

import org.ayo.notify.Toaster;

/**
 *
 */
public class PaintDemoMenuActivity extends BaseDrawerLayoutActivity {


    @Override
    protected int[] getLayoutId() {
        return new int[]{
                R.layout.ac_tmpl_drawerlayout_content,
                R.layout.ac_tmpl_drawerlayout_drawer
        };
    }

    @Override
    protected void initContentView(View viewContent) {
        Button btn_switch = (Button) viewContent.findViewById(R.id.btn_switch);
        Button btn_enable = (Button) viewContent.findViewById(R.id.btn_enable);

        btn_switch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getDrawerLayoutManager().toggle();
            }
        });

        btn_enable.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //drawerLayoutManager.lockOpen();
            }
        });
    }

    @Override
    protected void initDrawerView(View viewDrawer) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        Toaster.toastShort("onDrawerOpened");
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        Toaster.toastShort("onDrawerClosed");
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        //Toaster.toastShort("onDrawerStateChanged--" + newState);
    }
}
