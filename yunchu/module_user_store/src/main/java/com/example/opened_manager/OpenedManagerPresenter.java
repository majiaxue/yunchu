package com.example.opened_manager;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.bean.EquityBean;
import com.example.bean.OrderConfirmBean;
import com.example.bean.PostageBean;
import com.example.bean.ShippingAddressBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_manager_center.adapter.EquityAdapter;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class OpenedManagerPresenter extends BasePresenter<OpenedManagerView> {

    private List<EquityBean> equityBeanList = new ArrayList<>();

    public OpenedManagerPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initView() {
        equityBeanList.add(new EquityBean(R.drawable.icon_gouwuche, "购物省钱", ""));
        equityBeanList.add(new EquityBean(R.drawable.icon_fenxiang, "分享赚钱", ""));
        equityBeanList.add(new EquityBean(R.drawable.icon_chuangye, "创业机会", ""));
        equityBeanList.add(new EquityBean(R.drawable.icon_chaozhi, "超值好礼", ""));
        equityBeanList.add(new EquityBean(R.drawable.icon_daili, "代理特权", ""));
        equityBeanList.add(new EquityBean(R.drawable.icon_kaquan, "专享卡券", ""));
        equityBeanList.add(new EquityBean(R.drawable.icon_guanjia, "管家服务", ""));
        equityBeanList.add(new EquityBean(R.drawable.icon_shouhou, "专属售后", ""));

        EquityAdapter equityAdapter = new EquityAdapter(mContext, equityBeanList, R.layout.item_equity);
        if (getView() != null) {
            getView().loadAdapter(equityAdapter);
        }

    }

    public void getAddress() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.MOREN_ADDRESS, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("默认地址：" + result);
                ShippingAddressBean addressBean = JSON.parseObject(result, ShippingAddressBean.class);
                if (getView() != null) {
                    getView().loadAddress(addressBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("dizhi:" + errorCode + "-------" + errorMsg);
                if (getView() != null) {
                    getView().noAddress();
                }
            }
        }));

    }

    public void getPostage(OrderConfirmBean order, ShippingAddressBean addressBean) {
        Map map = MapUtil.getInstance().addParms("feightTemplateId", order.getFeightTemplateId())
                .addParms("provinceName", addressBean.getAddressProvince())
                .addParms("quantity", order.getQuantity())
                .addParms("skuId", order.getProductSkuId())
                .addParms("productId", order.getProductId())
                .build();
        List<Map> list = new ArrayList<>();
        list.add(map);
        String jsonString = JSON.toJSONString(list);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).postHeadWithBody(CommonResource.GET_YUNGEI, requestBody, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("运费：" + result);
                List<PostageBean> postageBean = JSON.parseArray(result, PostageBean.class);
                getView().loadPostage(postageBean.get(0));
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("运费失败" + errorMsg);
                Toast.makeText(mContext, "没有获取到运费，请返回重试", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
