package com.example.vip;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.VipBean;
import com.example.common.CommonResource;
import com.example.module_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;
import com.example.vip.adapter.VipAdapter;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class VipPresenter extends BasePresenter<VipView> {

    private List<VipBean> vipBeans;

    public VipPresenter(Context context) {
        super(context);
    }

    public void getMerberLevels() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.MERBERLEVELS);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("会员等级列表"+result);
                vipBeans = JSON.parseArray(result, VipBean.class);
                VipAdapter vipAdapter = new VipAdapter(mContext, vipBeans, R.layout.activity_my_vip_item);
                if (getView()!=null){
                    getView().loadAdapter(vipAdapter);
                }
                vipAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                             getUpLevel(index);
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("会员等级列表onError"+errorCode);
            }
        }));
    }
    public void getUpLevel(final int index){
        Map build = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).addParms("upLevelId",vipBeans.get(index).getId()).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getData(CommonResource.UPLEVEL, build);
        RetrofitUtil.getInstance().toSubscribe(data,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("会员升级"+result);
                String stringValue = SPUtil.getStringValue(CommonResource.LEVELID);
                if (!"2".equals(stringValue))
                {
                    ARouter.getInstance().build("/module_user_store/PaymentActivity").withInt("type",2).withString("payMoney",vipBeans.get(index).getPrice()+"").withString("levelId",vipBeans.get(index).getId()+"").navigation();
                }else if (!"3".equals(stringValue))
                {
                    ARouter.getInstance().build("/module_user_store/PaymentActivity").withInt("type",2).withString("payMoney",vipBeans.get(index).getPrice()+"").withString("levelId",vipBeans.get(index).getId()+"").navigation();
                }else if (!"4".equals(stringValue))
                {
                    ARouter.getInstance().build("/module_user_store/PaymentActivity").withInt("type",2).withString("payMoney",vipBeans.get(index).getPrice()+"").withString("levelId",vipBeans.get(index).getId()+"").navigation();
                }
                else {
                    Toast.makeText(mContext, "请勿重复升级", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("会员升级onError"+errorCode+errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }
        }));
    }


    @Override
    protected void onViewDestroy() {

    }
}
