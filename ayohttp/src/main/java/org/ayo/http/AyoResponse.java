package org.ayo.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * http response
 * if http status code is not 200, then code = -1， data = error info
 *
 * 这个是最原始的http response
 * 如果http返回成功：
 * code为200到300，inputstream里是响应结果，headers就是响应头
 *
 * @author cowthan
 *
 */
public class AyoResponse {
	
	public InputStream inputStream;
	public String data;
	public int code = -999;
	public Map<String, String> headers = new HashMap<String, String>();
	public Throwable exception;  //对于有些库，会将500，404，超时等错误包装成异常返回，此值可能为空
	
	/**
	 * http request is ok [200, 300)
	 * @return
	 */
	public boolean isSuccess(){
		return code >= 200 && code < 300;
	}

}
