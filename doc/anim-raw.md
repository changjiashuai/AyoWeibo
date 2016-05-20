动画1：帧动画
==========================================

几张图片，不断交替，产生动画，最原始也是最牛逼的方式


xml代码：res/drawable目录里

```xml
<?xml version="1.0" encoding="utf-8"?>
<animation-list
	android:oneshot="false"
	xmlns:android="http://schemas.android.com/apk/res/android">
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading1" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading2" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading3" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading4" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading5" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading6" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading7" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading8" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading9" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading10" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading11" />
	<item android:duration="100"  android:drawable="@drawable/ic_customer_loading12" />
</animation-list>


<ImageView android:id="@+id/animationIV"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="5px"
            android:src="@drawable/animation1"/>
```

怎么加载：
```java
animationIV = (ImageView) findViewById(R.id.animationIV);
animationIV.setImageResource(R.drawable.animation1);
animationDrawable = (AnimationDrawable) animationIV.getDrawable();
animationDrawable.start();
animationDrawable.stop();
```