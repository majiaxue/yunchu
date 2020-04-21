package com.example.cashout;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.ArithUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialog.v3.WaitDialog;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class CashoutPresenter extends BasePresenter<CashoutView> {
    public CashoutPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        ProcessDialogUtil.showProcessDialog(mContext);
//        WaitDialog.show((AppCompatActivity)mContext,null);
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.GETBALANCE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("余额：" + result);
                if (getView() != null) {
                    if (result == null || "".equals(result)) {
                        getView().loadBalance("0");
                    } else {
                        getView().loadBalance(result);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));

        Observable<ResponseBody> observable1 = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.GETUSERINFO, SPUtil.getToken());//"http://192.168.1.9:4001"
        RetrofitUtil.getInstance().toSubscribe(observable1, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                UserInfoBean userInfoBean = new Gson().fromJson(result, new TypeToken<UserInfoBean>() {
                }.getType());
                String realName = userInfoBean.getRealName();
                String aliAccount = userInfoBean.getAliAccount();

                if (getView() != null) {
//                    getView().loadInfo(realName == null ? "" : realName, aliAccount == null ? "" : aliAccount);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    public void tixian(String money, String zfb, String name) {
        if ("".equals(zfb.trim()) || "".equals(name.trim()) || "".equals(money) || "0".equals(money) || "0.".equals(money)) {
            Toast.makeText(mContext, "请把信息填写完整", Toast.LENGTH_SHORT).show();
        } else if (ArithUtil.exact(Double.valueOf(money), 2) == 0.0) {
            Toast.makeText(mContext, "提现金额不能为0", Toast.LENGTH_SHORT).show();
        } else {
            Map map = MapUtil.getInstance().addParms("account", zfb).addParms("amount", ArithUtil.exact(Double.valueOf(money), 2)).addParms("name", name).build();
            Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.TIXIAN, map, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("提现：" + result);
                    loadData();
                    Toast.makeText(mContext, "提现成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e(errorCode + "-------" + errorMsg);
                }
            }));
        }
    }
}
