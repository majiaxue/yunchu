package com.example.plan.plandetail;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.bean.RebateItemBean;
import com.example.common.CommonResource;
import com.example.module_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.plan.adapter.RebateItemAdapter;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class PlanDetailPresenter extends BasePresenter<PlanDetailView> {

    private List<RebateItemBean> rebateItemBeans;
    private RebateItemAdapter rebateItemAdapter;

    public PlanDetailPresenter(Context context) {
        super(context);
    }
    public void getRebateItem(String orderSn)
    {
        Map orderSn1 = MapUtil.getInstance().addParms("orderSn", orderSn).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).getData(CommonResource.REBATEPLANITEM, orderSn1);
        RetrofitUtil.getInstance().toSubscribe(data,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("返利计划详情"+result);
                rebateItemBeans = JSON.parseArray(result, RebateItemBean.class);
                if (rebateItemBeans!=null){
                    rebateItemAdapter = new RebateItemAdapter(mContext, rebateItemBeans, R.layout.my_plan_detail_item);
                    getView().loadAdapter(rebateItemAdapter);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("返利计划详情onError"+errorCode);
            }
        }));
    }

    @Override
    protected void onViewDestroy() {

    }
}
