package com.ayoview.sample.deepmind.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.cowthan.sample.R;

import org.ayo.Display;

/**
 *
 * 自定义控件：绘制一个bitmap，并使用ColorFilter的LightingColorFitler进行处理，实现光照效果
 */
public class ImageLightingFilterView extends View {
    private Paint mPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private Bitmap bitmap;// 位图

    private int x, y;// 位图绘制时左上角的起点坐标
    private boolean isClick;// 用来标识控件是否被点击过

    public ImageLightingFilterView(Context context) {
        this(context, null);
    }

    public ImageLightingFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes(context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 判断控件是否被点击过
                 */
                if (isClick) {
                    // 如果已经被点击了则点击时设置颜色过滤为空还原本色
                    mPaint.setColorFilter(null);
                    isClick = false;
                } else {
                    // 如果未被点击则点击时设置颜色过滤后为黄色
                    mPaint.setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0X00FFFF00));
                    isClick = true;
                }

                // 记得重绘
                invalidate();
            }
        });
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
