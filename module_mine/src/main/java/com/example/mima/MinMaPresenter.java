package com.example.mima;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.bean.MiMaBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MinMaPresenter extends BasePresenter<MinMaView> {
    public MinMaPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void gaiMiMa(String yunShi,String newMiMa,String newMiMa2){
        //oldPayPassword=123456
        //userCode=346231570551537664
        //newPayPassword=666666
        if (!yunShi.equals("123456")){
            Toast.makeText(mContext,"原始密码不正确",Toast.LENGTH_SHORT).show();
        }else if (!newMiMa.equals(newMiMa2)){
            Toast.makeText(mContext,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
        }else if (yunShi.equals("")){
            Toast.makeText(mContext,"原始密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (newMiMa.equals("")){
            Toast.makeText(mContext,"新密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (newMiMa2.equals("")){
            Toast.makeText(mContext,"请重复新密码",Toast.LENGTH_SHORT).show();
        }else if (newMiMa.length()!=6&&newMiMa2.length()!=6){
            Toast.makeText(mContext,"密码必须为6位",Toast.LENGTH_SHORT).show();
        }else {
            Map build = MapUtil.getInstance().addParms("oldPayPassword", yunShi).addParms("newPayPassword", newMiMa).addParms("userCode", SPUtil.getStringValue(CommonResource.USERCODE)).build();
            Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postData(CommonResource.XIUGAIMIMA, build);
            RetrofitUtil.getInstance().toSubscribe(observable,new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("这是修改支付密码"+result);
                    Toast.makeText(mContext,"密码修改成功",Toast.LENGTH_SHORT).show();
                    ((Activity)mContext).finish();                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e(errorCode+"------------------"+errorMsg);
                    Toast.makeText(mContext,errorMsg,Toast.LENGTH_SHORT).show();
                }
            }));
        }


    }
}
