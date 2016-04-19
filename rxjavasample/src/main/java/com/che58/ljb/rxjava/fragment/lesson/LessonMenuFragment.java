package com.che58.ljb.rxjava.fragment.lesson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.che58.ljb.rxjava.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主菜单Fragment
 * Created by ljb on 2016/3/23.
 */
public class LessonMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_menu, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btn_rxjava_1)
    void btn_rxjava_1() {
        open(new Rxjava1());
    }
    @OnClick(R.id.btn_rxjava_2)
    void btn_rxjava_2() {
        open(new Rxjava2());
    }
    @OnClick(R.id.btn_rxjava_3)
    void btn_rxjava_3() {
        open(new Rxjava3());
    }
    @OnClick(R.id.btn_rxjava_4)
    void btn_rxjava_4() {
        open(new Rxjava4());
    }
    @OnClick(R.id.btn_rxjava_5)
    void btn_rxjava_5() {
        open(new Rxjava5());
    }
    @OnClick(R.id.btn_rxjava_6)
    void btn_rxjava_6() {
        open(new Rxjava6());
    }
    @OnClick(R.id.btn_rxjava_7)
    void btn_rxjava_7() {
        open(new Rxjava7());
    }
    @OnClick(R.id.btn_rxjava_8)
    void btn_rxjava_8() {
        open(new Rxjava8());
    }


    /**
     * 开启新的Fragment
     */
    private void open(Fragment fragment) {
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.main_content, fragment, tag)
                .commit();
    }

}
