package com.example.yunchu_home_fragment.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/model_user_store/WebActivity")
public class WebActivity extends BaseActivity<WebsView, WebPresenter> implements WebsView {
    @BindView(R2.id.webview)
    WebView webview;
    @Autowired(name = "url")
    String url;
    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initData() {

        ARouter.getInstance().inject(this);

        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置Web视图
        webview.setWebViewClient(new WebViewClient ());



    }

    @Override
    public void initClick() {
    }

    @Override
    public WebsView createView() {
        return this;
    }

    @Override
    public WebPresenter createPresenter() {
        return new WebPresenter(this);
    }
}
