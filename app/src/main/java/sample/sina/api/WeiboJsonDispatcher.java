package sample.sina.api;

import org.ayo.http.callback.JsonResponseDispatcher;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.json.JsonUtils;

/**
 * Created by Administrator on 2016/4/13.
 */
public class WeiboJsonDispatcher extends JsonResponseDispatcher {
    /**
     * @param elementClass
     */
    public WeiboJsonDispatcher(Class elementClass) {
        super(elementClass);
    }

    @Override
    public ResponseModel parseResponseToModel(String response, Class clazz) {
        WeiboResponseModel r = JsonUtils.getBean(response, WeiboResponseModel.class);
        r.raw = response;
        return r;
    }
}
