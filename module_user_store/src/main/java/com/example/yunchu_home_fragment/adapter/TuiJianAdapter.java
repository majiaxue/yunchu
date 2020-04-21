package com.example.yunchu_home_fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.TuiJIanBean;
import com.example.user_store.R;

import java.util.List;

public class TuiJianAdapter extends MyRecyclerAdapter<TuiJIanBean.DataBean> {
    public TuiJianAdapter(Context context, List<TuiJIanBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, TuiJIanBean.DataBean data, int position) {
        holder.setText(R.id.tv_prodect,data.getName())
                .setText(R.id.induce,data.getProductCategoryName())
                .setText(R.id.price,"￥"+data.getPrice())
                .setText(R.id.vip_price,"￥"+data.getVipPrice())
                .setImageUrl(R.id.img,data.getPic());
        if (data.getVipPrice()==0){
            TextView vipprice = holder.itemView.findViewById(R.id.vip_price);
            TextView h = holder.itemView.findViewById(R.id.hong_vip);
            vipprice.setVisibility(View.GONE);
            h.setVisibility(View.GONE);
        }
    }
}
