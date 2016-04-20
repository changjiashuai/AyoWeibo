package org.ayo.weibo.api2;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.weibo.Config;
import org.ayo.weibo.model.ResponseTimeline;

import sample.http.FastJsonParser;

/**
 * Created by Administrator on 2016/4/21.
 */
public class ApiTimeLine {


    /**
     * 获取最新微博列表
     *
     * @param flag
     * @param callback
     */
    public static void getPublicTimelines(String flag,
                                          String access_token,
                                          String count,
                                          String page,
                                          String base_app,
                                          BaseHttpCallback<ResponseTimeline> callback) {
        WeiboApi.request().flag(flag)
                .url(Config.API.HOST_WEIBO + "statuses/public_timeline.json")
                .method("get")
                .param("access_token", access_token)
                .param("count", count)
                .param("page", page)
                .param("base_app", base_app)
                .go(new WeiboJsonDispatcher(ResponseTimeline.class, new FastJsonParser()), callback);
    }

    /**
     * 根据用户uid获取他发布的微博
     * <p/>
     * 注意：微博api规定，uid与screen_name只能为当前授权用户，就是说得和token对应
     * 并且这两个参数只能选一个
     * <p/>
     * <p/>
     * access_token	true	string	采用OAuth授权方式为必填参数，OAuth授权后获得。
     * uid	false	int64	需要查询的用户ID。
     * screen_name	false	string	需要查询的用户昵称。
     * since_id	false	int64	若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * max_id	false	int64	若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * count	false	int	单页返回的记录条数，最大不超过100，超过100以100处理，默认为20。
     * page	false	int	返回结果的页码，默认为1。
     * base_app	false	int	是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * feature	false	int	过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * trim_user	false	int	返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     */
    public static void getPublicTimelinesByUid(String flag,
                                               String access_token,
                                               int pageSize,
                                               int pageNow,
                                               String uid,
                                               BaseHttpCallback<ResponseTimeline> callback) {
        WeiboApi.request().flag(flag)
                .url(Config.API.HOST_WEIBO + "statuses/user_timeline.json")
                .method("get")
                .param("access_token", access_token)
                .param("uid", uid)
                .param("since_id", "0")
                .param("max_id", "0")
                .param("count", pageSize + "")
                .param("page", pageNow + "")
                .param("base_app", "0")
                .param("feature", "0")
                .param("trim_user", "0")
                .go(new WeiboJsonDispatcher(ResponseTimeline.class, new FastJsonParser()), callback);
    }

}
