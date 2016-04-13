package sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.FrameLayout;

import org.ayo.http.R;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WebViewActivity extends AppCompatActivity {

    public static void start(Context c, String id){
        Intent i = new Intent(c, WebViewActivity.class);
        i.putExtra("id", id);
        c.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_webview);

        FrameLayout fl_root = (FrameLayout) findViewById(R.id.fl_root);
        final BaseWebViewFragment frag = new BaseWebViewFragment() {
            @Override
            public void setJsInfomation(WebView webview) {

            }
        };
        getSupportFragmentManager().beginTransaction().replace(fl_root.getId(), frag).commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String url = "http://www.tngou.net/tnfs/show/" + getIntent().getStringExtra("id");
                frag.loadUrl(url, null);
            }
        }, 1000);

    }
}
