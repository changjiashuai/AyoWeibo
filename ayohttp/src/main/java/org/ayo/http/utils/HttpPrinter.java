package org.ayo.http.utils;

import org.ayo.http.AyoRequest;
import org.ayo.http.AyoResponse;

/**
 * Created by Administrator on 2016/4/11.
 */
public class HttpPrinter {

    public static void printRequest(String flag, AyoRequest req){
        printRequest(req);
    }

    public static void printResponse(String flag, AyoResponse resp){
        LogInner.debug(resp.data);
    }


    private static void printRequest(AyoRequest request) {
        try {
            LogInner.debug("--------------------");
            LogInner.debug("request param：");
            HttpHelper.printMap(request.params);
            LogInner.debug("request header：");
            HttpHelper.printMap(request.headers);
            LogInner.debug("request eintity：");
            LogInner.debug(request.stringEntity);
            LogInner.debug("reqeust URL：");
            LogInner.debug(request.url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
