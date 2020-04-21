package com.example.manager_order_details.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.ManagerOrderDetailsBean;
import com.example.user_store.R;

import java.util.List;

public class ManagerOrderDetailsAdapter extends MyRecyclerAdapter<ManagerOrderDetailsBean> {

    private int type;

    public ManagerOrderDetailsAdapter(Context context, List<ManagerOrderDetailsBean> mList, int mLayoutId, int type) {
        super(context, mList, mLayoutId);
        this.type = type;
    }

    @Override
    public void convert(RecyclerViewHolder holder, ManagerOrderDetailsBean data, int position) {
        holder.setText(R.id.manager_order_details_time, data.getOrderCreateTime());
        holder.setText(R.id.manager_order_details_price, "￥" + data.getIntegration());
        if (3 == type) {
            if (data.getStatus() == 3) {
                holder.setText(R.id.manager_order_details_message, "结算收入");
            } else {
                holder.setText(R.id.manager_order_details_message, "预估收入");
            }
        } else if (2 == type) {
            if (data.getStatus() != 3) {
                holder.setText(R.id.manager_order_details_message, "预估收入");
            }
        } else {
            if (data.getStatus() == 3) {
                holder.setText(R.id.manager_order_details_message, "结算收入");
            }
        }

    }
}
