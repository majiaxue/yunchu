package com.example.group_fans.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group_fans.adapter.GroupFansRvAdapter;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseFragment;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SpaceItemDecoration;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FansFragment extends BaseFragment<FansView, FansPresemter> implements FansView {
    @BindView(R2.id.rec_fans)
    RecyclerView recFans;
    @BindView(R2.id.smart)
    SmartRefreshLayout smart;
    private int page=1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    public void initData() {
        ProcessDialogUtil.showProcessDialog(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recFans.setLayoutManager(layoutManager);
        recFans.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.dp_8)));
        //设置 Header 为 官方主题 样式
        smart.setRefreshHeader(new MaterialHeader(getContext()));
        //设置 Footer 为 默认 样式
        smart.setRefreshFooter(new ClassicsFooter(getContext()));
        presenter.loadData(page,"1");

    }

    @Override
    public void initClick() {

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.loadData(page,"1");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.loadData(page,"1");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public FansView createView() {
        return this;
    }

    @Override
    public FansPresemter createPresenter() {
        return new FansPresemter(getContext());
    }

    @Override
    public void loadFinish() {
        smart.finishRefresh();
        smart.finishLoadMore();
    }

    @Override
    public void loadRv(GroupFansRvAdapter adapter) {
        recFans.setAdapter(adapter);
    }
}
