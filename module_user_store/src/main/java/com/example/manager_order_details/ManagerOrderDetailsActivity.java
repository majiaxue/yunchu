package com.example.manager_order_details;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manager_order_details.adapter.ManagerOrderDetailsAdapter;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;

import butterknife.BindView;

/**
 * 订单明细
 */
public class ManagerOrderDetailsActivity extends BaseActivity<ManagerOrderDetailsView, ManagerOrderDetailsPresenter> implements ManagerOrderDetailsView {


    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.manager_order_details_tab)
    TabLayout managerOrderDetailsTab;
    @BindView(R2.id.manager_order_details_rec)
    RecyclerView managerOrderDetailsRec;

    @Override
    public int getLayoutId() {
        return R.layout.activity_manager_order_details;
    }

    @Override
    public void initData() {
        includeTitle.setText("订单明细");
        presenter.initTab(managerOrderDetailsTab);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        managerOrderDetailsRec.setLayoutManager(linearLayoutManager);
        presenter.selectAllOrder(3);
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public ManagerOrderDetailsView createView() {
        return this;
    }

    @Override
    public ManagerOrderDetailsPresenter createPresenter() {
        return new ManagerOrderDetailsPresenter(this);
    }

    @Override
    public void loadAdapter(ManagerOrderDetailsAdapter managerOrderDetailsAdapter) {
        managerOrderDetailsRec.setAdapter(managerOrderDetailsAdapter);
    }
}
