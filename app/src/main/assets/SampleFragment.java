import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.http.R;


/**
 * Created by Administrator on 2016/4/5.
 */
public class SampleFragment extends Fragment implements ISubPage {


    /**
     * 是否第一次进这个页面
     */
    public boolean isFirstCome = true;

    /**
     * 本页面是否首页，即是否需要直接呈现给用户
     */
    public boolean isTheFirstPage = false;

    ListView lv_list;
    ProgressBar progress_bar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("pagegroup", title + "--onCreateView");
        View root = View.inflate(getActivity(), R.layout.frag_demo_page_group_sample_fragment_list, null);
        lv_list = (ListView) root.findViewById(R.id.lv_list);
        progress_bar = (ProgressBar) root.findViewById(R.id.progress_bar);
        TextView tv_title = (TextView) root.findViewById(R.id.tv_title);
        tv_title.setText(title);

        if(isTheFirstPage){
            loadData();
        }

        return root;
    }

    private String title = "no";
    public void setTitle(String t){
        this.title = t;
    }

    @Override
    public void setIsTheFirstPage(boolean isTheFirstPage){
        this.isTheFirstPage = isTheFirstPage;
    }

    private void loadData() {

        Log.i("pagegroup", title + "------------------loadData------------------");
        progress_bar.setVisibility(View.VISIBLE);
        lv_list.setVisibility(View.GONE);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        Log.i("pagegroup", title + "--setUserVisibleHint: " + isVisibleToUser);
        if(isVisibleToUser){
            if(isTheFirstPage && isFirstCome){
                //首页，第一次进来会在onCreateView中加载数据，这里不用
                isFirstCome = false;
            }else{
                //其他所有情况，都在这里加载
                loadData();
            }
        }

    }

    @Override
    public void onPageVisibleChange(boolean isVisible) {


        Log.i("pagegroup", title + "--onPageVisibleChange: " + isVisible);

        if(isVisible){

        }else{

        }

    }



}
