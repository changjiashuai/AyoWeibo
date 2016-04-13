package org.ayo.http.callback;

import org.ayo.http.callback.model.FailRespnseModel;
import org.ayo.http.callback.model.HtmlResponseModel;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.utils.HttpPrinter;
import org.ayo.utils.HttpProblem;
import org.ayo.http.AyoResponse;

/**
 * html reesponse handler
 * eg 1 new JsonResponseHandler<TestOrder>(TestOrder.class)
 * eg 2 new JsonResponseHandler<List<Order>>(Order.class)
 * @author cowthan
 *
 */
public class HtmlResponseDispatcher extends BaseResponseDispatcher {
	
	public HtmlResponseDispatcher(){
	}
	
	/**
	 * 
	 * @param clazz top level json
	 */
	public <T> void process(String flag, AyoResponse resp,
			BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz){

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
		HtmlResponseModel rm = parseResponseToModel(resp.data, clazz);

		try {
			callback.onFinish(true, HttpProblem.OK, null, (T)rm.html);
		} catch (Exception e) {
			e.printStackTrace();
			FailRespnseModel fm = new FailRespnseModel(-1, e.getMessage());
			callback.onFinish(false, HttpProblem.DATA_ERROR, fm, null);
		}
	}
	

	@Override
	public HtmlResponseModel parseResponseToModel(String response, Class<? extends ResponseModel> clazz) {
		HtmlResponseModel rm = new HtmlResponseModel();
		rm.html = response;
		return rm;
	}


	
}
