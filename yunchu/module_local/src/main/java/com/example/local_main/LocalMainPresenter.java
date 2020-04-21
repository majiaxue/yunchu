package com.example.local_main;

import android.content.Context;

import com.example.mvp.BasePresenter;

public class LocalMainPresenter extends BasePresenter<LocalMainView> {
    public LocalMainPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
