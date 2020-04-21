package com.example.intergation;

import com.example.bean.UserInfoBean;
import com.example.mvp.IView;
import com.example.order.adapter.OrderVPAdapter;

public interface MyIntegrationView extends IView {
  //  void refreshSuccess();

    void updateVP(OrderVPAdapter vpAdapter);

    void loadUserInfo(UserInfoBean userInfoBean);
}
