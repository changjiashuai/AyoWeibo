package org.ayo.notify;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 原生对话框
 *
 * 要注意的是，不同的主题呈现的样式是不同的，且不同手机也有不同，特别是魅族手机，所以建议统一使用AppCompact主题
 *
 * support v7里也有个AlertDialog，建议用这个
 *
 * 使用Dialog时，应该确定Activity是存在的
 *
 * Created by Administrator on 2016/3/28.
 */
public class DialogUtils {

    public interface OnDialogClicked{
        void onItemClicked(DialogInterface dialog, int which);
        void onPositiveClicked(DialogInterface dialog, int which);
        void onNegativeClicked(DialogInterface dialog, int which);
        void onNeutralClicked(DialogInterface dialog, int which);
    }

    public static class BaseOnDialogClicked implements OnDialogClicked{

        @Override
        public void onItemClicked(DialogInterface dialog, int which) {
        }

        @Override
        public void onPositiveClicked(DialogInterface dialog, int which) {

        }

        @Override
        public void onNegativeClicked(DialogInterface dialog, int which) {

        }

        @Override
        public void onNeutralClicked(DialogInterface dialog, int which) {

        }
    }

    /**
     *
     * @param a 使用Dialog时，应该确定Activity是存在的
     * @param title 标题
     * @param msg 信息
     * @param icon 图标
     */
    public static void alert(Activity a, String title, int icon, String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(a);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(icon);
        builder.create();
        builder.show();
    }

//    /**
//     * 两个按钮的alert框
//     * @param a
//     * @param title
//     * @param msg
//     * @param icon
//     * @param positiveButton 确定按钮
//     * @param negativeButton 取消按钮
//     * @param onButtonClicked 点击回调
//     */
//    public static void alert(Activity a,
//                             String title,
//                             String msg,
//                             int icon,
//                             final String positiveButton,
//                             final String negativeButton,
//                             final OnButtonClicked onButtonClicked
//                             ){
//        AlertDialog.Builder builder2=new AlertDialog.Builder(a);
//        builder2.setTitle(title);
//        builder2.setMessage(msg);
//        builder2.setIcon(icon);
//        builder2.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(onButtonClicked != null) onButtonClicked.onClicked(dialog, positiveButton, which);
//            }
//        });
//        builder2.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
//            //负能量按钮 NegativeButton
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(onButtonClicked != null) onButtonClicked.onClicked(dialog, negativeButton, which);
//            }
//        });
//        builder2.create().show();
//    }

    /**
     * 带按钮alert框
     * @param a
     * @param title
     * @param msg
     * @param icon
     * @param positiveButton 确定
     * @param neutralButton 中间按钮
     * @param negativeButton 取消
     * @param onButtonClicked 点击回调
     */
    public static void alert(Activity a,
                             String title,
                             int icon,
                             String msg,
                             final String positiveButton,
                             final String neutralButton,
                             final String negativeButton,
                             final BaseOnDialogClicked onButtonClicked
    ){
        AlertDialog.Builder builder2 = processCommonFeature(a, title, icon, positiveButton, neutralButton, negativeButton, onButtonClicked);
        builder2.setMessage(msg);
        builder2.create().show();
    }

    private static AlertDialog.Builder processCommonFeature(Activity a,
                                                            String title,
                                                            int icon,
                                      final String positiveButton,
                                      final String neutralButton,
                                      final String negativeButton,
                                      final BaseOnDialogClicked onButtonClicked){

        AlertDialog.Builder builder2=new AlertDialog.Builder(a);
        if(title != null && !title.equals("")) builder2.setTitle(title);
        if(icon > 0) builder2.setIcon(icon);

        if(positiveButton != null && !positiveButton.equals("")){
            builder2.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(onButtonClicked != null) onButtonClicked.onPositiveClicked(dialog, which);
                }
            });
        }

        if(neutralButton != null && !neutralButton.equals("")){
            builder2.setNeutralButton(neutralButton, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(onButtonClicked != null) onButtonClicked.onNeutralClicked(dialog, which);
                }
            });
        }

        if(negativeButton != null && !negativeButton.equals("")){
            builder2.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                //负能量按钮 NegativeButton
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(onButtonClicked != null) onButtonClicked.onNegativeClicked(dialog, which);
                }
            });
        }
        return builder2;
    }


    /**
     * 列表对话框
     * @param a
     * @param data 列表显示内容
     * @param onButtonClicked
     */
    public static void alert(Activity a,
                             String title,
                             int icon,
                             final String[] data,
                             final String positiveButton,
                             final String neutralButton,
                             final String negativeButton,
                             final BaseOnDialogClicked onButtonClicked){
        if(data == null || data.length == 0) return;
        AlertDialog.Builder builder2 = processCommonFeature(a, title, icon, positiveButton, neutralButton, negativeButton, onButtonClicked);
        builder2.create().show();
        builder2.setItems(data, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(onButtonClicked != null) onButtonClicked.onItemClicked(dialog, which);
            }
        });
        builder2.create().show();
    }

    /**
     * 列表对话框
     * @param a
     * @param title
     * @param icon
     * @param data
     * @param onButtonClicked
     */
//    public static void alert2(Activity a, String title, int icon, final String[] data, final OnButtonClicked onButtonClicked){
//        AlertDialog.Builder builder5=new AlertDialog.Builder(a);
//        builder5.setTitle(title);
//        builder5.setIcon(icon);
//        final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//        for (int i = 0; i < data.length; i++) {
//            Map<String,Object> map=new HashMap<String,Object>();
//            map.put("img", icon);
//            map.put("title", data[i]);
//            list.add(map);
//        }
//        //创建Adapter对象并实例化
//        SimpleAdapter adapter=new SimpleAdapter(
//                a,
//                list,
//                android.R.layout.,
//                new String[]{"img","title"},
//                new int[]{R.id.iv, R.id.tv});
//        //将数据填充到Adapter
//        builder5.setAdapter(adapter, new OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, "你选择了"+list.get(which).get("title").toString().trim(), 0).show();
//            }
//        });
//        builder5.create().show();
//    }

    /**
     * 单选列表
     * @param a
     * @param title
     * @param icon
     * @param data
     */
    public static void singleChoice(Activity a, String title, int icon, final String[] data,
                                    final String positiveButton){
//        AlertDialog.Builder builder2 = processCommonFeature(a, title, icon, positiveButton, neutralButton, negativeButton, onButtonClicked);
//        builder2.create().show();
//        builder2.setSingleChoiceItems(data, defaultSelected, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(onButtonClicked != null) onButtonClicked.onItemClicked(dialog, which);
//            }
//        });
//        builder2.create().show();
    }
}
