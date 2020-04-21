package com.example.integral_shop;

import com.example.mvp.BaseActivity;
import com.example.user_store.R;

public class IntegralShopActivity extends BaseActivity<IntegralShopView,IntegralShopPresenter> implements  IntegralShopView {


    @Override
    public int getLayoutId() {
        return R.layout.activity_integral_shop;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {

    }

    @Override
    public IntegralShopView createView() {
        return this;
    }

    @Override
    public IntegralShopPresenter createPresenter() {
        return new IntegralShopPresenter(this);
    }
}
