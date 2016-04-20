package sample.api;

import org.ayo.http.callback.model.ResponseModel;

/**
 * Created by Administrator on 2016/4/13.
 */
public class WeiboResponseModel extends ResponseModel {

    public String error;
    public int error_code;
    public String request;

    public String raw;

    @Override
    public boolean isOk() {
        return error == null || error.equals("");
    }

    @Override
    public int getResultCode() {
        return error_code;
    }

    @Override
    public String getFailMessage() {
        return error;
    }

    @Override
    public String getResult() {
        return raw;
    }
}
