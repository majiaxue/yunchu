package com.example.mineorder.orderall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.MineOrderBean;
import com.example.bean.SubmitOrderBean;
import com.example.common.CommonResource;
import com.example.mineorder.adapter.MineOrderParentAdapter;
import com.example.module_user_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:全部订单
 */
public class OrderAllPresenter extends BasePresenter<OrderAllView> {

    private List<MineOrderBean.OrderListBean> listBeans = new ArrayList<>();

    public OrderAllPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void orderAllRec() {
        ProcessDialogUtil.showProcessDialog(mContext);
//        WaitDialog.show((AppCompatActivity)mContext,null);
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.ORDERALL, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {

            private MineOrderParentAdapter mineOrderParentAdapter;

            @Override
            public void onSuccess(String result, String msg) {
                try {
                    ProcessDialogUtil.dismissDialog();
                    LogUtil.e("OrderAllPresenterResult-------->" + result);
//                MineOrderBean mineOrderBean = JSON.parseObject(result, new TypeReference<MineOrderBean>() {
//                }.getType());
                    final MineOrderBean mineOrderBean = JSON.parseObject(result, new TypeReference<MineOrderBean>() {
                    }.getType());
                    LogUtil.e("MineOrderBean1" + mineOrderBean);
                    if (mineOrderBean != null) {

                        if (mineOrderBean.getOrderList() != null && mineOrderBean.getOrderList().size() != 0) {
                            listBeans.clear();
                            listBeans.addAll(mineOrderBean.getOrderList());

                            if (mineOrderParentAdapter == null) {
                                mineOrderParentAdapter = new MineOrderParentAdapter(mContext, listBeans, R.layout.item_mine_order_parent_rec);
                            } else {
                                mineOrderParentAdapter.notifyDataSetChanged();
                            }

                            if (getView() != null) {
                                getView().load(mineOrderParentAdapter);
                            }

                            mineOrderParentAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(RecyclerView parent, View view, int position) {
                                    if (listBeans.get(position).getStatus() == 1) {
                                        ARouter.getInstance()
                                                .build("/module_user_mine/OrderDetailsActivity")
                                                .withString("orderSn", listBeans.get(position).getOrderItems().get(0).getOrderSn())
                                                .navigation();
                                    } else if (listBeans.get(position).getStatus() == 2) {
                                        ARouter.getInstance()
                                                .build("/module_user_mine/OrderDetailsActivity")
                                                .withString("orderSn", listBeans.get(position).getOrderItems().get(0).getOrderSn())
                                                .navigation();
                                    } else if (listBeans.get(position).getStatus() == 6) {
                                        //待付款
                                        ARouter.getInstance()
                                                .build("/module_user_mine/ObligationActivity")
                                                .withString("orderSn", listBeans.get(position).getOrderItems().get(0).getOrderSn())
                                                .navigation();
                                    }
                                }
                            });

                            mineOrderParentAdapter.setViewTwoOnClickListener(new MyRecyclerAdapter.ViewTwoOnClickListener() {
                                @Override
                                public void ViewTwoOnClick( View view2, View view3, final int position) {
                                    //去店铺

                                    int status = listBeans.get(position).getStatus();
                                    if (status == 1) {
                                        //1待发货
                                        //申请退款
                                        view2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
//                                            Toast.makeText(mContext, "申请退款", Toast.LENGTH_SHORT).show();
                                                ARouter.getInstance()
                                                        .build("/module_user_mine/RefundActivity")
                                                        .withSerializable("mineOrderBean", mineOrderBean)
                                                        .withInt("position", position)
                                                        .withString("type", "1")
                                                        .navigation();
                                            }
                                        });
                                        //提醒发货
                                        view3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(mContext, "已提醒商家发货!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (status == 3) {
                                        //3待评论
                                        //再次购买
                                        view2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
//                                            Toast.makeText(mContext, "再次购买", Toast.LENGTH_SHORT).show();
                                                //再次购买
                                                ARouter.getInstance()
                                                        .build("/module_user_store/GoodsDetailActivity")
                                                        .withString("id", listBeans.get(position).getOrderItems().get(0).getProductId() + "")
                                                        .withString("sellerId", listBeans.get(position).getSellerId())
                                                        .withString("commendId", listBeans.get(position).getOrderItems().get(0).getProductCategoryId() + "")
                                                        .navigation();
                                            }
                                        });
                                        //立即评价
                                        view3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ARouter.getInstance()
                                                        .build("/module_user_mine/OrderAssessActivity")
                                                        .withSerializable("beanList", listBeans.get(position))
                                                        .withInt("position", position)
                                                        .navigation();
                                            }
                                        });
                                    } else if (status == 6) {
                                        //6待付款
                                        //删除订单
                                        view2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Map build = MapUtil.getInstance().addParms("orderSn", listBeans.get(position).getOrderSn()).build();
                                                Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).getData(CommonResource.DELETE_ORDER, build);
                                                RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
                                                    @Override
                                                    public void onSuccess(String result, String msg) {
                                                        LogUtil.e("删除订单：" + result);
                                                        listBeans.remove(position);
                                                        mineOrderParentAdapter.notifyDataSetChanged();

                                                    }

                                                    @Override
                                                    public void onError(String errorCode, String errorMsg) {
                                                        LogUtil.e("删除errorMsg---------->" + errorMsg);
                                                    }
                                                }));
                                            }
                                        });
                                        //付款
                                        view3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SubmitOrderBean submitOrderBean = new SubmitOrderBean();
                                                submitOrderBean.setTotalAmount(listBeans.get(position).getTotalAmount());
                                                submitOrderBean.setMasterNo(listBeans.get(position).getOrderItems().get(0).getOrderSn());
                                                ARouter.getInstance().build("/module_user_store/PaymentActivity")
                                                        .withSerializable("submitOrderBean", submitOrderBean)
                                                        .navigation();
                                            }
                                        });
                                    } else if (status == 2) {
                                        //2待收货
                                        //查看物流
                                        view2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ARouter.getInstance()
                                                        .build("/module_user_mine/LogisticsInformationActivity")
                                                        .withString("orderSn", listBeans.get(position).getOrderItems().get(0).getOrderSn())
                                                        .withString("goodsImage", listBeans.get(position).getOrderItems().get(0).getProductPic())
                                                        .navigation();
                                            }
                                        });
                                        //确认收货
                                        view3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHeadWithout(CommonResource.ORDERCONFIRM + "/" + listBeans.get(position).getOrderId(), SPUtil.getToken());
                                                RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                                                    @Override
                                                    public void onSuccess(String result, String msg) {
                                                        LogUtil.e("确认收货---->" + result);
                                                        if ("true".equals(result)) {
                                                            listBeans.remove(position);
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(String errorCode, String errorMsg) {
                                                        LogUtil.e("确认收货error---->" + errorMsg);
                                                    }
                                                }));

                                            }
                                        });
                                    } else if (status == 4 || status == 5) {
                                        //45 已失效
                                        //删除订单
                                        view2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Map build = MapUtil.getInstance().addParms("orderId", listBeans.get(position).getOrderId()).build();
                                                Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.ORDERREMOVE, build, SPUtil.getToken());
                                                RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
                                                    @Override
                                                    public void onSuccess(String result, String msg) {
                                                        if ("true".equals(result)) {
                                                            listBeans.remove(position);
                                                            mineOrderParentAdapter.notifyDataSetChanged();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(String errorCode, String errorMsg) {
                                                        LogUtil.e("删除errorMsg---------->" + errorMsg);
                                                    }
                                                }));

                                            }
                                        });
                                        //再次购买
                                        view3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ARouter.getInstance()
                                                        .build("/module_user_store/GoodsDetailActivity")
                                                        .withString("id", listBeans.get(position).getOrderItems().get(0).getProductId() + "")
                                                        .withString("sellerId", listBeans.get(position).getSellerId())
                                                        .withString("commendId", listBeans.get(position).getOrderItems().get(0).getProductCategoryId() + "")
                                                        .navigation();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                ProcessDialogUtil.dismissDialog();
                LogUtil.e("OrderAllPresenterError-------->" + errorMsg);
            }
        }));

    }
}
