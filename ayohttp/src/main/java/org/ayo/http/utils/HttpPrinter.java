package org.ayo.http.utils;

import org.ayo.http.AyoRequest;
import org.ayo.http.AyoResponse;

/**
 * Created by Administrator on 2016/4/11.
 */
public class HttpPrinter {

    public static void printRequest(String flag, AyoRequest request){
        try {
            LogInner.debug(flag + "--" + "--------------------");
            LogInner.debug(flag + "--" + "request param：");
            HttpHelper.printMap(flag + "--", request.params);
            LogInner.debug(flag + "--" + "request header：");
            HttpHelper.printMap(flag + "--", request.headers);
            LogInner.debug(flag + "--" + "request eintity：");
            LogInner.debug(flag + "--" + request.stringEntity);
            LogInner.debug(flag + "--" + flag + "--" + "reqeust URL：");
            LogInner.debug(flag + "--" + request.url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printResponse(String flag, AyoResponse resp){
        LogInner.debug(flag + "--" + resp.data);
    }



}
