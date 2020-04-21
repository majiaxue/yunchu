package com.example.mineorder.stayappraise.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.MineOrderBean;
import com.example.module_user_mine.R;
import com.example.utils.ArithUtil;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/30
 * Describe:
 */
public class StayAppraiseChildAdapter extends MyRecyclerAdapter<MineOrderBean.OrderListBean.OrderItemsBean> {

    public StayAppraiseChildAdapter(Context context, List<MineOrderBean.OrderListBean.OrderItemsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, MineOrderBean.OrderListBean.OrderItemsBean data, int position) {

        holder.setImageFresco(R.id.stay_appraise_child_img, data.getProductPic());
        holder.setText(R.id.stay_appraise_child_name, data.getProductName());
        holder.setText(R.id.stay_appraise_child_message, data.getProductAttr());
        holder.setText(R.id.stay_appraise_child_price, "￥" + data.getProductPrice());
        holder.setText(R.id.stay_appraise_child_count, "X" + data.getProductQuantity());

        holder.setText(R.id.stay_appraise_child_total, "共" + data.getProductQuantity() + "件商品  合计：￥" + ArithUtil.mul(data.getProductQuantity(), data.getProductPrice()));

        viewTwoOnClickListener.ViewTwoOnClick(holder.getView(R.id.stay_appraise_child_btn_left), holder.getView(R.id.stay_appraise_child_btn_right), position);

    }
}
