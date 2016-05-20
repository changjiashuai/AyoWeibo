package com.ayoview.sample.ultra.pulltorefresh.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ayoview.sample.ultra.pulltorefresh.LocalDisplay;
import com.ayoview.sample.ultra.pulltorefresh.TitleBaseFragment;
import com.cowthan.sample.R;

import org.ayo.imageloader.ImageLoaderCallback;
import org.ayo.imageloader.VanGogh;
import org.ayo.notify.Toaster;
import org.ayo.view.pullrefresh.PtrFrameLayout;
import org.ayo.view.pullrefresh.PtrHandler;
import org.ayo.view.pullrefresh.header.MaterialHeader;

public class MaterialStyleFragment extends TitleBaseFragment {

    private String mUrl = "http://img5.duitang.com/uploads/blog/201407/17/20140717113117_mUssJ.thumb.jpeg";
    private long mStartLoadingTime = -1;
    private boolean mImageHasLoaded = false;
    protected PtrFrameLayout mPtrFrameLayout;

    @Override
    public void onEnter(Object data) {
        if (data != null && data instanceof String) {
            mUrl = (String) data;
        }
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_materail_style, null);
        setHeaderTitle(R.string.ptr_demo_material_style);

        final ImageView imageView = (ImageView) view.findViewById(R.id.material_style_image_view);

        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.material_style_ptr_frame);

        // header
        final MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(false);
            }
        }, 100);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                if (mImageHasLoaded) {
                    long delay = (long) (1000 + Math.random() * 2000);
                    delay = Math.max(0, delay);
                    delay = 0;
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
                            long delay = Math.max(0, 1000 - (System.currentTimeMillis() - mStartLoadingTime));
                            delay = 0;
                            mPtrFrameLayout.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccess) {
                                        Toaster.toastShort("这有行代码略过了，TransitionDrawable");
                                        //TransitionDrawable w1 = new TransitionDrawable(new Drawable[]{new ColorDrawable(Color.WHITE), (Drawable) bitmapDrawable});
                                        //imageView.setImageDrawable(w1);
                                        //w1.startTransition(200);
                                    }
                                    mPtrFrameLayout.refreshComplete();
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
