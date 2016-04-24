package org.ayo.weibo.ui.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ayo.Display;
import org.ayo.app.AyoViewLib;
import org.ayo.http.R;
import org.ayo.imageloader.Flesco;
import org.ayo.view.layout.FlowLayout;

import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class TimeLineImageFlowAdapter extends FlowLayout.BaseFlowAdapter<String> {

    public TimeLineImageFlowAdapter(Context c, List<String> list) {
        super(c, list);
    }

    @Override
    protected View getView(Context context, int position, String uri) {
        View v = View.inflate(context, R.layout.item_timeline_image, null);

        SimpleDraweeView iv_image = (SimpleDraweeView) v.findViewById(R.id.iv_image);

        int w = 100;
        int h = 100;
        int maxW = Display.screenWidth - Display.dip2px(30);

        if(list.size() == 1){
            w = maxW - Display.dip2px(2);
            h = w*3/4;
        }else if(list.size() == 2){
            w = maxW / 2 - Display.dip2px(4);
            h = w;
        }else if(list.size() == 3){
            w = maxW / 3 - Display.dip2px(6);
            h = w;
        }else if(list.size() == 4){
            w = maxW / 2 - Display.dip2px(2);
            h = w;
        }else{
            w = maxW / 3 - Display.dip2px(6);
            h = w;
        }
        AyoViewLib.setViewSize(iv_image, w, h);
        AyoViewLib.setViewSize(v, w + Display.dip2px(2), h + Display.dip2px(2));

        Flesco.setImageUri(iv_image, uri);
        return v;
    }
}
