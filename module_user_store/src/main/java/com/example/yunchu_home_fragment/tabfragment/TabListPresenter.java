package com.example.yunchu_home_fragment.tabfragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.TeJIaBean;
import com.example.bean.TuiJIanBean;
import com.example.common.CommonResource;
import com.example.goods_detail.GoodsDetailActivity;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.yunchu_home_fragment.adapter.TeJiaAdapter;
import com.example.yunchu_home_fragment.adapter.TuiJianAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class TabListPresenter extends BasePresenter<TabListView> {

    private List<TuiJIanBean.DataBean> dataBeans=new ArrayList<>();
    private TuiJianAdapter saleHotAdapter;

    public TabListPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
    public void getData(int id, final int pagrSize){
        LogUtil.e("这是id-------------"+id);
        //天天特价
        Map map = MapUtil.getInstance().addParms("categoryId", id).addParms("newStatus", "1").addParms("pageNum",pagrSize).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                getView().refresh();
                LogUtil.e("首页推荐: " + result);
                final TuiJIanBean hotSaleBean = JSON.parseObject(result, new TypeReference<TuiJIanBean>() {
                }.getType());
                if (hotSaleBean != null) {
                    if (pagrSize==1){
                        dataBeans.clear();
                    }
                    dataBeans.addAll(hotSaleBean.getData());
                    saleHotAdapter = new TuiJianAdapter(mContext, dataBeans, R.layout.item_tab_list);
                    if (getView()!=null){
                        getView().loadSaleHot(saleHotAdapter);
                    }else {
                        saleHotAdapter.notifyDataSetChanged();
                    }

                    saleHotAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            ARouter.getInstance()
                                    .build("/module_user_store/GoodsDetailActivity")
                                    .withString("id", dataBeans.get(position).getId() + "")
                                    .withString("sellerId", dataBeans.get(position).getSellerId()+"")
                                    .withString("commendId", dataBeans.get(position).getProductCategoryId() + "")
                                    .navigation();
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("热销商品: " + errorMsg);
            }
        }));

    }
}
