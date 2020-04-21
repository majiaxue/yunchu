package com.example.coupon.haveexpired;

import android.support.v7.widget.RecyclerView;

import com.example.module_user_mine.R;
import com.example.module_user_mine.R2;
import com.example.mvp.BaseFragment;

import butterknife.BindView;

public class HaveExpiredFragment extends BaseFragment<HaveExpiredView, HaveExpiredPresenter> implements HaveExpiredView {

    @BindView(R2.id.have_expired_rec)
    RecyclerView haveExpiredRec;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_have_expired_;
    }

    @Override
    public void initData() {
        presenter.haveExpiredRec(haveExpiredRec);
    }

    @Override
    public void initClick() {

    }

    @Override
    public HaveExpiredView createView() {
        return this;
    }

    @Override
    public HaveExpiredPresenter createPresenter() {
        return new HaveExpiredPresenter(getContext());
    }

}
