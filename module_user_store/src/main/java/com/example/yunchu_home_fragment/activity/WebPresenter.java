package com.example.yunchu_home_fragment.activity;

import android.content.Context;

import com.example.mvp.BasePresenter;

public class WebPresenter extends BasePresenter<WebsView> {
    public WebPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
