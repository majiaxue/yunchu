package com.example.coupon.all;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.module_user_mine.R;
import com.example.module_user_mine.R2;
import com.example.mvp.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllFragment extends BaseFragment<AllView, AllPresenter> implements AllView {

    @BindView(R2.id.all_rec)
    RecyclerView allRec;
    @BindView(R2.id.all_go)
    TextView allGo;
    @BindView(R2.id.all_hide)
    LinearLayout allHide;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    public void initData() {
        presenter.allRec(allRec);
    }

    @Override
    public void initClick() {

    }

    @Override
    public AllView createView() {
        return this;
    }

    @Override
    public AllPresenter createPresenter() {
        return new AllPresenter(getContext());
    }

}
