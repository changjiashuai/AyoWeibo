package org.ayo.weibo.api2;

import org.ayo.http.callback.JsonResponseDispatcher;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.json.JsonParser;

/**
 * Created by Administrator on 2016/4/13.
 */
public class WeiboJsonDispatcher<T> extends JsonResponseDispatcher {
    /**
     * @param elementClass
     */
    public WeiboJsonDispatcher(Class elementClass, JsonParser jsonParser) {
        super(elementClass, jsonParser);
    }

    @Override
    public ResponseModel parseResponseToModel(String response, Class clazz) {
        org.ayo.weibo.api2.WeiboResponseModel r = getJsonParser().getBean(response, org.ayo.weibo.api2.WeiboResponseModel.class);
        r.raw = response;
        return r;
    }
}
