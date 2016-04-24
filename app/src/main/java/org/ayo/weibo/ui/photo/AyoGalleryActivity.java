package org.ayo.weibo.ui.photo;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.app.base.BundleManager;
import org.ayo.app.base.SimpleBundle;
import org.ayo.app.common.FragmentContainerActivityAttacher;

import java.util.List;


public class AyoGalleryActivity extends FragmentContainerActivityAttacher {

    public static void start(Context c, List<? extends AyoGalleryFragment.IImageInfo> images){
        SimpleBundle bundle = BundleManager.getDefault().fetch();
        bundle.putExtra("images", images);
        ActivityAttacher.startActivity(c, AyoGalleryActivity.class, bundle, false, ActivityAttacher.LAUNCH_MODE_STANDARD);
    }

    private List<AyoGalleryFragment.IImageInfo> images;

    @Override
    protected void initFragment(FragmentManager fragmentManager, View root) {

        images = getBundle().getExtra("images");

        AyoGalleryFragment frag = new AyoGalleryFragment();
        frag.setImages(images);
        fragmentManager.beginTransaction().replace(root.getId(), frag).commit();
    }

}
