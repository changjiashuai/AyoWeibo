package org.ayo.imageloader;


import android.content.Context;
import android.net.Uri;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;

/*
 Fresco的工具类


关于SimpleDraweeView

1 基本使用

```xml

主要，xml文件里SimpleDraweeView不支持宽高都wrap_content，
原因很显而易见，如果大小不一致，假设使用的是 wrap_content，图像下载完之后，View将会重新layout，改变大小和位置。这将会导致界面跳跃。

固定宽高
<com.facebook.drawee.view.SimpleDraweeView
android:id="@+id/my_image_view"
android:layout_width="20dp"
android:layout_height="20dp"
fresco:placeholderImage="@drawable/my_drawable"
/>

固定比例：属性viewAspectRatio，这里宽高比是4:3，总之，最后还是能算出图片区域宽高
<com.facebook.drawee.view.SimpleDraweeView
    android:id="@+id/my_image_view"
    android:layout_width="20dp"
    android:layout_height="wrap_content"
    fresco:viewAspectRatio="1.33"
    <!-- other attributes -->

也可以在代码中指定显示比例：mSimpleDraweeView.setAspectRatio(1.33f);

全部属性
<com.facebook.drawee.view.SimpleDraweeView
    android:id="@+id/my_image_view"
    android:layout_width="20dp"
    android:layout_height="20dp"

    fresco:fadeDuration="300"
    fresco:actualImageScaleType="focusCrop"

    fresco:placeholderImage="@color/wait_color"   -----loading图
    fresco:placeholderImageScaleType="fitCenter"
    fresco:failureImage="@drawable/error"         ------error图
    fresco:failureImageScaleType="centerInside"
    fresco:retryImage="@drawable/retrying"       -------重试图：会覆盖error图
    fresco:retryImageScaleType="centerCrop"

    可以自己定义进度条
    fresco:progressBarImage="@drawable/progress_bar"  --如果设置一个进度条图片，提示用户正在加载。该图片会覆盖在 Drawee 上直到图片加载完成
    fresco:progressBarImageScaleType="centerInside"
    fresco:progressBarAutoRotateInterval="1000"

    背景图会最先绘制，在XML中只可以指定一个背景图，但是在JAVA代码中，可以指定多个背景图
    当指定一个背景图列表的时候，列表中的第一项会被首先绘制，绘制在最下层，然后依次往上绘制
    背景图片不支持缩放类型，会被强制到Drawee尺寸大小
    java接口：setBackground, setBackgrounds
    fresco:backgroundImage="@color/blue"

    叠加图会最后被绘制
    XML中只可以指定一个，如果想指定多个，可以通过JAVA代码实现
    当指定的叠加图是一个列表的时候，列表第一个元素会被先绘制，最后一个元素最后被绘制到最上层
    同样的，不支持各种缩放类型
    java接口：setOverlay, setOverlays
    fresco:overlayImage="@drawable/watermark"

    同样不支持缩放，用户按压DraweeView时呈现
    java接口：setPressedStateOverlay
    fresco:pressedStateOverlayImage="@color/red"

    fresco:roundAsCircle="false"     ----圆圈 - 设置roundAsCircle为true

    可使用以下两种方式:
    1 默认使用一个 shader 绘制圆角，但是仅仅占位图和所要显示的图有圆角效果。
    失败示意图和重下载示意图无圆角效果，
    且这种圆角方式不支持动画。

    2 叠加一个solid color来绘制圆角。但是背景需要固定成指定的颜色。
    在XML中指定 roundWithOverlayColor, 或者通过调用setOverlayColor来完成此设定。

    圆角分析：

    fresco:roundedCornerRadius="1dp"  ----圆角 - 设置roundedCornerRadius,设置圆角时，支持4个角不同的半径。XML中无法配置，但可在Java代码中配置
    fresco:roundTopLeft="true"
    fresco:roundTopRight="false"
    fresco:roundBottomLeft="false"
    fresco:roundBottomRight="true"

    fresco:roundWithOverlayColor="@color/blue"
    fresco:roundingBorderWidth="2dp"
    fresco:roundingBorderColor="@color/red"
  />

```

```java
Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/fresco-logo.png");
SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
draweeView.setImageURI(uri);
```

2 支持的uri类型

远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
本地文件	file://	FileInputStream
Content provider	content://	ContentResolver
asset目录下的资源	asset://	AssetManager
res目录下的资源	res://	Resources.openRawResource
res 示例:

Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);

3 DraweeView属性控制

对应xml里的属性
对应java里的GenericDraweeHierarchy类

在java中使用
```java
//对于同一个View，请不要多次调用setHierarchy，即使这个View是可回收的。
//创建 DraweeHierarchy 的较为耗时的一个过程，应该多次利用
List<Drawable> backgroundsList;
List<Drawable> overlaysList;
GenericDraweeHierarchyBuilder builder =
    new GenericDraweeHierarchyBuilder(getResources());
GenericDraweeHierarchy hierarchy = builder
    .setFadeDuration(300)
    .setPlaceholderImage(new MyCustomDrawable())
    .setBackgrounds(backgroundList)
    .setOverlays(overlaysList)
    .build();
mSimpleDraweeView.setHierarchy(hierarchy);


//修改显示属性：DraweeHierarchy 的一些属性可以在运行时改变
GenericDraweeHierarchy hierarchy = mSimpleDraweeView.getHierarchy();

//占位图：修改占位图为资源id,或者修改为一个 Drawable
//在调用setController 或者 setImageURI 之后，占位图开始显示，直到图片加载完成
//默认值: a transparent ColorDrawable
hierarchy.setPlaceholderImage(R.drawable.placeholderId);
hierarchy.setPlaceholderImage(drawable);

//失败占位图：修改占位图为资源id,或者修改为一个 Drawable
//如果URI是无效的，或者下载过程中网络不可用，将会导致加载失败，显示失败占位图
//默认值: The placeholder image
hierarchy.setFailureImage(R.drawable.placeholderId);
hierarchy.setFailureImage(drawable);

//最终图修改缩放类型：最终图默认是centerCrop，占位图默认是cenerInside
//比较特殊的focusCrop模式，需要指定一个中心点
//其他的缩放模式都类似ScaleType
hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//如果你选择缩放类型为 focusCrop，需要指定一个居中点:
hierarchy.setActualImageFocusPoint(point);


//设置颜色过滤矩阵：ColorFilter
ColorFilter filter;
hierarchy.setActualImageColorFilter(filter);

//圆角参数修改：在运行时，不能改变呈现方式: 原本是圆角，不能改为圆圈
RoundingParams roundingParams = hierarchy.getRoundingParams();
roundingParams.setCornersRadius(10);
hierarchy.setRoundingParams(roundingParams);

//圆角参数添加
RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
roundingParams.setOverlayColor(R.color.green);
// 或用 fromCornersRadii 以及 asCircle 方法
genericDraweeHierarchyBuilder.setRoundingParams(roundingParams);

//圆角的限制
* 当使用BITMAP_ONLY（默认）模式时的限制：
    * 并非所有的图片分支部分都可以实现圆角，目前只有占位图片和实际图片可以实现圆角，我们正在努力为背景图片实现圆角功能。
    * 只有BitmapDrawable 和 ColorDrawable类的图片可以实现圆角。
    * 我们目前不支持包括NinePatchDrawable和 ShapeDrawable在内的其他类型图片。（无论他们是在XML或是程序中声明的）
    * 动画不能被圆角。
    * 由于Android的BitmapShader的限制，当一个图片不能覆盖全部的View的时候，边缘部分会被重复显示，而非留白。对这种情况可以使用不同的缩放类型（比如centerCrop）来保证图片覆盖了全部的View。
    * OVERLAY_COLOR模式没有上述限制，但由于这个模式使用在图片上覆盖一个纯色图层的方式来模拟圆角效果，因此只有在图标背景是静止的并且与图层同色的情况下才能获得较好的效果。
    * Drawee 内部实现了一个CLIPPING模式。但由于有些Canvas的实现并不支持路径剪裁（Path Clipping），这个模式被禁用了且不对外开放。并且由于路径剪裁不支持反锯齿，会导致圆角的边缘呈现像素化的效果。
    * 总之，如果生成临时bitmap的方法，所有的上述问题都可以避免。但是这个方法并不被支持因为这会导致很严重的内存问题。
    * 综上所述，在 Android 中实现圆角效果，没有一个绝对好的方案，你必须在上述的方案中进行选择。

```

缩放模式：
center	居中，无缩放。
centerCrop	保持宽高比缩小或放大，使得两边都大于或等于显示边界，且宽或高契合显示边界。居中显示。
focusCrop	同centerCrop, 但居中点不是中点，而是指定的某个点。
centerInside	缩放图片使两边都在显示边界内，居中显示。和 fitCenter 不同，不会对图片进行放大。
如果图尺寸大于显示边界，则保持长宽比缩小图片。
fitCenter	保持宽高比，缩小或者放大，使得图片完全显示在显示边界内，且宽或高契合显示边界。居中显示。
fitStart	同上。但不居中，和显示边界左上对齐。
fitEnd	同fitCenter， 但不居中，和显示边界右下对齐。
fitXY	不保存宽高比，填充满显示边界。
none	如要使用tile mode显示, 需要设置为none
这些缩放类型和Android ImageView 支持的缩放类型几乎一样.

唯一不支持的缩放类型是 matrix。Fresco 提供了 focusCrop 作为补充，通常这个使用效果更佳。
不要使用 android:scaleType 属性，也不要使用 setScaleType() 方法，它们对 Drawees 无效

* 关于focusCrop
    * centerCrop缩放模式会保持长宽比，放大或缩小图片，填充满显示边界，居中显示。这个缩放模式在通常情况下很有用
    * 但是对于人脸等图片时，一味地居中显示，这个模式可能会裁剪掉一些有用的信息
    * 以人脸图片为例，借助一些类库，我们可以识别出人脸所在位置。如果可以设置以人脸位置居中裁剪显示，那么效果会好很多
    * Fresco的focusCrop缩放模式正是为此而设计。只要提供一个居中聚焦点，显示时就会尽量以此点为中心
    * 居中点是以相对方式给出的，比如 (0f, 0f) 是左上对齐显示，(1f, 1f) 是右下角对齐。相对坐标使得居中点位置和具体尺寸无关，这是非常实用的
    * (0.5f, 0.5f) 的居中点位置和缩放类型 centerCrop 是等价的
    * xml设置： fresco:actualImageScaleType="focusCrop"

```java
mSimpleDraweeView
    .getHierarchy()
    .setActualImageFocusPoint(focusPoint);
```



4 加载的图片的设置：DraweeController

所需加载的图片实际是 DraweeController 的一个属性，而不是 DraweeHierarchy 的属性
可使用setImageURI方法或者通过设置DraweeController 来进行设置

```java



ControllerListener listener = new BaseControllerListener() {...}

ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
    .setBackgroundColor(Color.GREEN)
    .build();

Postprocessor myPostprocessor = new Postprocessor() { ... }
ImageRequest request = ImageRequestBuilder
    .newBuilderWithSource(uri)
    .setProgressiveRenderingEnabled(true)  //支持渐进式JPEG
    .setAutoRotateEnabled(true)   //根据exif自动旋转图片，避免用户看到侧着的图片
    .setResizeOptions(new ResizeOptions(width, height)) //图片Resize，比较耗时，且支持jpeg，但开启了向下采样就快了
    .setDownsampleEnabled(true)  // 代替resize，需要先设置ResizeOption，支持jpeg，webp，png，后期版本会默认支持
    .setPostprocessor(myPostprocessor)
    .setImageDecodeOptions(decodeOptions)
    .setLocalThumbnailPreviewsEnabled(true)
    .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)

    .build();

DraweeController controller = Fresco.newDraweeControllerBuilder()
    .setUri(uri)
    .setTapToRetryEnabled(true)
    .setImageRequest(request)
    .setControllerListener(listener)
    .setAutoPlayAnimations(true)   ----加载完自动播放动画，动态图应该用这个

    //多图加载，先显式低分辨率的，再显式高分辨率的
    //和setImageRequest(request)冲突吗？？？？
    .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
    .setImageRequest(ImageRequest.fromUri(highResUri))

    .setOldController(mSimpleDraweeView.getController())
    .build();

mSimpleDraweeView.setController(controller);



//在指定一个新的controller的时候，使用setOldController，这可节省不必要的内存分配

在更进一步的用法中，你需要给Image pipeline 发送一个ImageRequest。下面是一个图片加载后，使用后处理器(postprocessor) 进行图片后处理的例子.

Uri uri;


DraweeController controller = Fresco.newDraweeControllerBuilder()
    .setImageRequest(request)
    .setOldController(mSimpleDraweeView.getController())
    // 其他设置
    .build();

```

*这里涉及到的概念：
    * Controller Listeners
    * ImageRequest
    * 渐进图
    * 后处理器(Postprocessors)
    * 加载多个图片
    * 缩放和旋转图片
    * 显示级别：在缓存，磁盘缓存，下载图片之间选一个级别，指定到哪儿就停止

（1）渐进图：ProgressiveJpeg

现在需要显式的配置，API可能会变，变成setImageUri可以直接支持渐进图

原理我也不懂，大体代码如下：

```jajva
///---`全局配置里`
ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {

  //返回下一个需要解码的扫描次数
  @Override
  public int getNextScanNumberToDecode(int scanNumber) {
    return scanNumber + 2;
  }

  // 确定多少个扫描次数之后的图片才能开始显示。
  public QualityInfo getQualityInfo(int scanNumber) {
    boolean isGoodEnough = (scanNumber >= 5);
    return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
  }
}

ImagePipelineConfig config = ImagePipelineConfig.newBuilder()
    .setProgressiveJpegConfig(pjpeg)
    .build();
Fresco.initialize(context, config);

//下面的实例中，为了实现节省CPU，并不是每个扫描都进行解码
//每次解码完之后，调用getNextScanNumberToDecode, 等待扫描值大于返回值，才有可能进行解码
假设，随着下载的进行，下载完的扫描序列如下: 1, 4, 5, 10。那么：

首次调用getNextScanNumberToDecode返回为2， 因为初始时，解码的扫描数为0。
那么1将不会解码，下载完成4个扫描时，解码一次。下个解码为扫描数为6
5不会解码，10才会解码


///---在每个图片加载请求里显式配置支持渐进式JPEG
Uri uri;
ImageRequest request = ImageRequestBuilder
    .newBuilderWithSource(uri)
    .setProgressiveRenderingEnabled(true)
    .build();
PipelineDraweeController controller = Fresco.newControllerBuilder()
    .setImageRequest(requests)
    .setOldController(mSimpleDraweeView.getController())
    .build();

mSimpleDraweeView.setController(controller);

```

（2）动态图：gif和webP

Fresco 支持 GIF 和 WebP 格式的动画图片。
对于 WebP 格式的动画图的支持包括扩展的 WebP 格式，即使 Android 2.3及其以后那些没有原生 WebP 支持的系统。



`动画现在还不支持 postprocessors`

*如果你希望图片下载完之后自动播放，同时，当View从屏幕移除时，停止播放，只需要在 image request 中简单设置*
```java
Uri uri;
DraweeController controller = Fresco.newDraweeControllerBuilder()
    .setUri(uri)
    .setAutoPlayAnimations(true)  ///-----
    . // other setters
    .build();
mSimpleDraweeView.setController(controller);
```

*手动控制动画图播放*
也许你希望在代码中直接控制动画的播放。这种情况下，你需要监听图片是否加载完毕，然后才能控制动画的播放：
```java
ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
    @Override
    public void onFinalImageSet(
        String id,
        @Nullable ImageInfo imageInfo,
        @Nullable Animatable anim) {
    if (anim != null) {
      // app-specific logic to enable animation starting
      anim.start();
    }
};
```

也提供了对动画的访问
```java
Animatable animation = mSimpleDraweeView.getController().getAnimatable();
if (animation != null) {
  // 开始播放
  animation.start();
  // 一段时间之后，根据业务逻辑，停止播放
  animation.stop();
}
``


（3）ImageRequest，后处理器PostProcessors

http://www.fresco-cn.org/docs/modifying-image.html#_

（4）加载多个图片

-----------情况1  提供一张低分辨率的图片

*先显示低分辨率的图，然后是高分辨率的图*
假设你要显示一张高分辨率的图，但是这张图下载比较耗时。与其一直显示占位图，你可能想要先下载一个较小的缩略图。
这时，你可以设置两个图片的URI，一个是低分辨率的缩略图，一个是高分辨率的图

```java
Uri lowResUri, highResUri;
DraweeController controller = Fresco.newDraweeControllerBuilder()
    .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
    .setImageRequest(ImageRequest.fromUri(highResUri))
    .setOldController(mSimpleDraweeView.getController())
    .build();
mSimpleDraweeView.setController(controller);
```

-----------情况2  缩略图预览，本功能仅支持本地URI，并且是JPEG图片格式

如果本地JPEG图，有EXIF的缩略图，image pipeline 可以立刻返回它作为一个缩略图。
Drawee 会先显示缩略图，完整的清晰大图在 decode 完之后再显示。

```java
Uri uri;
ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
    .setLocalThumbnailPreviewsEnabled(true)
    .build();

DraweeController controller = Fresco.newDraweeControllerBuilder()
    .setImageRequest(request)
    .setOldController(mSimpleDraweeView.getController())
    .build();
mSimpleDraweeView.setController(controller);
```

-----------情况3 加载最先可用的图片

大部分时候，一张图片只有一个 URI。加载它，然后工作完成～

但是假设同一张图片有多个 URI 的情况。比如，你可能上传过一张拍摄的照片。
原始图片太大而不能上传，所以图片首先经过了压缩。
在这种情况下，首先尝试获取本地压缩后的图片 URI，
如果失败的话，尝试获取本地原始图片 URI，
如果还是失败的话，尝试获取上传到网络的图片 URI。
直接下载我们本地可能已经有了的图片不是一件光彩的事。


Image pipeline 会首先从内存中搜寻图片，然后是磁盘缓存，再然后是网络或其他来源。
对于多张图片，不是一张一张按上面的过程去做，而是 pipeline 先检查所有图片是否在内存。
只有没在内存被搜寻到的才会寻找磁盘缓存。还没有被搜寻到的，才会进行一个外部请求。

使用时，创建一个image request 数组，然后传给 ControllerBuilder :

```java
Uri uri1, uri2;
ImageRequest request = ImageRequest.fromUri(uri1);
ImageRequest request2 = ImageRequest.fromUri(uri2);
ImageRequest[] requests = { request1, request2 };

DraweeController controller = Fresco.newDraweeControllerBuilder()
    .setFirstAvailableImageRequests(requests)
    .setOldController(mSimpleDraweeView.getController())
    .build();
mSimpleDraweeView.setController(controller);
//这些请求中只有一个会被展示。第一个被发现的，无论是在内存，磁盘或者网络，都会是被返回的那个。
//pipeline 认为数组中请求的顺序即为优先顺序。
```

----------------情况4：自定义 DataSource Supplier

为了更好的灵活性，你可以在创建 Drawee controller 时自定义 DataSource Supplier。
你可以以 FirstAvailiableDataSourceSupplier,IncreasingQualityDataSourceSupplier为例自己实现 DataSource Supplier，
或者以AbstractDraweeControllerBuilder为例将多个 DataSource Supplier 根据需求组合在一起。


（6）缩放图片

* 基本概念
    * Scaling 是一种画布操作，通常是由硬件加速的。图片实际大小保持不变，它只不过在绘制时被放大或缩小。
    * Resizing 是一种软件执行的管道操作。它返回一张新的，尺寸不同的图片。
    * Downsampling 同样是软件实现的管道操作，替代Resizing，它不是创建一张新的图片，而是在解码时改变图片的大小。

如何实现scaling：
唯一的缺点是当图片远大于显示大小时，会浪费内存
可以通过 GPU 进行加速。这在大部分情况下是最快，同时也是最高效地将图片显示为你想要的尺寸的方式
对于 scale，只需要指定 SimpleDraweeView 中 layout_width 和 layout_height 的大小，就像在其他 Android View 中做的那样。然后指定缩放类型

如何实现Resizing：
你应该只在需要展示一张远大于显示大小的图片时使用 resize 以节省内存。一个例子是当你想要在 1280*720(大约 1MP) 的 view 中显示一张 8MP 的照片时。
一张 8MP 的图片当解码为 4字节/像素的 ARGB 图片时大约占 32MB 的内存。如果 resize 为显示大小，它只占用少于 4MB 内存
*显示jpeg照片时可能能用到，但向下采样更好*
对于网络图片，在考虑 resize 之前，先尝试请求大小合适的图片，省流量，还提高性能

```java
Uri uri = "file:///mnt/sdcard/MyApp/myfile.jpg";
int width = 50, height = 50;
ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
    .setResizeOptions(new ResizeOptions(width, height))
    .build();
PipelineDraweeController controller = Fresco.newDraweeControllerBuilder()
    .setOldController(mDraweeView.getController())
    .setImageRequest(request)
    .build();
mSimpleDraweeView.setController(controller);
```

如何实现down samping
向下采样是一个最近添加到 Fresco 的特性。使用的话需要在设置 image pipeline 时进行设置setDownsampleEnabled(true)

如果开启该选项，pipeline 会向下采样你的图片，代替 resize 操作。你仍然需要像上面那样在每个图片请求中调用 setResizeOptions 。

向下采样在大部分情况下比 resize 更快。除了支持 JPEG 图片，它还支持 PNG 和 WebP(除动画外) 图片。

我们希望在将来的版本中默认开启此选项。

（7）旋转图片

如果看到的图片是侧着的，用户会非常难受。许多设备会在 JPEG 文件的 metadata 中记录下照片的方向。如果你想图片呈现的方向和设备屏幕的方向一致，你可以简单地这样做到:

ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
    .setAutoRotateEnabled(true)
    .build();

（8）Controller Listeners

在这里只能得到图片信息，不能修改图片，修改图片需要用到后处理器
可以得到图片宽高，或许可以在这里先设置一下View的大小，缩放模式

简单定义一个ControllerListener即可，推荐继承BaseControllerListener:

```java
ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
    @Override
    public void onFinalImageSet(
        String id,
        @Nullable ImageInfo imageInfo,
        @Nullable Animatable anim) {
      if (imageInfo == null) {
        return;
      }
      QualityInfo qualityInfo = imageInfo.getQualityInfo();
      FLog.d("Final image received! " +
          "Size %d x %d",
          "Quality level %d, good enough: %s, full quality: %s",
          imageInfo.getWidth(),
          imageInfo.getHeight(),
          qualityInfo.getQuality(),
          qualityInfo.isOfGoodEnoughQuality(),
          qualityInfo.isOfFullQuality());
    }

    @Override
    public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
      FLog.d("Intermediate image received");
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
      FLog.e(getClass(), throwable, "Error loading %s", id)
    }
};

Uri uri;
DraweeController controller = Fresco.newControllerBuilder()
    .setControllerListener(controllerListener)
    .setUri(uri);
    // other setters
    .build();
mSimpleDraweeView.setController(controller);
```
对所有的图片加载，onFinalImageSet 或者 onFailure 都会被触发。前者在成功时，后者在失败时。

如果允许呈现渐进式JPEG，同时图片也是渐进式图片，onIntermediateImageSet会在每个扫描被解码后回调。具体图片的那个扫描会被解码，参见渐进式JPEG图

（9）显示级别

最低请求级别#
Image pipeline 加载图片时有一套明确的请求流程

检查内存缓存，有如，立刻返回。这个操作是实时的。
检查未解码的图片缓存，如有，解码并返回。
检查磁盘缓存，如果有加载，解码，返回。
下载或者加载本地文件。调整大小和旋转（如有），解码并返回。对于网络图来说，这一套流程下来是最耗时的。
setLowestPermittedRequestLevel允许设置一个最低请求级别，请求级别和上面对应地有以下几个取值:

BITMAP_MEMORY_CACHE
ENCODED_MEMORY_CACHE
DISK_CACHE
FULL_FETCH
如果你需要立即取到一个图片，或者在相对比较短时间内取到图片，否则就不显示的情况下，这非常有用

5 进度条

要显示进度，最简单的办法就是在 构建 hierarchy 时使用 ProgressBarDrawable，如下：
.setProgressBarImage(new ProgressBarDrawable())
这样，在 Drawee 的底部就会有一个深蓝色的矩形进度条。

自定义

自定义进度条
如果你想自定义进度条，请注意，如果想精确显示加载进度，需要重写 Drawable.onLevelChange：
```java
class CustomProgressBar extends Drawable {
   @Override
   protected void onLevelChange(int level) {
     // level is on a scale of 0-10,000
     // where 10,000 means fully downloaded

     // your app's logic to change the drawable's
     // appearance here based on progress
   }
}
```

6 自定义View

http://www.fresco-cn.org/docs/writing-custom-views.html#_

实现PhotoView效果：http://bluereader.org/article/111332013
SimpleDraweeView版的photoview：https://github.com/biezhihua/MySimpleDraweeView

7 fresco和线程

* 线程池
    * Image pipeline 默认有3个线程池，7个线程
    * 3个线程用于网络下载
    * 两个线程用于磁盘操作: 本地文件的读取，磁盘缓存操作。
    * 两个线程用于CPU相关的操作: 解码，转换，以及后处理等后台操作。

8 Fresco提供的可关闭的引用，处理5.0之前Bitmap内存问题

5.0有了asheme内存，释放内存不会引发GC

http://www.fresco-cn.org/docs/closeable-references.html#_

 */
public class Flesco {



    public static void initFresco(Context context, String cacheDir){

        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };

        final ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(context)
                .setProgressiveJpegConfig(pjpegConfig)
                .setNetworkFetcher(new HttpUrlConnectionNetworkFetcher())
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryName("image_cache")
                                .setBaseDirectoryPath(new File(cacheDir))
                                .setMaxCacheSize(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnLowDiskSpace(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnVeryLowDiskSpace(20 * ByteConstants.MB)
                                .build())
                .build();
        Fresco.initialize(context, config);
    }

    private static void setImageUri(SimpleDraweeView iv, Uri uri){
        iv.setImageURI(uri);

    }

    public static void setImageUri(SimpleDraweeView iv, String uri){
        if(uri == null || uri.equals("")){
            return;
        }else if(uri.startsWith("http")){
            setImageUri(iv, Uri.parse(uri));
        }else if(uri.startsWith("file")){
            setImageUri(iv, Uri.parse(uri));
        }else if(uri.startsWith("/")){
            //本地绝对路径
            setImageUri(iv, Uri.parse(VanGogh.getUri(uri)));
        }else if(uri.startsWith("asset")){
            setImageUri(iv, Uri.parse(uri));
        }
    }

    public static void setImageUri(SimpleDraweeView iv, String localPath, String remotePath){
        ImageRequest request = ImageRequest.fromUri(localPath);
        ImageRequest request2 = ImageRequest.fromUri(remotePath);
        ImageRequest[] requests = { request, request2 };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setFirstAvailableImageRequests(requests)
                .setOldController(iv.getController())
                .build();
        iv.setController(controller);
    }

    public static void setImageResource(SimpleDraweeView iv, int id){
        Uri uri = Uri.parse("res:///" + id);
        setImageUri(iv, uri);
    }

//    public static void getBitmapInputStream(final DataSubscriberCallback callback, String thumb) {
//        if (TextUtils.isEmpty(thumb) && callback != null) {
//            callback.onFailed();
//            return;
//        }
//        ImageRequest imageRequest = ImageRequest.fromUri(thumb);
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        DataSource<CloseableReference<PooledByteBuffer>> dataSource = imagePipeline
//                .fetchEncodedImage(imageRequest, null);
//        dataSource.subscribe(new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
//            @Override
//            protected void onNewResultImpl(
//                    DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
//                try {
//                    CloseableReference<PooledByteBuffer> imageReference = dataSource.getResult();
//                    if (imageReference != null) {
//                        try {
//                            PooledByteBuffer image = imageReference.get();
//                            // do something with the image
//                            PooledByteBufferInputStream sourceIs = new PooledByteBufferInputStream(
//                                    image);
//                            BufferedInputStream bis = new BufferedInputStream(sourceIs);
//                            if (callback != null) {
//                                callback.onSuccess(bis);
//                                return;
//                            }
//                        } finally {
//                            CloseableReference.closeSafely(imageReference);
//                        }
//                    }
//                    if (callback != null)
//                        callback.onFailed();
//                } finally {
//                    dataSource.close();
//                }
//            }
//
//            @Override
//            protected void onFailureImpl(
//                    DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
//                if (callback != null)
//                    callback.onFailed();
//            }
//        }, CallerThreadExecutor.getInstance());
//    }

}
