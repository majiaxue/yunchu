package com.example.local_main;

import com.example.module_local.R;
import com.example.mvp.BaseFragmentActivity;

public class LocalMainActivity extends BaseFragmentActivity<LocalMainView, LocalMainPresenter> implements LocalMainView {
    @Override
    public int getLayoutId() {
        return R.layout.activity_local_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {

    }

    @Override
    public LocalMainView createView() {
        return this;
    }

    @Override
    public LocalMainPresenter createPresenter() {
        return new LocalMainPresenter(this);
    }
}
