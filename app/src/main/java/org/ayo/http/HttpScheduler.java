package org.ayo.http;

import android.os.AsyncTask;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseDispatcher;
import org.ayo.http.worker.HttpWorkerUseXUtils;
import org.ayo.utils.HttpHelper;
import org.ayo.utils.HttpPrinter;
import org.ayo.utils.IOUtils;

/**
 * 负责调度http请求：
 * 首先请知晓，这个类并没有完成我们期望它完成的工作，为什么呢？
 * 本来是想让这个类负责调度http和线程，但我们现阶段用的Volly和XUtils都有自己的线程
 * 调度机制，所以我们这个类暂时不用干这个事了
 *
 * 现在负责：
 * 1 拼get请求的url，处理路径参数
 * 2 打印请求参数，便于调试接口
 * 3 使用request里的worker发起请求
 * 4 如果worker支持异步，则发起异步请求
 * 5 如果worker不支持异步，则发起同步请求，还要负责解析返回结果（还没用上，也没测过）
 *
 * 注意，这里只负责请求和响应结果都是String的请求
 */
public class HttpScheduler {
	
	/**
	 * 发起http请求
	 * @param req
	 * @param callback
	 */
	public static <T> void start(final AyoRequest req, final BaseResponseDispatcher responseHandler, final BaseHttpCallback<T> callback){

		//替换路径参数，类似http://www.xaax.com/id/{id}中的{id}
		if(req.pathParams.size() > 0){
			for(String key: req.pathParams.keySet()){
				req.url = req.url.replace("{" + key + "}", req.pathParams.get(key) + "");
			}
		}

		//如果是get请求，需要把param拼到url里
	    if(req.method.equals("get")){
	        req.url = HttpHelper.makeURL(req.url, req.params);
	    }

		//打印请求信息
		HttpPrinter.printRequest(req.flag, req);

		//发起请求
		if(req.stringEntity != null && !req.stringEntity.equals("")){
			//有entity形式的参数，截止到2016/4/11，还只有XUtils支持
			if(!(req.worker instanceof HttpWorkerUseXUtils))
				throw new RuntimeException("只有XUtils支持string entity形式的提交");
		}

		if(req.files.size() > 0){
			//有文件上传，截止到2016/4/11，还只有XUtils支持
			if(!(req.worker instanceof HttpWorkerUseXUtils))
				throw new RuntimeException("只有XUtils支持文件上传");
		}


		req.worker.performRequestAsync(req, responseHandler, callback);

//		if(req.worker.enableAsync()){
//			req.worker.performRequestAsync(req, responseHandler, callback);
//		}else{
//			startSync(req, responseHandler, callback);
//		}
		
	}
	
//	private static <T> void startSync(final AyoRequest req, final BaseResponseDispatcher responseHandler, final BaseHttpCallback<T> callback){
//		new AsyncTask<Void, Void, AyoResponse>(){
//
//			@Override
//			protected AyoResponse doInBackground(Void... params) {
//
//				AyoResponse resp = req.worker.performRequest(req);
//				if(resp.isSuccess()){
//					String s = IOUtils.fromStream(resp.inputStream);
//					resp.data = s;
//					IOUtils.closeQuietly(resp.inputStream);
//				}
//				return resp;
//			}
//
//			protected void onPostExecute(AyoResponse r) {
//				responseHandler.process(req.flag, r, callback, req.myRespClazz);
//				//JsonResponseHandler.newHandler(r, callback).process(req.myRespClazz);
//			}
//
//		}.execute();
//	}
	
	/**
	 * @param req
	 * @param callback
	 */
	public static <T> void startUpload(AyoRequest req, final BaseResponseDispatcher responseHandler, BaseHttpCallback<T> callback){
		//UploadUtils.uploadMethod(req.flag, req.params, req.headers, req.files, req.url, req.myRespClazz, responseHandler, callback);
	}

	/**
	 * @param flag
	 * @param current
	 * @param total
	 * @param callback
	 */
	public static <T> void notifyProgress(String flag, int current, int total, BaseHttpCallback<T> callback){
	}
	
	public static void cancel(String flag){
		
	}
	

}
