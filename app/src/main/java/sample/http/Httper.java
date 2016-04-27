package sample.http;

import org.ayo.http.AyoRequest;
import org.ayo.http.worker.HttpWorkerUseOkhttp;

/**
 * Created by Administrator on 2016/4/24.
 */
public class Httper {

    public static AyoRequest request(){
        AyoRequest r = AyoRequest.newInstance()
                .header("os", "android")      //可选
                .header("version", "1.0.0")   // 可选
                .myResponseClass(MyResponseMode.class)  //必须，且理论上应该项目唯一
                        //.worker(new HttpWorkerUseXUtils(true)); //必须
                .worker(new HttpWorkerUseOkhttp()); //必须
        //.worker(new HttpWorkerUseXUtils()); //必须
        return r;
    }

}
