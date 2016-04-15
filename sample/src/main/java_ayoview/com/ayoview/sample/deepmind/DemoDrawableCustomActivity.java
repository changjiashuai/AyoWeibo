package com.ayoview.sample.deepmind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * 自定义drawable的demo
 * Created by Administrator on 2016/4/7.
 */
public class DemoDrawableCustomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dm_ac_drawable_custom);

        //RoundImageDrawable
        /**
         * 图片290*435
         * getIntrinsicWidth  870
         * getIntrinsicHeight 1305
         * 尺寸扩大了：
         * ——在raw下扩大了3倍，870*1305
         * ——在xhdpi下，宽高是435*653
         * ——在xxhdpi下，宽高是435*653
         *
         * 每次setImageDrawable都调用了两次setBounds
         * setBounds--0, 0, 870, 1305
         * setBounds--0, 0, 870, 1305
         *
         * iv_1是wrap_content的，最终大小是870*1305
         * iv_2是100dp*100dp，最终大小是100dp*100dp，但应该是fitxy
         *
         *
         */
        ImageView iv_1 = findViewById(R.id.iv_1);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_middle3);
        iv_1.setImageDrawable(new RoundImageDrawable(bitmap));

        ImageView iv_2 = findViewById(R.id.iv_2);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.test_middle3);
        iv_2.setImageDrawable(new RoundImageDrawable(bitmap2));

        ImageView iv_3 = findViewById(R.id.iv_3);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.test_middle3);
        //iv_2.setImageDrawable(new RoundImageDrawable(bitmap2));
        iv_3.setBackground(new RoundImageDrawable(bitmap3));

        //CircleImageDrawable
        ImageView iv_4 = findViewById(R.id.iv_4);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.test_middle3);
        iv_4.setImageDrawable(new CircleImageDrawable(bitmap4));

        //自定义State
        final MessageListItemView mli_item = findViewById(R.id.mli_item);
        final Message message = new Message("Not heard from you", false);
        mli_item.setMessageSubject(message.subject);
        mli_item.setMessageUnread(message.unread);

        final CheckBox cb_change_status = findViewById(R.id.cb_change_status);
        cb_change_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    message.unread = true;
                    cb_change_status.setText("设为未读");
                }else{
                    message.unread = false;
                    cb_change_status.setText("设为已读");
                }
                mli_item.setMessageUnread(message.unread);
            }
        });
    }

    private static class Message {

        private String subject;
        private boolean unread;

        private Message(String subject, boolean unread) {
            this.subject = subject;
            this.unread = unread;
        }

    }
}
