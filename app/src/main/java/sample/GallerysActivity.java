package sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ayo.http.R;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.utils.HttpProblem;

import java.util.ArrayList;
import java.util.List;

import sample.http.Httpper;
import sample.model.Gallery;

/**
 * Created by Administrator on 2016/4/11.
 */
public class GallerysActivity extends AppCompatActivity {

    public static void start(Context c, String classId){
        Intent i = new Intent(c, GallerysActivity.class);
        i.putExtra("id", classId);
        c.startActivity(i);
    }
    ListView lv_list;

    private int pageNow = 1;
    private boolean isLoadMore = false;
    GalleryAdapter mAdapter;
    List<Gallery> list;
    Button btn_more;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_gallerys);

        lv_list = (ListView) findViewById(R.id.lv_list);
        mAdapter = new GalleryAdapter(null);
        lv_list.setAdapter(mAdapter);
        btn_more = (Button) findViewById(R.id.btn_more);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNow++;
                isLoadMore = true;
                loadData();
            }
        });

        loadData();

//        Utils.setOnScrollListenerOnListView(lv_list, new Utils.OnListViewStatusChangedListener(){
//
//            @Override
//            public void onTopReached() {
//
//            }
//
//            @Override
//            public void onBottomReached() {
//                pageNow++;
//                isLoadMore = true;
//                loadData();
//            }
//
//            @Override
//            public void onItemVisibilityChanged(int startPosition, int endPostion) {
//
//            }
//        });
    }

    private void loadData(){
        Httpper.getGalleryByClass("获取图片列表", getIntent().getStringExtra("id"), pageNow, new BaseHttpCallback<List<Gallery>>() {
            @Override
            public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, List<Gallery> galleryClasses) {

                if(isSuccess){
                    if(galleryClasses == null || galleryClasses.size() == 0){
                        Toast.makeText(GallerysActivity.this, "没数据了", Toast.LENGTH_SHORT).show();
                    }else{
                        if(isLoadMore){
                            if(list == null || list.size() == 0){
                                list = galleryClasses;
                            }else{
                                list.addAll(galleryClasses);
                            }
                            mAdapter.notifyDataSetChanged(list);
                        }else{
                            list = galleryClasses;
                            mAdapter.notifyDataSetChanged(list);
                        }
                    }

                    btn_more.setText("加载第X页".replace("X", (pageNow + 1) + ""));

                }else{
                    Log.i("http", "结束2。。。。。");
                    Toast.makeText(App.app, resp.getFailMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class GalleryAdapter extends BaseAdapter{

        private List<Gallery> list;

        private GalleryAdapter(List<Gallery> list){
            this.list = list;
            if(this.list == null) this.list = new ArrayList<Gallery>();
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

            final Gallery bean = list.get(i);
            tv_title.setText(bean.title + "(" + bean.size + ")" + "http://www.tngou.net/tnfs/show/" + bean.id);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.start(GallerysActivity.this, bean.id + "");
                }
            });

            return v;
        }

        public void notifyDataSetChanged(List<Gallery> list) {
            this.list = list;
            this.notifyDataSetChanged();
        }
    }
}
