package org.ayo.http;

import android.text.TextUtils;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseDispatcher;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.worker.HttpWorker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class AyoRequest {
	
	private AyoRequest(){
	}
	
	public static AyoRequest newInstance(){
		AyoRequest r = new AyoRequest();
		return r;
	}
	
	public Map<String, String> params = new HashMap<String, String>();
	public Map<String, String> pathParams = new HashMap<String, String>();
	public Map<String, String> headers = new HashMap<String, String>();
	public Map<String, File> files = new HashMap<String, File>();
	public String stringEntity;
	public File file;  //post一个file，不知道这是什么原理，不过和表单提交不一样
	public String url = "";
	public String method = "get";
	public String flag = "";
	public HttpWorker worker;
	public Class<? extends ResponseModel> myRespClazz;
	
	
	//---------------------------------------------------------------//
	public AyoRequest flag(String flag){
		if(!TextUtils.isEmpty(this.flag)){
			throw new RuntimeException("flag is duplicated.");
		}
		
		this.flag = flag;
		return this;
	}
	
	public AyoRequest param(String name, String value){
		if(this.params == null) this.params = new HashMap<String, String>();
		if(value == null) value = "";
		params.put(name, value);
		return this;
	}
	
	private boolean uploadFile = false;
	private boolean needCompress = false;
	
	public AyoRequest param(String name, File value){
		if(this.files == null) this.files = new HashMap<String, File>();
		files.put(name, value);
		uploadFile = true;
		return this;
	}

	public AyoRequest file(File f){
		file = f;
		return this;
	}

	public AyoRequest path(String name, String value){
		if(this.pathParams == null) this.pathParams = new HashMap<String, String>();
		pathParams.put(name, value);
		return this;
	}
	
	public AyoRequest header(String name, String value){
		if(this.headers == null) this.headers = new HashMap<String, String>();
		headers.put(name, value);
		return this;
	}
	
	public AyoRequest method(String method){
		this.method = method;
		return this;
	}

    /**
     * don't know how to pass this in volly, now just work in xutils
     * @param entity
     * @return
     */
	public AyoRequest stringEntity(String entity){
		this.stringEntity = entity;
		return this;
	}
	
	public AyoRequest url(String url){
		this.url = url;
		return this;
	}
	
	public AyoRequest worker(HttpWorker worker){
		this.worker = worker;
		return this;
	}
	
	/**
	 * depends on specific jons format
	 * @param myRespClazz
	 * @return
	 */
	public AyoRequest myResponseClass(Class<? extends ResponseModel> myRespClazz){
		this.myRespClazz = myRespClazz;
		return this;
	}
	
	public <T> void go(final BaseResponseDispatcher responseHandler, final BaseHttpCallback<T> callback){
		HttpScheduler.start(this, responseHandler, callback);
	}
}
