package com.ayoview.notifysample;

import android.content.DialogInterface;

import com.cowthan.sample.BaseDemoMenuActivity;
import com.cowthan.sample.R;
import com.cowthan.sample.menu.Leaf;

import org.ayo.notify.DialogUtils;
import org.ayo.notify.Toaster;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DialogDemoActivity extends BaseDemoMenuActivity {
    @Override
    protected Leaf[] getMenus() {
        Leaf[] leaves = {
                new Leaf("普通对话框", "", null),
                new Leaf("确定取消对话框", "", null),
                new Leaf("多按钮对话框", "", null),
                new Leaf("列表对话框", "", null),
                new Leaf("带Adapter的对话框", "", null),
                new Leaf("单选对话框", "", null),
                new Leaf("多选对话框", "", null),
                new Leaf("日期对话框", "", null),
                new Leaf("时间对话框", "", null),
                new Leaf("自定义对话框", "", null),
        };
        return leaves;
    }

    @Override
    public void onClicked(String btnText) {
        if(btnText.equals("普通对话框")){
            DialogUtils.alert(getActivity(), "This is title", R.drawable.ic_launcher, "show me a message ! ");
        }else if(btnText.equals("确定取消对话框")){
            DialogUtils.alert(getActivity(), "This is title", R.drawable.ic_launcher, "show me a message ! ",
                    "确定啊",
                    "",
                    "取消吧",
                    new DialogUtils.BaseOnDialogClicked() {

                        @Override
                        public void onPositiveClicked(DialogInterface dialog, int which) {
                            Toaster.toastShort("确定");
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegativeClicked(DialogInterface dialog, int which) {
                            Toaster.toastShort("取消");
                            dialog.dismiss();
                        }

                    }
            );
        }else if(btnText.equals("多按钮对话框")){
            DialogUtils.alert(getActivity(), "This is title", R.drawable.ic_launcher, "show me a message ! ",
                    "确定啊",
                    "再等会",
                    "取消吧",
                    new DialogUtils.BaseOnDialogClicked() {

                        @Override
                        public void onPositiveClicked(DialogInterface dialog, int which) {
                            Toaster.toastShort("确定");
                            dialog.dismiss();
                        }

                        @Override
                        public void onNeutralClicked(DialogInterface dialog, int which) {
                            Toaster.toastShort("再等等");
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegativeClicked(DialogInterface dialog, int which) {
                            Toaster.toastShort("取消");
                            dialog.dismiss();
                        }

                    }
            );
        }else if(btnText.equals("列表对话框")){
        }else if(btnText.equals("带Adapter的对话框")){
            Toaster.toastShort("列表样式，但item可以定制");
        }else if(btnText.equals("单选对话框")){
        }else if(btnText.equals("多选对话框")){

        }else if(btnText.equals("日期对话框")){

        }else if(btnText.equals("时间对话框")){

        }else if(btnText.equals("自定义对话框")){

        }
    }

    private String[] items = {
            "足球",
            "篮球",
            "皮球",
            "气球",
            "肉球",
    };

    private int choice = 1;  //单选对话框，默认选中第一个


}
