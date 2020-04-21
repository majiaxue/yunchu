package com.example.browsinghistory.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.BrowsingHistoryBean;
import com.example.module_user_mine.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class BrowsingHistoryChildAdapter extends MyRecyclerAdapter<BrowsingHistoryBean> {


    public BrowsingHistoryChildAdapter(Context context, List<BrowsingHistoryBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, BrowsingHistoryBean data, int position) {

        holder.setImageFresco(R.id.browsing_history_child_image, data.getPic());
        holder.setText(R.id.browsing_history_child_name, data.getName());
        holder.setText(R.id.browsing_history_child_price, "￥" + data.getPrice());
        holder.setText(R.id.browsing_history_child_payment_amount, data.getSale() + "人付款");
//        holder.setText(R.id.browsing_history_child_good_reputation, data.getGoodReputation() + "好评");
//        holder.setText(R.id.browsing_history_child_shop, data.getSellerName());

    }

}
