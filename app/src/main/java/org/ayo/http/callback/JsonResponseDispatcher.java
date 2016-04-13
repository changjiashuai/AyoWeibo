package org.ayo.http.callback;

import android.util.Log;

import org.ayo.http.callback.model.FailRespnseModel;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.AyoResponse;
import org.ayo.json.JsonUtils;
import org.ayo.utils.HttpPrinter;
import org.ayo.utils.HttpProblem;

/**
 * json handler
 * eg1 new JsonResponseHandler<TestOrder>(TestOrder.class)
 * eg2 new JsonResponseHandler<List<Order>>(Order.class)
 * @author cowthan
 *
 * @param <T>
 */
public class JsonResponseDispatcher<T> extends BaseResponseDispatcher {
	
	//BaseHttpCallback<T> callback;
	//UniversalHttpResponse resp;
	Class<?> elementClass;
	
	/**
	 * @param elementClass
	 */
	public JsonResponseDispatcher(Class<?> elementClass){
		this.elementClass = elementClass;
	}
	
	/**
	 * 
	 */
	public <T> void process(String flag, AyoResponse resp, BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz){
		//this.callback = callback;
		//this.resp = resp;
		try {
			_process(flag, resp, callback, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			FailRespnseModel fm = new FailRespnseModel(-1, e.getMessage());
			callback.onFinish(false, HttpProblem.DATA_ERROR, fm, null);
		}
	}
	
	private <T> void _process(String flag, AyoResponse resp, BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz){

		///-------打印响应结果，不论成败
		HttpPrinter.printResponse(flag, resp);

		///--------处理server错误
		if(!resp.isSuccess()){

			String errorInfo = "";
			if(resp.data != null){
				errorInfo = resp.data;
			}else{
				errorInfo = "未知错误";
			}

			if(resp.exception != null){
				resp.exception.printStackTrace();
			}

			//LogInner.print("Http请求失败，错误码：" + resp.code + ", 错误原因：" + resp.data);
			FailRespnseModel m = new FailRespnseModel(resp.code, errorInfo);
			callback.onFinish(false, HttpProblem.SERVER_ERROR, m, null);
			return;
		}

		///--------原始字符串预处理，例如加密解密
		resp.data = callback.processRawResponse(resp.data);

		///--------原始字符串转为model，类型是你自定义的ResponseModel子类，适用于你的项目的json格式
		ResponseModel rm = parseResponseToModel(resp.data, clazz);

		///--------json解析，对接BaseHttpCallback
		if(rm.isOk()){
			///json parse
			try {
				Class<?> c = elementClass;
				String json = rm.getResult();
				Log.e("ddd1111", json);
				if(json == null || json.equals("") || json.equals("{}") || json.equals("[]")){
					
				}
				
				
				if(c == String.class){
					if(isArrayJson(json)){
						//would not parse ["aaa","dddd","eee"] to List<String>
						callback.onFinish(true, HttpProblem.OK, rm, (T)json);
					}else{
						callback.onFinish(true, HttpProblem.OK, rm, (T)json);
					}
				}else if(c == Boolean.class){
					callback.onFinish(true, HttpProblem.OK, rm, (T)new Boolean(true));
				}else{
					if(isArrayJson(json)){
						//try to parse ["aaa","dddd","eee"] to List<String>
						callback.onFinish(true, HttpProblem.OK, rm, (T) JsonUtils.getBeanList(json, c));
					}else{
						//Log.e("ddd", json);
						callback.onFinish(true, HttpProblem.OK, rm, (T)JsonUtils.getBean(json, c));
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				FailRespnseModel fm = new FailRespnseModel(-1, e.getMessage());
				callback.onFinish(false, HttpProblem.DATA_ERROR, fm, null);
			}
			
			///callback.onOK
		}else{
			callback.onFinish(false, HttpProblem.LOGIN_FAIL, rm, null);
		}
	}
	
	private boolean isArrayJson(String json){
		return json != null && json.startsWith("[");
	}


	@Override
	public ResponseModel parseResponseToModel(String response, Class<? extends ResponseModel> clazz) {
		ResponseModel rm = (ResponseModel) JsonUtils.getBean(response, clazz);
		return rm;
	}
	
}
