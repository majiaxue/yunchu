package com.example.flashsale;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.FlashSaleGoodsBean;
import com.example.bean.FlashSaleTimeBean;
import com.example.common.CommonResource;
import com.example.flashsale.adapter.FlashSaleGoodsAdapter;
import com.example.flashsale.adapter.FlashSaleTimeAdapter;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_store.R;
import com.example.utils.DisplayUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.kongzue.dialog.v3.WaitDialog;
import com.raizlabs.android.dbflow.list.IFlowCursorIterator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class FlashSalePresenter extends BasePresenter<FlashSaleView> {

    private List<FlashSaleTimeBean> timeList = new ArrayList<>();
    private FlashSaleTimeAdapter flashSaleTimeAdapter;
    private List<FlashSaleGoodsBean> flashSaleGoodsBeans;
    private List<FlashSaleGoodsBean> GoodsList = new ArrayList<>();
    private FlashSaleGoodsAdapter flashSaleGoodsAdapter;

    public FlashSalePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initView(SimpleDateFormat simpleDateFormat,String format) throws ParseException {

        Date parse = simpleDateFormat.parse(format);
        final long time = parse.getTime();

        timeList.add(new FlashSaleTimeBean(simpleDateFormat.format(time - 1 * 60 * 60 * 1000), false));
        timeList.add(new FlashSaleTimeBean(simpleDateFormat.format(time), true));
        timeList.add(new FlashSaleTimeBean(simpleDateFormat.format(time + 1 * 60 * 60 * 1000), false));
        timeList.add(new FlashSaleTimeBean(simpleDateFormat.format(time + 2 * 60 * 60 * 1000), false));

        flashSaleTimeAdapter = new FlashSaleTimeAdapter(mContext, timeList, R.layout.item_flash_sale_top);
        if (getView() != null) {
            getView().loadAdapter(flashSaleTimeAdapter);
        }

        flashSaleTimeAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                timeList.get(position).setCheck(true);
                for (int i = 0; i < timeList.size(); i++) {
                    if (position != i) {
                        timeList.get(i).setCheck(false);
                    }
                    flashSaleTimeAdapter.notifyDataSetChanged();
                }
//                WaitDialog.show((AppCompatActivity) mContext, "");
                initGoods(timeList.get(position).getTime(), 1);
            }
        });

        initGoods(simpleDateFormat.format(time), 1);

    }

    public void initGoods(final String time, final int page) {
        ProcessDialogUtil.showProcessDialog(mContext);
        Map map = MapUtil.getInstance().addParms("shiduan", time).addParms("page", page).build();
        final Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.QUERYXSQGSPLIST, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("限时商品" + result);
                getView().refreshSuccess();
                flashSaleGoodsBeans = JSON.parseArray(result, FlashSaleGoodsBean.class);
                if (flashSaleGoodsBeans.size() != 0) {
                    getView().noGoods(false);
                    if (1 == page) {
                        GoodsList.clear();
                    }
                    GoodsList.addAll(flashSaleGoodsBeans);
                    if (flashSaleGoodsAdapter == null) {
                        flashSaleGoodsAdapter = new FlashSaleGoodsAdapter(mContext, GoodsList, R.layout.item_flash_sale_goods, time);
                        if (getView() != null) {
                            getView().loadAdapter(flashSaleGoodsAdapter);
                        }
                    } else {
                        flashSaleGoodsAdapter.notifyDataSetChanged();
                    }

                    flashSaleGoodsAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int index) {
                            final TextView textView = view.findViewById(R.id.flash_sale_goods_rec_type);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if ("马上抢购".equals(textView.getText().toString())) {
//                                        Toast.makeText(mContext, "马上抢购", Toast.LENGTH_SHORT).show();
                                        ARouter.getInstance()
                                                .build("/module_user_store/GoodsDetailActivity")
                                                .withString("id", GoodsList.get(index).getId() + "")
                                                .withString("sellerId", GoodsList.get(index).getSeller_id() + "")
                                                .withString("commendId", GoodsList.get(index).getProduct_category_id() + "")
                                                .navigation();
                                    } else if ("已结束".equals(textView.getText().toString())) {
                                        Toast.makeText(mContext, "已结束", Toast.LENGTH_SHORT).show();

                                    } else if ("即将开抢".equals(textView.getText().toString())) {
                                        Toast.makeText(mContext, "即将开抢", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(mContext, "没有商品", Toast.LENGTH_SHORT).show();
                    getView().noGoods(true);
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("限时失败" + errorCode + "-----------" + errorMsg);
                getView().refreshSuccess();
            }
        }));
    }


}
