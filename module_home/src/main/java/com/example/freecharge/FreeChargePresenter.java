package com.example.freecharge;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.fastjson.JSON;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.FreeChargeBean;
import com.example.common.CommonResource;
import com.example.freecharge.adapter.FreeChargeAdapter;
import com.example.freecharge.adapter.FreeChargeLookAdapter;
import com.example.module_home.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class FreeChargePresenter extends BasePresenter<FreeChargeView> {

    private FreeChargeAdapter freeChargeAdapter;
    private FreeChargeLookAdapter freeChargeLookAdapter;

    public FreeChargePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void freeChargeActivity(final int activityType, final RecyclerView freeChargeRec) {
        Map type = MapUtil.getInstance().addParms("type", activityType).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.TAOLIJIN,type);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                ProcessDialogUtil.dismissDialog();
                LogUtil.e("FreeChargePresenterResult" + result);
                final List<FreeChargeBean> freeChargeBeans = JSON.parseArray(result, FreeChargeBean.class);
                if (freeChargeBeans.size() != 0) {
                    if (getView() != null) {
                        getView().noGoods(false);
                    }
                    if (activityType == 0) {
                        if (freeChargeAdapter == null) {
                            freeChargeAdapter = new FreeChargeAdapter(mContext, freeChargeBeans, R.layout.item_free_charge_activity_rec);
                        }else{
                            freeChargeAdapter.notifyDataSetChanged();
                        }
                        getView().load(freeChargeAdapter);

                        freeChargeAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                            @Override
                            public void ViewOnClick(View view, final int index) {
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        jumpToTB(freeChargeBeans.get(index).getSendUrl());
                                    }
                                });
                            }
                        });
                    } else {
                        if (freeChargeLookAdapter == null) {
                            freeChargeLookAdapter = new FreeChargeLookAdapter(mContext, freeChargeBeans, R.layout.item_free_charge_look_back_rec);
                        }else{
                            freeChargeLookAdapter.notifyDataSetChanged();
                        }
                        getView().load(freeChargeLookAdapter);
                    }
                } else {
                    if (getView() != null) {
                        getView().noGoods(true);
                    }
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                ProcessDialogUtil.dismissDialog();
                LogUtil.e("FreeChargePresenterErrorMsg" + errorMsg);
            }
        }));
    }

    private void jumpToTB(String originUrl) {
        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        //打开指定页面
        AlibcPage alibcPage = new AlibcPage(originUrl);
        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show((Activity) mContext, alibcPage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                        LogUtil.e(tradeResult.toString());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                        LogUtil.e("阿里百川" + code + "         " + msg);
                    }
                });
    }


}
