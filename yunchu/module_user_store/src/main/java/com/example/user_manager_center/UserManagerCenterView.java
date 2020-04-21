package com.example.user_manager_center;

import com.example.bean.UserInfoBean;
import com.example.mvp.IView;
import com.example.user_manager_center.adapter.EquityAdapter;
import com.example.user_manager_center.adapter.QueryVipGoodsAdapter;

public interface UserManagerCenterView extends IView {
    void loadAdapter(EquityAdapter equityAdapter);

    void loadAdapter(QueryVipGoodsAdapter queryVipGoodsAdapter);

    void loadUserInfoBean(UserInfoBean userInfoBean);

    void loadUserProfit(UserInfoBean userInfoBean);
}
