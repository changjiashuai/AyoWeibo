package com.ayoview.sample.deepmind.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import org.ayo.Display;

/**
 * 自定义控件：不断扩展的圆环
 */
public class CustomView2 extends View implements Runnable{

    private Paint mPaint;

    public CustomView2(Context context) {
        super(context);
        initPaint();
    }

    public CustomView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /*
         * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        mPaint.setStyle(Paint.Style.STROKE);

        // 设置画笔颜色为浅灰色
        mPaint.setColor(Color.LTGRAY);

        /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        mPaint.setStrokeWidth(10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制圆环
        canvas.drawCircle(Display.screenWidth / 2, Display.screenHeight / 2, radius, mPaint);
    }

    private float radius = 50;

    public void setRadius(float r){
        this.radius = r;
        if(this.radius >= 150) this.radius = 50;
        postInvalidate();
    }


    @Override
    public void run() {
        /*
         * 确保线程不断执行不断刷新界面
         */
        int radiu = 50;
        while (true) {
            try {
            /*
             * 如果半径小于200则自加否则大于200后重置半径值以实现往复
             */
                if (radiu <= 200) {
                    radiu += 10;

                    // 刷新View
                   setRadius(radiu);
                } else {
                    radiu = 50;
                }

                // 每执行一次暂停40毫秒
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
