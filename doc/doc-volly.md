Volly源码解析
===========================
* HTTP请求
    * 如何构造一个请求
    * 发起请求，管理请求：RequestQueue
    * 响应
    * 使用详解

####
##1 构造一个请求：Request

* http请求：
    * Request.Method枚举了所有请求方式
    * get请求：需要传入url，拼接query parameters
        * StringRequest
        * RequestManager.get()
    * post请求1：普通post，带request parameters
        * StringRequest
        * RequestManager.post()
    * post请求2：传入StringEntity
        * ByteArrayRequest，但是这个类只有package权限
        * 只能用RequestManager.sendRequest或者post
    * post请求3：上传文件
        * 用到RequestMap，这个类可以往里传file，inputstream等
        * RequestManager.sendRequest或者post方法中参数data的就是RequestMap
    * PUT
    * DELETE
    * HEAD
    * OPTIONS
    * TRACE
    * PATCH

* 测试接口
    * get :
    * post 1: StringRequest
    * post 2: 得用到
    * 上传文件
    * 下载文件
    * PUT
    * DELETE
    * HEAD
    * OPTIONS
    * TRACE
    * PATCH
    * 仿验证码请求


###HurlStack

1 处理底层http请求，使用了URLConnection，大体上就是根据Request，返回Response

2 POST，PUT，PATCH这三种请求方式会添加body，所有会调用：addBodyIfExists
```java
byte[] body = request.getBody();
if (body != null) {
    connection.setDoOutput(true);
    connection.addRequestProperty(HEADER_CONTENT_TYPE, request.getBodyContentType());
    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
    out.write(body);
    out.close();
}

///注意，body分多种，post请求参数，post entity，文件上传等
```

注意，request.getBody()，每一种Request都会把自己的所有参数转化为一个body，涉及到键值对，字符串，文件，输入流等

3 超时时间等在这里设置

4 返回HttpResponse：

* HttpResponse里封装了：
    * connection.getInputStream()，还没读呢
    * content length等参数
    * 响应的header


###Network，及其子类BasicNetwork

* 有何用
    * 驱动了HurlStack
    * 处理缓存：底层的缓存
    * 重定向
    * 把IO异常封装成Volly异常
    * 处理retry policy: attemptRetryOnException方法

***
不懂：ByteArrayPool这个是干什么的


```java
//返回：
return new NetworkResponse(statusCode, responseContents,
						    responseHeaders, false,
						    SystemClock.elapsedRealtime() - requestStart);
其中responseContents，是一个byte[]，就是把hurlStack返回的inputStream读出来了，
这个功能由entityToBytes方法完成，里面用到了ByteArrayPool
```


###RequestQueue

* 必须调用start()方法来启动volly，所以可以认为volly启动只有有个大循环
    * 等待队列：Map<String, Queue<Request<?>>> mWaitingRequests
    * 干活队列： PriorityBlockingQueue<Request<?>> mNetworkQueue
    * 缓存队列：PriorityBlockingQueue<Request<?>> mCacheQueue
    * 当前集合：Set<Request<?>> mCurrentRequests

* 默认线程池：
    * DEFAULT_NETWORK_THREAD_POOL_SIZE = 4
    * 个人推荐：根据Rxjava的思想：
        * IO线程应该是一个线程池，线程数不限，但重用空闲线程
        * CPU密集型线程，即计算密集型，有几个CPU，就有几个线程

* 干活的类：
    * Cache 和 CacheDispatcher：默认new DiskBasedCache(cacheDir)
    * Network 和 NetworkDispatcher[]： 数组大小就是线程池大小
    * ResponseDelivery： 默认new ExecutorDelivery(new Handler(Looper.getMainLooper()))
    * List<RequestFinishedListener> mFinishedListeners

####

* 初始化：start() 大循环开始
    * CacheDispatcher作为一个thread，启动了，处理的是mCacheQueue
    * NetworkDispatcher作为n个thread，启动了，n级线程池的线程数，默认是4，处理的是mNetworkQueue

####

* NetworkDispatcher怎么工作？
    * 线程优先级：Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
    * 处理的是mNetworkQueue
    * 对于每一个Request：
        * 调用NetWork.performRequest(request)，默认是BasicNetwork
        * 响应结果预处理：Response<?> response = request.parseNetworkResponse(networkResponse)，主要功能是把byte[]转成Request<T>的这个T类型，如变成String，文件等
        * 处理缓存：mCache.put(request.getCacheKey(), response.cacheEntry);  ？？？？？？？？？？？？？？？？？
            * 每一个请求有个唯一标识，默认url
        * 转发响应：ResponseDelivery.postResponse(request, response)，默认实现是ExecutorDelivery
            * 注意传入的参数，是个带主循环的handler：new ExecutorDelivery(new Handler(Looper.getMainLooper()))
            * 所有代码都在主线程运行
            * 转发到RequestQueue的RequestFinishedListener：这个是大回调，监听所有请求，可以根据id筛选
            * 转发到每个reqeust的deliverResponse方法，如StringReuqest，用到了Response.Listener
        * 基本完事
    * 总结：
        * 每个NetworkDispatcher都从队列里take，谁快谁就take下一个

####

* 缓存相关：
    * 存的值类型是Cache.Entry，存的是http响应中的byte[]，header，状态字段等
    * 取出：在BasicNetwork中，
        * 如果http响应是not modified的，就不做进一步解析了，而是从cache取出已经解析好的东西
            * Entry entry = request.getCacheEntry();
        * 但这个entry是谁放进request里的呢, 在CacheDispatcher，111行
    * 保存：在NetworkDispatcher中，保存，mCache.put(request.getCacheKey(), response.cacheEntry);
        * request.getCacheKey()，默认就是url，只对get请求做缓存？
        * response.cacheEntry，在Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))时赋值，参考StringRequest.parseNetworkResponse
    * ClearCacheRequest：一个虚构的请求（带hack性质的请求），调用isCanceled，会清空缓存， cache.clear()，并在handler队列的最前面执行一个runnable的callback
    * 接口是：Cache
    * 看默认实现类：DiskBasedCache
        * 文件保存
    * CacheDispatcher如何工作？
        * 独自启动一个线程，占一个大循环，在mCacheQueue队列中工作
        * 没有缓存，则放回mNetworkQueue，等NetworkDispatcher处理
        * 有缓存，但已过期
            * 则先把缓存放到request.cacheEntry里
            * 再把request放到mNetworkQueue
            * 如果服务器返回的结果是not modified，则使用缓存，否则忽略缓存
        * 有缓存，且有效，直接deliver出去
        * 有缓存，且有效，但已被设置为需要刷新，也是回到mNetworkQueue，和modified状态值对比

####

* RequestQueue.add(Request<?> request)
    * 涉及到mCurrentRequests，mNetworkQueue，mWaitingRequests，mCacheQueue

###StringRequest

传入键值对，以get，post等方式发起请求

响应是String


###ByteArrayRequest：通过RequestManager使用


###RequestMap：上传文件，但是回调中没有进度提示


###
