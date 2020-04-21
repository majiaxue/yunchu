package com.example.vip.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.VipBean;
import com.example.module_mine.R;

import java.util.List;

public class VipAdapter extends MyRecyclerAdapter<VipBean> {
    public VipAdapter(Context mContext, List<VipBean> vipBeans, int activity_my_vip_item) {
        super(mContext, vipBeans, activity_my_vip_item);
    }

    @Override
    public void convert(RecyclerViewHolder holder, VipBean data, int position) {
        holder.setText(R.id.vip_note,"购买返佣商品返利时间为"+data.getCycle()+"个月")
                .setText(R.id.vip_name,data.getName());
        TextView kaitong = holder.getView(R.id.vip_kaitong);
        RelativeLayout vipItemRel = holder.getView(R.id.vip_item_rel);
        if (data.getPrice() ==0)
        {
            kaitong.setVisibility(View.GONE);
        }else {
            kaitong.setVisibility(View.VISIBLE);
            vipItemRel.setBackgroundResource(R.drawable.dengjivip);
            kaitong.setText("￥"+data.getPrice()+"立即开通");
        }
        if (viewOnClickListener!=null)
        {
            viewOnClickListener.ViewOnClick(kaitong,position);
        }
    }
}
