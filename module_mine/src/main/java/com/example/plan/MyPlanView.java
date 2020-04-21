package com.example.plan;

import com.example.mvp.IView;
import com.example.plan.adapter.RebateAdapter;

public interface MyPlanView extends IView {

    void loadAdapter(RebateAdapter rebateAdapter);

    void loadFinish();
}
