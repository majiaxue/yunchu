package com.example.earnings_preview;

import android.content.Context;

import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import io.reactivex.Observable;

public class EarningsPreviewPrsenter extends BasePresenter<EarningsPreviewView> {

    public EarningsPreviewPrsenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void selectUserProfit(){
        Map userCode = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postData(CommonResource.SELECTUSERPROFIT, userCode);
        RetrofitUtil.getInstance().toSubscribe(observable,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                UserInfoBean userInfoBean = new Gson().fromJson(result, new TypeToken<UserInfoBean>() {
                }.getType());
                LogUtil.e("收益预览:" + userInfoBean);
                if (userInfoBean != null) {
                    if (getView() != null) {
                        getView().loadUserProfit(userInfoBean);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }
}
