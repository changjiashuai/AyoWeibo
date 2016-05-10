Android2016：Ayo库的sample工程以及团队成员学习demo展示平台
===========================


## 1 小键盘管理

### 禁止自动弹出

方法1：刚进入Activity时，禁止小键盘弹出:
android:windowSoftInputMode="adjustPan|stateHidden"
```xml
<activity
    android:name="com.iwomedia.zhaoyang.ui.shop.ShopAllListActivity"
    android:configChanges="orientation|keyboardHidden|navigation"
    android:windowSoftInputMode="adjustPan|stateHidden"
    android:screenOrientation="portrait"
    android:theme="@style/Theme.NoShadow.NoTitle" />
```

方法2：如果有Tab切换，则上面的方法就不起作用，可以：

```java
//在xml文件中加入一个隐藏的TextView
<TextView  
    android:id="@+id/config_hidden"  
    android:layout_width="wrap_content"  
    android:layout_height="wrap_content"  
    android:focusable="true"  
    android:focusableInTouchMode="true"  
        />  

//然后再在Activity中加入:
TextView config_hidden = (TextView) this.findViewById(R.id.config_hidden);  
config_hidden.requestFocus();  
```