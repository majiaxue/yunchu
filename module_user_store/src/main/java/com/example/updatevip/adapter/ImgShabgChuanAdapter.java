package com.example.updatevip.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.user_store.R;

import java.util.List;

public class ImgShabgChuanAdapter extends MyRecyclerAdapter<String> {
    public ImgShabgChuanAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String data, int position) {
        Glide.with(context).load(data).into((ImageView) holder.getView(R.id.rv_order_assess_img));
        if (position==getItemCount()-1){
            holder.getView(R.id.rv_order_assess_delete).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.rv_order_assess_delete).setVisibility(View.VISIBLE);
        }
        if (viewOnClickListener != null) {
            viewOnClickListener.ViewOnClick(holder.getView(R.id.rv_order_assess_delete), position);
        }
    }
}
