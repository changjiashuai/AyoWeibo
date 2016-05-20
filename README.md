Ayo2016
========================

##前言：这是什么


##如何往项目里添加你的代码

* 分2步：
    * 配置你的个人页入口：在Config类中搜`菜单1：笔记`，配置你自己的page和菜单列表
    * 开始写你的代码
        * 如果要使用Activity代理框架，请移步：[Activity代理框架](./doc/doc-ayoview-acagent.md)
        * 如果不想用Activity代理框架，请参考org.ayo.app.orig
        * 如果都不感兴趣，直接写你的Activity就行


####
__注册你的个人目录__
```java
sourceSets {
    main {
        jniLibs.srcDirs = ['libs']
        java.srcDirs = ['src/main/java',
                        'src/main/java_ayosdk',
                        'src/main/java_ayoview',
                        'src/main/java_issues',
                        'src/main/java_opensource',
                        'src/main/java_seven',
                        'src/main/java_snowy',
                        'src/main/lib_particle']
    }
```

####
* 务必注意：
    * 写之前先过一遍现有的库，避免重复引入，省的干重复的事
    * 尽量给个文档，文档目录在README.md
	* 图片都放在七牛的本人账号下，空间cowthan1103，域名```http://7xo0ny.com1.z0.glb.clouddn.com/```


####
* 日程安排
    * 2016.03.28 完善ui lib
	
	
	
##目录

* 基础设施:
    * [Ayo库怎么用](./doc/doc-doc.md)
    * [常用工具类](./doc/doc-common.md)
	* [Activity免声明框架：AyoActivity，多模块开发]
	    * [安卓Activity，Fragment基础教程]
	    * [Activity状态保存](./doc/doc-state.md)
	    * [AyoActivity思路和教程]
		* [和Fragment的配合: getActivity()得不到attacher问题解决]
		* [多模块开发研究--加载，资源文件的加载]
		* [插件化发布研究]
		* [热补丁]
    * [控件注入](./doc/doc-inject.md)
        * [ButterKnife研究]
        * [dagger研究]
    * [日志系统](./doc/doc-log.md)
        * [JLog：小众化]
        * [Logger：大众化]
        * [崩溃日志]
        * [用户行为日志]
    * [IO操作](./doc/doc-io.md)
	    * [SharedPrefernce]
		* [安卓文件操作：SD卡，上下文目录，assets，raw，res资源]
		* [流：原生]
		* [流：Okio]
    * [http请求](./doc/doc-http.md)
	    * [AyoHttp框架]
		* [底层：UrlConnection，OKHttp]
		* [XUtils：http请求，下载文件，上传文件]
        * [Volly教程和源码解析](./doc/doc-volly.md)
	    * [retrofit教程和源码解析]
	    * [webview参考手册，js桥]
	    * [react-native研究]
	* [json解析]
	    * [原生json和泛型问题]
		* [Bean嵌套，Map，List，"1,2,3,4"的解析]
	    * [FastJson]
	    * [Gson]
	    * [AyoJson：顶层框架]
	* [xml解析](./doc/doc-xml.md)
	    * [原生]
	    * [第三方库，orm方式解析]
    * [网络图片加载](./doc/doc-onlineimage.md)
	    * [两层缓存]
	    * [Ayo-Vangoph]
	    * [UniversalImageDownloader]
		* [Fresco]
	    * [Picasso]
    * [多线程](./doc/doc-async.md)
		* [安卓消息队列机制]
	    * [java多线程]
		* [RxJava]
	* [多进程]
	    * [怎么用，何时用，怎么证明开了多个进程，多个进程之间的关系]
		* [极光，友盟等的单进程是咋回事，有何意义]
		* [我们啥时能用上]
		* [不死服务]
    * [缓存](./doc/doc-cache.md)
        * [SharedPreference封装]
        * [简单版：Json配合SharedPreference]
        * [LruCache]
    * [数据库](./doc/doc-database.md)
        * [原生SqlHelper用法]
        * [原生 + RxJava：square的sqlbrite]
        * [orm：XUtils]
        * [orm：greendao]
        * [orm：ormlite]
    * [事件总线：EventBus](./doc/doc-eventbus.md)
        * [EventBus]
        * [otto]
    * [推送]
        * [mqtt：自己搭个服务器，自己封装sdk]
        * [极光等第三方推送]

    * [binder]
        * [binder机制]
        * [aidl:http://android.blog.51cto.com/268543/537684/]

    * [IM通信]
        * [openfire]
        * [蘑菇街开源IM框架]
        * [自定义聊天组件：基于融云]
    * [加密解密](./doc/doc-crypt.md)
    * [手机功能接口]
        * [读取联系人，短信]
        * [拦截短信]
        * [解锁屏幕，保持屏幕点亮等]
        * [各种广播receiver]


####
* UIFramework：控件
    * [Activity]
        * 生命周期
        * onSaveInstance
        * 连续打开多个Activity
        * SingleTask：不能作为splash使用
        * 横竖屏切换
        * ActionBar
        * manifest配置研究
        * 主题研究
    * [Fragment应用及其通信](./doc/v_fragment.md)
    * [状态栏控制](./doc/README-ayo.md)
    * [Drawable系列](./doc/v_drawable.md)
    * [TextView系列](./doc/v_textview.md)
    * [EditText系列](./doc/v_textview.md)
    * [ImageView系列](./doc/v_imageview.md)
	    * 这部分应该基于Fresco的SimpleDraweeView构建，如果你信任fresco的话
		* PhotoView
		* Gif
		* webP
		* 圆角，圆形
    * [ProgressView系列](./doc/README-ayo.md)
    * [View切换系列](./doc/README-ayo.md)
    * [ListView手册和源码分析](./doc/README-ayo.md)
        * 普通
        * 上下拉及定制
        * 滑动删除和动画
        * sticky或者pinned
        * Item Type
        * drag
    * [RecyclerView和ListView](./doc/README-ayo.md)
	    * 性能，这就不知道怎么弄了
	    * 转屏时是否自动加载
		* scrollToPostion(), scrollTo()
    * [DrawerLayout](./doc/README-ayo.md)
    * [关于ActionBar](./doc/README-ayo.md)
    * [表单系列](./doc/README-ayo.md)
        * 输入框
        * 下拉框：原地，底部，中间
        * WheelView
        * 各种picker
        * 单选
        * 复选
        * Lable + Input 组合
        * 校验
    * [布局系列](./doc/README-ayo.md)
        * LinearLayout，Relativelayout，FrameLayout
        * AutoLayout
        * PercentageLayout
        * FlowLayout
        * WaterFallLayout
        * DrawerLayout
        * SwipeLayout
        * SwipeBackLayout
        * ScrollView
        * SwipeRefreshLayout
        * PullLayout
    * [模板：ListView和GridView](./doc/README-ayo.md)
    * [模板：UI主框架，PageGroupView](./doc/README-ayo.md)
    * [模板：Tab主框架](./doc/README-ayo.md)
    * [模板：个人页常见模式](./doc/README-ayo.md)

####
* UIFramework：用户提示
    * [原生Dialog](./doc/n_dialog_origin.md)
    * [Dialog：Alert系列](./doc/README-ayo.md)
    * [WheelPicker：列表选择](./doc/README-ayo.md)
    * [DatePicker：日期选择](./doc/README-ayo.md)
    * [ActionSheet](./doc/README-ayo.md)
    * [Popup](./doc/README-ayo.md)
    * [Toast](./doc/README-ayo.md)
    * [Snackbar](./doc/README-ayo.md)
    * [Headup](./doc/README-ayo.md)
    * [Notification](./doc/README-ayo.md)
    * [声音，LED，震动，亮屏](./doc/README-ayo.md)
    * [其他](./doc/README-ayo.md)

####
* UIFramework：动画
    * [Activity切换动画](./doc/README-ayo.md)
    * [属性动画](./doc/README-ayo.md)
    * [缓动函数--daimajia ease](./doc/README-ayo.md)
    * [spring rebound](./doc/README-ayo.md)
    * [Transition](./doc/README-ayo.md)
    * [布局](./doc/README-ayo.md)
    * [path动画]

####
* UIFramework：绘图
    * [OpenGL基础](./doc/README-ayo.md)
    * [onDraw里都能干什么](./doc/README-ayo.md)

####
* UIFramework：自定义控件
    * [控件增强](./doc/README-ayo.md)
    * [控件组合](./doc/README-ayo.md)
    * [自定义布局](./doc/README-ayo.md)

####
* UIFramework：onTouch详解
    * [onTouch基础](./doc/README-ayo.md)
    * [手势应用](./doc/README-ayo.md)
    * [ScrollView里的onTouch](./doc/README-ayo.md)
    * [ViewPager里的onTouch](./doc/README-ayo.md)
    * [常见冲突](./doc/README-ayo.md)
    * [应用场景](./doc/README-ayo.md)

####
* 安卓res详解
    * [res下几个目录到底怎么回事](./doc/README-2016.md)
    * [values目录](./doc/README-2016.md)
    * [主题和style](./doc/README-2016.md)
    * [适配问题](./doc/README-2016.md)
    * [资源文件切换问题：换肤](./doc/README-2016.md)

####
* 单元测试
    * [单元测试怎么写](./doc/README-2016.md)

####
* [常见问题和代码段](./doc/README-issue.md)
    * [ScrollView嵌套ViewPager冲突](./doc/README-issue.md)
    * [ScrollView嵌套ListView或GridView](./doc/README-issue.md)
    * [小键盘管理](./doc/README-issue.md)
    * [三星手机拍照问题](./doc/README-issue.md)
    * [魅族的UI适配](./doc/README-issue.md)
    * [windowIsTranlusent问题](./doc/README-issue.md)
    * [windowIsTranslusent为true导致Activity无法横竖切换](./doc/README-issue.md)
	* [Java单例模式]

####
* 基于git的work flow
    * [git教程](./doc/README-2016.md)

####
* 打包编译
    * 从gradle说起
    * 库管理，上传module的jcenter
    * 多渠道打包
    * [热补丁]

####
* 杂七杂八
    * [MVP模式](./doc/README-2016.md)
    * [基于状态管理的复杂业务逻辑如何实现？](./doc/README-2016.md)
    * [基于状态管理的复杂业务逻辑如何实现？](./doc/README-realthing.md)


####
* 其他第三方常用库介绍
    * 统计：友盟统计
    * 更新：友盟自动更新
    * 反馈：友盟反馈
    * 第三方登录
    * 第三方分享
    * 七牛：图片云服务
    * 图片选择器
    * 自定义相机
    * 视频播放：第三方平台依赖，优酷
    * 视频播放：第三方平台依赖，乐视
    * 视频播放：视频自己管理
    * 录像
    * 音频播放器
    * 自定义app内置浏览器
    * 支付宝


####
* 项目管理
    * 项目的任务分级：
        * 简单型：如画个ListView，画个Item，改改界面布局等
        * 长期型：一般由主导人员完成，如优化底层http，抢单实现，订单管理实现
        * 讨论型：未定的东西，一般会转化为长期型
        * 缺陷型：大多数是简单型，下个版本就必须随着上线的东西
        * 技术难点型：需要先调研，再讨论，再分解为简单和长期