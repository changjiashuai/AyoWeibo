package com.ayoview.sample.deepmind.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.cowthan.sample.R;

import org.ayo.Display;
import org.ayo.notify.Toaster;

/**
 * 自定义控件：绘制一个bitmap，并使用ColorFilter的ColorMatrixColorFitler进行处理，实现图片滤镜效果
 */
public class ImageColorMatrixFilterView extends View{

    private Paint mPaint;
    private Bitmap bitmap;// 位图
    private int x,y;// 位图绘制时左上角的起点坐标

    private int currentEffect = 0;

    public ImageColorMatrixFilterView(Context context) {
        super(context);
        initPaint();
    }

    public ImageColorMatrixFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ImageColorMatrixFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageColorMatrixFilterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColorFilter(new ColorMatrixColorFilter(ColorMatrixMgmr.getColorMatrix(ColorMatrixMgmr.key(currentEffect))));
        initRes(getContext());

        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentEffect++;
                if(currentEffect >= ColorMatrixMgmr.effectCount()){
                    currentEffect = 0;
                }

                ColorMatrix matrix = ColorMatrixMgmr.getColorMatrix(ColorMatrixMgmr.key(currentEffect));
                mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));

                Toaster.toastShort(ColorMatrixMgmr.key(currentEffect));
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
