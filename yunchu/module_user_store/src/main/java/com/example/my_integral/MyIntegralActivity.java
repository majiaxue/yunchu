package com.example.my_integral;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;

import butterknife.BindView;

@Route(path = "/module_user_store/MyIntegralActivity")
public class MyIntegralActivity extends BaseActivity<MyIntegralView, MyIntegralPresenter> implements MyIntegralView {


    @BindView(R2.id.my_integral_back)
    ImageView myIntegralBack;
    @BindView(R2.id.my_integral_shop)
    TextView myIntegralShop;
    @BindView(R2.id.my_integral_text)
    TextView myIntegralText;
    @BindView(R2.id.my_integral_tab)
    TabLayout myIntegralTab;
    @BindView(R2.id.my_integral_rec)
    RecyclerView my_integral_rec;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_integral;
    }

    @Override
    public void initData() {
        presenter.initTab(myIntegralTab);
    }

    @Override
    public void initClick() {
        myIntegralBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //积分商城
        myIntegralShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public MyIntegralView createView() {
        return this;
    }

    @Override
    public MyIntegralPresenter createPresenter() {
        return new MyIntegralPresenter(this);
    }

}
