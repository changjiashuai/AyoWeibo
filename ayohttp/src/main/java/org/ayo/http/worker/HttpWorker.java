package org.ayo.http.worker;

import org.ayo.http.AyoRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseDispatcher;


/**
 *
 * there are a lot of http library in android, some support sync, some support async and some support both,
 * so here we provide 2 perform interfaces
 *
 * 要处理的是：
 * 1 解析AyoRequest使用对应的底层库发起请求，设置回调
 *    —— method对应，现在还只支持get，post，这个需要结合各个库来设置
 *    —— 错误解析，每个库对应的错误回调，一般是返回个Exception，看看是不是需要解析成更清晰的404,500等错误
 *
 * 2 在回调中将成功的响应和错误的响应都转为AyoResponse，发给BaseResponseDispatcher
 *
 * @author seven
 *
 */
public interface HttpWorker{
	/**
	 * start a request, get a response, all params you need is in reqeust
	 * @param request
	 */
	//public abstract <T> AyoResponse performRequest(AyoRequest request);
	
	public abstract <T> void performRequestAsync(AyoRequest request, BaseResponseDispatcher responseHandler, BaseHttpCallback<T> callback);
	
	
	/**
	 * @return
	 */
	//public abstract boolean enableAsync();
}
