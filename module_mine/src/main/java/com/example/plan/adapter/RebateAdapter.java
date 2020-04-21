package com.example.plan.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.RebateBean;
import com.example.module_mine.R;

import java.util.List;

public class RebateAdapter extends MyRecyclerAdapter<RebateBean> {
    public RebateAdapter(Context mContext, List<RebateBean> rebateBeans, int my_plan_item) {
        super(mContext, rebateBeans, my_plan_item);
    }

    @Override
    public void convert(RecyclerViewHolder holder, RebateBean data, int position) {
        holder.setText(R.id.my_plan_goods_name,data.getGoodsName())
                .setText(R.id.my_plan_goods_fanli,data.getRebateSuccess()+"/"+data.getRebateNum())
                .setText(R.id.my_plan_goods_gongji_jifen,data.getRebateAmount()+"积分")
                .setText(R.id.my_plan_goods_price,"￥"+data.getPayAmount())
                .setImageUrl(R.id.my_plan_goods_img,data.getFirstPhoto()+"")
                .setText(R.id.my_plan_goods_time,data.getOrderCreateTime()+"")
        ;
    }
}
