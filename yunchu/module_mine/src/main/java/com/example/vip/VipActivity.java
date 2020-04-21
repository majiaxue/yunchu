package com.example.vip;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.utils.ProcessDialogUtil;
import com.example.vip.adapter.VipAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = "/mine/vip")
public class VipActivity extends BaseActivity<VipView, VipPresenter> implements VipView {
    @BindView(R2.id.dazhuanpan_back)
    ImageView dazhuanpanBack;
    @BindView(R2.id.vip_recy)
    RecyclerView vipRecy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_vip;
    }

    @Override
    public void initData() {
        ProcessDialogUtil.showProcessDialog(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        vipRecy.setLayoutManager(linearLayoutManager);
        presenter.getMerberLevels();

    }

    @Override
    public void initClick() {
        dazhuanpanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public VipView createView() {
        return this;
    }

    @Override
    public VipPresenter createPresenter() {
        return new VipPresenter(this);
    }

    @Override
    public void loadAdapter(VipAdapter vipAdapter) {
        vipRecy.setAdapter(vipAdapter);
    }
}
