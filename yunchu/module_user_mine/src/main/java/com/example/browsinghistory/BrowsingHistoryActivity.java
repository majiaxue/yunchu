package com.example.browsinghistory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.browsinghistory.adapter.BrowsingHistoryChildAdapter;
import com.example.module_user_mine.R;
import com.example.module_user_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.utils.ProcessDialogUtil;
import com.example.view.SelfDialog;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 浏览历史
 */
@Route(path = "/module_user_mine/BrowsingHistoryActivity")
public class BrowsingHistoryActivity extends BaseActivity<BrowsingHistoryView, BrowsingHistoryPresenter> implements BrowsingHistoryView {

    @BindView(R2.id.browsing_history_rec)
    RecyclerView browsingHistoryRec;
    @BindView(R2.id.browsing_history_check_all)
    ImageView browsingHistoryCheckAll;
    @BindView(R2.id.browsing_history_delete)
    TextView browsingHistoryDelete;
    @BindView(R2.id.browsing_history_bottom)
    LinearLayout browsingHistoryBottom;
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.include_right_btn)
    TextView includeRightBtn;
    @BindView(R2.id.browsing_history_smart_refresh)
    SmartRefreshLayout browsingHistorySmartRefresh;

    private int nextPage = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_browsing_history;
    }

    @Override
    public void initData() {
        includeRightBtn.setText("删除");
        includeTitle.setText("浏览记录");
        ProcessDialogUtil.showProcessDialog(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        browsingHistoryRec.setLayoutManager(linearLayoutManager);
        presenter.browsingHistoryRec(nextPage);


        browsingHistorySmartRefresh.setRefreshHeader(new MaterialHeader(this));
        browsingHistorySmartRefresh.setRefreshFooter(new ClassicsFooter(this));

    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //删除
        includeRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SelfDialog selfDialog = new SelfDialog(BrowsingHistoryActivity.this);
                selfDialog.setTitle("警告");
                selfDialog.setMessage("您即将删除所有浏览历史,是否确认删除!");
                selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                    }
                });

                selfDialog.setYesOnclickListener("确认", new SelfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        ProcessDialogUtil.showProcessDialog(BrowsingHistoryActivity.this);
                        presenter.deleteList();
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
            }
        });


        //设置上拉刷新下拉加载
        browsingHistorySmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                nextPage = 1;
                presenter.browsingHistoryRec(nextPage);

            }
        });
        browsingHistorySmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                nextPage++;
                presenter.browsingHistoryRec(nextPage);
            }
        });
    }

    @Override
    public BrowsingHistoryView createView() {
        return this;
    }

    @Override
    public BrowsingHistoryPresenter createPresenter() {
        return new BrowsingHistoryPresenter(this);
    }

    @Override
    public void noGoods(boolean noGoods) {
        if (noGoods) {
            includeRightBtn.setVisibility(View.GONE);
        } else {
            includeRightBtn.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void refreshSuccess() {
        browsingHistorySmartRefresh.finishLoadMore();
        browsingHistorySmartRefresh.finishRefresh();
    }

    @Override
    public void loadAdapter(BrowsingHistoryChildAdapter browsingHistoryChildAdapter) {
        browsingHistoryRec.setAdapter(browsingHistoryChildAdapter);
    }
}
