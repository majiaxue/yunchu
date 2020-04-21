package com.example.businessapplication.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.PopupGoodsClassBean;
import com.example.module_user_mine.R;

import java.util.List;

public class PopupGoodsClassifyAdapter extends MyRecyclerAdapter<PopupGoodsClassBean> {

    public PopupGoodsClassifyAdapter(Context context, List<PopupGoodsClassBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, PopupGoodsClassBean data, int position) {
        holder.setText(R.id.popup_item_goods_classify_text,data.getSellerCategoryName());
    }
}
