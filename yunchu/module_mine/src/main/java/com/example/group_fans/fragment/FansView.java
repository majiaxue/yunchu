package com.example.group_fans.fragment;

import com.example.group_fans.adapter.GroupFansRvAdapter;
import com.example.mvp.IView;

public interface FansView extends IView {
    void loadFinish();

    void loadRv(GroupFansRvAdapter adapter);
}
