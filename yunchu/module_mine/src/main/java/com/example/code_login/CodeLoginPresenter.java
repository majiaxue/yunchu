package com.example.code_login;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.JpushUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.PhoneNumUtil;
import com.example.utils.SPUtil;
import com.example.view.SelfDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import io.reactivex.Observable;

public class CodeLoginPresenter extends BasePresenter<CodeLoginView> {
    private boolean isAgree = true;

    public CodeLoginPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void getCode(String string) {
        if (PhoneNumUtil.isMobileNO(string)) {
            Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.LOGIN_PHONE + "/" + string);
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("获取验证码" + result);
                    getView().getCodeSuccess();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("获取验证码失败" + errorMsg);
                    final SelfDialog selfDialog = new SelfDialog(mContext);
                    selfDialog.setTitle("提示");
                    selfDialog.setMessage("该手机号没有注册请先注册");
                    selfDialog.setYesOnclickListener("注册", new SelfDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            ARouter.getInstance().build("/module_mine/RegisterActivity").navigation();
                        }
                    });
                    selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            selfDialog.dismiss();
                        }
                    });
                    selfDialog.show();
                }
            }));
        } else {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    public void submit(String phone, String code) {
        if (!PhoneNumUtil.isMobileNO(phone)) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        } else if ("".equals(code) || code == null) {
            Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
        } else if (!isAgree) {
            Toast.makeText(mContext, "请阅读用户协议", Toast.LENGTH_SHORT).show();
        } else {
            Map map = MapUtil.getInstance().addParms("phone", phone).addParms("checkCode", code).build();
            Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getData(CommonResource.LOGIN_CODE, map);
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("验证码登录：" + result);
                    UserInfoBean userInfoBean = new Gson().fromJson(result, new TypeToken<UserInfoBean>() {
                    }.getType());
                    SPUtil.addParm(CommonResource.TOKEN, "JWT " + userInfoBean.getToken());
                    SPUtil.addParm(CommonResource.USERCODE, userInfoBean.getUserCode());
                    SPUtil.addParm(CommonResource.USER_NAME, userInfoBean.getNickname());
                    SPUtil.addParm(CommonResource.USER_PIC, userInfoBean.getIcon());
                    SPUtil.addParm(CommonResource.USER_INVITE, userInfoBean.getInviteCode());
                    SPUtil.addParm(CommonResource.LEVEL, userInfoBean.getLevel());
                    SPUtil.addParm("lljl",userInfoBean.getLljlNum());
                    SPUtil.addParm("spsc",userInfoBean.getSpscNum());
                    JpushUtil.setAlias(userInfoBean.getUserCode());
//                    ARouter.getInstance().build("/home/main").withString("type", "login").navigation();
                    ARouter.getInstance().build("/module_user_store/UserActivity").navigation();
                    ((Activity) mContext).finish();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    Toast.makeText(mContext, "" + errorMsg, Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }

    public void checkAgreement() {
        if (isAgree) {
            isAgree = false;
            getView().disagreeAgreement();
        } else {
            isAgree = true;
            getView().agreeAgreement();
        }
    }
}
