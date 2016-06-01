package org.ayo.view.recycler;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.ayo.app.adapter.AdapterDelegate;
import org.ayo.app.adapter.AdapterDelegatesManager;
import org.ayo.view.recycler.adapter.AyoViewHolder;

import java.util.List;


/**
 * 通用的adapter
 */
public class AyoAdapter<T>  extends RecyclerView.Adapter<AyoViewHolder> {

    private AdapterDelegatesManager<List<T>> delegatesManager;
    private List<T> items;

    public AyoAdapter(Activity activity, List<T> items, List<AdapterDelegate<List<T>>> templates) {
        this.items = items;

        // Delegates
        delegatesManager = new AdapterDelegatesManager<>();
        for(AdapterDelegate<List<T>> template: templates){
            delegatesManager.addDelegate(template);
        }
    }

    public void notifyDataSetChanged(List<T> list){
        this.items = list;
        this.notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override public AyoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(AyoViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override public int getItemCount() {
        return items == null ? 0 : items.size();
    }

}

