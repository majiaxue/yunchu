package com.example.yunchushengxian.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.CommonResource;
import com.example.entity.EventBusBean;
import com.example.utils.LogUtil;
import com.example.utils.SPUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, CommonResource.WXAPPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            LogUtil.e("============"+baseResp.errCode);
            if (baseResp.errCode == 0) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                String wxpay = SPUtil.getStringValue("wxpay");
                if ("1".equals(wxpay)) {
                    SPUtil.addParm("wxpay", "0");
                    EventBus.getDefault().post(new EventBusBean(CommonResource.WXPAY_SUCCESS_LOCAL));
                } else if ("2".equals(wxpay)) {
                    SPUtil.addParm("wxpay", "3");
                    EventBus.getDefault().post(new EventBusBean(CommonResource.WXPAY_SUCCESS));
                } else if ("4".equals(wxpay)) {
                    SPUtil.addParm("wxpay", "5");
                    EventBus.getDefault().post(new EventBusBean(CommonResource.WXPAY_SUCCESS_UP));
                }else  if ("5".equals(wxpay)){
                    SPUtil.addParm("wxpay", "6");
                    EventBus.getDefault().post(new EventBusBean(CommonResource.WXPAY_SUCCESS_LOCAL));
                }
                finish();
            } else {
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                ARouter.getInstance().build("/module_user_mine/MineOrderActivity")
                        .withInt("type", 1)
                        .navigation();
                if ("2".equals(SPUtil.getStringValue("wxpay"))) {
                    EventBus.getDefault().post(new EventBusBean(CommonResource.WXPAY_CANCEL));
                }
                finish();
            }
        }
    }
}
