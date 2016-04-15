package org.ayo.http.json;

import java.util.List;

public interface JsonParser {

	<T> List<T> getBeanList(String jsonArrayString, Class<T> cls);

	<T> T getBean(String jsonString, Class<T> cls);
	
	String toJson(Object bean);
	
}
