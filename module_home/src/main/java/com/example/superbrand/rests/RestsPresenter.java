package com.example.superbrand.rests;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.RestsBean;
import com.example.common.CommonResource;
import com.example.module_home.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnTripartiteCallBack;
import com.example.net.RetrofitUtil;
import com.example.superbrand.adapter.RestsAdapter;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class RestsPresenter extends BasePresenter<RestsView> {

    private List<RestsBean.DataBeanX> dataListBean = new ArrayList<>();
    private RestsAdapter restsAdapter;

    public RestsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initList(final int page, int index) {
        LogUtil.e("index          "+index+"page        "+page);
        Map build = MapUtil.getInstance().addParms("min_id", page).addParms("brandcat", index).build();
        final Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.BRANDLIST, build);
        RetrofitUtil.getInstance().toSubscribe(data, new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                ProcessDialogUtil.dismissDialog();
                LogUtil.e("RestsPresenter" + result);
                if (result.contains("\"code\":1")) {
                    final RestsBean restsBean = JSON.parseObject(result, new TypeReference<RestsBean>() {
                    }.getType());

                    if (page == 1) {
                        dataListBean.clear();
                    }
                    dataListBean.addAll(restsBean.getData());
                    if (restsAdapter == null){
                        LogUtil.e("1111111111111111111111111");
                        restsAdapter = new RestsAdapter(mContext, dataListBean, R.layout.item_rests_rec);
                        if (getView() != null) {
                            getView().loadAdapter(restsAdapter);
                        }
                    }else{
                        LogUtil.e("222222222222222222222222"+restsAdapter);
                        restsAdapter.notifyDataSetChanged();
                    }

                    restsAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int index) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(mContext, "点击了我", Toast.LENGTH_SHORT).show();
                                    ARouter.getInstance()
                                            .build("/module_classify/tshop_home")
                                            .withString("url", restsBean.getData().get(index).getItem().get(0).getCouponurl())
                                            .navigation();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(mContext, "没有更多商品了", Toast.LENGTH_SHORT).show();
                }

                if (getView() != null) {
                    getView().refreshSuccess();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("RestsPresenterErrorMsg" + errorMsg);
                ProcessDialogUtil.dismissDialog();
                if (getView() != null) {
                    getView().refreshSuccess();
                }
            }
        }));
    }

}
