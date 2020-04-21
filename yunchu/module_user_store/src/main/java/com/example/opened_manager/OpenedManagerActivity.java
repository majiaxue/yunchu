package com.example.opened_manager;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.OrderConfirmBean;
import com.example.bean.PostageBean;
import com.example.bean.ShippingAddressBean;
import com.example.bean.SubmitOrderBean;
import com.example.common.CommonResource;
import com.example.mvp.BaseActivity;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_manager_center.adapter.EquityAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.example.utils.SPUtil;
import com.example.utils.StatusBarUtils;

import butterknife.BindView;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 开通店长
 */
@Route(path = "/module_user_store/OpenedManagerActivity")
public class OpenedManagerActivity extends BaseActivity<OpenedManagerView, OpenedManagerPresenter> implements OpenedManagerView {


    @BindView(R2.id.opened_manager_back)
    ImageView openedManagerBack;
    @BindView(R2.id.opened_manager_price)
    TextView openedManagerPrice;
    @BindView(R2.id.opened_manager_rec)
    RecyclerView openedManagerRec;
    @BindView(R2.id.opened_manager_receiver)
    TextView openedManagerReceiver;
    @BindView(R2.id.opened_manager_receiver_address)
    TextView openedManagerReceiverAddress;
    @BindView(R2.id.opened_manager_address)
    RelativeLayout openedManagerAddress;
    @BindView(R2.id.opened_manager_edit)
    EditText openedManagerEdit;
    @BindView(R2.id.opened_manager_payment)
    TextView openedManagerPayment;

    @Autowired(name = "order")
    OrderConfirmBean orderConfirmBean;

    private PostageBean postageBean1;

    private ShippingAddressBean addressBean1;
    private int status = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_opened_manager;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
        if (!TextUtils.isEmpty(SPUtil.getStringValue("firstCode"))){
            openedManagerEdit.setEnabled(false);
            openedManagerEdit.setText(SPUtil.getStringValue("firstCode"));
        }else{
            openedManagerEdit.setEnabled(true);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        openedManagerRec.setLayoutManager(gridLayoutManager);
        presenter.initView();
        presenter.getAddress();
    }

    @Override
    public void initClick() {
        openedManagerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //选择地址
        openedManagerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/module_user_mine/ShippingAddressActivity").navigation(OpenedManagerActivity.this, 111);
            }
        });

        //立即支付
        openedManagerPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(openedManagerReceiverAddress.getText().toString())) {
                    Toast.makeText(OpenedManagerActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(openedManagerEdit.getText().toString())) {
                    Toast.makeText(OpenedManagerActivity.this, "请填写邀请码", Toast.LENGTH_SHORT).show();
                } else {
                    orderConfirmBean.setOrderAddress(addressBean1.getAddressDetail());
                    orderConfirmBean.setReceiverCity(addressBean1.getAddressCity());
                    orderConfirmBean.setReceiverName(addressBean1.getAddressName());
                    orderConfirmBean.setReceiverPhone(addressBean1.getAddressPhone());
                    orderConfirmBean.setReceiverProvince(addressBean1.getAddressProvince());
                    orderConfirmBean.setReceiverRegion(addressBean1.getAddressArea());
                    orderConfirmBean.setFreightAmount(postageBean1.getFeight());
                    String jsonString = JSON.toJSONString(orderConfirmBean);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
                    Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postDataWithBody(CommonResource.PAY99MANAGER + "/" + SPUtil.getUserCode() + "/" + openedManagerEdit.getText().toString(), requestBody);
                    RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                        @Override
                        public void onSuccess(String result, String msg) {
                            LogUtil.e("支付会员" + result);
                            JSONObject jsonObject = JSON.parseObject(result);
                            String masterNo = jsonObject.getString("masterNo");
                            Double totalAmount = jsonObject.getDouble("totalAmount");
                            SubmitOrderBean submitOrderBean = new SubmitOrderBean();
                            submitOrderBean.setMasterNo(masterNo);
                            submitOrderBean.setTotalAmount(totalAmount);


                            ARouter.getInstance().build("/module_user_store/PaymentActivity")
                                    .withSerializable("submitOrderBean", submitOrderBean)
                                    .withInt("type", 1)
                                    .navigation();
                            finish();
                        }

                        @Override
                        public void onError(String errorCode, String errorMsg) {
                            if ("1".equals(errorCode)) {
                                Toast.makeText(OpenedManagerActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }));
                }
            }
        });
    }

    @Override
    public OpenedManagerView createView() {
        return this;
    }

    @Override
    public OpenedManagerPresenter createPresenter() {
        return new OpenedManagerPresenter(this);
    }

    @Override
    public void loadAdapter(EquityAdapter equityAdapter) {
        openedManagerRec.setAdapter(equityAdapter);
    }

    @Override
    public void loadAddress(ShippingAddressBean addressBean) {
        this.addressBean1 = addressBean;
        status++;
        presenter.getPostage(orderConfirmBean, addressBean);
        openedManagerReceiver.setText("收货人: " + addressBean.getAddressName() + "   " + addressBean.getAddressPhone());
        openedManagerReceiverAddress.setText("收货地址: " + addressBean.getAddressProvince() + addressBean.getAddressCity() + addressBean.getAddressArea() + addressBean.getAddressDetail());
    }

    @Override
    public void loadPostage(PostageBean postageBean) {
        this.postageBean1 = postageBean;
        openedManagerPrice.setText("￥" + postageBean.getTotal());
    }

    @Override
    public void noAddress() {
        openedManagerReceiver.setText("暂无地址快去添加地址吧");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            addressBean1 = (ShippingAddressBean) data.getSerializableExtra("address");
            openedManagerReceiver.setText("收货人: " + addressBean1.getAddressName() + "   " + addressBean1.getAddressPhone());
            openedManagerReceiverAddress.setText("收货地址: " + addressBean1.getAddressProvince() + addressBean1.getAddressCity() + addressBean1.getAddressArea() + addressBean1.getAddressDetail());
            presenter.getPostage(orderConfirmBean, addressBean1);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        presenter.getAddress();
        if (status != 0) {
            presenter.getPostage(orderConfirmBean, addressBean1);
        }

    }
}
