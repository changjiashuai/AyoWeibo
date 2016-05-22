TextView
===========================

* 本文探讨的是：
    * TextView到底能将文本显示到什么程度
    * 常用的显示模式
    * 字体

##<a name="text"/>1 显示html

**TextView显示html标签**
```java
Spanned s = Html.fromHtml(string);
tv_title.setText(s);
```

**自定义标签**
    默认支持的标签：参考android.text.Html源码的handleStartTag方法


* 标签的处理其实涉及到了：
    * TextView，Html， Html.TagHandler等接口的使用
    * xml处理：处理html标签文本，主要是截取出html包含的文本，属性等
    * 使用SpannabeString的setSpan方法，配合各种span，关于span参考下一节

关于TagHandler的用法，可以参考demo中的MxgsaTagHandler和HtmlHandlerActivity这两个类

参考文章：http://www.cnblogs.com/mxgsa/archive/2012/11/15/2760256.html


```java

///这个例子是一个超链接显示，定义了一个mxgsa标签，将包裹的文本显示为超链接，并且可以点击

import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;

public class MxgsaTagHandler implements TagHandler{
    private int sIndex = 0;
    private  int eIndex=0;
    private final Context mContext;

    public MxgsaTagHandler(Context context){
        mContext=context;
    }

    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        //这里要做的工作就是处理tag标签包裹的文本output
        //xmlReader里面是什么还不清楚
        if (tag.toLowerCase().equals("mxgsa")) {
            if (opening) {
                sIndex=output.length();
            }else {
                eIndex=output.length();
                output.setSpan(new MxgsaSpan(), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
    private class MxgsaSpan extends ClickableSpan implements OnClickListener{
        @Override
        public void onClick(View widget) {
            // TODO Auto-generated method stub
            //具体代码，可以是跳转页面，可以是弹出对话框，下面是跳转页面
            mContext.startActivity(new Intent(mContext,MainActivity.class));
        }
    }

}
```

```java
///使用mxgsa标签：
final String txt = "测试自定义标签：<br><h1><mxgsa>测试自定义标签</mxgsa></h1>";
tv.setText(Html.fromHtml(txt, null, new MxgsaTagHandler(this)));
tv.setClickable(true);
tv.setMovementMethod(LinkMovementMethod.getInstance());  //想让标签可点击，必须调用这个
```


```java
//下面再给出个例子，探讨handleTag到底怎么被调用，html标签到底怎么解析

/**
 *  原文是：<span style="{color:#e60012}">哈哈哈</span>：
 *  html--第1次进来，opening=true, output=, tag=html
 *  html--第2次进来，opening=true, output=, tag=body
 *  html--第3次进来，opening=true, output=, tag=span           ---
 *  html--第4次进来，opening=false, output=哈哈哈, tag=span     ---
 *  html--第5次进来，opening=false, output=哈哈哈, tag=body
 *  html--第6次进来，opening=false, output=哈哈哈, tag=html
 * @param opening
 * @param tag
 * @param output
 * @param xmlReader
 */
public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
    count++;
    JLog.i("html", "html--第" + count + "次进来，opening=" + opening + ", output=" + output + ", tag=" + tag);
    if (tag.toLowerCase().equals("span")) {
        if (opening) {
            sIndex = output.length();
        }else {
            eIndex = output.length();
            output.setSpan(new MxgsaSpan(), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}


////现在尝试使用XmlReader读出span的属性style，获取其中的color
//问题是，现在handleTag只进一次了，而且output是空
public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
    count++;
    JLog.i("html", "html--第" + count + "次进来，opening=" + opening + ", output=" + output + ", tag=" + tag);

    //如果在这里直接用XmlReader解析，也没有解析多次？？比较奇怪，还是去看看源码吧
    if(count == 1){
        xmlReader.setContentHandler(new XMLContentHandler());
    }else{
        xmlReader.setContentHandler(null);
    }

    if (tag.toLowerCase().equals("span")) {
        if (opening) {
            sIndex = output.length();
            //如果在这里用XmlReader解析，已经过了<span>标签的开始了，好像是解析的这部分：哈哈哈</span>
        }else {
            eIndex = output.length();
            if(fontColor == null || fontColor.equals("")){
                fontColor = "#000000";
            }else{

            }
            JLog.i("html", "html--第" + count + "次进来，fontColor = " + fontColor);
            output.setSpan(new ForegroundColorSpan(Color.parseColor(fontColor)), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


    }
}

private String fontColor = "";
//    private String output = "";
//    private int startIndex = 0;
//    private int endIndex = 0;

public class XMLContentHandler extends DefaultHandler {
    private static final String TAG = "XMLContentHandler";

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        Log.i(TAG, "解析内容：" + new String(ch, start, length));
    }
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.i(TAG, "文档解析完毕。");
    }
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        Log.i(TAG, localName+"解析完毕");
    }
    @Override
    public void startDocument() throws SAXException {
        Log.i(TAG, "开始解析... ...");
    }
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        Log.i(TAG, "解析元素："+localName);

        if(localName.equals("span")){
            Log.i(TAG, "解析元素："+localName);
            if(attributes != null){
                for(int i = 0; i < attributes.getLength(); i++){
                    Log.i(TAG, "====" + attributes.getLocalName(i) + ": " + attributes.getQName(i) + ": " + attributes.getValue(i));
                    if(attributes.getLocalName(i).equals("style")){
                        String style = attributes.getValue(i); //{color:#e60012}
                        fontColor = style.replace("{", "").replace("}", "").replace("color", "").replace(":", "");
                        Log.i(TAG, "fontColor=" + fontColor);
                    }
                }
            }
        }
    }
}

```

##<a name="custom_expand"/>自定义标签的替代方案

**自定义标签扩展**

    安卓提供的自定义标签的疑问：
    不知道怎么拿到标签的属性，例如解析<span style="{color:#e60012}">哈哈哈</span>

**Ayo库提供了自定义标签的替代方案:**

可以参考这个工程：[HtmlTagHandler](https://github.com/cowthan/HtmlTagHandler)

已经整合到Ayo库中了




##2 spannable到底怎么用

一段文本中，如果有多种文本格式，可以使用spannable对各段文本设置不同格式

先给个例子，这个如何实现？

![](http://7xo0ny.com1.z0.glb.clouddn.com/ayo_v_tv_1.png)

```java
//使用Spannable
String content = "目前有{numHospital}家医院{numSeller}位咨询师";
content = content.replace("{numHospital}", info.hospitalNum).replace("{numSeller}", info.zxsNum);
Spanned s = Html.fromHtml(content);
int index0 = content.indexOf("前有")+2;
int index1 = content.indexOf("家医院") + 3;
int index2 = content.indexOf("位咨");

msp = new SpannableString(s);
msp.setSpan(new RelativeSizeSpan(1.25f), index0, index1-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半  
msp.setSpan(new RelativeSizeSpan(1.2f), index1,index2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半  
msp.setSpan(new ForegroundColorSpan(Color.parseColor(color)), index0, index1-3,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色  
msp.setSpan(new ForegroundColorSpan(Color.parseColor(color)),index1,index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色 
msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), index0, index1-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体  
msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), index1,index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体  
msp.setSpan(new ScaleXSpan(0.9f), index0, index1-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
msp.setSpan(new ScaleXSpan(0.9f), index1,index2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
tv_info.setText(msp);

```

#####

* 先说setSpan的最后一个参数：Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    * 是用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果
    * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
    * Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
    * Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
    * Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)

#####

TextView可以设置的显示样式：

![](http://7xo0ny.com1.z0.glb.clouddn.com/ayo_368eff70-10ae-393c-8e00-e94ee213c1a2.png)


* 字体：TypefaceSpan
    * TypefaceSpan("monospace")
    * 字体有：default,default-bold,monospace,serif,sans-serif

* 字体样式：StyleSpan
    * android.graphics.Typeface.NORMAL
    * android.graphics.Typeface.BOLD
    * android.graphics.Typeface.ITALIC
    * android.graphics.Typeface.BOLD_ITALIC

* 字体大小：
    * 绝对大小，单位像素：AbsoluteSizeSpan(20)
    * 相对大小，指定默认字体大小的多少倍：RelativeSizeSpan
    * 缩放：ScaleXSpan，表示为默认字体宽度的多少倍

* 字体颜色：
    * 前景色：ForegroundColorSpan(Color.MAGENTA)
    * 背景色：BackgroundColorSpan(Color.CYAN)

* 字体综合：TextAppearanceSpan
    * 可以设置字体，样式，大小，颜色等

```java
//设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
ColorStateList csllink = null;
ColorStateList csl = null;
XmlResourceParser xppcolor=getResources().getXml (R.color.color);
try {
    csl= ColorStateList.createFromXml(getResources(),xppcolor);
}catch(XmlPullParserException e){
    // TODO: handle exception
    e.printStackTrace();
}catch(IOException e){
    // TODO: handle exception
    e.printStackTrace();
}

XmlResourceParser xpplinkcolor=getResources().getXml(R.color.linkcolor);
try {
    csllink= ColorStateList.createFromXml(getResources(),xpplinkcolor);
}catch(XmlPullParserException e){
    // TODO: handle exception
    e.printStackTrace();
}catch(IOException e){
    // TODO: handle exception
    e.printStackTrace();
}
msp.setSpan(new TextAppearanceSpan("monospace",android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
```

* 各种线：
    * 下划线：UnderlineSpan
    * 删除线：StrikethroughSpan

* 上标下标：
    * 下标：SubscriptSpan
    * 上标：SuperscriptSpan

* 超链接：
    * 电话：URLSpan("tel:4155551212")
    * 邮件：URLSpan("mailto:webmaster@google.com")
    * 网页：URLSpan("http://www.baidu.com")
    * 短信：URLSpan("sms:4155551212")，或者smsto
    * 地图：URLSpan("geo:38.899533,-77.036476")


* 项目符号
    * BulletSpan

```java
//设置项目符号
msp.setSpan(new BulletSpan(android.text.style.BulletSpan.STANDARD_GAP_WIDTH,Color.GREEN), 0 ,msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色
```

* 设置图片
    * ImageSpan

```java
//设置图片
Drawable drawable = getResources().getDrawable(R.drawable.icon);
drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
msp.setSpan(new ImageSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

mTextView.setText(msp);
mTextView.setMovementMethod(LinkMovementMethod.getInstance());
```


##3 AwesomeTextView

一段文本中，如果有多种文本格式，可以使用AwesomeTextView对各段文本设置不同格式

其内部实现用到了将view转为bitmap，可能存在性能上的问题，但能做到的事肯定比spannable多

```java
String amount = content.getAmount();
viewHolder.tv_amount.setText("￥" + amount);
if(SB.common.isNotEmpty(amount)){
   	AwesomeTextHandler awesomeTextViewHandler = new AwesomeTextHandler();
    awesomeTextViewHandler
   		.addViewSpanRenderer(amount, new CardAmountSpanRenderer())
    	.setView(viewHolder.tv_amount);
}

public class CardAmountSpanRenderer implements AwesomeTextHandler.ViewSpanRenderer {

    private final static int textSizeInDips = 20;

    @Override
    public View getView(String text, Context context) {
    	TextView view = new TextView(context);
    	view.setTextColor(Color.parseColor("#ff8d84"));
    	view.setTextSize(SB.display.sp2px(App.app, 20));
    	SpannableString  ss = new SpannableString(text);
    	view.setText(ss);
        return view;
    }
}
```
* 注意addViewSpanRenderer
    * 参数1可以是正则表达式，告诉renderer渲染哪段文本
	* 参数2是个ViewSpanRenderer


##4 BaggerView

效果如下：

![](http://7xo0ny.com1.z0.glb.clouddn.com/ayo_badgerview.png)

未读消息提示，个位数时是圆，两位数或者更多时，是矩形圆

来源：https://github.com/stefanjauker/BadgeView

有个bug：华为pad上，setTargetView之后，角标跑后边去了，其他手机都是在上面啊我日

默认是红色，可以修改

```xml
<com.iwomedia.zhaoyang.widget.BadgeView
	    android:id="@+id/tv_unread_count"
	    android:layout_width="15dp"
	    android:layout_height="15dp"
	    android:textColor="@android:color/white"
	    android:textSize="10sp"
	    />

```

```java
//可以当TextView使用：默认是红底白字，红底还带点透明，可以当未读消息的小红点用
BadgeView bagde = findViewById(R.id.tv_unread_count);
badge.setText("99+");

//作者提供了更多功能：有demo
BadgeView badge = new BadgeView(getActivity());
badge.setTargetView(myView);
badge.setBadgeCount(42);

```


##7 广告条

来自：https://github.com/Rowandjj/VerticalBannerView

效果如下：

![](http://7xo0ny.com1.z0.glb.clouddn.com/ayo_verticalbannerview.gif)

* Feature
    * 可自由定义展示的内容
    * 使用方式类似ListView/RecyclerView
    * 可为当前的内容添加各种事件,比如点击打开某个页面等


使用方法类似ListView

1. 定义item的布局

    ```
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:background="#000"
                    android:gravity="center_vertical"
                  android:layout_height="60dp">

        <ImageView
            android:layout_marginLeft="10dp"
            android:id="@+id/iv"
            android:src="@android:drawable/ic_dialog_email"
            android:layout_width="30dp"
            android:layout_height="30dp"
            />

        <TextView
            android:id="@+id/tv_02"
            android:text="hello world"
            android:layout_marginLeft="10dp"
            android:textSize="16sp"
            android:layout_toRightOf="@id/iv"
            android:paddingLeft="5dp"
            android:textColor="#fff"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

        <TextView
            android:layout_below="@id/tv_02"
            android:text="I am detail message......"
            android:layout_marginLeft="10dp"
            android:textSize="14sp"
            android:layout_centerVertical="true"
            android:layout_toRightOf="@id/iv"
            android:paddingLeft="5dp"
            android:textColor="#fff"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
    </RelativeLayout>
    ```

2. 实现adapter

    ```
    public class SampleAdapter01 extends BaseBannerAdapter<Model01> {

        private List<Model01> mDatas;

        public SampleAdapter01(List<Model01> datas) {
            super(datas);
        }

        @Override
        public View getView(VerticalBannerView parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_01,null);
        }

        @Override
        public void setItem(final View view, final Model01 data) {
            TextView tv = (TextView) view.findViewById(R.id.tv_01);
            tv.setText(data.title);
            //你可以增加点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //比如打开url
                    Toast.makeText(view.getContext(),data.url,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    ```

3. 在布局中增加view的声明

    ```
       <com.taobao.library.VerticalBannerView
                android:id="@+id/banner_01"
                android:layout_width="match_parent"
                android:layout_height="40dp"
                app:animDuration="900" ---->动画间隔900ms
                app:gap="2000"/> ----->切换时长2000ms
    ```

4. 设置Adapter并启动

    ```
    List<Model01> datas02 = new ArrayList<>();
    datas02.add(new Model01("Life was so beautiful","--->Life was so beautiful,"));
    datas02.add(new Model01("From morning to evening","--->From morning to evening"));
    datas02.add(new Model01("We enjoyed the road to school","--->We enjoyed the road to school,"));
    datas02.add(new Model01("Birds chirped and Peace lingered","--->Birds chirped and Peace lingered"));
    final SampleAdapter02 adapter02 = new SampleAdapter02(datas02);
    final VerticalBannerView banner02 = (VerticalBannerView) findViewById(R.id.banner_02);
    banner02.setAdapter(adapter02);
    banner02.start();
    ```

5. 更新数据

    ```
    List<Model01> newData = new ArrayList<>();
    newData.add(new Model01("锄禾日当午","--->锄禾日当午"));
    newData.add(new Model01("汗滴禾下土","--->汗滴禾下土"));
    newData.add(new Model01("谁知盘中餐","--->谁知盘中餐"));
    newData.add(new Model01("粒粒皆辛苦","--->粒粒皆辛苦"));
    adapter02.setData(newData);

    ```

6. 停止

    ```
    banner02.stop();
    ``

##8 装饰：Drawable及IconTextView

