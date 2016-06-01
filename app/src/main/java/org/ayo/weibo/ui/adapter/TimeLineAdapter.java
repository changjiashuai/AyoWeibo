package org.ayo.weibo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ayo.http.R;
import org.ayo.lang.JsonUtils;
import org.ayo.view.recycler.adapter.AyoViewHolder;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.App;
import org.ayo.weibo.model.Timeline;
import org.ayo.weibo.ui.activity.TimeLineDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class TimeLineAdapter extends SimpleRecyclerAdapter<Timeline> {

    public TimeLineAdapter(Context context, List<Timeline> list) {
        super(context, list);
    }

    @Override
    protected AyoViewHolder newView(ViewGroup viewGroup, int viewType) {
        View v = View.inflate(App.app, R.layout.item_timeline, null);
        AyoViewHolder holder = new AyoViewHolder(v);
        return holder;
    }

    @Override
    protected void bindView(AyoViewHolder holder, Timeline bean, int position) {

        Log.e("iiiiii--thumbnail_pic", JsonUtils.toJson(bean));
        if(bean.retweeted_status != null){
            //转发的，retweeted_status里才是被转发的原微博
            bean = bean.retweeted_status;
        }

        final String text = bean.text;

        TextView tv_content = (TextView) holder.findViewById(R.id.tv_content);
        SimpleDraweeView iv_user_logo = (SimpleDraweeView) holder.findViewById(R.id.iv_user_logo);
        TextView tv_user_name = (TextView) holder.findViewById(R.id.tv_user_name);
        TextView tv_info = (TextView) holder.findViewById(R.id.tv_info);

        if(hasShowableUrl(text)){
            bean.text = "可点击观看（多图或视频）---\n" + bean.text;
        }
        tv_content.setText(bean.text);
        String info = bean.created_at + " " + "来自 " + bean.source;
        Spanned infoSpan = Html.fromHtml(info);
        tv_info.setText(infoSpan);

        if(bean.user != null){
            tv_user_name.setText(bean.user.name);
            iv_user_logo.setImageURI(parse(bean.user.avatar_large));
        }


        holder.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开放api不直接返回多图的全部url（只返回第一个），也不会返回视频地址（只会返回网页地址）
                //如果是多图，视频的微博，会在text的最后加一个链接：http://t.cn/Rq95i8f
                //t.cn是固定的，后面是7个字符
                if(hasShowableUrl(text)){
                    String url = getShowableUrl(text);
                    TimeLineDetailActivity.start((Activity) mContext, url);
                }

            }
        });

    }

    public static Uri parse(String url) {
        if (TextUtils.isEmpty(url))
            return null;
        return Uri.parse(url);
    }

    public static boolean hasShowableUrl(String text){
        String sampleUrl = "http://t.cn/Rq95i8f";
        if(text.trim().length() >= sampleUrl.length()){
            String maybeUrl = text.trim().substring(text.trim().length() - sampleUrl.length());
            if(maybeUrl.startsWith("http://t.cn")){
                return true;
            }
        }
        return false;
    }

    public static String getShowableUrl(String text){
        String sampleUrl = "http://t.cn/Rq95i8f";
        if(text.trim().length() >= sampleUrl.length()){
            String maybeUrl = text.trim().substring(text.trim().length() - sampleUrl.length());
            if(maybeUrl.startsWith("http://t.cn")){
                return maybeUrl;
            }
        }
        return "";
    }
}
