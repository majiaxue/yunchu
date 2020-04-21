package com.example.intergation.tixian;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.bean.CashInfoBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.PUT;

public class TiXianPresenter extends BasePresenter<TiXianView> {


    public TiXianPresenter(Context context) {
        super(context);
    }
     public void getAliPay(String amount,String name,String account )
     {
         Map build = MapUtil.getInstance().addParms("account", account).addParms("amount", amount).addParms("name", name).build();
         LogUtil.e("account====="+account);
         LogUtil.e("amount========="+amount);
         LogUtil.e("name========="+name);
         Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.ALIPAY, build, SPUtil.getToken());
         RetrofitUtil.getInstance().toSubscribe(head,new OnMyCallBack(new OnDataListener() {
             @Override
             public void onSuccess(String result, String msg) {
                 LogUtil.e("提现"+result);
                 ((Activity)mContext).finish();
             }

             @Override
             public void onError(String errorCode, String errorMsg) {
                 LogUtil.e("提现onError"+errorCode+errorMsg);
                 Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
             }
         }));

     }
    @Override
    protected void onViewDestroy() {
        EventBus.getDefault().unregister(this);
    }
    public void getCashInfo(){
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.CASHINFO);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("服务费"+result);
                CashInfoBean cashInfoBean = JSON.parseObject(result, CashInfoBean.class);
                if (getView()!=null)
                {
                    getView().loadCashInfo(cashInfoBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("服务费onError"+errorCode);
            }
        }));
    }

    public void getPassWord(){

    }
}
