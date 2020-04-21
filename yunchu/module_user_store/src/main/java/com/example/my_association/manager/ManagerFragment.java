package com.example.my_association.manager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mvp.BaseFragment;
import com.example.my_association.adapter.ManagerAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

public class ManagerFragment extends BaseFragment<ManagerView, ManagerPresenter> implements ManagerView {

    @BindView(R2.id.manager_rec)
    RecyclerView managerRec;
    @BindView(R2.id.manager_smart)
    SmartRefreshLayout managerSmart;

    private int index;
    private int page = 1;

    public static ManagerFragment newInstance(int index) {
        ManagerFragment fragment = new ManagerFragment();
        Bundle args = new Bundle();
        args.putInt("Index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_manager;
    }

    @Override
    public void initData() {
        index = getArguments().getInt("Index");
        LogUtil.e("下标" + index);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        managerRec.setLayoutManager(linearLayoutManager);
        presenter.initView(page, index);

        managerSmart.setRefreshHeader(new MaterialHeader(getContext()));
        managerSmart.setRefreshFooter(new ClassicsFooter(getContext()));
    }

    @Override
    public void initClick() {
        //设置上拉刷新下拉加载
        managerSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                LogUtil.e("page" + page + "index" + getArguments().getInt("Index"));
                presenter.initView(page, getArguments().getInt("Index"));
            }
        });
        managerSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                LogUtil.e("page" + page + "index" + getArguments().getInt("Index"));
                presenter.initView(page, getArguments().getInt("Index"));
            }
        });
    }

    @Override
    public ManagerView createView() {
        return this;
    }

    @Override
    public ManagerPresenter createPresenter() {
        return new ManagerPresenter(getContext());
    }


    @Override
    public void loadAdapter(ManagerAdapter managerAdapter) {
        managerRec.setAdapter(managerAdapter);
    }

    @Override
    public void refreshSuccess() {
        managerSmart.finishLoadMore();
        managerSmart.finishRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        index = getArguments().getInt("Index");
//        LogUtil.e("下标"+index);

    }
}
