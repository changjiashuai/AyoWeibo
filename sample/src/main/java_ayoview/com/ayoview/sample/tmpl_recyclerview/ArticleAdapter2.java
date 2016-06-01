package com.ayoview.sample.tmpl_recyclerview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayoview.sample.tmpl_listview.TmplBean;
import com.cowthan.sample.R;

import org.ayo.imageloader.VanGogh;
import org.ayo.view.recycler.adapter.AyoViewHolder;
import org.ayo.view.recycler.SimpleRecyclerAdapter;

import java.util.List;


/**
 * 文章列表适配器
 */
public class ArticleAdapter2 extends SimpleRecyclerAdapter<TmplBean> {


	public ArticleAdapter2(Activity context, List<TmplBean> list) {
		super(context, list);
		mContext = context;
	}


	@Override
	protected AyoViewHolder newView(ViewGroup viewGroup, int viewType) {
		View v = View.inflate(mContext, R.layout.item_tmpl_item_template, null);
		AyoViewHolder holder = new AyoViewHolder(v);

		return holder;
	}

	@Override
	protected void bindView(AyoViewHolder holder, TmplBean bean, int position) {
		TextView tv_title = (TextView) holder.findViewById(R.id.tv_title);
		ImageView iv_photo = (ImageView) holder.findViewById(R.id.iv_photo);

		tv_title.setText(bean.title);
		VanGogh.paper(iv_photo).paintSmallImage(bean.cover_url,null, null);
	}
}