package sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ayo.http.R;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.utils.HttpProblem;

import java.util.ArrayList;
import java.util.List;

import sample.http.Httpper;
import sample.model.GalleryClass;

/**
 * Created by Administrator on 2016/4/11.
 */
public class GalleryClassesActivity extends AppCompatActivity {

    public static void start(Context c){
        Intent i = new Intent(c, GalleryClassesActivity.class);
        c.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_gallery_classes);

        final ListView lv_list = (ListView) findViewById(R.id.lv_list);
        lv_list.setAdapter(new GalleryClassAdapter(null));

        Log.i("http", "开始。。。。。");
        Httpper.getGalleryClassList("获取图片分类", new BaseHttpCallback<List<GalleryClass>>() {
            @Override
            public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, List<GalleryClass> galleryClasses) {

                if(isSuccess){
                    Log.i("http", "结束1。。。。。");
                    GalleryClassAdapter adapter = new GalleryClassAdapter(galleryClasses);
                    lv_list.setAdapter(adapter);
                }else{
                    Log.i("http", "结束2。。。。。");
                    Toast.makeText(App.app, resp.getFailMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class GalleryClassAdapter extends BaseAdapter{

        private List<GalleryClass> list;

        private GalleryClassAdapter(List<GalleryClass> list){
            this.list = list;
            if(this.list == null) this.list = new ArrayList<GalleryClass>();
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

            final GalleryClass bean = list.get(i);
            tv_title.setText(bean.title);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GallerysActivity.start(GalleryClassesActivity.this, bean.id + "");
                }
            });

            return v;
        }
    }
}
