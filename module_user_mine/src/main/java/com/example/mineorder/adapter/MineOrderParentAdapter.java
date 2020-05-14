package com.example.mineorder.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.MineOrderBean;
import com.example.mineorder.stayappraise.adapter.StayAppraiseChildAdapter;
import com.example.module_user_mine.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/6/11
 * Describe:
 */
public class MineOrderParentAdapter extends MyRecyclerAdapter<MineOrderBean.OrderListBean> {

    private TextView left;
    private TextView right;

    public MineOrderParentAdapter(Context context, List<MineOrderBean.OrderListBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, final MineOrderBean.OrderListBean data, int position) {
        left = holder.getView(R.id.mine_order_parent_btn_left);
        right = holder.getView(R.id.mine_order_parent_btn_right);
        if (data.getStatus() == 1) {
            //1待发货
            holder.setText(R.id.mine_order_parent_status, "买家已付款");
            holder.setText(R.id.mine_order_parent_btn_left, "申请退款");
            holder.setText(R.id.mine_order_parent_btn_right, "提醒发货");
        } else if (data.getStatus() == 3) {
            //3待评论
            holder.setText(R.id.mine_order_parent_status, "交易成功");
//            holder.setText(R.id.mine_order_parent_btn_left, "再次购买");
//            holder.setText(R.id.mine_order_parent_btn_right, "立即评价");
            left.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
        } else if (data.getStatus() == 6) {
            //6待付款
            holder.setText(R.id.mine_order_parent_status, "等待买家付款");
            holder.setText(R.id.mine_order_parent_btn_left, "删除订单");
            holder.setText(R.id.mine_order_parent_btn_right, "付款");
        } else if (data.getStatus() == 2) {
            //2待收货
            holder.setText(R.id.mine_order_parent_status, "卖家已发货");
            holder.setText(R.id.mine_order_parent_btn_left, "查看物流");
            holder.setText(R.id.mine_order_parent_btn_right, "确认收货");
        } else if (data.getStatus() == 4 || data.getStatus() == 5) {
            //45 已失效
            holder.setText(R.id.mine_order_parent_status, "已失效");
            holder.setText(R.id.mine_order_parent_btn_left, "删除订单");
            holder.setText(R.id.mine_order_parent_btn_right, "再次购买");
        }else if (data.getStatus() == 7){
            //7 已评价
            holder.setText(R.id.mine_order_parent_status, "已评价");
            holder.setText(R.id.mine_order_parent_btn_left, "已评价");
            right.setVisibility(View.GONE);
        }else if (data.getStatus() == 11)
        {
            holder.setText(R.id.mine_order_parent_status, "已失效");
            left.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
        }

        holder.setText(R.id.mine_order_parent_shop, data.getSellerName());

        holder.setText(R.id.mine_order_parent_total, "共" + data.getTotalCount() + "件商品  合计：￥" + data.getPayAmount());

        viewTwoOnClickListener.ViewTwoOnClick( holder.getView(R.id.mine_order_parent_btn_left), holder.getView(R.id.mine_order_parent_btn_right), position);

        RecyclerView childRec = holder.getView(R.id.mine_order_parent_child_rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        childRec.setLayoutManager(linearLayoutManager);
        if (data.getStatus() == 3) {
            StayAppraiseChildAdapter stayAppraiseChildAdapter = new StayAppraiseChildAdapter(context, data.getOrderItems(), R.layout.item_stay_appraise_child);
            childRec.setAdapter(stayAppraiseChildAdapter);
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

        } else {
            MineOrderChildAdapter mineOrderChildAdapter = new MineOrderChildAdapter(context, data.getOrderItems(), R.layout.item_mine_order_child_rec);
            childRec.setAdapter(mineOrderChildAdapter);
            if (data.getStatus() == 1) {
                //待发货
                mineOrderChildAdapter.setOnItemClick(new OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        ARouter.getInstance()
                                .build("/module_user_mine/OrderDetailsActivity")
                                .withString("orderSn", data.getOrderItems().get(position).getOrderSn())
                                .navigation();
                    }
                });
            } else if (data.getStatus() == 2) {
                //待收货
                mineOrderChildAdapter.setOnItemClick(new OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        ARouter.getInstance()
                                .build("/module_user_mine/OrderDetailsActivity")
                                .withString("orderSn", data.getOrderItems().get(position).getOrderSn())
                                .navigation();
                    }
                });
            } else if (data.getStatus() == 6) {
                //待付款
                mineOrderChildAdapter.setOnItemClick(new OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        ARouter.getInstance()
                                .build("/module_user_mine/ObligationActivity")
                                .withString("orderSn", data.getOrderItems().get(position).getOrderSn())
                                .navigation();
                    }
                });
            }
        }
    }
}
