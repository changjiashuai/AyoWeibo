package com.ayoview.sample.deepmind;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import org.ayo.jlog.JLog;

/**
 * 圆角drawable
 *
 * 注意draw方法里的canvas.drawRoundRect()方法，这里canvas画个什么形状，就可以显示什么形状的图片
 */
public class RoundImageDrawable extends Drawable{


    private Paint mPaint;
    private Bitmap mBitmap;
    private RectF rectF;

    public RoundImageDrawable(Bitmap bitmap){
        this.mBitmap = bitmap;

        //初始化paint
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(shader);
    }

    /**
     *     The {@link #setBounds} method <var>must</var> be called to tell the
     *     Drawable where it is drawn and how large it should be.  All Drawables
     *     should respect the requested size, often simply by scaling their
     *     imagery.  A client can find the preferred size for some Drawables with
     *     the {@link #getIntrinsicHeight} and {@link #getIntrinsicWidth} methods
     *
     *      Specify a bounding rectangle for the Drawable. This is where the drawable
     *      will draw when its draw() method is called.
     *
     *      大体意思就是setBounds必须被调用以指定drawable绘制的位置和大小
     *      而drawable本身也必须遵循这里指定的位置和大小，至少要respect，具体表现就是根据这个来缩放图片
     *
     *      ---------
     *      这句啥意思？
     *      A client can find the preferred size for some Drawables with
     *     the {@link #getIntrinsicHeight} and {@link #getIntrinsicWidth} methods
     *
     *      注意getIntrinsicHeight的注释：
     *      Return the intrinsic height of the underlying drawable object. Returns
     *      -1 if it has no intrinsic height, such as with a solid color.
     *
     *      这个intrinsic height是内在的高度，其实也就是最小高度，这个应该是drawable告诉外界的，如ImageView
     *
     *      drawable作为src时：
     *      getIntrinsicWidth、getIntrinsicHeight主要是为了在View使用wrap_content的时候，提供一下尺寸，默认为-1可不是我们希望的。
     *      setBounds就是去设置下绘制的范围
     *
     *      drawable作为ImageView的src时，以ImageView的尺寸优先，如果是wrap_content，则尺寸由scaleType和drawable的getgetIntrinsic决定
     *      drawable作为ImageView的background时
     *
     *      -----------------
     *      setAlpha和setColorFilter
     *      值都来自外界，用户给设置
     *
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        JLog.i("RoundImageDrawable", "setBounds--" + left + ", " + top + ", " + right + ", " + bottom);
        rectF = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRoundRect(rectF, 30, 30, mPaint);
    }

    @Override
    public int getIntrinsicWidth()
    {
        JLog.i("RoundImageDrawable", "getIntrinsicWidth--bitmap宽-" + mBitmap.getWidth());
        return mBitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight()
    {
        JLog.i("RoundImageDrawable", "getIntrinsicHeight--bitmap高-" + mBitmap.getHeight());
        return mBitmap.getHeight();
    }

    @Override
    public void setAlpha(int alpha)
    {
        mPaint.setAlpha(alpha);
    }

    /**
     * Specify a color and Porter-Duff mode to be the color filter for this
     * drawable.
     */
    @Override
    public void setColorFilter(ColorFilter cf)
    {
        mPaint.setColorFilter(cf);
    }

    /**
     * Return the opacity/transparency of this Drawable.  The returned value is
     * one of the abstract format constants in
     * {@link android.graphics.PixelFormat}:
     * {@link android.graphics.PixelFormat#UNKNOWN},
     * {@link android.graphics.PixelFormat#TRANSLUCENT},
     * {@link android.graphics.PixelFormat#TRANSPARENT}, or
     * {@link android.graphics.PixelFormat#OPAQUE}.
     *
     * <p>Generally a Drawable should be as conservative as possible with the
     * value it returns.  For example, if it contains multiple child drawables
     * and only shows one of them at a time, if only one of the children is
     * TRANSLUCENT and the others are OPAQUE then TRANSLUCENT should be
     * returned.  You can use the method {@link #resolveOpacity} to perform a
     * standard reduction of two opacities to the appropriate single output.
     *
     * <p>Note that the returned value does <em>not</em> take into account a
     * custom alpha or color filter that has been applied by the client through
     * the {@link #setAlpha} or {@link #setColorFilter} methods.
     *
     * @return int The opacity class of the Drawable.
     *
     * @see android.graphics.PixelFormat
     *
     * 看完这注释还他妈不懂
     */
    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }
}
