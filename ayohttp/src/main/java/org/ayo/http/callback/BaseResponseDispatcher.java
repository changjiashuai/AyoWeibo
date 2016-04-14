package org.ayo.http.callback;

import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.AyoResponse;

/**
 *
 * process the http response, and call the api of BaseHttpCallback
 *
 * dispatcher的工作分为几部分：
 * 1 把原始响应字符串转为ResponseModel的子类，这个子类由用户定义，基本上一个项目一个，本质上是一种json格式对应一个
 *    一般情况下，json格式是code(status), message, result里面是所有业务字段
 *    特殊情况下，json格式是code(status)，message，各种业务字段并列，result被拆开了，这种情况，ResponseMode和业务Bean有交集了就
 *
 *    ——解决方案就是在ResponseModel子类中和parseResponseToModel方法中，把原始字符串传给model
 *    ——总之，json会解析两次，一次解析成功与否，一次解析业务字段，对性能应该不至于造成多大的损失
 *    ——无论出现什么奇怪的json格式，最终都应该只在ResponseModel子类中和parseResponseToModel里写逻辑，代码再丑陋，也要让BaseHttpCallback拿到的直接就是业务bean或者出错信息
 *
 * 2 根据ResponseModel，判断业务逻辑的成功失败，分发给BaseHttpCallback，而BaseHttpCallback总是由用户实现
 *
 *
 * @author cowthan
 *
 */
public abstract class BaseResponseDispatcher {

	/**
	 * process the http response, and call the interface of BaseHttpCallback
	 * @param resp data already has value, just don't use InputStream
	 * 
	 * if resp.code != 200, data may not have value, or data is error info
	 * 
	 * @param callback
	 * @param clazz  ResponseModel.class, the subclass of ResponseModel is the top level json
	 */
	public abstract <T> void process(String flag, AyoResponse resp, BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz);
	
	/**
	 * @param response
	 * @return
	 */
	public abstract ResponseModel parseResponseToModel(String response, Class<? extends ResponseModel> clazz);
}
