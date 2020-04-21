package com.example.user_manager_center.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.EquityBean;
import com.example.user_store.R;

import java.util.List;

public class EquityAdapter extends MyRecyclerAdapter<EquityBean> {

    public EquityAdapter(Context context, List<EquityBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, EquityBean data, int position) {
        holder.setImageResource(R.id.equity_img, data.getImage());
        holder.setText(R.id.equity_title, data.getTitle());
        if (!TextUtils.isEmpty(data.getMessage())) {
            holder.setText(R.id.equity_message, data.getMessage());
        } else {
            holder.getView(R.id.equity_message).setVisibility(View.GONE);
        }
    }
}
