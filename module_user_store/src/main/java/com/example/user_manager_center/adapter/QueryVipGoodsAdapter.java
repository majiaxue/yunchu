package com.example.user_manager_center.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.QueryVipGoodsBean;
import com.example.user_store.R;

import java.util.List;

public class QueryVipGoodsAdapter extends MyRecyclerAdapter<QueryVipGoodsBean> {

    public QueryVipGoodsAdapter(Context context, List<QueryVipGoodsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, QueryVipGoodsBean data, int position) {
        holder.setImageFresco(R.id.bottom_img, data.getPic());
        holder.setText(R.id.bottom_name, data.getName());
        holder.setText(R.id.bottom_price, "￥" + data.getPrice());
    }
}
