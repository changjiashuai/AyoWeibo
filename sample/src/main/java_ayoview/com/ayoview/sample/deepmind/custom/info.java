package com.ayoview.sample.deepmind.custom;

/*

自定义View 1：

1 继承View

（1）构造函数，现在是有4个
（2）如果要自己在onDraw里画东西，用到的纸就是Canvas，就是onDraw的参数，
用到的笔就是paint，应该提前定义，不应该在onDraw里new，否则会警告：Avoid object allocations during draw/layout operations (preallocate and reuse instead)


2 关于笔

Paint paint = new Paint();

paint.setAntiAlias(true);
或者mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
抗锯齿，让图像边缘显得更圆滑光泽动感的碉堡算法

mPaint.set(Paint src);
把另一个画笔的属性设置Copy过来

mPaint.setAlpha(255);  //[0..255]
mPaint.setARGB(a, r, g, b);  // 和setColor一样，这里每个值都是(0..255)
mPaint.setColor(Color.BLUE);


mPaint.setFlags(int flags);
mPaint.setAntiAlias(true);
抗锯齿，锯齿是依赖于算法的，算法决定抗锯齿的效率，在我们绘制棱角分明的图像时，比如一个矩形、一张位图，我们不需要打开抗锯齿

mPaint.setDither(false);
mPaint.setFakeBoldText(false);
mPaint.setFilterBitmap(false);
mPaint.setLinearText(false);
mPaint.setStrikeThruText(false);
mPaint.setUnderlineText(false);
mPaint.setSubpixelText(false);
mPaint.setHinting(int mode);


mPaint.setColorFilter();

mPaint.setMaskFilter(MaskFilter mf);

mPaint.setPathEffect(PathEffect pe);

mPaint.setXfermode(Xfermode xfermode);

//已废弃
mPaint.setRasterizer(Resterizer rasterizer);

mPaint.setShader(Shader shader);

mPaint.setShadowLayer(float radius, float dx, float dy, int shadowColor);

mPaint.setStrokeCap(Cap cap);
mPaint.setStrokeJoin(Join join);
mPaint.setStrokeMiter(float miter);
mPaint.setStrokeWidth(float width);
mPaint.setStyle(Paint.Style style);

mPaint.setTextAlign(Align align);
mPaint.setTextLocale(Locale.CHINESE);
mPaint.setTextScaleX(float scaleX);
mPaint.setTextSize(float textSize);
mPaint.setTextSkewX(float skewZ);
mPaint.setTypeface(Typeface typeface);


----------------------------------------------------------------------------------------
ColorFilter详解

图片滤镜好像用这个，作用就是给所有颜色都做一个映射，变成另一个颜色，比如去掉绿色，图片变成黑白等
----------------------------------------------------------------------------------------

对应方法是setColorFilter(ColorFilter f)
ColorFilter有3个子类：ColorMatrixColorFilter、LightingColorFilter和PorterDuffColorFilter

---------------------------
ColorMatrixColorFilter：色彩矩阵颜色过滤器
---------------------------
先了解什么是色彩矩阵。在Android中图片是以RGBA像素点的形式加载到内存中的，修改这些像素信息需要一个叫做ColorMatrix类的支持，其定义了一个4x5的float[]类型的矩阵：
```java
ColorMatrix colorMatrix = new ColorMatrix(new float[]{
        1, 0, 0, 0, 0,   //第一行表示的R（红色）的向量
        0, 1, 0, 0, 0,   //第二行表示的G（绿色）的向量
        0, 0, 1, 0, 0,   //第三行表示的B（蓝色）的向量
        0, 0, 0, 1, 0,   //最后一行表示A（透明度）的向量
});
```

这个矩阵不同的位置表示的RGBA值，其范围在0.0F至2.0F之间，1为保持原图的RGB值

每一行的第五列数字表示偏移值，何为偏移值？
当我们想让颜色更倾向于红色的时候就增大R向量中的偏移值，想让颜色更倾向于蓝色的时候就增大B向量中的偏移值，这是最最朴素的理解，
但是事实上色彩偏移的概念是基于白平衡来理解的，什么是白平衡呢？说得简单点就是白色是什么颜色！如果大家是个单反爱好者或者会些PS
就会很容易理解这个概念，在单反的设置参数中有个色彩偏移，其定义的就是白平衡的色彩偏移值，就是当你去拍一张照片的时候白色是什么
颜色的，在正常情况下白色是（255, 255, 255, 255）但是现实世界中我们是无法找到这样的纯白物体的，所以在我们用单反拍照之前就会
拿一个我们认为是白色的物体让相机记录这个物体的颜色作为白色，然后拍摄时整张照片的颜色都会依据这个定义的白色来偏移！而这个我们
定义的“白色”（比如：255, 253, 251, 247）和纯白（255, 255, 255, 255）之间的偏移值（0, 2, 4, 8）我们称之为白平衡的色彩偏移。

大体意思是：这个矩阵可以用来改变颜色，例如改变图片的整体颜色
——因为是矩阵相乘，所以可以进行颜色衰减，颜色偏移，颜色互换，还可以根据红绿来决定蓝，等等等等

ColorMatrix colorMatrix = new ColorMatrix(new float[]{
        Ra, Rb, Rc, Rd, Re,   //第一行表示的R（红色）的向量
        Ga, Gb, Gc, Gd, Ge,   //第二行表示的G（绿色）的向量
        Ba, Bb, Bc, Bd, Be,   //第三行表示的B（蓝色）的向量
        Aa, Ab, Ac, Ad, Ae,   //最后一行表示A（透明度）的向量
});

e这一列表示偏移

具体怎么相乘呢？
原Color = new float[]{
    R,
    G,
    B,
    A
}
注意，这里的值进行了归一化，原color值被转换后，范围是[0,1]


矩阵相乘之后：

Result = ColorMatrix * 原Color = new float[]{

    R = Ra*R + Rb*G + Rc*B + Rd*A + Re,
    G = Ga*R + Gb*G + Gc*B + Gd*A + Ge,
    B = Ba*R + Bb*G + Bc*B + Bd*A + Be,
    A = Aa*R + Ab*G + Ac*B + Ad*A + Ae,

}

注意，如果要不进行任何过滤，则colorMatrix是：
ColorMatrix colorMatrix = new ColorMatrix(new float[]{
        1, 0, 0, 0, 0,   //第一行表示的R（红色）的向量
        0, 1, 0, 0, 0,   //第二行表示的G（绿色）的向量
        0, 0, 1, 0, 0,   //第三行表示的B（蓝色）的向量
        0, 0, 0, 1, 0,   //最后一行表示A（透明度）的向量
});

则
Result = new float[]{
    R = 1*R + 0*G + 0*B + 0*A + 0 = R,
    G = G,
    B = B,
    A = A,
}


---------------------------
LightingColorFilter：光照颜色过滤
---------------------------
LightingColorFilter (int mul, int add)

mul全称是colorMultiply意为色彩倍增，而add全称是colorAdd意为色彩添加，这两个值都是16进制的色彩值0xAARRGGBB。这个方法使用也是非常的简单

当LightingColorFilter(0xFFFFFFFF, 0x00000000)的时候原图是不会有任何改变的，如果我们想增加红色的值，那么LightingColorFilter(0xFFFFFFFF, 0x00XX0000)就好，其中XX取值为00至FF



---------------------------
PorterDuffColorFilter：混合模式颜色过滤
---------------------------

类似PS里的图层混合，可以选择多种混合模式，和PS里模式名相同的，产生的效果也一样

PorterDuffColorFilter(int color, PorterDuff.Mode mode)

这个构造方法也接受两个值，一个是16进制表示的颜色值这个很好理解，而另一个是PorterDuff内部类Mode中的一个常量值，这个值表示混合模式。那么什么是混合模式呢？混合混合必定是有两种东西混才行，第一种就是我们设置的color值而第二种当然就是我们画布上的元素了！，比如这里我们把Color的值设为红色，而模式设为PorterDuff.Mode.DARKEN变暗

mode有很多
PorterDuff.Mode中的模式不仅仅是应用于图像色彩混合，还应用于图形混合，比如PorterDuff.Mode.DST_OUT就表示裁剪混合图，
如果我们在PorterDuffColorFilter中强行设置这些图形混合的模式将不会看到任何对应的效果


PorterDuff.Mode.ADD;
PorterDuff.Mode.MULTIPLY;
PorterDuff.Mode.XOR;

PorterDuff.Mode.SCREEN;
PorterDuff.Mode.CLEAR;
PorterDuff.Mode.OVERLAY;
PorterDuff.Mode.DARKEN;
PorterDuff.Mode.LIGHTEN;

PorterDuff.Mode.DST;
PorterDuff.Mode.DST_ATOP;
PorterDuff.Mode.DST_IN;
PorterDuff.Mode.DST_OUT;
PorterDuff.Mode.DST_OVER;

PorterDuff.Mode.SRC_OVER;
PorterDuff.Mode.SRC_ATOP;
PorterDuff.Mode.SRC_IN;
PorterDuff.Mode.SRC_OUT;

3 onDraw里都能画什么

canvas.drawARGB();
canvas.drawColor();
canvas.drawPaint();

canvas.drawPoint();
canvas.drawPoints();
canvas.drawLine();
canvas.drawLines();
canvas.drawPath();
canvas.drawArc();
canvas.drawCircle();
canvas.drawOval();
canvas.drawRect();
canvas.drawRoundRect();

canvas.drawText();
canvas.drawTextOnPath();
canvas.drawTextRun();

canvas.drawBitmap();
canvas.drawPicture();

canvas.drawVertices();

canvas.translate();
canvas.scale();
canvas.rotate();
canvas.skew();

canvas.setDrawFilter();
canvas.setMatrix();
canvas.setDensity();
canvas.setBitmap();


canvas.save();
canvas.saveLayer();
canvas.restore();
canvas.restoreToCount();

canvas.clipPath();
canvas.clipRect();

canvas.concat();
canvas.isHardwareAccelerated();
canvas.isOpaque();
canvas.quickReject();

4 Color


5 Font


6 Path

 */
public class info {
}
