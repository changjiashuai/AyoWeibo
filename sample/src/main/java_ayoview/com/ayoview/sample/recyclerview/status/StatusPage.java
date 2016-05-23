package com.ayoview.sample.recyclerview.status;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类：带状态的页面，是个fragment
 *
 * 一个页面，只接受：
 * 1 状态
 * 2 动作：受制于状态，并且会导致状态改变
 * 3 数据：状态 + 数据，决定了呈现给用户什么，并且这里的数据还分为状态切换前数据，切换后数据
 * 4 输入：用户输入，不影响这里的逻辑
 *
 * 一个page只关心界面有几种变化及所需要的数据，不关心数据从哪儿来，也不关心接口被谁调，参考MVP
 *
 * page：
 * 内部保存状态，控制界面切换
 * 对外提供动作接口，如onLoadOk，refreshUnreadMsgCount等，也就是MVP的V
 *
 * P：
 *
 *
 * 可以定义状态
 * 状态page不会
 */
public class StatusPage extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ///View v =
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
