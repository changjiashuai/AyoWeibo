package org.ayo.http.worker;

import org.ayo.http.AyoResponse;
import org.ayo.http.AyoRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseDispatcher;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

public class HttpWorkerUseXUtils implements HttpWorker{
	
	//private boolean enableAsync = true;
	
	public HttpWorkerUseXUtils(){
//		if(!enableAsync){
//			throw new RuntimeException("XUtils同步模式http我还没实现");
//		}
//		this.enableAsync = enableAsync;
	}

//	@Override
//	public <T> AyoResponse performRequest(final AyoRequest request) {
//		throw new RuntimeException("XUtils同步模式http我还没实现");
//		String url = request.url;
//        HttpMethod m = request.method.equalsIgnoreCase("get") ? HttpMethod.GET :HttpMethod.POST;
//
//        RequestParams params = new RequestParams();
//        if(request.headers != null){
//            for(Map.Entry<String, String> entry: request.headers.entrySet()){
//                params.addHeader(entry.getKey(), entry.getValue());
//            }
//        }
//
//        if(request.params != null){
//            for(Map.Entry<String, String> entry: request.params.entrySet()){
//                params.addBodyParameter(entry.getKey(), entry.getValue());
//            }
//        }
//
//        if(request.files != null){
//			for(String k : request.files.keySet()){
//				params.addBodyParameter(k, request.files.get(k));
//			}
//		}
//
//        if(request.stringEntity != null && !request.stringEntity.equals("")){
//			params.addBodyParameter("", request.stringEntity);
//        }
//
//
//
//
//        HttpUtils http = new HttpUtils();
//        try {
//			ResponseStream rs = http.sendSync(m, url, params);
//			UniversalHttpResponse r = new UniversalHttpResponse();
//			r.code = rs.getStatusCode();
//			r.headers = new HashMap<String, String>();
//			r.inputStream = rs.getBaseStream();
//			return r;
//		} catch (HttpException e) {
//			e.printStackTrace();
//			UniversalHttpResponse r = new UniversalHttpResponse();
//			r.code = -1;
//			r.headers = null;
//			r.inputStream = null;
//			r.data = e.getMessage();
//			return r;
//		}
//	}

	@Override
	public <T> void performRequestAsync(final AyoRequest request, final BaseResponseDispatcher responseHandler, final BaseHttpCallback<T> callback) {

		String url = request.url;

		RequestParams params = new RequestParams(url);
		if(request.headers != null){
			for(Map.Entry<String, String> entry: request.headers.entrySet()){
				params.addHeader(entry.getKey(), entry.getValue());
			}
		}

		if(!request.method.equalsIgnoreCase("get")){
			if(request.params != null){
				for(Map.Entry<String, String> entry: request.params.entrySet()){
					params.addBodyParameter(entry.getKey(), entry.getValue());
				}
			}
		}

		if(request.files != null){
			params.setMultipart(true);
			for(String k : request.files.keySet()){
				params.addBodyParameter(k, request.files.get(k));
			}
		}

		if(request.stringEntity != null && !request.stringEntity.equals("")){
			params.addBodyParameter("", request.stringEntity);
		}

		Callback.ProgressCallback<String> xutilsCallback = new Callback.ProgressCallback<String>(){

			@Override
			public void onWaiting() {
				//LogInner.debug("XUtils--http请求(" + request.flag + ")--onWaiting...");
			}

			@Override
			public void onStarted() {
				//LogInner.debug("XUtils--http请求(" + request.flag + ")--onStarted!");
			}

			@Override
			public void onLoading(long total, long current, boolean isDownloading) {
				//LogInner.debug("XUtils--http请求(" + request.flag + ")--onLoading--isDownloading = " + isDownloading);
				callback.onLoading(current, total);
			}

			@Override
			public void onSuccess(String result) {
				//LogInner.print("请求结果(" + request.flag + "): \n" + result);
				//JLog.json("请求结果(" + request.flag + ")", result);
				//JLog.v("aaaa-v-请求over");
				//JLog.d("aaaa-d-请求over");
				//JLog.i("aaaa-i-请求over");
				//JLog.e("aaaa-e-请求over");
				//JLog.wtf("aaaa-wtf-请求over");
				AyoResponse resp = new AyoResponse();
				resp.code = 200;
				resp.data = result;
				resp.headers = null;
				resp.inputStream = null;
				responseHandler.process(request.flag, resp, callback, request.myRespClazz);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {

				//ex.printStackTrace();

				AyoResponse resp = new AyoResponse();
				resp.code = -1;
				resp.data = ex.getLocalizedMessage();
				resp.exception = ex;
				resp.headers = null;
				resp.inputStream = null;
				responseHandler.process(request.flag, resp, callback, request.myRespClazz);

				//LogInner.debug("XUtils--http请求(" + request.flag + ")--onError：" + ex.getMessage());
				//callback.onFinish(false, HttpProblem.SERVER_ERROR, new FailRespnseModel(-1, ex.getMessage()), null);
			}

			@Override
			public void onCancelled(CancelledException ex) {
				//ex.printStackTrace();
				//LogInner.debug("XUtils--http请求(" + request.flag + ")--onError：" + ex.getMessage());
				//callback.onFinish(false, HttpProblem.SERVER_ERROR, new FailRespnseModel(-1, ex.getMessage()), null);

				AyoResponse resp = new AyoResponse();
				resp.code = -1;
				resp.data = ex.getLocalizedMessage();
				resp.exception = ex;
				resp.headers = null;
				resp.inputStream = null;
				responseHandler.process(request.flag, resp, callback, request.myRespClazz);

			}

			@Override
			public void onFinished() {
				//LogInner.debug("XUtils--http请求(" + request.flag + ")--onFinished");
			}
		};

		HttpMethod m = request.method.equalsIgnoreCase("get") ? HttpMethod.GET :HttpMethod.POST;
		x.http().request(m, params, xutilsCallback);
	}
	
//	@Override
//	public boolean enableAsync() {
//		return enableAsync;
//	}

	

}
