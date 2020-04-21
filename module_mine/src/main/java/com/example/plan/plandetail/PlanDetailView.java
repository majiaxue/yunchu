package com.example.plan.plandetail;

import com.example.mvp.IView;
import com.example.plan.adapter.RebateItemAdapter;

public interface PlanDetailView extends IView {
    void loadAdapter(RebateItemAdapter rebateItemAdapter);
}
