原生对话框：AlertDialog
===========================

support v7里也有个AlertDialog，下面都是基于这个

三个核心参数：title，icon，msg，不设置就没有，设置空也是没设置，也不占地，都不设置就只显示个黑色透明背景

没有title，设置icon也没用

##第一种 普通对话框

```java
AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
builder.setTitle("普通对话框");//标题
builder.setMessage("这是一个普通的对话框");//信息
builder.setIcon(R.drawable.ic_launcher);//图标
builder.create();//创建
builder.show();//显示
```


##第二种 确定取消对话框

```java
AlertDialog.Builder builder2=new AlertDialog.Builder(MainActivity.this);
builder2.setTitle("确定取消对话框");
builder2.setMessage("请选择确定或取消");
builder2.setIcon(R.drawable.ic_launcher);
builder2.setPositiveButton("确定", new OnClickListener() {
        //正能量按钮 Positive
        @Override
        public void onClick(DialogInterface dialog, int which) {
                //这里写点击按钮后的逻辑代码
                Toast.makeText(MainActivity.this, "你点击了确定", 0).show();
        }
});
builder2.setNegativeButton("取消", new OnClickListener() {
        //负能量按钮 NegativeButton
        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"你选择了取消",0).show();
        }
});
builder2.create().show();
```

##第三种 多按钮对话框

```java
AlertDialog.Builder builder3=new AlertDialog.Builder(MainActivity.this);
builder3.setTitle("多个按钮对话框");
builder3.setMessage("请选择");
builder3.setIcon(R.drawable.ic_launcher);
builder3.setPositiveButton("继续浏览", new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"继续浏览精彩内容",0).show();
        }
});
builder3.setNeutralButton("暂停休息", new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"起来活动活动吧", 0).show();
        }
});
builder3.setNegativeButton("离开页面", new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"欢迎下次使用", 0).show();
        }
});
builder3.create().show();
```

##第四种 列表对话框

item的布局不可定制

```xml
先在string.xml中添加以下代码
<string-array
        name="oem">
        <item >小米</item>
        <item >荣耀</item>
        <item >魅族</item>
        <item >乐视</item>
        <item >奇酷</item>
        <item >锤子</item>
    </string-array>
```

```java
//然后添加逻辑代码
final String arrItem[]=getResources().getStringArray(R.array.oem);
builder4.setItems(arrItem, new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"你选择了第"+arrItem[which],0).show();
        }
});
builder4.create().show();
```


##第五种 带Adapter的对话框

item的布局可以定制，传入一个adapter

```java
AlertDialog.Builder builder5=new AlertDialog.Builder(MainActivity.this);
builder5.setTitle("带Adapter的对话框");
builder5.setIcon(R.drawable.ic_launcher);
//获取数据源
//创建一个List对象并实例化
final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//图片
int arrImgID[]={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
                R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
for (int i = 0; i < arrImgID.length; i++) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("img", arrImgID[i]);
        map.put("title", "title"+i);
        list.add(map);
}
//创建Adapter对象并实例化
SimpleAdapter adapter=new SimpleAdapter(
                MainActivity.this,
                list,
                R.layout.layout_test1,
                new String[]{"img","title"},
                new int[]{R.id.iv,R.id.tv});
//将数据填充到Adapter
builder5.setAdapter(adapter, new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "你选择了"+list.get(which).get("title").toString().trim(), 0).show();
        }
});
builder5.create().show();
```

##第六种 单选对话框

可以不设置确定取消按钮，但不设置的话，单选点击之后就得立马dismiss吧，单选就显得没意义了

```java
AlertDialog.Builder builder6=new AlertDialog.Builder(MainActivity.this);
builder6.setTitle("单选对话框");
builder6.setIcon(R.drawable.ic_launcher);
//参数1  item数据源       参数2   默认选中的item  参数3 item点击监听
builder6.setSingleChoiceItems(R.array.oem, 0, new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, which+"", 0).show();
        }
});
//设置按钮
builder6.setPositiveButton("确定", new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
});
builder6.create().show();
```

##第七种 多选对话框

```java
AlertDialog.Builder builder7=new AlertDialog.Builder(MainActivity.this);
builder7.setTitle("多选对话框");
builder7.setIcon(R.drawable.ic_launcher);
builder7.setMultiChoiceItems(R.array.oem, null, new OnMultiChoiceClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Toast.makeText(MainActivity.this, which+""+isChecked, 0).show();
        }
});
builder7.create().show();
```

##第八种 日期对话框

```java
//创建DatePickerDialog对象并实例化
//国内外日期计算不同    注意此处输出月份需+1   默认设置月份需-1
DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this,
                new OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                        int dayOfMonth) {
                                Toast.makeText(MainActivity.this,
                                                year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日", 0).show();
                        }
                },
                2015, 8, 21);
//Date和Time只用show()  不用create()
datePickerDialog.show();
```

##第九种 时间对话框

```java
TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity.this,
    new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Toast.makeText(MainActivity.this,
                                    hourOfDay+"点"+minute+"分", 0).show();
            }
    },
    17, 49, true);
timePickerDialog.show();
```

##第十种 自定义对话框
```java
AlertDialog.Builder builder10=new AlertDialog.Builder(MainActivity.this);
builder10.setTitle("自定义对话框");
builder10.setIcon(R.drawable.ic_launcher);
//获取自定义对话框View
View view=LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_test2, null);
//获取控件
final EditText et_name=(EditText)view.findViewById(R.id.et_name);
final EditText et_pwd=(EditText)view.findViewById(R.id.et_pwd);
//设置按钮
builder10.setPositiveButton("确定", new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "您的信息为 姓名:"+et_name.getText().toString()+" 密码:"+et_pwd.getText().toString(), 0).show();
        }
});
//加载自定义布局
builder10.setView(view).create().show();
```
