package org.ayo.weibo.ui.fragment.main;

import android.view.View;

import org.ayo.app.tmpl.AyoRecyclerViewFragment;
import org.ayo.app.tmpl.Condition;
import org.ayo.app.tmpl.ErrorReason;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.download.SimpleDownloader;
import org.ayo.http.utils.HttpProblem;
import org.ayo.jlog.JLog;
import org.ayo.lang.Lang;
import org.ayo.notify.Toaster;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.Config;
import org.ayo.weibo.api3.ApiStatus;
import org.ayo.weibo.model.timeline.AyoResponseTimeline;
import org.ayo.weibo.model.timeline.AyoTimeline;
import org.ayo.weibo.topic.Topic;
import org.ayo.weibo.topic.TopicManager;
import org.ayo.weibo.ui.adapter.AyoTimeLineAdapter;

/**
 * 微博列表页
 */
public class TimelineListFragment extends AyoRecyclerViewFragment<AyoTimeline> implements ISubPage{

    private boolean isFirstPage = false;
    private boolean isFirstCome = true;


    /**
     * 对应sd卡工作目录下的一个目录，app数据对应Config.DIR.APP,  用户对应Config.DIR.USER
     */
    private String dataDir = "";

    /**
     * 主题，其实对应dataDir下的一个子目录，这里面存的才是类似1.json, 2.json的文件
     */
    private Topic topic;

    public void setDataDir(String dataDir){
        this.dataDir = dataDir;
    }

    public void setTopic(Topic topic){
        this.topic = topic;
    }

    @Override
    protected void onCreateViewFinished(View root) {

        if(dataDir.equals(Config.DIR.APP_DIR)){
            topic = TopicManager.getDefaultAppTopic();
        }else if(dataDir.equals(Config.DIR.USER_DIR)){
            topic = TopicManager.getDefaultUserTopic();
        }

        if(topic == null){
            onLoadFail(ErrorReason.LOCAL, true);
            return;
        }

        if(isFirstPage){
            //如果是第一个显示的页面
            autoRefresh();
        }else{
            //不干什么事
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(isFirstPage && isFirstCome){
                //首页，第一次进来会在onCreateView中加载数据，这里不用
                isFirstCome = false;
            }else{
                //其他所有情况，都在这里重新加载（也他妈不一定，看业务需求）
                if(Lang.isEmpty(getList())){
                    //如果没有数据，就自动加载
                    autoRefresh();
                }else{
                    //如果已经有数据了，先别加载了
                }
            }
        }

    }


    private void loadData() {
        if(topic == null){
            onLoadFail(ErrorReason.LOCAL, true);
            return;
        }
        TimeLineCondition cond = (TimeLineCondition) getCondition();
        JLog.i("加载数据--" + dataDir + ", " + topic.dirName + ", " + cond.pageNow);
        ApiStatus.getStatuses("获取本地微博", dataDir, topic.dirName, cond.pageNow, callback);
    }

    private BaseHttpCallback<AyoResponseTimeline> callback = new BaseHttpCallback<AyoResponseTimeline>() {
        @Override
        public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, AyoResponseTimeline responseTimeline) {
            if(isSuccess){
                onLoadOk(responseTimeline.statuses);
            }else{
                onLoadFail(ErrorReason.SERVER, Lang.isEmpty(getList()));
            }
        }
    };

    @Override
    public void onPageVisibleChange(boolean isVisible) {

    }

    @Override
    public void setIsTheFirstPage(boolean isTheFirstPage) {
        isFirstPage = isTheFirstPage;
    }

    @Override
    protected SimpleRecyclerAdapter newAdapter() {
        return new AyoTimeLineAdapter(getActivity(), null);
    }

    @Override
    protected void onRefresh() {
        final TimeLineCondition cond = (TimeLineCondition) getCondition();
        cond.onPullDown(); //本来应该是调用这个，但现在page需要外面算出来

        if(dataDir.equals(Config.DIR.APP_DIR)){
            TopicManager.downloadTopData(topic, new SimpleDownloader.Callback() {
                @Override
                public void onOk(String savePath) {
                    JLog.i("下载完成--" + savePath);
                    cond.pageNow = TopicManager.getMaxPage(dataDir, topic);
                    loadData();
                }

                @Override
                public void onFuck(String fuckReason) {
                    cond.pageNow = TopicManager.getMaxPage(dataDir, topic);
                    loadData();
                }
            });
        }else{
            cond.pageNow = TopicManager.getMaxPage(dataDir, topic);
            loadData();
        }

    }

    @Override
    protected void onLoadMore() {
        TimeLineCondition cond = (TimeLineCondition) getCondition();
        cond.onPullUp();
        loadData();
    }


    @Override
    public void onNotAnyMore() {
        Toaster.toastShort("没有更多数据了");
    }

    @Override
    public Condition initCondition() {
        TimeLineCondition cond = new TimeLineCondition();
        cond.reset();
        return cond;
    }




    private class TimeLineCondition extends Condition{

        public int pageNow = 1;
        public int pageSize = 20;

        @Override
        public void onPullDown() {
            pageNow = 1;
        }

        @Override
        public void onPullUp() {
            pageNow--;
            if(pageNow < 0){
                pageNow = 0;
            }
        }

        @Override
        public void reset() {
            pageNow = 1;
        }
    }
}
