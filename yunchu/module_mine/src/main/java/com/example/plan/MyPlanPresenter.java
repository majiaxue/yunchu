package com.example.plan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.RebateBean;
import com.example.common.CommonResource;
import com.example.module_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.plan.adapter.RebateAdapter;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class MyPlanPresenter extends BasePresenter<MyPlanView> {

    private RebateAdapter rebateAdapter;
    private List<RebateBean> rebateBeans;
    private List<RebateBean> dateBeans= new ArrayList<>();

    public MyPlanPresenter(Context context) {
        super(context);
    }

    public void getRabatePlan(final int page) {
        Map userCode = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).addParms("page", page).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).getData(CommonResource.REBATEPLAN, userCode);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("返利计划列表" + result);
                if (getView() != null) {
                    if (page ==1)
                    {
                        dateBeans.clear();
                    }
                    rebateBeans = JSON.parseArray(result, RebateBean.class);
                    LogUtil.e("++++++++++rebateBeans+++"+rebateBeans);
                    dateBeans.addAll(rebateBeans);
                    if (rebateAdapter ==null)
                    {
                        rebateAdapter = new RebateAdapter(mContext, dateBeans, R.layout.my_plan_item);
                        getView().loadAdapter(rebateAdapter);

                    }else {
                        rebateAdapter .notifyDataSetChanged();
                    }
                    if (getView() != null) {
                        getView().loadFinish();
                    }
                    rebateAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            ARouter.getInstance().build("/mine/plandetail").withString("orderSn", dateBeans.get(position).getOrderSn()).navigation();
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("返利计划列表onError" + errorCode);
                if (getView() != null) {

                    getView().loadFinish();
                }
            }
        }));
    }

    @Override
    protected void onViewDestroy() {

    }
}
