package com.ayoview.sample.tmpl_listview;


import org.ayo.http.callback.model.ResponseModel;

public class MyHttpResponse extends ResponseModel {

	public int code;
	public String message;
	public String result;
	
	@Override
	public boolean isOk() {
		return code == 0;
	}

	@Override
	public int getResultCode() {
		return code;
	}

	@Override
	public String getFailMessage() {
		return message;
	}

	@Override
	public String getResult() {
		return result;
	}

}
