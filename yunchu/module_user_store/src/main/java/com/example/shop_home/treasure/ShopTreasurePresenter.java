package com.example.shop_home.treasure;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.HotSaleBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.type_detail.adapter.TypeDetailLstAdapter;
import com.example.type_detail.adapter.TypeDetailWaterfallAdapter;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class ShopTreasurePresenter extends BasePresenter<ShopTreasureView> {

    private List<HotSaleBean.DataBean> dataList = new ArrayList<>();
    private TypeDetailLstAdapter lstAdapter;
    private TypeDetailWaterfallAdapter waterfallAdapter;

    private boolean isWaterfall = false;
    private boolean isPositiveSalesVolume = false;
    private boolean isPositivePrice = false;
    private boolean isPositiveCredit = false;
    private int flag = 0;

    public ShopTreasurePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(String sellerId, final int page) {
        Map map = MapUtil.getInstance().addParms("sellerId", sellerId).addParms("pageNum", page).addParms("pageSize", "2").build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                if (getView() != null) {
                    getView().loadFinish();
                }
                LogUtil.e("店铺详情：" + result);
                if (page == 1) {
                    dataList.clear();
                }
                HotSaleBean hotSaleBean = JSON.parseObject(result, new TypeReference<HotSaleBean>() {
                }.getType());
                dataList.addAll(hotSaleBean.getData());
                if (lstAdapter == null) {
                    lstAdapter = new TypeDetailLstAdapter(mContext, dataList, R.layout.rv_type_detail_lst);
                    waterfallAdapter = new TypeDetailWaterfallAdapter(mContext, dataList, R.layout.rv_commend);
                    getView().loadLstRv(lstAdapter, flag);
                } else {
                    if (isWaterfall) {
                        getView().loadWaterfallRv(waterfallAdapter, flag > 1 ? flag - 1 : flag);
                    } else {
                        getView().loadLstRv(lstAdapter, flag > 1 ? flag - 1 : flag);
                    }
                }
                lstAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        ARouter.getInstance().build("/module_user_store/GoodsDetailActivity")
                                .withString("id", dataList.get(position).getId() + "")
                                .withString("sellerId", dataList.get(position).getSellerId())
                                .withString("commendId", dataList.get(position).getProductCategoryId() + "")
                                .navigation();
//                        mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class));
                    }
                });

                waterfallAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        ARouter.getInstance().build("/module_user_store/GoodsDetailActivity")
                                .withString("id", dataList.get(position).getId() + "")
                                .withString("sellerId", dataList.get(position).getSellerId())
                                .withString("commendId", dataList.get(position).getProductCategoryId() + "")
                                .navigation();
//                        mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class));
                    }
                });
                flag = dataList.size();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                if (getView() != null) {
                    getView().loadFinish();
                }
                LogUtil.e(errorCode + "------------" + errorMsg);
            }
        }));
    }


    public void ChangeShow() {
        if (isWaterfall) {
            getView().loadLstRv(lstAdapter, flag);
            isWaterfall = false;
        } else {
            getView().loadWaterfallRv(waterfallAdapter, flag);
            isWaterfall = true;
        }
    }

    public void changeTyep(int index) {
        isPositiveSalesVolume = index == 1 ? !isPositiveSalesVolume : false;
        isPositivePrice = index == 2 ? !isPositivePrice : false;
        isPositiveCredit = index == 3 ? !isPositiveCredit : false;
        getView().updateTitle(isPositiveSalesVolume, isPositivePrice, isPositiveCredit);
    }

    public void loadMore(String sellerId, final int page) {
        Map map = MapUtil.getInstance().addParms("sellerId", sellerId).addParms("pageNum", page).addParms("pageSize", "2").build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                if (getView() != null) {
                    getView().loadFinish();
                }
                LogUtil.e("店铺详情：" + result);
                if (page == 1) {
                    dataList.clear();
                }
                HotSaleBean hotSaleBean = JSON.parseObject(result, new TypeReference<HotSaleBean>() {
                }.getType());
                dataList.addAll(hotSaleBean.getData());

                if (isWaterfall) {
                    getView().loadWaterfallRv(waterfallAdapter, 0);
                } else {
                    getView().loadLstRv(lstAdapter, 0);
                }

                flag = dataList.size();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                if (getView() != null) {
                    getView().loadFinish();
                }
                LogUtil.e(errorCode + "------------" + errorMsg);
            }
        }));
    }
}
