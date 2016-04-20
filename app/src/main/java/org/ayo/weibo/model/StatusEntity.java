package org.ayo.weibo.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class StatusEntity {
    /**
     * in_reply_to_status_id :
     * created_at : Tue May 24 18:04:53 +0800 2011
     * truncated : false
     * mid : 5610221544300749636
     * annotations : []
     * source :
     * geo : null
     * in_reply_to_screen_name :
     * comments_count : 8
     * in_reply_to_user_id :
     * id : 11142488790
     * text : 我的相机到了。
     * reposts_count : 5
     * favorited : false
     */
    public String in_reply_to_status_id;
    public String created_at;
    public boolean truncated;
    public String mid;
    public List<?> annotations;
    public String source;
    public String geo;
    public String in_reply_to_screen_name;
    public int comments_count;
    public String in_reply_to_user_id;
    public long id;
    public String text;
    public int reposts_count;
    public boolean favorited;
}
