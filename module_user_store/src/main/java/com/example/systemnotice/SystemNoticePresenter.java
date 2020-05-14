package com.example.systemnotice;

import android.content.Context;

import com.example.mvp.BasePresenter;

public class SystemNoticePresenter extends BasePresenter<SystemNoticeView> {
    public SystemNoticePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
