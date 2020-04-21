package com.example.integral_shop;

import android.content.Context;

import com.example.mvp.BasePresenter;

public class IntegralShopPresenter extends BasePresenter<IntegralShopView> {

    public IntegralShopPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
