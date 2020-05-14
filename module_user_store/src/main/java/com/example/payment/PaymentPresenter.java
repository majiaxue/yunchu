package com.example.payment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.example.bean.AliPayBean;
import com.example.bean.SubmitOrderBean;
import com.example.bean.WeChatPayBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.OnTripartiteCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.PopUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.example.view.SelfDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.cache.Sp;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PaymentPresenter extends BasePresenter<PaymentView> {
    private String info = "";
    private final int ALI_CODE = 0x123;
    private SubmitOrderBean submitOrderBean;

    public PaymentPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ALI_CODE) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                String resultStatus = map.get("resultStatus");
                String result = map.get("result");
                String memo = map.get("memo");
                if ("9000".equals(resultStatus)) {
                    ARouter.getInstance().build("/module_user_store/pay_success")
                            .withSerializable("bean", submitOrderBean)
                            .navigation();
                    ((Activity) mContext).finish();
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    ARouter.getInstance().build("/module_user_mine/MineOrderActivity")
                            .withInt("type", 1)
                            .navigation();
                    ((Activity) mContext).finish();
                }
            }
        }

    };

    public void payYueE(final SubmitOrderBean submitOrderBean) {
        //totalAmount  userCode outTradeNo(就是orderSn)  note（备注）
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalAmount", submitOrderBean.getTotalAmount() + "");
        jsonObject.put("userCode", SPUtil.getUserCode());
        jsonObject.put("outTradeNo", submitOrderBean.getMasterNo());
        jsonObject.put("note", "1234");
        String jsonString = JSON.toJSONString(jsonObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postDataWithBody(CommonResource.BALANCEPAY, requestBody);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("余额支付" + result);
                ARouter.getInstance().build("/module_user_store/pay_success")
                        .withSerializable("bean", submitOrderBean)
                        .navigation();
                ((Activity) mContext).finish();
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("余额支付onError" + errorCode);
                ARouter.getInstance().build("/module_user_mine/MineOrderActivity")
                        .withInt("type", 1)
                        .navigation();
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public void pay(boolean isWeChat, SubmitOrderBean submitOrderBean, int type) {
        LogUtil.e("type" + type);
        if (type == 1) {
            SubmitOrderBean submitOrderBean1 = new SubmitOrderBean();
            submitOrderBean1.setTotalAmount(submitOrderBean.getTotalAmount());
            submitOrderBean1.setMasterNo(submitOrderBean.getMasterNo());
            this.submitOrderBean = submitOrderBean1;
        } else {
            this.submitOrderBean = submitOrderBean;
        }
        if (isWeChat) {
//            Toast.makeText(mContext, "微信对接中,请使用支付宝支付", Toast.LENGTH_SHORT).show();
            final IWXAPI api = WXAPIFactory.createWXAPI(mContext, CommonResource.WXAPPID, false);
            if (type == 1) {
               Map map=new HashMap();
//                if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
//                     map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getVipPrice()).addParms("user_code", SPUtil.getUserCode()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").build();
//                }else {
//                     map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getTotalAmount()).addParms("user_code", SPUtil.getUserCode()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").build();
//                }
                map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getTotalAmount()).addParms("user_code", SPUtil.getUserCode()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").build();

                LogUtil.e("价钱--"+submitOrderBean.getTotalAmount());
               // Map map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getTotalAmount()).addParms("user_code", SPUtil.getUserCode()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").build();
                Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postData(CommonResource.WXPAYPAY, map);
                RetrofitUtil.getInstance().toSubscribe(observable, new OnTripartiteCallBack(new OnDataListener() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        LogUtil.e("微信支付-------------->" + result);
                        try {

                            WeChatPayBean payBean = JSON.parseObject(result, WeChatPayBean.class);

                            PayReq request = new PayReq();
                            request.appId = payBean.getAppid();
                            request.partnerId = payBean.getPartnerid();
                            request.prepayId = payBean.getPrepayid();
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr = payBean.getNoncestr();
                            request.timeStamp = payBean.getTimestamp();
                            request.sign = payBean.getSign();

                            api.sendReq(request);
                            SPUtil.addParm("wxpay", "1");
                            getView().callBack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        LogUtil.e(errorCode + "------------" + errorMsg);
                    }
                }));
            } else {
                Map wxMap=new HashMap();
//                if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
//                     wxMap = MapUtil.getInstance().addParms("totalAmout", submitOrderBean.getVipPrice()).addParms("orderSn", submitOrderBean.getMasterNo()).addParms("productName", "易购商城").build();
//                }else {
//                    wxMap = MapUtil.getInstance().addParms("totalAmout", submitOrderBean.getTotalAmount()).addParms("orderSn", submitOrderBean.getMasterNo()).addParms("productName", "易购商城").build();
//
//                }
                wxMap = MapUtil.getInstance().addParms("totalAmout", submitOrderBean.getTotalAmount()).addParms("orderSn", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").build();

                LogUtil.e("这是价钱----===="+submitOrderBean.getTotalAmount());
                Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postData(CommonResource.WXPAY, wxMap);
                RetrofitUtil.getInstance().toSubscribe(observable, new OnTripartiteCallBack(new OnDataListener() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        LogUtil.e("微信支付-------------->" + result);
                        try {
                            WeChatPayBean payBean = JSON.parseObject(result, WeChatPayBean.class);
                            PayReq request = new PayReq();
                            request.appId = payBean.getAppid();
                            request.partnerId = payBean.getPartnerid();
                            request.prepayId = payBean.getPrepayid();
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr = payBean.getNoncestr();
                            request.timeStamp = payBean.getTimestamp();
                            request.sign = payBean.getSign();
                            api.sendReq(request);
                            SPUtil.addParm("wxpay", "1");
                            getView().callBack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        LogUtil.e(errorCode + "------------" + errorMsg);
                    }
                }));
            }

        } else {
            if (type == 1) {
                Map map = new HashMap();
//                if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
//                    Map build = MapUtil.getInstance().addParms("user_code", SPUtil.getUserCode()).build();
//                    String jsonString = JSON.toJSONString(build);
//                     map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getVipPrice()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").addParms("map", jsonString).addParms("type", 1).build();
//                }else {
//                    Map build = MapUtil.getInstance().addParms("user_code", SPUtil.getUserCode()).build();
//                    String jsonString = JSON.toJSONString(build);
//                     map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getTotalAmount()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").addParms("map", jsonString).addParms("type", 1).build();
//                }

                Map build = MapUtil.getInstance().addParms("user_code", SPUtil.getUserCode()).build();
                String jsonString = JSON.toJSONString(build);
                map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getTotalAmount()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").addParms("map", jsonString).addParms("type", 1).build();

                ProcessDialogUtil.showProcessDialog(mContext);
//                WaitDialog.show((AppCompatActivity) mContext, null);

                Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHead(CommonResource.TOPAY, map, SPUtil.getToken());
                RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        LogUtil.e("支付宝：" + result);
                        AliPayBean aliPayBean = JSON.parseObject(result, AliPayBean.class);
                        info = aliPayBean.getBody();
                        Thread thread = new Thread(payRunnable);
                        thread.start();
                        getView().callBack();
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {

                    }
                }));
            } else {
                Map map = new HashMap();
//                if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
//                    Map build = MapUtil.getInstance().addParms("user_code", SPUtil.getUserCode()).build();
//                    String jsonString = JSON.toJSONString(build);
//                    map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getVipPrice()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").addParms("userCode", SPUtil.getUserCode()).addParms("type", 0).build();
//                }else {
//                    Map build = MapUtil.getInstance().addParms("user_code", SPUtil.getUserCode()).build();
//                    String jsonString = JSON.toJSONString(build);
//                    map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getTotalAmount()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").addParms("userCode", SPUtil.getUserCode()).addParms("type", 0).build();
//                }
                Map build = MapUtil.getInstance().addParms("user_code", SPUtil.getUserCode()).build();
                String jsonString = JSON.toJSONString(build);
                map = MapUtil.getInstance().addParms("totalAmount", submitOrderBean.getTotalAmount()).addParms("masterNo", submitOrderBean.getMasterNo()).addParms("productName", "云厨生鲜").addParms("userCode", SPUtil.getUserCode()).addParms("type", 0).build();
                ProcessDialogUtil.showProcessDialog(mContext);
//                WaitDialog.show((AppCompatActivity) mContext, null);

                Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHead(CommonResource.TOPAY, map, SPUtil.getToken());
                RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        LogUtil.e("支付宝：" + result);
                        AliPayBean aliPayBean = JSON.parseObject(result, AliPayBean.class);
                        info = aliPayBean.getBody();
                        Thread thread = new Thread(payRunnable);
                        thread.start();
                        getView().callBack();
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {

                    }
                }));
            }

        }
    }

    private Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(info, true);

            Message msg = new Message();
            msg.what = ALI_CODE;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };

    public void goBack() {
        final SelfDialog dialog = new SelfDialog(mContext);
        dialog.setTitle("提示");
        dialog.setMessage("确定要离开吗？");
        dialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        });

        dialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                ARouter.getInstance().build("/module_user_mine/MineOrderActivity")
                        .withInt("type", 1)
                        .navigation();
                dialog.dismiss();
                ((Activity) mContext).finish();
            }
        });

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                PopUtil.setTransparency(mContext, 1.0f);
            }
        });
        PopUtil.setTransparency(mContext, 0.3f);
    }

    public void paySuccess() {
        ARouter.getInstance().build("/module_user_store/pay_success")
                .withSerializable("bean", submitOrderBean)
                .navigation();
        ((Activity) mContext).finish();
    }
    public void paySuccesss(String money) {
        ARouter.getInstance().build("/module_user_store/pay_success")
                .withString("money", money)
                .withString("type","2")
                .navigation();
        ((Activity) mContext).finish();
    }

    public void getLevelPay(boolean isWeChat, String payMoney, String levelId) {
        //  参数:totalAmount   userCode   levelId

        if (isWeChat) {
            final IWXAPI api = WXAPIFactory.createWXAPI(mContext, CommonResource.WXAPPID, false);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalAmount", payMoney);
            jsonObject.put("userCode", SPUtil.getUserCode());
            jsonObject.put("levelId", levelId);
            String jsonString = JSON.toJSONString(jsonObject);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postDataWithBody(CommonResource.WXPAYLEVEL, requestBody);
            RetrofitUtil.getInstance().toSubscribe(observable, new OnTripartiteCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("微信支付-------------->" + result);
                    try {
                        WeChatPayBean payBean = JSON.parseObject(result, WeChatPayBean.class);
                        PayReq request = new PayReq();
                        request.appId = payBean.getAppid();
                        request.partnerId = payBean.getPartnerid();
                        request.prepayId = payBean.getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = payBean.getNoncestr();
                        request.timeStamp = payBean.getTimestamp();
                        request.sign = payBean.getSign();
                        api.sendReq(request);
                        SPUtil.addParm("wxpay", "5");
                        getView().callBack();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("微信支付onError"+errorCode + "------------" + errorMsg);
                }
            }));
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalAmount", payMoney);
            LogUtil.e("支付宝价钱----"+payMoney);
            jsonObject.put("userCode", SPUtil.getUserCode());
            jsonObject.put("levelId", levelId);
            String jsonString = JSON.toJSONString(jsonObject);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            //Map map = MapUtil.getInstance().addParms("totalAmout", payMoney).addParms("levelId", levelId).addParms("userCode", SPUtil.getUserCode()).build();
            ProcessDialogUtil.showProcessDialog(mContext);
//                WaitDialog.show((AppCompatActivity) mContext, null);
            Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postDataWithBody(CommonResource.ALIPAYLEVEL, requestBody);
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("支付宝：" + result);
                    AliPayBean aliPayBean = JSON.parseObject(result, AliPayBean.class);
                    info = aliPayBean.getBody();
                    Thread thread = new Thread(payRunnable);
                    thread.start();
                    getView().callBack();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("支付宝onError：" + errorCode  +"---------"+errorMsg);
                }
            }));
        }


    }
}
