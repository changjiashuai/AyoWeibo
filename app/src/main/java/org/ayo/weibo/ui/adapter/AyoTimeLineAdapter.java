package org.ayo.weibo.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ayo.http.R;
import org.ayo.lang.JsonUtils;
import org.ayo.lang.Lang;
import org.ayo.view.layout.FlowLayout;
import org.ayo.view.recycler.adapter.AyoViewHolder;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.App;
import org.ayo.weibo.model.timeline.AyoTimeline;
import org.ayo.weibo.ui.photo.AyoGalleryActivity;
import org.ayo.weibo.ui.photo.AyoGalleryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class AyoTimeLineAdapter extends SimpleRecyclerAdapter<AyoTimeline> {

    public AyoTimeLineAdapter(Context context, List<AyoTimeline> list) {
        super(context, list);
    }

    @Override
    protected AyoViewHolder newView(ViewGroup viewGroup, int viewType) {
        View v = View.inflate(App.app, R.layout.item_timeline, null);
        AyoViewHolder holder = new AyoViewHolder(v);
        return holder;
    }

    @Override
    protected void bindView(AyoViewHolder holder, final AyoTimeline bean, int position) {

        Log.e("iiiiii--thumbnail_pic", JsonUtils.toJson(bean));

        final String text = bean.text;

        TextView tv_content = (TextView) holder.findViewById(R.id.tv_content);
        SimpleDraweeView iv_user_logo = (SimpleDraweeView) holder.findViewById(R.id.iv_user_logo);
        TextView tv_user_name = (TextView) holder.findViewById(R.id.tv_user_name);
        TextView tv_info = (TextView) holder.findViewById(R.id.tv_info);

        tv_content.setText(bean.text);
        String info = bean.created_at + " " + "来自 " + bean.source;
        Spanned infoSpan = Html.fromHtml(info);
        tv_info.setText(infoSpan);

        if(bean.user != null){
            tv_user_name.setText(bean.user.name);
            iv_user_logo.setImageURI(parse(bean.user.avatar_large));
        }

        ///-----------------处理图片，视频----------------------//
        FrameLayout fl_image_container = (FrameLayout) holder.findViewById(R.id.fl_image_contaner);
        FlowLayout fl_flowlayout = (FlowLayout) holder.findViewById(R.id.fl_flowlayout);
        if(Lang.isNotEmpty(bean.urls)){
            fl_image_container.setVisibility(View.VISIBLE);
            if(bean.type == 1){
                //文+图，最多9张
                TimeLineImageFlowAdapter flowAdapter = new TimeLineImageFlowAdapter(mContext, bean.urls);
                fl_flowlayout.setAdapter(flowAdapter);

            }else if(bean.type == 2){
                //文+图，只有两张，0是封面图， 1是url地址
            }
        }else{
            //没有图片，视频
            fl_image_container.setVisibility(View.GONE);
        }
        ///---------------------图片视频 over----------------------//

        holder.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fl_flowlayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(bean.urls != null){
                    List<ImageInfo> images = new ArrayList<ImageInfo>();
                    for(String url: bean.urls){
                        ImageInfo ii = new ImageInfo(url);
                        images.add(ii);
                    }
                    AyoGalleryActivity.start(mContext, images);

                }
            }
        });


    }

    public static Uri parse(String url) {
        if (TextUtils.isEmpty(url))
            return null;
        return Uri.parse(url);
    }


    public static class ImageInfo implements AyoGalleryFragment.IImageInfo{

        private String url;

        public ImageInfo(String url) {
            this.url = url;
        }

        @Override
        public String getUri() {
            return url;
        }

        @Override
        public String getLocalUri() {
            return "";
        }
    }
}
