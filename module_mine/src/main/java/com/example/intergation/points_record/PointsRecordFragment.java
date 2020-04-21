package com.example.intergation.points_record;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.intergation.adapter.PointsRecordAdapter;
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

public class PointsRecordFragment extends BaseFragment<PointsRecordView, PointsRecordPresenter> implements PointsRecordView {
    @BindView(R2.id.points_record_rv)
    RecyclerView pointsRecordRv;
    @BindView(R2.id.smart)
    SmartRefreshLayout smart;
    private int page =1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_points_record;
    }

    @Override
    public void initData() {
        ProcessDialogUtil.showProcessDialog(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        pointsRecordRv.setLayoutManager(layoutManager);
        pointsRecordRv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.dp_8)));

        //设置 Header 为 官方主题 样式
        smart.setRefreshHeader(new MaterialHeader(getContext()));
        //设置 Footer 为 默认 样式
        smart.setRefreshFooter(new ClassicsFooter(getContext()));
    }

    @Override
    public void initClick() {
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.loadData(page);
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.loadData(page);
            }
        });

    }

    @Override
    public void loadRv(PointsRecordAdapter adapter) {
        pointsRecordRv.setAdapter(adapter);
    }

    @Override
    public PointsRecordView createView() {
        return this;
    }

    @Override
    public PointsRecordPresenter createPresenter() {
        return new PointsRecordPresenter(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(page);
    }

    @Override
    public void loadFinish() {
        smart.finishRefresh();
        smart.finishLoadMore();
    }
}
