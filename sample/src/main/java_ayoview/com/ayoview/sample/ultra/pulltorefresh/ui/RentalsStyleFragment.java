package com.ayoview.sample.ultra.pulltorefresh.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ayoview.sample.ultra.pulltorefresh.LocalDisplay;
import com.ayoview.sample.ultra.pulltorefresh.TitleBaseFragment;
import com.ayoview.sample.ultra.pulltorefresh.ui.header.RentalsSunHeaderView;
import com.cowthan.sample.R;

import org.ayo.imageloader.ImageLoaderCallback;
import org.ayo.imageloader.VanGogh;
import org.ayo.view.pullrefresh.PtrFrameLayout;
import org.ayo.view.pullrefresh.PtrHandler;

public class RentalsStyleFragment extends TitleBaseFragment {

    private String mUrl = "http://img4.duitang.com/uploads/blog/201407/07/20140707113856_hBf3R.thumb.jpeg";
    private long mStartLoadingTime = -1;
    private boolean mImageHasLoaded = false;

    @Override
    public void onEnter(Object data) {
        if (data != null && data instanceof String) {
            mUrl = (String) data;
        }
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_materail_style, null);
        setHeaderTitle(R.string.ptr_demo_rentals_style);

        final ImageView imageView = (ImageView) view.findViewById(R.id.material_style_image_view);

        final PtrFrameLayout frame = (PtrFrameLayout) view.findViewById(R.id.material_style_ptr_frame);

        // header
        final RentalsSunHeaderView header = new RentalsSunHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setUp(frame);

        frame.setLoadingMinTime(1000);
        frame.setDurationToCloseHeader(1500);
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);
        // frame.setPullToRefresh(true);
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.autoRefresh(true);
            }
        }, 100);

        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                if (mImageHasLoaded) {
                    long delay = 1500;
                    frame.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            frame.refreshComplete();
                        }
                    }, delay);
                } else {
                    mStartLoadingTime = System.currentTimeMillis();
                    VanGogh.paper(imageView).paintMiddleImage(mUrl, null, new ImageLoaderCallback() {
                        @Override
                        public void onLoading(String uri, int current, int total) {

                        }

                        @Override
                        public void onFinish(final boolean isSuccess, String uri, String savedPath, String failInfo) {
                            mImageHasLoaded = true;
                            long delay = 1500;
                            frame.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccess) {
                                        //Toaster.toastShort("这有行代码略过了，TransitionDrawable");
                                        //TransitionDrawable w1 = new TransitionDrawable(new Drawable[]{new ColorDrawable(Color.WHITE), (Drawable) bitmapDrawable});
                                        //imageView.setImageDrawable(w1);
                                        //w1.startTransition(200);
                                    }
                                    frame.refreshComplete();
                                }
                            }, delay);
                        }
                    });
                }
            }
        });

        return view;
    }
}
