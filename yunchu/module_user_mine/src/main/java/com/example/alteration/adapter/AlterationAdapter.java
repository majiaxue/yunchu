package com.example.alteration.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.AlterationBean;
import com.example.module_user_mine.R;
import com.example.utils.LogUtil;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class AlterationAdapter extends MyRecyclerAdapter<AlterationBean.RBean> {

    public AlterationAdapter(Context context, List<AlterationBean.RBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, AlterationBean.RBean data, int position) {
        holder.setText(R.id.alteration_rec_shop_name, data.getSellerName());
        LogUtil.e("data.getReturnType()"+data.getReturnType());
        if ("0".equals(data.getReturnType())) {
            //退货退款
            holder.setText(R.id.alteration_rec_type, "退货退款");
        } else if ("1".equals(data.getReturnType())) {
            //未收货
            holder.setText(R.id.alteration_rec_type, "未收货");
        } else {
            //只退款
            holder.setText(R.id.alteration_rec_type, "只退款");
        }
        //申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝
        if (data.getStatus() == 0) {
            holder.setText(R.id.alteration_rec_status, "等待商家处理");
        } else if (data.getStatus() == 1) {
            holder.setText(R.id.alteration_rec_status, "处理中");
        } else if (data.getStatus() == 2) {
            holder.setText(R.id.alteration_rec_status, "退款成功");
        } else {
            holder.setText(R.id.alteration_rec_status, "商家已拒绝");
        }

        viewOnClickListener.ViewOnClick(holder.getView(R.id.alteration_rec_view_details), position);

        RecyclerView alterationChildRec = holder.getView(R.id.alteration_child_rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        alterationChildRec.setLayoutManager(linearLayoutManager);
        AlterationChildAdapter alterationChildAdapter = new AlterationChildAdapter(context, data.getItemlist(), R.layout.item_alteration_child_rec);
        alterationChildRec.setAdapter(alterationChildAdapter);

    }
}
