package com.example.plan.plandetail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.plan.adapter.RebateItemAdapter;
import com.example.utils.LogUtil;


import butterknife.BindView;
@Route(path = "/mine/plandetail")
public class PlanDetailActivity extends BaseActivity<PlanDetailView, PlanDetailPresenter> implements PlanDetailView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.my_plan_detail_recy)
    RecyclerView myPlanDetailRecy;
    @Autowired(name="orderSn")
    String orderSn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_plan_detail;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        includeTitle.setText("返利计划");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myPlanDetailRecy.setLayoutManager(linearLayoutManager);
        presenter.getRebateItem(orderSn);
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public PlanDetailView createView() {
        return this;
    }

    @Override
    public PlanDetailPresenter createPresenter() {
        return new PlanDetailPresenter(this);
    }

    @Override
    public void loadAdapter(RebateItemAdapter rebateItemAdapter) {
        myPlanDetailRecy.setAdapter(rebateItemAdapter);
    }
}
