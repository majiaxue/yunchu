package com.example.confirm_order.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.CartBean;
import com.example.user_store.R;

import java.util.List;

public class ConfirmOrderInsideAdapter extends MyRecyclerAdapter<CartBean> {
    public ConfirmOrderInsideAdapter(Context context, List<CartBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CartBean data, int position) {
        holder.setText(R.id.confirm_order_count, "X" + data.getQuantity())
                .setImageFresco(R.id.confirm_order_img, data.getProductPic())
                .setText(R.id.confirm_order_goods, data.getProductName())
                .setText(R.id.confirm_order_color, data.getProductAttr())
                .setText(R.id.confirm_order_price, "￥" + data.getPrice());
    }
}
