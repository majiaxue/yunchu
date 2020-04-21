package com.example.group_fans.fragment2;

import com.example.group_fans.adapter.GroupFansRvAdapter;
import com.example.mvp.IView;

public interface Fans2View extends IView {
    void loadFinish();

    void loadRv(GroupFansRvAdapter adapter);
}
