package com.ayoview.sample.deepmind;

/*


自定义控件简陋参考手册

* 以下内容均参考自网络：
    * drawble相关：http://blog.csdn.net/lmj623565791/article/details/43752383
    * 自定义控件相关：http://blog.csdn.net/column/details/androidcustomview.html
    * 动画相关：参考daimajia，和

--------------------------------------
drawable系列
1 自定义drawable
2 自定义state
3 利用Drawable去提升UI的性能
4 paint相关
5 canvas相关
6 BitmapShader：http://blog.csdn.net/lmj623565791/article/details/41967509
7 Xfermode：http://blog.csdn.net/lmj623565791/article/details/42094215

1 自定义drawable

自定义Drawable，相比View来说，Drawable属于轻量级的、使用也很简单。以后自定义实现一个效果的时候，可以改变View first的思想，尝试下Drawable first

实例：用drawable实现圆角图片，圆形图片

参考com.ayoview.sample.deepmind.RoundImageDrawable和CircleImageDrawable

2 自定义state

例子参考：https://github.com/CharlesHarley/Example-Android-CustomDrawableStates

有几点：
——定义state，和自定义属性一样
——在xml中使用，类似selector
——自定义个View，并提供setXXStatus的接口，处理状态（有套路）

参考



 --------------------------------------
 view系列
 1 measure，layout，draw相关
 2 属性相关，顺便说一下drawable，state等自定义属性
 3 点击事件
 4 触摸事件
 5 scroll相关

 --------------------------------------
 动画系列
 tween：预定义一堆，类似smile
 属性：详细整理api，整理common动画，以daimajia的形式封装平移等
 布局动画
 transition
 activity切换效果：低级整理，封装，高级版的原理

 */
public class info {
}
