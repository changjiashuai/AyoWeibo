安卓开发实践
===========================
本文不是介绍什么最新框架，也不说千万级亿万级的app需要注意的问题，我也不知道，就说本人维护了一个项目一年半，每个月出
两个版本所体会到的一些东西

* 本文致力于说明，但不保证说清：
    * 怎么能开心的做项目
    * 剔除框架，剔除你用的牛逼开源组件，回到最本质的问题，业务逻辑的代码应该怎么写，能让你一年后改这些代码心情还很好
    * 常见的开发问题有哪些需要注意的
    * 以下问题都是本人遇到过问题，或者没遇到过问题，但导致后期代码不容易维护的


****
* 如果有任何问题或意见，请联系我
    * Auor: Damon
    * E-mail: cowthan@163.com

===========================
#1 Activity和Fragment

* 建议
    * 建议所有东西都放在Fragment里，将Activity作为一个空架子使用，除了标题栏
    * Activity和Fragment都要有统一的基类，最好都基于AppCompactActivity，主题也用compact的主题
    * 使用SwipeBackLayout和windowIsTranslusent主题，有些手机可能有问题，注意魅族
    * 最好所有Activity都处理onSaveInstance的问题，特别是需要调用拍照时，一定要测一下三星手机
    * 屏幕旋转是orientation|screenSize

#2 ListView

* 建议
    * 定义一个通用的ViewHolder，封装一下双重优化的逻辑，重复代码就不要他妈的重复写了
    * 能用对象的成员变量存的东西，都可以用Map存，二者基本是等价的，这说的是ViewHolder
    * 考虑什么时候将item封装成一个对象：getView里的逻辑开始变的复杂，已经不是简单的填充内容时
    * 不要用onItemClick，直接在adapter里监听事件吧
    * 同样的列表，比如文章列表，用一个adapter，用一套xml，不同的逻辑，通过set设置标志位
    * adapter要考虑数据为空的问题
    * 一旦find到ListView，立马设置一个adapter，数据给空就行，其他时候都用notifyDataSetChanged
    * 每次要操作ListView，都考虑一下，是否需要刷新整个列表，如果只是局部刷新，考虑findViewWithTag
    * 考虑ListView的loading，empty，error三个状态，error还要考虑当前是否有数据显示
    * 什么时候刷新列表？请仔细考虑，不要做多余的事，也不要让app做多余的事

#3 数据统计

* 友盟
    * 每次发包都测一下统计，或者提醒测试测一下，因为友盟他妈的也不稳定啊，而且数据格式和api也他妈改

#4 优酷视频sdk

* 扇他逼脸
    * 不支持64位手机



