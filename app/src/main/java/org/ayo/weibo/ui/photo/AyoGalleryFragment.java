package org.ayo.weibo.ui.photo;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import org.ayo.app.common.AyoFragment;
import org.ayo.http.R;
import org.ayo.notify.Toaster;
import org.ayo.view.photo.PhotoDraweeView;

import java.util.List;


public class AyoGalleryFragment extends AyoFragment {

    public interface IImageInfo{
        String getUri();
        String getLocalUri();
    }

    private List<IImageInfo> images;

    public void setImages(List<IImageInfo> images){
        this.images = images;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ayo_frag_gallery;
    }

    int currentPosition = 1;

    @Override
    protected void onCreateView(View root) {
        MultiTouchViewPager viewPager = (MultiTouchViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new DraweePagerAdapter(getActivity(), images));

        final TextView page_number = findViewById(R.id.page_number);
        page_number.setText(currentPosition + "/" + images.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position + 1;
                page_number.setText(currentPosition + "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ImageView download = findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toaster.toastShort("下载");
            }
        });
    }

    public class DraweePagerAdapter extends PagerAdapter {

        private List<IImageInfo> images;
        private Activity mActivity;

        public DraweePagerAdapter(Activity mActivity, List<IImageInfo> images) {
            this.images = images;
            this.mActivity = mActivity;
        }

        @Override public int getCount() {
            return images == null ? 0 : images.size();
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override public Object instantiateItem(ViewGroup viewGroup, int position) {

            IImageInfo bean = images.get(position);
            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(Uri.parse(bean.getUri()));
            controller.setOldController(photoDraweeView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());

                    if(animatable != null){
                        animatable.start();
                    }
                }
            });
            photoDraweeView.setController(controller.build());

            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return photoDraweeView;
        }
    }
}
