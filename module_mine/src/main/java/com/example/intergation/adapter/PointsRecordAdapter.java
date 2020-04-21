package com.example.intergation.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.PointsMingxiBean;
import com.example.module_mine.R;

import java.util.List;

public class PointsRecordAdapter extends MyRecyclerAdapter<PointsMingxiBean> {
    public PointsRecordAdapter(Context context, List<PointsMingxiBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, PointsMingxiBean data, int position) {
        holder.setText(R.id.my_integral_rec_type, data.getNote())
                .setText(R.id.my_integral_rec_time, data.getCreateTime());
        LinearLayout huoqu = holder.getView(R.id.lin_huoqu);
        TextView tixian = holder.getView(R.id.my_integral_rec_count1);
        if (data.getType() == 0) {
            holder.setText(R.id.my_integral_rec_count1, "+" + data.getPrice());
            huoqu.setVisibility(View.GONE);
            tixian.setVisibility(View.VISIBLE);
        } else if (data.getType() == 1) {
            holder.setText(R.id.my_integral_rec_count1, "-" + data.getPrice());
            huoqu.setVisibility(View.GONE);
            tixian.setVisibility(View.VISIBLE);
        }
        //status  提现状态（0：待审核 1：已通过 2：已拒绝）  加了一个这个
//        if (data.getStatus() == 0) {
//            holder.setText(R.id.my_integral_rec_agree, "待审核");
//        } else if (data.getStatus() == 1) {
//            holder.setText(R.id.my_integral_rec_agree, "已通过");
//        } else if (data.getStatus() == 2) {
//            holder.setText(R.id.my_integral_rec_agree, "已拒绝");
//        }
    }
}
