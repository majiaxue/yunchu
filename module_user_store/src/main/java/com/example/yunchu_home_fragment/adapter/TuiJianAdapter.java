package com.example.yunchu_home_fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.HotSaleBean;
import com.example.bean.TuiJIanBean;
import com.example.user_store.R;

import java.math.BigDecimal;
import java.util.List;

public class TuiJianAdapter extends MyRecyclerAdapter<HotSaleBean.DataBean> {
    public TuiJianAdapter(Context context, List<HotSaleBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, HotSaleBean.DataBean data, int position) {
        BigDecimal bigDecimal=new BigDecimal(data.getPrice());
        double v = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal big=new BigDecimal(data.getVipPrice());
        double v1 = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
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
