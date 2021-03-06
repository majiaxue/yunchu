package com.example.yunchu_home_fragment.tabfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvp.BaseFragment;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.RvItemDecoration;
import com.example.view.CustomHeader;
import com.example.yunchu_home_fragment.adapter.TuiJianAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class TabListFragment extends BaseFragment<TabListView, TabListPresenter> implements TabListView {
    int id;
    @BindView(R2.id.rec)
    RecyclerView rec;
    @BindView(R2.id.smart)
    SmartRefreshLayout smart;
    int page=1;

    @SuppressLint("ValidFragment")
    public TabListFragment(int id) {
        this.id = id;

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tablist;
    }

    @Override
    public void initData() {
        presenter.getData(id,page);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rec.setLayoutManager(staggeredGridLayoutManager);
        rec.addItemDecoration(new RvItemDecoration((int) getContext().getResources().getDimension(R.dimen.dp_12), (int) getContext().getResources().getDimension(R.dimen.dp_12)));
        //下拉刷新样式
        CustomHeader customHeader = new CustomHeader(getActivity());
        customHeader.setPrimaryColors(getResources().getColor(R.color.colorTransparency));
        smart.setRefreshHeader(customHeader);
    }

    @Override
    public void initClick() {
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getData(id,page);
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getData(id,page);
            }
        });
    }

    @Override
    public TabListView createView() {
        return this;
    }

    @Override
    public TabListPresenter createPresenter() {
        return new TabListPresenter(getContext());
    }

    @Override
    public void loadSaleHot(TuiJianAdapter saleHotAdapter) {
        rec.setAdapter(saleHotAdapter);
    }

    @Override
    public void refresh() {
        smart.finishRefresh();
        smart.finishLoadMore();

    }

    @Override
    public void refreshsuccess() {

    }
}
