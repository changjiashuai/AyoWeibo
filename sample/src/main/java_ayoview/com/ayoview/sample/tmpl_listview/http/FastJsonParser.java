package com.ayoview.sample.tmpl_listview.http;

import com.alibaba.fastjson.JSON;

import org.ayo.http.json.JsonParser;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class FastJsonParser implements JsonParser {
    @Override
    public <T> List<T> getBeanList(String jsonArrayString, Class<T> cls) {
        List<T> beanList = JSON.parseArray(jsonArrayString, cls);
        return beanList;
    }

    @Override
    public <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        t = JSON.parseObject(jsonString, cls);
        return t;
    }

    @Override
    public String toJson(Object bean) {
        if(bean == null){
            return "{}";
        }
        return JSON.toJSONString(bean);
    }
}
