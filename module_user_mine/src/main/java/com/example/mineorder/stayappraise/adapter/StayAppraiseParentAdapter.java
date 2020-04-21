package com.example.mineorder.stayappraise.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.MineOrderBean;
import com.example.module_user_mine.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/30
 * Describe:待评价
 */
public class StayAppraiseParentAdapter extends MyRecyclerAdapter<MineOrderBean.OrderListBean> {

    private RecyclerView stayAppraiseChildRec;
    private LinearLayoutManager linearLayoutManager;
    private StayAppraiseChildAdapter stayAppraiseChildAdapter;
//    private List<MineOrderBean.OrderListBean.OrderItemsBean> beanList = new ArrayList<>();

    public StayAppraiseParentAdapter(Context context, List<MineOrderBean.OrderListBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, final MineOrderBean.OrderListBean data, int position) {
        if (data.getStatus() == 3) {
            //3待评论
            holder.setText(R.id.stay_appraise_parent_status, "交易成功");
        }
        holder.setText(R.id.stay_appraise_parent_shop, data.getSellerName());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.stay_appraise_parent_shop), position);

//        beanList.addAll(data.getOrderItems());

        stayAppraiseChildRec = holder.getView(R.id.stay_appraise_child_rec);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        stayAppraiseChildRec.setLayoutManager(linearLayoutManager);
        stayAppraiseChildAdapter = new StayAppraiseChildAdapter(context, data.getOrderItems(), R.layout.item_stay_appraise_child);
        stayAppraiseChildRec.setAdapter(stayAppraiseChildAdapter);

        stayAppraiseChildAdapter.setViewTwoOnClickListener(new ViewTwoOnClickListener() {
            @Override
            public void ViewTwoOnClick(View view1, View view2, final int position) {
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //再次购买
                        ARouter.getInstance()
                                .build("/module_user_store/GoodsDetailActivity")
                                .withString("id", data.getOrderItems().get(position).getProductId() + "")
                                .withString("sellerId", data.getSellerId())
                                .withString("commendId", data.getOrderItems().get(position).getProductCategoryId() + "")
                                .navigation();
                    }
                });

                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //立即评价
                        ARouter.getInstance()
                                .build("/module_user_mine/OrderAssessActivity")
                                .withInt("position", position)
                                .withSerializable("beanList", data)
                                .navigation();

                    }
                });
            }
        });
    }
}
