package sample.sina;

import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ayo.http.R;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.utils.HttpProblem;

import java.util.ArrayList;
import java.util.List;

import sample.App;
import sample.BaseFragment;
import sample.sina.api.WeiboApi;
import sample.sina.model.ResponseTimeline;
import sample.sina.model.Timeline;

/**
 * Created by Administrator on 2016/4/11.
 */
public class TimelineListFragment2 extends BaseFragment {


    ListView lv_list;

    private int pageNow = 1;
    private boolean isLoadMore = false;
    TimeLineAdapter mAdapter;
    List<Timeline> list;
    Button btn_more;

    private String timeLineType = "";

    public void setTimeLineType(String timeLineType) {
        this.timeLineType = timeLineType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_tmpl_timeline_list;
    }

    @Override
    protected void onCreateView(View root) {
        lv_list = (ListView) findViewById(R.id.lv_list);
        mAdapter = new TimeLineAdapter(null);
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
    }

    private void loadData() {

        WeiboApi.getPublicTimelines("公共微博", new BaseHttpCallback<ResponseTimeline>() {
            @Override
            public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, ResponseTimeline responseTimeline) {
                if (isSuccess) {
                    if (responseTimeline.statuses == null || responseTimeline.statuses.size() == 0) {
                        Toast.makeText(getActivity(), "没数据了", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isLoadMore) {
                            if (list == null || list.size() == 0) {
                                list = responseTimeline.statuses;
                            } else {
                                list.addAll(responseTimeline.statuses);
                            }
                            mAdapter.notifyDataSetChanged(list);
                        } else {
                            list = responseTimeline.statuses;
                            mAdapter.notifyDataSetChanged(list);
                        }
                    }

                    btn_more.setText("加载第X页".replace("X", (pageNow + 1) + ""));
                } else {
                    Toast.makeText(App.app, resp.getFailMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    class TimeLineAdapter extends BaseAdapter {

        private List<Timeline> list;

        private TimeLineAdapter(List<Timeline> list) {
            this.list = list;
            if (this.list == null) this.list = new ArrayList<Timeline>();
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
            View v = View.inflate(App.app, R.layout.item_timeline, null);
            TextView tv_content = (TextView) v.findViewById(R.id.tv_content);
            SimpleDraweeView iv_user_logo = (SimpleDraweeView) v.findViewById(R.id.iv_user_logo);
            SimpleDraweeView iv_content = (SimpleDraweeView) v.findViewById(R.id.iv_content);
            TextView tv_user_name = (TextView) v.findViewById(R.id.tv_user_name);
            TextView tv_info = (TextView) v.findViewById(R.id.tv_info);

            final Timeline bean = list.get(i);
            tv_content.setText(bean.text);
            tv_user_name.setText(bean.user.name);

            String info = bean.created_at + " " + "来自 " + bean.source;
            Spanned infoSpan = Html.fromHtml(info);
            tv_info.setText(infoSpan);

            iv_user_logo.setImageURI(parse(bean.user.avatar_large));

            if(bean.hasImage()){
                iv_content.setVisibility(View.VISIBLE);
                iv_content.setImageURI(parse(bean.getImageUrl()));
                Log.e("iiiiii--thumbnail_pic", bean.thumbnail_pic);
                Log.e("iiiiii--bmiddle_pic", bean.bmiddle_pic);
                Log.e("iiiiii--original_pic", bean.original_pic);
                Log.e("iiiiii--pic_ids", bean.pic_ids + "");
            }else{
                iv_content.setVisibility(View.GONE);
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //WebViewActivity.start(getActivity()s, bean.id + "");
                }
            });

            
            return v;
        }

        public void notifyDataSetChanged(List<Timeline> list) {
            this.list = list;
            this.notifyDataSetChanged();
        }
    }

    public static Uri parse(String url) {
        if (TextUtils.isEmpty(url))
            return null;
        return Uri.parse(url);
    }
}
