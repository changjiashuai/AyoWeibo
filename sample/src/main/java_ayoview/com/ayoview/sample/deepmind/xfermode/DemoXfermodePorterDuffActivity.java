package com.ayoview.sample.deepmind.xfermode;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.Display;
import org.ayo.app.AyoViewLib;

/**
 *
 */
public class DemoXfermodePorterDuffActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dm_xfermode_porterduff_ac_main);

		final LinearLayout ll_root = findViewById(R.id.ll_root);

		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		View view_1 = findViewById(R.id.view_1);
		AyoViewLib.setViewSize(view_1, Display.screenWidth, Display.screenHeight);

		View view_2 = findViewById(R.id.view_2);
		AyoViewLib.setViewSize(view_2, Display.screenWidth, Display.screenHeight);

		View view_3 = findViewById(R.id.view_3);
		AyoViewLib.setViewSize(view_3, Display.screenWidth, Display.screenHeight);

		View view_4 = findViewById(R.id.view_4);
		AyoViewLib.setViewSize(view_4, Display.screenWidth, Display.screenHeight);

		View view_5 = findViewById(R.id.view_5);
		AyoViewLib.setViewSize(view_5, Display.screenWidth, Display.screenHeight);

		View view_6 = findViewById(R.id.view_6);
		AyoViewLib.setViewSize(view_6, Display.screenWidth, Display.screenHeight);
	}
}
