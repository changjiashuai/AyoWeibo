package com.ayoview.sample.deepmind.custom;

/*
 *
 * 这里的东西要合到info里，主要是因为昨天家里电脑info里的东西没提交，为了避免冲突，省点麻烦，在公司就暂时开个新文件
 *

setXfermode(Xfermode xfermode)

Xfermode国外有大神称之为过渡模式，这种翻译比较贴切但恐怕不易理解，大家也可以直接称之为图像混合模式，因为所谓的“过渡”其实就是图像混合的一种

3个子类：AvoidXfermode, PixelXorXfermode，PorterDuffXfermode

这三个子类实现的功能要比setColorFilter的三个子类复杂得多，主要是是涉及到图像处理的一些知识可能对大家来说会比较难以理解

--------------
AvoidXfermode：
AvoidXfermode(int opColor, int tolerance, AvoidXfermode.Mode mode)
---------------
这个API因为不支持硬件加速，在API 16已经过时了（大家可以在HardwareAccel查看那些方法不支持硬件加速）
如果想在高于API 16的机子上测试这玩意，必须现在应用或手机设置中关闭硬件加速，在应用中我们可以通过在AndroidManifest.xml文件中设置application节点下的android:hardwareAccelerated属性为false来关闭硬件加速
android:hardwareAccelerated="false"

参数1指定一个颜色，16进制，可以带透明通道
参数2，tolerance，是容差值，容什么差呢，可以这么理解，参数1和2就确定了一个颜色范围
——参数2标识了一个值，和参数1的颜色相差不超过这个值的，都在容差范围内
参数3：可选值只有两个：AvoidXfermode.Mode.AVOID或者AvoidXfermode.Mode.TARGET
——AVOID：选中参数1,2决定的颜色范围之外的颜色
——TARGET：选中参数1,2决定的颜色范围之内的颜色
——选中之后干什么？替换掉，替换成什么颜色？画笔决定的颜色

--------------
PixelXorXfermode：
PixelXorXfermode(int opColor)
---------------
与AvoidXfermode一样也在API 16过时了，该类也提供了一个含参的构造方法PixelXorXfermode(int opColor)，该类的计算实现很简单，从官方给出的计算公式来看就是：op ^ src ^ dst，像素色值的按位异或运算，如果大家感兴趣，可以自己用一个纯色去尝试，并自己计算异或运算的值是否与得出的颜色值一样，这里我就不讲了，Because it was deprecated and useless

--------------
PorterDuffXfermode：
PorterDuffXfermode(PorterDuff.Mode mode)
---------------

历史：
虽说构造方法的签名列表里只有一个PorterDuff.Mode的参数，但是它可以实现很多酷毙的图形效果！！
PorterDuffXfermode就是图形混合模式的意思，其概念最早来自于SIGGRAPH的Tomas Proter和Tom Duff，
混合图形的概念极大地推动了图形图像学的发展，
延伸到计算机图形图像学像Adobe和AutoDesk公司著名的多款设计软件都可以说一定程度上受到影响

看图：

porterduff_mode.png
porterduff_mode2.png

API中Android为我们提供了18种（比上图多了两种ADD和OVERLAY）模式

PorterDuff.Mode.ADD,   计算方式：Saturate(S + D)；Chinese：饱和相加，ADD模式简单来说就是对图像饱和度进行相加，这个模式在应用中不常用，我唯一一次使用它是通过代码控制RGB通道的融合生成图片
PorterDuff.Mode.XOR, 计算方式：[Sa + Da - 2 * Sa * Da, Sc * (1 - Da) + (1 - Sa) * Dc]；Chinese：在源图像和目标图像重叠之外的任何地方绘制他们，而在不重叠的地方不绘制任何内容
PorterDuff.Mode.MULTIPLY,计算方式：[Sa * Da, Sc * Dc]；Chinese：正片叠底
    该模式通俗的计算方式很简单，源图像素颜色值乘以目标图像素颜色值除以255即得混合后图像像素的颜色值，该模式在设计领域应用广泛，因为其特性黑色与任何颜色混合都会得黑色，在手绘的上色、三维动画的UV贴图绘制都有应用，具体效果大家自己尝试
PorterDuff.Mode.CLEAR,  计算方式：[0, 0]；Chinese：清除
PorterDuff.Mode.DARKEN, 计算方式：[Sa + Da - Sa*Da, Sc*(1 - Da) + Dc*(1 - Sa) + min(Sc, Dc)]；Chinese：变暗，两个图像混合，较深的颜色总是会覆盖较浅的颜色，如果两者深浅相同则混合，如图，黄色覆盖了红色而蓝色和青色因为是跟透明混合所以不变
    DARKEN模式的应用在图像色彩方面比较广泛我们可以利用其特性来获得不同的成像效果，这点与之前介绍的ColorFilter有点类似
PorterDuff.Mode.LIGHTEN, 计算方式：[Sa + Da - Sa*Da, Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)]；Chinese：变亮，与DARKEN相反
PorterDuff.Mode.OVERLAY, 计算方式：未给出；Chinese：叠加
    这个模式没有在官方的API DEMO中给出，谷歌也没有给出其计算方式，在实际效果中其对亮色和暗色不起作用，也就是说黑白色无效，它会将源色与目标色混合产生一种中间色，这种中间色生成的规律也很简单，如果源色比目标色暗，那么让目标色的颜色倍增否则颜色递减
PorterDuff.Mode.SCREEN, 计算方式：[Sa + Da - Sa * Da, Sc + Dc - Sc * Dc]；Chinese：滤色
    滤色产生的效果我认为是Android提供的几个色彩混合模式中最好的，它可以让图像焦媃幻化，有一种色调均和的感觉
PorterDuff.Mode.DST,   计算方式：[Da, Dc]；Chinese：只绘制目标图像
PorterDuff.Mode.DST_ATOP, 计算方式：[Sa, Sa * Dc + Sc * (1 - Da)]；Chinese：在源图像和目标图像相交的地方绘制目标图像而在不相交的地方绘制源图像
PorterDuff.Mode.DST_IN,  计算方式：[Sa * Da, Sa * Dc]；Chinese：只在源图像和目标图像相交的地方绘制目标图像，最常见的应用就是蒙板绘制，利用源图作为蒙板“抠出”目标图上的图像，这里我讲一个很简单的例子，如果大家用过PS就很容易理解
    抠图？
    看DisInView
    其实是蒙版绘制，蒙块板子绘制，绘制出来的就是没被板子盖住的地方，蒙版mask是src，被盖住的图是dis，相交的地方就是蒙版透明的地方，也就是漏出来的地方，所以留下来的就是src也就是蒙版的透明区
PorterDuff.Mode.DST_OUT, 计算方式：[Da * (1 - Sa), Dc * (1 - Sa)]；Chinese：只在源图像和目标图像不相交的地方绘制目标图像
PorterDuff.Mode.DST_OVER, 计算方式：[Sa + (1 - Sa)*Da, Rc = Dc + (1 - Da)*Sc]；Chinese：在源图像的上方绘制目标图像，就是两个图片谁在上谁在下的意思
PorterDuff.Mode.SRC, 计算方式：[Sa, Sc]；Chinese：显示源图,只绘制源图，SRC类的模式跟DIS的其实差不多就不多说了
PorterDuff.Mode.SRC_ATOP, 计算方式：[Da, Sc * Da + (1 - Sa) * Dc]；Chinese：在源图像和目标图像相交的地方绘制源图像，在不相交的地方绘制目标图像
PorterDuff.Mode.SRC_IN, 计算方式：[Sa * Da, Sc * Da]；Chinese：只在源图像和目标图像相交的地方绘制源图像
PorterDuff.Mode.SRC_OUT, 计算方式：[Sa * (1 - Da), Sc * (1 - Da)]；Chinese：只在源图像和目标图像不相交的地方绘制源图像
PorterDuff.Mode.SRC_OVER, 计算方式：[Sa + (1 - Sa)*Da, Rc = Sc + (1 - Sa)*Dc]；Chinese：在目标图像的顶部绘制源图像

这18种模式Android还为我们提供了它们的计算方式，
比如LIGHTEN的计算方式为[Sa + Da - Sa*Da, Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)]
其中Sa全称为Source alpha表示源图的Alpha通道
Sc全称为Source color表示源图的颜色
Da全称为Destination alpha表示目标图的Alpha通道
Dc全称为Destination color表示目标图的颜色
[……]里是两部分：前面是Alpha通道，后面是颜色值，图形混合后的图片依靠这个矢量来计算ARGB的值
如果大家感兴趣可以查看维基百科中对Alpha合成的解释：http://en.wikipedia.org/wiki/Alpha_compositing


例子：
PorterDuffView

在PorterDuffBo里生成了两个渐变图的bitmap，左上角绘制src，右上角绘制dst，这俩只是做个对比，
真正使用混合模式是在中间的大图

Src为源图像，意为将要绘制的图像；Dis为目标图像，意为我们将要把源图像绘制到的图像

实际应用：
1 PorterDuffXfermode的另一个比较常见的应用就是橡皮檫效果，我们可以通过手指不断地触摸屏幕绘制Path，再以Path作遮罩遮掉填充的色块显示下层的图像

参考EraserView

2 闹钟图标：

alarm.png

构思一下怎么做，我们需要画一个圆圈做钟体，两个Path（Path为路径，我们将会在1/3详细学习到，这里就先follow me）作为指针，问题是两个铃铛~~~~如果我们不会混合模式，一定会想怎么计算坐标啊去绘制曲线然后闭合然后填充啊之类………………实际上有必要吗？这个闹铃不就是一个小圆再用一个大圆去遮罩吗

问题是不是一下子就变简单了？如果等你去计算怎么画路径怎么闭合曲线填充颜色还有多屏幕的匹配………………哥已经死了又活过来又死了…………在学完1/2的View尺寸计算和布局后我会教大家如何做类似的View并匹配在所有的屏幕上~~~~这里就先缓一缓。大家一定要有这样的思维，当你想要去画一个View的时候一定要想想看这个View的图形是不是可以通过基本的几何图形混合来生成，如果可以，那么恭喜你，使用PorterDuffXfermode的混合模式你可以事半功倍！


-----------------
你知道Shader是什么吗？Xfermode和Colorfilter给我们带来了炫酷的图像混合效果，然而仅仅是这样肯定是体现不出Android在图形图像处理上的逼格
 */
public class info2 {
}
