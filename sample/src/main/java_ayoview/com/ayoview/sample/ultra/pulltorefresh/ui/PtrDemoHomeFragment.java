package com.ayoview.sample.ultra.pulltorefresh.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ayoview.sample.ultra.pulltorefresh.BlockMenuFragment;
import com.ayoview.sample.ultra.pulltorefresh.LocalDisplay;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.AutoRefresh;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.EvenOnlyATextView;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.HideHeader;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.KeepHeader;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.PullToRefresh;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.ReleaseToRefresh;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.WithGridView;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.WithListView;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.WithListViewAndEmptyView;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.WithScrollView;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.WithTextViewInFrameLayoutFragment;
import com.ayoview.sample.ultra.pulltorefresh.ui.classic.WithWebView;
import com.ayoview.sample.ultra.pulltorefresh.ui.storehouse.StoreHouseUsingPointList;
import com.ayoview.sample.ultra.pulltorefresh.ui.storehouse.StoreHouseUsingString;
import com.ayoview.sample.ultra.pulltorefresh.ui.storehouse.StoreHouseUsingStringArray;
import com.cowthan.sample.R;

import org.ayo.view.pullrefresh.PtrDefaultHandler;
import org.ayo.view.pullrefresh.PtrFrameLayout;
import org.ayo.view.pullrefresh.PtrHandler;
import org.ayo.view.pullrefresh.header.StoreHouseHeader;

import java.util.ArrayList;

public class PtrDemoHomeFragment extends BlockMenuFragment {

    @Override
    protected void addItemInfo(ArrayList<MenuItemInfo> itemInfos) {

        // GridView
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_grid_view, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(WithGridView.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_frame_layout, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(WithTextViewInFrameLayoutFragment.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_only_text_view, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(EvenOnlyATextView.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_list_view, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(WithListView.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_web_view, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(WithWebView.class, null);
            }
        }));

        itemInfos.add(newItemInfo(R.string.ptr_demo_block_with_list_view_and_empty_view, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(WithListViewAndEmptyView.class, null);
            }
        }));

        itemInfos.add(newItemInfo(R.string.ptr_demo_block_scroll_view, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(WithScrollView.class, null);
            }
        }));
        itemInfos.add(null);
        itemInfos.add(null);

        itemInfos.add(newItemInfo(R.string.ptr_demo_block_keep_header, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(KeepHeader.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_hide_header, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(HideHeader.class, null);
            }
        }));

        itemInfos.add(newItemInfo(R.string.ptr_demo_block_release_to_refresh, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(ReleaseToRefresh.class, null);
            }
        }));

        itemInfos.add(newItemInfo(R.string.ptr_demo_block_pull_to_refresh, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(PullToRefresh.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_auto_fresh, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(AutoRefresh.class, null);
            }
        }));
        itemInfos.add(null);

        itemInfos.add(newItemInfo(R.string.ptr_demo_block_storehouse_header_using_string_array, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(StoreHouseUsingStringArray.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_storehouse_header_using_string, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(StoreHouseUsingString.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_storehouse_header_using_point_list, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(StoreHouseUsingPointList.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_material_style, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(MaterialStyleFragment.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_material_style_pin_content, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(MaterialStylePinContentFragment.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_with_long_press, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(WithLongPressFragment.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_block_with_view_pager, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), ViewPagerActivity.class);
//                startActivity(intent);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_rentals_style, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(RentalsStyleFragment.class, null);
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_enable_next_ptr_at_once, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(EnableNextPTRAtOnce.class, null);
            }
        }));

        itemInfos.add(newItemInfo(R.string.ptr_demo_placeholder, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_placeholder, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        }));
        itemInfos.add(newItemInfo(R.string.ptr_demo_placeholder, R.color.cube_mints_4d90fe, new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        }));
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.createView(inflater, container, savedInstanceState);
        view.setBackgroundColor(Color.parseColor("#333333"));

        final PtrFrameLayout ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.fragment_ptr_home_ptr_frame);
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        header.initWithString("Ultra PTR");

        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 1500);
            }
        });

//        BlockListView mBlockListView = (BlockListView) view.findViewById(R.id.fragment_block_menu_block_list);
//        mBlockListView.setAdapter(mBlockListAdapter);
//        mBlockListAdapter.displayBlocks(mItemInfos);


        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ptr_home;
    }

    @Override
    protected void setupViews(View view) {
        super.setupViews(view);
        setHeaderTitle("Ultra Pull To Refresh");
    }
}
