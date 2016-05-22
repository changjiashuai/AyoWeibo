package com.ayoview.sample.ultra.pulltorefresh.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ayoview.sample.ultra.pulltorefresh.TitleBaseFragment;
import com.ayoview.sample.ultra.pulltorefresh.data.Item;
import com.ayoview.sample.ultra.pulltorefresh.data.JsonData;
import com.cowthan.sample.R;

import org.ayo.app.SBSimpleAdapter;
import org.ayo.imageloader.VanGogh;
import org.ayo.view.pullrefresh.PtrDefaultHandler;
import org.ayo.view.pullrefresh.PtrFrameLayout;

import java.util.List;

public class WithLongPressFragment extends TitleBaseFragment {


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_with_long_press, null);

        setHeaderTitle(R.string.ptr_demo_block_with_long_press);

        final PtrFrameLayout ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.with_long_press_list_view_frame);

        ListView listView = (ListView) view.findViewById(R.id.with_long_press_list_view);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Long Pressed: " + id, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        final MyAdapter adapter = new MyAdapter(getActivity(), JsonData.getList());
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged(JsonData.getList());
                        ptrFrameLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh();
            }
        }, 100);
        listView.setAdapter(adapter);
        return view;
    }

     class MyAdapter extends SBSimpleAdapter<Item>{

         public MyAdapter(Activity context, List<Item> list) {
             super(context, list);
         }

         @Override
         protected int getLayoutId() {
             return R.layout.with_long_press_list_view_item;
         }

         @Override
         public boolean isConvertViewUseable(View convertView, int position) {
             return true;
         }

         @Override
         public void fillHolder(ViewHolder holder, View convertView, Item bean, int position) {
             ImageView mImageView = (ImageView) holder.findViewById(R.id.with_long_press_list_image);
             VanGogh.paper(mImageView).paintMiddleImage(bean.pic, null, null);
         }
     }

}
