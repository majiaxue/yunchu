package com.example.flashsale;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flashsale.adapter.FlashSaleGoodsAdapter;
import com.example.flashsale.adapter.FlashSaleTimeAdapter;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 限时抢购
 */
@Route(path = "/module_user_store/FlashSaleActivity")
public class FlashSaleActivity extends BaseActivity<FlashSaleView, FlashSalePresenter> implements FlashSaleView {

    @BindView(R2.id.flash_sale_back)
    ImageView flashSaleBack;
    @BindView(R2.id.flash_sale_time_rec)
    RecyclerView flashSaleTimeRec;
    @BindView(R2.id.flash_sale_goods_rec)
    RecyclerView flashSaleGoodsRec;
    @BindView(R2.id.flash_sale_smart_refresh)
    SmartRefreshLayout flashSaleSmartRefresh;

    private String format;
    private int page;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flash_sale;
    }

    @Override
    public void initData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");// yyyy年MM月dd日 HH:mm:ss
//        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        format = simpleDateFormat.format(date);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        flashSaleTimeRec.setLayoutManager(gridLayoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        flashSaleGoodsRec.setLayoutManager(linearLayoutManager);
        try {
            presenter.initView(simpleDateFormat, format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        flashSaleSmartRefresh.setRefreshHeader(new MaterialHeader(this));
        flashSaleSmartRefresh.setRefreshFooter(new ClassicsFooter(this));
    }

    @Override
    public void initClick() {
        flashSaleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        flashSaleSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                presenter.initGoods(format, page);
            }
        });
        flashSaleSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page += page;
                presenter.initGoods(format, page);
            }
        });
    }

    @Override
    public FlashSaleView createView() {
        return this;
    }

    @Override
    public FlashSalePresenter createPresenter() {
        return new FlashSalePresenter(this);
    }

    @Override
    public void loadAdapter(FlashSaleTimeAdapter flashSaleTimeAdapter) {
        flashSaleTimeRec.setAdapter(flashSaleTimeAdapter);
    }

    @Override
    public void loadAdapter(FlashSaleGoodsAdapter flashSaleGoodsAdapter) {
        flashSaleGoodsRec.setAdapter(flashSaleGoodsAdapter);
    }

    @Override
    public void noGoods(boolean onGoods) {
        if (onGoods) {
            flashSaleGoodsRec.setVisibility(View.GONE);
        } else {
            flashSaleGoodsRec.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refreshSuccess() {
        flashSaleSmartRefresh.finishLoadMore();
        flashSaleSmartRefresh.finishRefresh();
    }
}
