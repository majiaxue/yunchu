package com.example.confirm_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.bean.CartBean;
import com.example.bean.ShippingAddressBean;
import com.example.confirm_order.adapter.ConfirmOrderAdapter;
import com.example.confirm_order.adapter.ConfirmOrderInsideAdapter;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.SpaceItemDecoration;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 确认订单
 */

public class ConfirmOrderActivity extends BaseActivity<ConfirmOrderView, ConfirmOrderPresenter> implements ConfirmOrderView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.confirm_order_name)
    TextView confirmOrderName;
    @BindView(R2.id.confirm_order_phone)
    TextView confirmOrderPhone;
    @BindView(R2.id.confirm_order_detail)
    TextView mDetail;
    @BindView(R2.id.confirm_order_choose_address)
    TextView confirmOrderChooseAddress;
    @BindView(R2.id.confirm_order_rela)
    RelativeLayout confirmOrderRela;
    @BindView(R2.id.confirm_order_total_price)
    TextView confirmOrderTotalPrice;
    @BindView(R2.id.confirm_order_total_yunfei)
    TextView confirmOrderTotalYunfei;
    @BindView(R2.id.confirm_order_total_coupon)
    TextView confirmOrderTotalCoupon;
    @BindView(R2.id.confirm_order_final_price)
    TextView confirmOrderFinalPrice;
    @BindView(R2.id.confirm_order_submit)
    TextView confirmOrderSubmit;
    @BindView(R2.id.confirm_order_count)
    TextView mCount;
    @BindView(R2.id.confirm_order_inside_rv)
    RecyclerView confirmOrderInsideRv;
    @BindView(R2.id.confirm_order_delivery_txt2)
    TextView confirmOrderDeliveryTxt2;
    @BindView(R2.id.confirm_order_delivery_choose_coupon)
    TextView confirmOrderDeliveryChooseCoupon;
    @BindView(R2.id.confirm_order_temp1)
    TextView confirmOrderTemp1;
    @BindView(R2.id.confirm_order_temp2)
    TextView confirmOrderTemp2;

    private double couponMoney = 0.0;
    private double totalMoney;

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initData() {
        includeTitle.setText("确认订单");
        Intent intent = getIntent();
        String bean = intent.getStringExtra("bean");
        List<CartBean> beanList = JSON.parseArray(bean, CartBean.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        confirmOrderInsideRv.setLayoutManager(layoutManager);
        if (confirmOrderInsideRv.getItemDecorationCount() < 1) {
            confirmOrderInsideRv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getResources().getDimension(R.dimen.dp_10)));
        }
        presenter.loadData(beanList);
        presenter.getAddress();
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmOrderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToPayment();
            }
        });

        confirmOrderRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToShippingAddress();
            }
        });

        confirmOrderChooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToShippingAddress();
            }
        });

        confirmOrderDeliveryChooseCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmOrderActivity.this, "暂无可用优惠劵", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            ShippingAddressBean addressBean = (ShippingAddressBean) data.getSerializableExtra("address");
            presenter.addressBean = addressBean;
            presenter.isCan = false;
            loadAddress(addressBean);
        }
    }

    @Override
    public void loadAddress(ShippingAddressBean addressBean) {
        confirmOrderName.setText(addressBean.getAddressName());
        confirmOrderPhone.setText(addressBean.getAddressPhone());
        mDetail.setText(addressBean.getAddressProvince() + addressBean.getAddressCity() + addressBean.getAddressArea() + addressBean.getAddressDetail());
        presenter.getPostage(addressBean.getAddressProvince());
        confirmOrderChooseAddress.setVisibility(View.GONE);
    }

    @Override
    public void loadPostage(double feight, double price, int number) {
        totalMoney = price;
        confirmOrderTotalYunfei.setText("+￥" + new DecimalFormat("0.00").format(feight));
        confirmOrderTotalPrice.setText("￥" + new DecimalFormat("0.00").format((price - feight)));
        confirmOrderFinalPrice.setText(new DecimalFormat("0.00").format(price - couponMoney) + "");
        mCount.setText("共" + number + "件");
    }

    @Override
    public void couponAfter(double amount) {
        couponMoney = amount;
        confirmOrderTotalCoupon.setText("-￥" + new DecimalFormat("0.00").format(couponMoney));
        if (totalMoney != 0) {
            confirmOrderFinalPrice.setText(new DecimalFormat("0.00").format(totalMoney - couponMoney) + "");
        }
    }

    @Override
    public void noAddress() {
        confirmOrderChooseAddress.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadRv(ConfirmOrderInsideAdapter adapter) {
        confirmOrderInsideRv.setAdapter(adapter);
    }

    @Override
    public ConfirmOrderView createView() {
        return this;
    }

    @Override
    public ConfirmOrderPresenter createPresenter() {
        return new ConfirmOrderPresenter(this);
    }

}
