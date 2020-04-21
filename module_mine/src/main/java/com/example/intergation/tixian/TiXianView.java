package com.example.intergation.tixian;

import com.example.bean.CashInfoBean;
import com.example.mvp.IView;

public interface TiXianView extends IView {
    void loadCashInfo(CashInfoBean cashInfoBean);
}
