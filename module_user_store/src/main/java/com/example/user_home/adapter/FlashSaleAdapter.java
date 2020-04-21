package com.example.user_home.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.FlashSaleGoodsBean;
import com.example.user_store.R;

import java.util.List;

public class FlashSaleAdapter extends MyRecyclerAdapter<FlashSaleGoodsBean> {

    public FlashSaleAdapter(Context context, List<FlashSaleGoodsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, FlashSaleGoodsBean data, int position) {
        holder.setImageFresco(R.id.rv_hot_image, data.getPic())
                .setText(R.id.rv_hot_name, data.getName())
                .setText(R.id.rv_hot_price_new, "￥" + data.getPrice());
    }
}
