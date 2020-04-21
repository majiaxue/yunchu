package com.example.plan.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.RebateItemBean;
import com.example.module_mine.R;

import java.util.List;

public class RebateItemAdapter extends MyRecyclerAdapter<RebateItemBean> {
    public RebateItemAdapter(Context mContext, List<RebateItemBean> rebateItemBeans, int my_plan_detail_item) {
        super(mContext, rebateItemBeans, my_plan_detail_item);
    }

    @Override
    public void convert(RecyclerViewHolder holder, RebateItemBean data, int position) {
        holder.setText(R.id.my_plan_detail_item_qi, "第" + data.getNum() + "期")
                .setText(R.id.my_plan_detail_item_jifen, data.getRebateBalance() + "积分")
                .setText(R.id.my_plan_detail_item_time, data.getRebateTime());
        if (data.getRebateStatus() == 0) {
            holder.setText(R.id.my_plan_detail_item_daozhang, "等待中");
        } else if (data.getRebateStatus() == 1) {
            holder.setText(R.id.my_plan_detail_item_daozhang, "到账成功")
                    .setTextColor(R.id.my_plan_detail_item_daozhang, R.color.colorIndicatorColor);
        }
    }
}
