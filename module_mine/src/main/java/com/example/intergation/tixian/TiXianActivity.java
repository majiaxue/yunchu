package com.example.intergation.tixian;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.CashInfoBean;
import com.example.common.CommonResource;
import com.example.entity.EventBean;
import com.example.entity.EventBusBean;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.ArithUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.PopUtils;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.example.utils.net_change_util.PasswordEditText;
import com.example.utils.net_change_util.PayPasswordView;
import com.example.utils.net_change_util.PwdEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@Route(path = "/mine/tixian")
public class TiXianActivity extends BaseActivity<TiXianView, TiXianPresenter> implements TiXianView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.include_right)
    ImageView includeRight;
    @BindView(R2.id.include_right_btn)
    TextView includeRightBtn;
    @BindView(R2.id.tixian_money)
    EditText tixianMoney;
    @BindView(R2.id.tixian_money_lin)
    LinearLayout tixianMoneyLin;
    @BindView(R2.id.tixian_money_dao_lin)
    RelativeLayout tixianMoneyDaoLin;
    @BindView(R2.id.tixian_money_name)
    EditText tixianMoneyName;
    @BindView(R2.id.tixian_money_name_lin)
    LinearLayout tixianMoneyNameLin;
    @BindView(R2.id.tixian_money_zh)
    EditText tixianMoneyZh;
    @BindView(R2.id.tixian_money_zh_lin)
    LinearLayout tixianMoneyZhLin;
    @BindView(R2.id.setting_logout)
    TextView settingLogout;
    @BindView(R2.id.tixian_service)
    TextView tixianService;

    @Autowired(name = "jifen")
    double jifen;
    private PasswordEditText.PasswordFullListener mPasswordFullListener;
    private String pasWord;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_tixian;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        tixianMoney.setHint("您当前可提现" + jifen + "积分");
        includeTitle.setText("提现");
        presenter.getCashInfo();
        if (jifen == 0.0) {
            tixianMoney.setEnabled(false);
            tixianMoneyName.setEnabled(false);
            tixianMoneyZh.setEnabled(false);
            settingLogout.setEnabled(false);
        } else {
            tixianMoney.setEnabled(true);
            tixianMoneyName.setEnabled(true);
            tixianMoneyZh.setEnabled(true);
            settingLogout.setEnabled(true);
        }
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        settingLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(tixianMoney.getText().toString())) {
                    Toast.makeText(TiXianActivity.this, "提现金额不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(tixianMoneyName.getText().toString())) {
                    Toast.makeText(TiXianActivity.this, "请输入您的真实姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(tixianMoneyZh.getText().toString())) {
                    Toast.makeText(TiXianActivity.this, "请输入您的提现账号", Toast.LENGTH_SHORT).show();
                } else if (Integer.valueOf(tixianMoney.getText().toString()) % 10 != 0) {
                    Toast.makeText(TiXianActivity.this, "提现金额必须是10的倍数", Toast.LENGTH_SHORT).show();
                } else {
                    if (Double.valueOf(tixianMoney.getText().toString()) > jifen) {
                        Toast.makeText(TiXianActivity.this, "您当前最多可用" + jifen + "积分", Toast.LENGTH_SHORT).show();
                    } else {
                        openPayPasswordDialog();
                    }

                }

            }
        });

    }

    private void openPayPasswordDialog() {
        PayPasswordView payPasswordView = new PayPasswordView(this);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(payPasswordView);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBean eventBusBean) {
        if ("password".equals(eventBusBean.getCode())) {
            pasWord = eventBusBean.getMsg();
            LogUtil.e("这是password----------" + pasWord);
            getMoney();
        }
    }

    public void getMoney() {
        Map build = MapUtil.getInstance().addParms("payPassword", pasWord).addParms("userCode", SPUtil.getStringValue(CommonResource.USERCODE)).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postData(CommonResource.YANZHENG, build);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("支付密码----------" + result);
                ProcessDialogUtil.showProcessDialog(TiXianActivity.this);
                if (result.equals("支付密码正确")) {
                    Toast.makeText(TiXianActivity.this, result, Toast.LENGTH_SHORT).show();
                    presenter.getAliPay(tixianMoney.getText().toString(), tixianMoneyName.getText().toString(), tixianMoneyZh.getText().toString());
                } else {
                    Toast.makeText(TiXianActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "-------------" + errorMsg);
                Toast.makeText(TiXianActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

//    private void popupwindow() {
//        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_mima, null);
////        ImageView duiHuanVipClose = inflate.findViewById(R.id.dui_huan_vip_close);//取消按钮
////        TextView tvQuXiao = inflate.findViewById(R.id.tv_cancel);   //取消按钮
////        TextView tvOk = inflate.findViewById(R.id.tv_ok);        //popupwindow的确认按钮
////        final PwdEditText popDuihuan = inflate.findViewById(R.id.duihuanjifenba);
//
//        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popupWindow.showAtLocation(new View(this), Gravity.CENTER, 0, 0);
//        PopUtils.setTransparency(this, 0.3f);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                PopUtils.setTransparency(TiXianActivity.this, 1f);
//            }
//        });
////        tvQuXiao.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                popupWindow.dismiss();
////            }
////        });
////        duiHuanVipClose.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                popupWindow.dismiss();
////            }
////        });
////        tvOk.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
//    }

    @Override
    public TiXianView createView() {
        return this;
    }

    @Override
    public TiXianPresenter createPresenter() {
        return new TiXianPresenter(this);
    }


    @Override
    public void loadCashInfo(CashInfoBean cashInfoBean) {
        tixianService.setText("注：1积分=1元,提现最低10的倍数,需扣除" + cashInfoBean.getServiceFee() + "%的手续费");
    }
}
