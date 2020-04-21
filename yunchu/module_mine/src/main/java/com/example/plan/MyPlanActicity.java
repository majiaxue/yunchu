package com.example.plan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.plan.adapter.RebateAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = "/mine/plan")
public class MyPlanActicity extends BaseActivity<MyPlanView, MyPlanPresenter> implements MyPlanView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.include_right)
    ImageView includeRight;
    @BindView(R2.id.include_right_btn)
    TextView includeRightBtn;
    @BindView(R2.id.my_plan)
    RecyclerView myPlan;
    @BindView(R2.id.smart)
    SmartRefreshLayout smart;
    private int page =1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_plan;
    }

    @Override
    public void initData() {
        includeTitle.setText("返利计划");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myPlan.setLayoutManager(linearLayoutManager);
        presenter.getRabatePlan(page);
        //设置 Header 为 官方主题 样式
        smart.setRefreshHeader(new MaterialHeader(this));
        //设置 Footer 为 默认 样式
        smart.setRefreshFooter(new ClassicsFooter(this));

    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getRabatePlan(page);
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getRabatePlan(page);
            }
        });
    }
    @Override
    public void loadFinish() {
        smart.finishRefresh();
        smart.finishLoadMore();
    }

    @Override
    public MyPlanView createView() {
        return this;
    }

    @Override
    public MyPlanPresenter createPresenter() {
        return new MyPlanPresenter(this);
    }


    @Override
    public void loadAdapter(RebateAdapter rebateAdapter) {
        myPlan.setAdapter(rebateAdapter);
    }
}
