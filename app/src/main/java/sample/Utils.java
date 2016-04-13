package sample;

import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Utils {

    public interface OnListViewStatusChangedListener {
        /**
         * 到达顶部
         */
        void onTopReached();

        /**
         * 到达底部
         */
        void onBottomReached();

        /**
         * 显示条目发生变化
         * @param startPosition
         * @param endPostion
         */
        void onItemVisibilityChanged(int startPosition, int endPostion);
    }

    /**
     * 给ListView加个滚动监听，监听以下状态：
     * 1 滑动到底部
     * 2 滑动到顶部
     * 3 当前显示发生变化
     */
    public static void setOnScrollListenerOnListView(final ListView lv, final OnListViewStatusChangedListener onListViewStatusChangedListener){
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:

                        if(lv.getCount() == 0){
                            if(onListViewStatusChangedListener != null) onListViewStatusChangedListener.onTopReached();
                            if(onListViewStatusChangedListener != null) onListViewStatusChangedListener.onBottomReached();
                            return;
                        }else{
                            // 判断滚动到底部
                            if (lv.getLastVisiblePosition() == (lv.getCount() - 1)) {
                                if(onListViewStatusChangedListener != null) onListViewStatusChangedListener.onBottomReached();
                            }
                            // 判断滚动到顶部

                            if(lv.getFirstVisiblePosition() == 0){
                                if(onListViewStatusChangedListener != null) onListViewStatusChangedListener.onTopReached();
                            }
                        }


                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//                if (firstVisibleItem + visibleItemCount == totalItemCount && !flag) {
//                    flag = true;
//                } else{
//                    flag = false;
//                }
            }
        });
    }

}
