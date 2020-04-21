package com.example.shop_home;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.example.mvp.BasePresenter;
import com.example.utils.LogUtil;

public class TShopHomePresenter extends BasePresenter<TShopHomeView> {
    public TShopHomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void login() {

        final AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin((Activity) mContext, new AlibcLoginCallback() {

            @Override
            public void onSuccess() {

                LogUtil.e("获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(mContext, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
