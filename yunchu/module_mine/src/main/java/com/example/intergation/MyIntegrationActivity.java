package com.example.intergation;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.intergation.tixian.TiXianActivity;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.order.adapter.OrderVPAdapter;
import com.example.utils.SPUtil;

import butterknife.BindView;

@Route(path = "/mine/intergation")
public class MyIntegrationActivity extends BaseActivity<MyIntegrationView, MyIntegrationPresenter> implements MyIntegrationView {
    @BindView(R2.id.my_integral_back)
    ImageView myIntegralBack;
    @BindView(R2.id.my_integral_shop)
    TextView myIntegralShop;
    @BindView(R2.id.my_integral_text)
    TextView myIntegralText;
    @BindView(R2.id.my_integral_tab)
    TabLayout myIntegralTab;
    @BindView(R2.id.points_mx_vp)
    ViewPager pointMyVp;
    private double jifen;




    @Override
    public int getLayoutId() {
        return R.layout.activity_my_integral;
    }

    @Override
    public void initData() {
        presenter.initTab(myIntegralTab);
        myIntegralTab.setupWithViewPager(pointMyVp);
        presenter.initVp(getSupportFragmentManager());
    }

    @Override
    public void initClick() {
        myIntegralBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //提现
        myIntegralShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/mine/tixian").withDouble("jifen",jifen).navigation();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadUserInfo();
    }

    @Override
    public MyIntegrationView createView() {
        return this;
    }

    @Override
    public MyIntegrationPresenter createPresenter() {
        return new MyIntegrationPresenter(this);
    }



    @Override
    public void updateVP(OrderVPAdapter vpAdapter) {
        pointMyVp.setAdapter(vpAdapter);
    }

    @Override
    public void loadUserInfo(UserInfoBean userInfoBean) {
        jifen =userInfoBean.getBlance();
        myIntegralText.setText(userInfoBean.getBlance()+"");
    }
}
