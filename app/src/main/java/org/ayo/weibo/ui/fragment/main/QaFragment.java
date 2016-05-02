package org.ayo.weibo.ui.fragment.main;

import android.support.v4.app.FragmentManager;
import android.view.View;

import org.ayo.app.common.AyoFragment;
import org.ayo.app.tmpl.pagegroup.ISubPage;
import org.ayo.http.R;
import org.ayo.weibo.ui.fragment.qa.QaListFragment;

/**
 * 问答
 *
 * 问答列表：最新，热门
 * 问答分类
 * 我的问答：我的提问，我的回答
 * 去提问
 * 问题搜索
 * 直播
 *
 */
public class QaFragment extends AyoFragment implements ISubPage{

    QaListFragment frag;

    @Override
    protected int getLayoutId() {
        return R.layout.wb_frag_qa;
    }

    @Override
    protected void onCreateView(View root) {

        frag = new QaListFragment();
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.fl_root, frag).commit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            frag.refresh();
        }
    }

    @Override
    public void onPageVisibleChange(boolean isVisible) {

    }

    @Override
    public void setIsTheFirstPage(boolean isTheFirstPage) {

    }
}
