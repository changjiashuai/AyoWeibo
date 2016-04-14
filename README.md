# AyoHttpWeiboDemo
AyoHttp框架的demo，本意是提供一套顶层框架，用来任意配置底层实现，如okhttp，URLConnection，volly等，
但现在各方面还都不够完善，demo也不够完善

##########
    2016/4/14有感而发
    给你们讲讲我写这个框架的感受，让你们引以为戒，http作为天天用到的一个东西，底层不懂，这没关系，还可以用，
    要封装个好用的框架，也封装不到retrofit那个层次，绕来绕去把自己都绕进去了，
    底层和架构都不追求了，只追求给用户提供个友好的接口吧，最后发现对外接口也复杂，不得不写文档，
    文档说来说去也说不清楚，
    最后说http不是我的强项吧，应该写个别的库，发现别的技术点也没有深入理解的
    所以学习之路，还是任重而道远，下一步就是深入研究各个技术点，以前是横向发展，以后要纵向发展
    以前的目标是会用，以后的目标是能写

##########
* 用来测试的接口提供者：
    * 微博：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
    * 天狗云美女接口：http://www.tngou.net/doc/gallery
    * 聚合数据：https://www.juhe.cn/docs


#########
* 后期工作：
    * json库分离出来吧，让http库不依赖json相关的东西
    * 参考retrofit和okhttp，想办法支持RxJava
    * 架构可能要重整
    * 上传文件没测试，post file和post form两种形式
    * 下载文件还没实现啊，XUtils都他妈给删了
    * flag和cancel相关的实现
    * 缓存需要在这里考虑吗？
    * 三大超时时间的选择
    * 重发机制，主要是volly（有还是没有）
    * 对put，head，delete等的支持

#########
* 注意：
    * 要想看微博相关接口，必须使用weibosdk/doc/debug.keystore来打调试包，你得把C:/Users/xx/.android/debug.keystore换成这个
    * 要想看微博相关接口，先通过微博授权，就是主页第二个按钮，用微博登录一下，这就是为什么要替换dubug.keystore，这其实是微博官方demo的keystore

###1 怎么用

####第0步 初始化http库

#####

传入个全局Context，你懂的 `AyoHttp.init(this)`

####第1步 定义顶层json业务对象

#####

你得定义个ResponseModel的子类，对应的是你要解析的json，把状态信息，失败信息，业务数据分离出来

例如，微博开放api的json格式是：
成功时：
```json
{
    "statuses": [
        {
            "created_at": "Tue May 31 17:46:55 +0800 2011",
            "id": 11488058246,
            "text": "求关注。"，
            "user": {
                "id": 1404376560,
                "screen_name": "zaku",
                "name": "zaku",
            }
        },
        ..
    ],
    "previous_cursor": 0,
    "next_cursor": 11488013766,
    "total_number": 81655
}
```

错误时：
```json
{
    "error": "source paramter(appkey) is missing",
    "error_code": 10006,
    "request": "/2/statuses/public_timeline.json"
}
```

* 简单分析一下：
    * 成功时，返回的json里就只有业务数据，没有状态信息，原则就是没有就是最好的结果
    * 失败时，返回在哪儿发生了什么错误，错误代号
    * 所以能知道，没有error或者error_code字段时，就是成功状态

对应的ResponseModel是：
```java
public class WeiboResponseModel extends ResponseModel {

    public String error;
    public int error_code;
    public String request;

    public String raw;

    @Override
    public boolean isOk() {
        return error == null || error.equals("");
    }

    @Override
    public int getResultCode() {
        return error_code;
    }

    @Override
    public String getFailMessage() {
        return error;
    }

    @Override
    public String getResult() {
        return raw;
    }
}
```

注意这里面多了个raw字段，这个代表原始json串，这个原始json串需要被传进来，下一步还要被转换为业务对象
加这个字段的原因是业务数据在这里无法作为ResponseModel的一个字段通过json解析进来，所以手动传进来


####第2步 定义BaseHttpDispatcher，指定如何解析json，如何解析ResponseModel，并调用BaseHttpCallback的相关接口

#####

```java
public class WeiboJsonDispatcher<T> extends JsonResponseDispatcher {
    /**
     * @param elementClass
     */
    public WeiboJsonDispatcher(Class elementClass) {
        super(elementClass);
    }

    @Override
    public ResponseModel parseResponseToModel(String response, Class clazz) {
        WeiboResponseModel r = JsonUtils.getBean(response, WeiboResponseModel.class);
        r.raw = response;
        return r;
    }
}
```

这里要注意的是，JsonResponseDispatcher其实可以直接处理如下json形式：
```json
{
    code: 0,
    message: "错误信息,成功则空",
    result:{
        所有业务字段放在这里
    }
}
```

BaseHttpDispatcher的两个接口：
```java
public abstract <T> void process(String flag, AyoResponse resp, BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz);
public abstract ResponseModel parseResponseToModel(String response, Class<? extends ResponseModel> clazz);
```

process()接口的任务是解析resp中的data字符串（可能是json，xml，html），并根据各种情况来调用callback
parseResponseToModel()的任务是把原始字符串转为顶层业务对象，里面应该包含成功失败信息，成功时还有业务字段


####第3步 定制你的接口规则

#####

```java
//每个接口都需要传入两个header：os和version
//以WeiboResonseModel作为统一的顶层业务bean，当然也可以每个接口有不同定制
//HttpWorkerUseOkhttp表示使用okhttp作为底层实现

public static AyoRequest request(){
    AyoRequest r = AyoRequest.newInstance()
            .header("os", "android")      //可选
            .header("version", "1.0.0")   // 可选
            .myResponseClass(WeiboResponseModel.class)  //必须，且理论上应该项目唯一
                    //.worker(new HttpWorkerUseXUtils(true)); //必须
                    //.worker(new HttpWorkerUseOkhttp()); //必须
            .worker(new HttpWorkerUseOkhttp()); //必须
    return r;
}
```

####第4步 发起请求，处理响应

#####

```java
public static void getPublicTimelines(String flag, BaseHttpCallback<ResponseTimeline> callback){
    WeiboApi.request().flag(flag)
            .url("https://api.weibo.com/2/statuses/public_timeline.json")
            .method("get")
            .param("access_token", AccessTokenKeeper.readAccessToken(App.app).getToken())
            .param("count", "50")
            .param("page", "1")
            .param("base_app", "0")
            .go(new WeiboJsonDispatcher(ResponseTimeline.class), callback);
}
```

go()就是发起异步请求，go了之后要考虑的就是返回之后的问题：
new WeiboJsonDispatcher(ResponseTimeline.class)表示返回的是json，把json转成WeiboResponseModel之后，
如果失败，回调callback的失败接口
如果成功，把业务字段转成ResponseTimeline，再回调callback的成功接口
这么一分析，myResponseClass(WeiboResponseModel.class)这个方法似乎不应该是Request提供的，这是响应相关的事


响应相关的应该都给Dispatcher，并且理想情况是项目唯一，可以统一配置：
顶层响应bean
callback
json解析器
xml解析器
html解析器

```java
WeiboApi.getPublicTimelines("公共微博", new BaseHttpCallback<ResponseTimeline>() {
        @Override
        public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, ResponseTimeline responseTimeline) {
            if (isSuccess) {
                //成功，访问responseTimeline里的数据，此时不要依赖于resp了
            } else {
                //失败，访问ResponseModel里的错误信息，
                //HttpProblem对应几种错误情况
                Toast.makeText(App.app, resp.getFailMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    });
}
```

* HttpProblem对应几种错误情况
    * OK，成功
    * OFFLINE，手机没联网
    * SERVER_ERROR，http请求错误，状态码肯定不是[200, 300)，或者连接超时了，网不好时，服务器出问题时发生，需要考虑如何给用户提示
    * DATA_ERROR，http返回状态码是200到300，但本地处理返回数据时发生异常，如json转换时的类型对应问题，生产环境不应该发生这个问题
    * LOGIN_FAIL，http返回状态码是200到300，但业务逻辑出错，如发评论但有违禁词，看文章但已删除等，这个错误情况会经常发生
    * UNKNOWN，不知道出什么错了，这个情况竟然会有可能发生，我还没找到原因






