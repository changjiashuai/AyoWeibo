package org.ayo.weibo.ui.adapter.tops;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ayo.http.R;
import org.ayo.lang.Lang;
import org.ayo.notify.Toaster;
import org.ayo.view.recycler.MyViewHolder;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.model.top.Top;
import org.ayo.weibo.ui.fragment.TopDetailActivity;

import java.util.List;

/**
 */
public class TopAdapter extends SimpleRecyclerAdapter<Top>{

    public TopAdapter(Context context, List<Top> list) {
        super(context, list);
    }

    @Override
    protected MyViewHolder newView(ViewGroup viewGroup, int viewType) {
        View v = View.inflate(mContext, R.layout.item_top, null);
        MyViewHolder h = new MyViewHolder(v);
        return h;
    }

    @Override
    protected void bindView(MyViewHolder holder, final Top bean, int position) {
        TextView tv_title = (TextView) holder.findViewById(R.id.tv_title);
        tv_title.setText(bean.title);

        holder.findViewById(R.id.root).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(Lang.isEmpty(bean.fromurl)){
                    Toaster.toastShort("没有详情url");
                }else{
                    TopDetailActivity.start((Activity) mContext, bean.fromurl);
                }
            }
        });

    }
}
