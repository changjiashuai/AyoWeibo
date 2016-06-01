package org.ayo.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.ayo.view.recycler.adapter.AyoViewHolder;

import java.util.List;

/**
 * RecyclerView的adapter封装，封装了通用ViewHolder
 */
public abstract class SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<AyoViewHolder>  {

    protected List<T> list;
    protected Context mContext;

    public SimpleRecyclerAdapter(Context context, List<T> list){
        this.mContext = context;
        this.list = list;
    }

    public void notifyDataSetChanged(List<T> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    protected abstract AyoViewHolder newView(ViewGroup viewGroup, int viewType);
    protected abstract void bindView(AyoViewHolder holder, T bean, int position);


    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     *
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     *                 <code>position</code>. Type codes need not be contiguous.
     */
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public AyoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return newView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(AyoViewHolder holder, int position) {
        bindView(holder, list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
