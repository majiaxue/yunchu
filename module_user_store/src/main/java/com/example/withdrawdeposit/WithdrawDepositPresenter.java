package com.example.withdrawdeposit;

import android.app.Activity;
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

public class WithdrawDepositPresenter extends BasePresenter<WithdrawDepositView> {

    public WithdrawDepositPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void aliPayWithdraw(String zfb, String name, String money) {
        Map map = MapUtil.getInstance().addParms("account", zfb).addParms("amount", Double.valueOf(money)).addParms("name", name).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.TIXIAN, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("支付宝提现成功" + result);
//                loadData();
                Toast.makeText(mContext, "提现成功,次日的下午4点至6点到账请注意查收", Toast.LENGTH_SHORT).show();
                ((Activity) mContext).finish();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("支付宝提现失败" + "-------" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
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
//                        getView().loadBalance("0");
                    } else {
//                        getView().loadBalance(result);
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
}
