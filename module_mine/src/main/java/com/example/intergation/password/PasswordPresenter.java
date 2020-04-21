package com.example.intergation.password;

import android.content.Context;

import com.example.mvp.BasePresenter;

public class PasswordPresenter extends BasePresenter<PasswordView> {
    public PasswordPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
