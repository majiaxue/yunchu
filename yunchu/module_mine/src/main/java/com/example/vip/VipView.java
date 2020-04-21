package com.example.vip;

import com.example.mvp.IView;
import com.example.vip.adapter.VipAdapter;

public interface VipView extends IView {
    void loadAdapter(VipAdapter vipAdapter);
}
