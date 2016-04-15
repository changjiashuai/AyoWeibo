package com.ayoview.sample.deepmind.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import com.cowthan.sample.R;

import org.ayo.Display;
import org.ayo.notify.Toaster;

/**
 * 自定义控件：绘制一个bitmap，并使用ColorFilter的PorterDuffColorFilter进行处理，混合模式
 *
 */
public class ImagePorterDuffColorFilterView extends View {
    private Paint mPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private Bitmap bitmap;// 位图

    private int x, y;// 位图绘制时左上角的起点坐标

    public ImagePorterDuffColorFilterView(Context context) {
        this(context, null);
    }

    public ImagePorterDuffColorFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes(context);
    }

    private int currentModePostion = 0;

    private PorterDuff.Mode[] modes = {
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.XOR,

            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.OVERLAY,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.LIGHTEN,

            PorterDuff.Mode.DST,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.DST_OVER,

            PorterDuff.Mode.SRC_OVER,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.SRC_OUT,
        };

    private String[] modesStr = {
            "PorterDuff.Mode.ADD",
            "PorterDuff.Mode.MULTIPLY",
            "PorterDuff.Mode.XOR",

            "PorterDuff.Mode.SCREEN",
            "PorterDuff.Mode.CLEAR",
            "PorterDuff.Mode.OVERLAY",
            "PorterDuff.Mode.DARKEN",
            "PorterDuff.Mode.LIGHTEN",

            "PorterDuff.Mode.DST",
            "PorterDuff.Mode.DST_ATOP",
            "PorterDuff.Mode.DST_IN",
            "PorterDuff.Mode.DST_OUT",
            "PorterDuff.Mode.DST_OVER",

            "PorterDuff.Mode.SRC_OVER",
            " PorterDuff.Mode.SRC_ATOP",
            "PorterDuff.Mode.SRC_IN",
            "PorterDuff.Mode.SRC_OUT",
    };

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 设置颜色过滤
        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));

        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentModePostion++;
                if(currentModePostion >= modes.length){
                    currentModePostion = 0;
                }
                Toaster.toastShort(modesStr[currentModePostion]);
                mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, modes[currentModePostion]));
                postInvalidate();
            }
        });
    }


    /**
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取位图
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_middle2);

        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         * 屏幕坐标x轴向左偏移位图一半的宽度
         * 屏幕坐标y轴向上偏移位图一半的高度
         */
        x = Display.screenWidth / 2 - bitmap.getWidth() / 2;
        y = Display.screenHeight / 2 - bitmap.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制位图
        canvas.drawBitmap(bitmap, x, y, mPaint);
    }
}