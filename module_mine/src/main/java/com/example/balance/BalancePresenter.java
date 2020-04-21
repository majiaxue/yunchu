package com.example.balance;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.balance.income.IncomeFragment;
import com.example.balance.payout.PayoutFragment;
import com.example.cashout.CashoutActivity;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.order.adapter.OrderVPAdapter;
import com.example.utils.LogUtil;
import com.example.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class BalancePresenter extends BasePresenter<BalanceView> {

    private List<Fragment> fragmentList;

    public BalancePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initVP(FragmentManager fm, String[] titleArr) {
        fragmentList = new ArrayList<>();
        fragmentList.add(new IncomeFragment());
        fragmentList.add(new PayoutFragment());
        OrderVPAdapter vpAdapter = new OrderVPAdapter(fm, fragmentList, titleArr);
        if (getView() != null) {
            getView().updateVP(vpAdapter);
        }
    }

    public void loadData() {
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
    }

    public void jumpToCashout() {
        mContext.startActivity(new Intent(mContext, CashoutActivity.class));
    }
}
