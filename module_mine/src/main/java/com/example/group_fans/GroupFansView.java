package com.example.group_fans;

import com.example.bean.GroupFansPeopleBean;
import com.example.group_fans.adapter.GroupFansRvAdapter;
import com.example.mvp.IView;
import com.example.order.adapter.OrderVPAdapter;

public interface GroupFansView extends IView {
    void loadUI(int totalPage, int totalFans);

    void loadCount(GroupFansPeopleBean peopleBean);

    void loadFinish();

    void noFans();

    void loadRv(GroupFansRvAdapter adapter);

    void getType(String type);

    void updateVP(OrderVPAdapter vpAdapter);
}
