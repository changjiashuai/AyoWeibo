package org.ayo.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2016/2/18.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public MyViewHolder(View itemView) {
        super(itemView);
        viewHolder = new SparseArray<View>();
		view = itemView;
    }


		private SparseArray<View> viewHolder;
		private View view;

		public View findViewByID(int id, View view) {
			View holdedView = viewHolder.get(id);
			if (holdedView == null) {
				holdedView = view.findViewById(id);
				viewHolder.put(id, holdedView);
			}
			return holdedView;
		}

		public View findViewById(int id){
			if(view != null){
				return findViewByID(id, view);
			}else{
				return null;
			}
		}

		public static MyViewHolder getViewHolder(View view) {
			Object viewTag = view.getTag();
			if (viewTag != null && viewTag instanceof MyViewHolder) {
				return (MyViewHolder) viewTag;
			} else {
				viewTag = new MyViewHolder(view);
				view.setTag(viewTag);
				return (MyViewHolder) viewTag;
			}
		}

}
