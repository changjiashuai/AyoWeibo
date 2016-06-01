package org.ayo.weibo.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.ayo.http.R;
import org.ayo.view.recycler.adapter.AyoViewHolder;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.model.qa.QaSimple;

import java.util.List;

/**
 */
public class QaAdapter extends SimpleRecyclerAdapter<QaSimple> {

    public QaAdapter(Context context, List<QaSimple> list) {
        super(context, list);
    }

    @Override
    protected AyoViewHolder newView(ViewGroup viewGroup, int viewType) {
        View v = View.inflate(mContext, R.layout.item_qa, null);
        AyoViewHolder h = new AyoViewHolder(v);
        return h;
    }

    @Override
    protected void bindView(AyoViewHolder holder, QaSimple bean, int position) {

    }
}
