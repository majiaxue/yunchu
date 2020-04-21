package com.example.refundparticulars;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.bean.AlterationBean;
import com.example.common.CommonResource;
import com.example.module_user_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.refundparticulars.adapter.RefundParticularsRecAdapter;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.example.utils.CustomDialog;
import com.kongzue.dialog.v3.WaitDialog;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class RefundParticularsPresenter extends BasePresenter<RefundParticularsView> {

//    private CustomDialog customDialog = new CustomDialog(mContext);

    public RefundParticularsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initView(String orderSn) {
//        customDialog.show();
        ProcessDialogUtil.showProcessDialog(mContext);
//        WaitDialog.show((AppCompatActivity)mContext,null);
        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHead(CommonResource.RETURNTABLE, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
//                customDialog.dismiss();
//                WaitDialog.dismiss();
                LogUtil.e("RefundParticularsResult退款详情------------->" + result);
//                List<AlterationBean> list = JSON.parseArray(result, AlterationBean.class);
                AlterationBean alterationBean = JSON.parseObject(result, new TypeReference<AlterationBean>() {
                }.getType());
                if (alterationBean != null && alterationBean.getR().size() != 0) {
                    if (getView() != null) {
                        getView().initView(alterationBean.getR());
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
//                customDialog.dismiss();
//                WaitDialog.dismiss();
                LogUtil.e("RefundParticularsErrorMsg------------->" + errorMsg);
            }
        }));

    }

    public void goodsList(RecyclerView refundParticularsRec, List<AlterationBean.RBean.ItemlistBean> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        refundParticularsRec.setLayoutManager(linearLayoutManager);
        RefundParticularsRecAdapter refundParticularsRecAdapter = new RefundParticularsRecAdapter(mContext, itemList, R.layout.item_refund_particulars_rec);
        refundParticularsRec.setAdapter(refundParticularsRecAdapter);
    }
}
