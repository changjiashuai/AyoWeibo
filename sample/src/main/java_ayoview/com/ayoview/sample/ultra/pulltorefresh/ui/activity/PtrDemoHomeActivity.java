package com.ayoview.sample.ultra.pulltorefresh.ui.activity;

import android.os.Bundle;

import com.ayoview.sample.ultra.pulltorefresh.CubeFragmentActivity;
import com.ayoview.sample.ultra.pulltorefresh.ui.PtrDemoHomeFragment;
import com.cowthan.sample.R;

public class PtrDemoHomeActivity extends CubeFragmentActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ultra_activity_main);
        pushFragmentToBackStack(PtrDemoHomeFragment.class, null);
    }

    @Override
    protected String getCloseWarning() {
        return null;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}