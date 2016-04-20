package org.ayo.weibo.ui.fragment;

import android.view.View;

import com.sina.weibo.sdk.demo.AccessTokenKeeper;

import org.ayo.app.tmpl.AyoRecyclerViewFragment;
import org.ayo.app.tmpl.Condition;
import org.ayo.app.tmpl.ErrorReason;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.model.ResponseModel;
import org.ayo.http.retrofit.RetrofitManager;
import org.ayo.http.utils.HttpProblem;
import org.ayo.lang.Lang;
import org.ayo.notify.Toaster;
import org.ayo.view.recycler.SimpleRecyclerAdapter;
import org.ayo.weibo.App;
import org.ayo.weibo.Config;
import org.ayo.weibo.api.WeiboService;
import org.ayo.weibo.api2.ApiTimeLine;
import org.ayo.weibo.model.ResponseTimeline;
import org.ayo.weibo.model.Timeline;
import org.ayo.weibo.ui.adapter.TimeLineAdapter;
import org.ayo.weibo.utils.Utils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 微博列表页
 */
public class TimelineListFragment extends AyoRecyclerViewFragment implements ISubPage{

    private boolean isFirstPage = false;
    private boolean isFirstCome = true;


    @Override
    protected void onCreateViewFinished(View root) {
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
        TimeLineCondition cond = (TimeLineCondition) getCondition();
        cond.onPullDown();

        if(Config.API.USE_RETROFIT){
            WeiboService api = RetrofitManager.getRetrofit().create(WeiboService.class);
            Observable<ResponseTimeline> observable = api.getPublicTimelines(
                    cond.access_token,
                    cond.pageSize + "",
                    cond.pageNow + "",
                    cond.base_app);
            observable.map(new Func1<ResponseTimeline, List<Timeline>>() {
                @Override
                public List<Timeline> call(ResponseTimeline responseTimeline) {
                    return responseTimeline.statuses;
                }

            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Timeline>>() {
                        @Override
                        public void call(List<Timeline> statuses) {
                            onLoadOk(statuses);
                        }
                    });
        }else{
//            ApiTimeLine.getPublicTimelines("获取最新微博",
//                    cond.access_token,
//                    cond.pageSize + "",
//                    cond.pageNow + "",
//                    cond.base_app,
//                    callback);

            ApiTimeLine.getPublicTimelinesByUid("获取我发布的微博",
                    cond.access_token,
                    cond.pageSize,
                    cond.pageNow,
                    Utils.getCurrentWeiboUserUid(),
                    callback);
        }



    }

    private BaseHttpCallback<ResponseTimeline> callback = new BaseHttpCallback<ResponseTimeline>() {
        @Override
        public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, ResponseTimeline responseTimeline) {
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
        return new TimeLineAdapter(getActivity(), null);
    }

    @Override
    protected void onRefresh() {
        TimeLineCondition cond = (TimeLineCondition) getCondition();
        cond.onPullDown();
        loadData();
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
        public String access_token;
        public int pageSize = 20;
        public String base_app = "0";

        @Override
        public void onPullDown() {
            pageNow = 1;
        }

        @Override
        public void onPullUp() {
            pageNow++;
        }

        @Override
        public void reset() {
            pageNow = 1;
            access_token = AccessTokenKeeper.readAccessToken(App.app).getToken();
            pageSize = 20;
            base_app = "0";
        }
    }
}
