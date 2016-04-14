package org.ayo.http.callback;

import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.utils.HttpProblem;

/**
 *
 * request.callback(
 * 		new GeniusHttpCallback<List<Article>>(Article.class, MyHttpResponse.class){
 * 			void onFinish(){}
 * 		}
 * 	).go();
 * 
 * @author cowthan
 *
 * @param <T>
 */
public abstract class BaseHttpCallback<T> {
	
	//public Class<?> elementClazz;
	
	
	/**
	 * if T is normal POJO, class = T.class
	 * if T is List<E>, class = E.class
	 */
	public BaseHttpCallback(){
	}
	
	public abstract void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, T t);
	
	/**
	 * @param current
	 * @param total
	 */
	public void onLoading(long current, long total){
		
	}
	
	
	/**
	 * the raw reponse will be pre-processed here, such as crypt or sth else
	 * @param rawResponse
	 * @return
	 */
	public String processRawResponse(String rawResponse){
		
		return rawResponse;
		
	}
	
}
