package com.example.earnings_preview;

import com.example.bean.UserInfoBean;
import com.example.mvp.IView;

public interface EarningsPreviewView extends IView {
    void loadUserProfit(UserInfoBean userInfoBean);
}
