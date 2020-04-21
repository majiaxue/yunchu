package com.example.shop_home;

import com.example.mvp.IView;
import com.example.shop_home.adapter.ShopHomeVPAdapter;

public interface ShopHomeView extends IView {
    void loadVP(ShopHomeVPAdapter adapter);

    void collectSuccess();
}
