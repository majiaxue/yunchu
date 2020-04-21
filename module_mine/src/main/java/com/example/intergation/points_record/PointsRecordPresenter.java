package com.example.intergation.points_record;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.bean.PointsMingxiBean;
import com.example.common.CommonResource;
import com.example.intergation.adapter.PointsRecordAdapter;
import com.example.module_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;

import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class PointsRecordPresenter extends BasePresenter<PointsRecordView> {

    private List<PointsMingxiBean> recordBeans;
    private PointsRecordAdapter adapter;
    private List<PointsMingxiBean> dateBeans=new ArrayList<>();
    public PointsRecordPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(final int page) {
        Map map = MapUtil.getInstance().addParms("type", "0").addParms("userCode",SPUtil.getUserCode()).addParms("page",page).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.MEMBERBANLANCE, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("积分明细：" + result);

                try {
                    if (result != null) {
                        if (page ==1)
                      {
                          dateBeans.clear();
                      }
                      recordBeans = JSON.parseArray(result, PointsMingxiBean.class);
                      dateBeans.addAll(recordBeans);
                        if (adapter ==null)
                        {
                            adapter = new PointsRecordAdapter(mContext, dateBeans, R.layout.rv_points_record_item);
                            getView().loadRv(adapter);
                        }else {
                            adapter.notifyDataSetChanged();
                        }
                        if (getView() != null) {

                            getView().loadFinish();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "-------" + errorMsg);
            }
        }));

    }
}
