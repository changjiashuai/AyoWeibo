package sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.WBDemoMainActivity;

import org.ayo.http.R;

import java.util.ArrayList;
import java.util.List;

import sample.sina.TimeLineActivity;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        Log.i("dddd", "MainActivity");
        final ListView lv_list = (ListView) findViewById(R.id.lv_list);
        lv_list.setAdapter(new MainMenuAdapter());

    }

    class MainMenuAdapter extends BaseAdapter {

        private List<String> list;

        private MainMenuAdapter(){
            this.list = new ArrayList<String>();
            list.add("天狗云api测试--美女");
            list.add("新浪微博api测试--主要为了授权");
            list.add("新浪微博api测试--自己实现api");
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = View.inflate(App.app, R.layout.item_gallery_class, null);
            TextView tv_title = (TextView) v.findViewById(R.id.tv_title);

            final String bean = list.get(i);
            tv_title.setText(bean);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.equals("天狗云api测试--美女")) {
                        GalleryClassesActivity.start(MainActivity.this);
                    } else if (bean.equals("新浪微博api测试--主要为了授权")) {
                        WBDemoMainActivity.start(MainActivity.this);
                    }else if (bean.equals("新浪微博api测试--自己实现api")) {
                        TimeLineActivity.start(MainActivity.this);
                    }
                }
            });

            return v;
        }
    }
}
