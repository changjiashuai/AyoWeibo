package org.ayo.weibo.ui.prompt;

import android.app.Activity;

import org.ayo.notify.Toaster;
import org.ayo.notify.actionsheet.ActionSheetDialog;
import org.ayo.notify.actionsheet.ActionSheetUtils;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Poper {

    /**
     *  新建，其实就是拍个照，录个视频
     */
    public static void showCreateSheet(Activity activity) {

        ActionSheetUtils.showActionSheet(activity, new String[]{"拍照", "拍美化照", "录视频", "录微视频"}, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Toaster.toastShort(""+which);
            }
        });

    }
}
