package com.example.payment;

import android.support.design.widget.BottomSheetDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.SubmitOrderBean;
import com.example.common.CommonResource;
import com.example.entity.EventBean;
import com.example.mvp.BaseActivity;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.example.utils.net_change_util.PayPasswordView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 确认支付
 */
@Route(path = "/module_user_store/PaymentActivity")
public class PaymentActivity extends BaseActivity<PaymentView, PaymentPresenter> implements PaymentView {
    @BindView(R2.id.payment_back)
    ImageView paymentBack;
    @BindView(R2.id.payment_money)
    TextView paymentMoney;
    @BindView(R2.id.payment_weixin_img)
    ImageView paymentWeixinImg;
    @BindView(R2.id.payment_weixin)
    LinearLayout paymentWeixin;
    @BindView(R2.id.payment_zfb_img)
    ImageView paymentZfbImg;
    @BindView(R2.id.payment_zfb)
    LinearLayout paymentZfb;
    @BindView(R2.id.payment_btn)
    TextView paymentBtn;
    @BindView(R2.id.payment_yue)
    LinearLayout paymentYuE;
    @BindView(R2.id.payment_yue_img)
    ImageView paymentYueEImg;

    @Autowired(name = "submitOrderBean")
    SubmitOrderBean submitOrderBean;
    @Autowired(name = "wxpay")
    String wxpay;
    @Autowired(name = "type")
    int type;
    private boolean isWeChat = true;
    @Autowired(name = "payMoney")
    String payMoney;
    @Autowired(name = "levelId")
    String levelId;
    private boolean isYuE = false;
    private String pasWord;

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
//        if (type == 1) {
//            paymentMoney.setText("￥" + totalAmount);
//
//        } else {

//        }
        if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
            if (type == 2) {
                paymentMoney.setText("￥" + payMoney);
                paymentYuE.setVisibility(View.GONE);
            }else {
                paymentYuE.setVisibility(View.VISIBLE);
                paymentMoney.setText("￥" + submitOrderBean.getVipPrice());
            }
        }else {
            if (type == 2) {
                paymentMoney.setText("￥" + payMoney);
                paymentYuE.setVisibility(View.GONE);
            } else {
                paymentYuE.setVisibility(View.VISIBLE);
                paymentMoney.setText("￥" + submitOrderBean.getTotalAmount());
            }
        }

    }

    @Override
    public void initClick() {
        paymentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goBack();
            }
        });

        paymentWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWeChat = true;
                isYuE = false;
                changePayType();
            }
        });

        paymentZfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWeChat = false;
                isYuE = false;
                changePayType();
            }
        });
        paymentYuE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentWeixinImg.setImageResource(R.drawable.icon_weixuanzhong);
                paymentZfbImg.setImageResource(R.drawable.icon_weixuanzhong);
                paymentYueEImg.setImageResource(R.drawable.icon_xuanzhong);
                isYuE = true;
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                paymentBtn.setEnabled(false);
                if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                    if (isYuE) {
                        openPayPasswordDialog();
                    } else if (type == 2) {
                        presenter.getLevelPay(isWeChat, submitOrderBean.getVipPrice()+"", levelId);
                    } else {
                        presenter.pay(isWeChat, submitOrderBean, type);
                    }
                }else {
                    if (isYuE) {
                        openPayPasswordDialog();
                    } else if (type == 2) {
                        presenter.getLevelPay(isWeChat, payMoney, levelId);
                    } else {
                        presenter.pay(isWeChat, submitOrderBean, type);
                    }
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBean eventBusBean) {
        if ("password".equals(eventBusBean.getCode())) {
            pasWord = eventBusBean.getMsg();
            LogUtil.e("这是password----------" + pasWord);
            getMoney();
        }
    }

    private void getMoney() {
        Map build = MapUtil.getInstance().addParms("payPassword", pasWord).addParms("userCode", SPUtil.getStringValue(CommonResource.USERCODE)).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postData(CommonResource.YANZHENG, build);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("支付密码----------" + result);
                ProcessDialogUtil.showProcessDialog(PaymentActivity.this);
                if (result.equals("支付密码正确")) {
                    Toast.makeText(PaymentActivity.this, result, Toast.LENGTH_SHORT).show();
                    presenter.payYueE(submitOrderBean);
                } else {
                    Toast.makeText(PaymentActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "-------------" + errorMsg);
                Toast.makeText(PaymentActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void openPayPasswordDialog() {
        PayPasswordView payPasswordView = new PayPasswordView(this);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(payPasswordView);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        presenter.goBack();
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ("0".equals(SPUtil.getStringValue("wxpay"))) {
            SPUtil.addParm("wxpay", "");
            presenter.paySuccess();
        } else if ("6".equals(SPUtil.getStringValue("wxpay"))) {
            SPUtil.addParm("wxpay", "");
            presenter.paySuccesss(payMoney);
        }
    }

    @Override
    public void callBack() {
//        paymentBtn.setEnabled(true);
    }

    private void changePayType() {
        paymentWeixinImg.setImageResource(isWeChat ? R.drawable.icon_xuanzhong : R.drawable.icon_weixuanzhong);
        paymentZfbImg.setImageResource(isWeChat ? R.drawable.icon_weixuanzhong : R.drawable.icon_xuanzhong);
        paymentYueEImg.setImageResource(R.drawable.icon_weixuanzhong);
    }

    @Override
    public PaymentView createView() {
        return this;
    }

    @Override
    public PaymentPresenter createPresenter() {
        return new PaymentPresenter(this);
    }
}
