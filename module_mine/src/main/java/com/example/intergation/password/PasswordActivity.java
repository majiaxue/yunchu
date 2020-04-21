package com.example.intergation.password;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_mine.R;
import com.example.mvp.BaseActivity;
@Route(path = "/model_mine/PasswordActivity")
public class PasswordActivity extends BaseActivity<PasswordView,PasswordPresenter> implements PasswordView{
    @Override
    public int getLayoutId() {
        return R.layout.acticvity_mima2;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {

    }

    @Override
    public PasswordView createView() {
        return this;
    }

    @Override
    public PasswordPresenter createPresenter() {
        return new PasswordPresenter(this);
    }
}
