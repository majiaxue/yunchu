package com.example.user_mine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.MineOrderCountBean;
import com.example.bean.SubmitOrderBean;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.bean.ApplicationBean;
import com.example.bean.BrowsingBean;
import com.example.bean.GoodsCollectCountBean;
import com.example.bean.ShopCollectCountBean;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.PopUtils;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:
 */
public class MinePresenter extends BasePresenter<MineView> {

    private int count1 = 0;
    private int count2 = 0;
    private int count3 = 0;
    private int count6 = 0;

    public MinePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }


    public void loadUserInfo() {
        LogUtil.e("token--->" + SPUtil.getToken());
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.GETUSERINFO, SPUtil.getToken());//"http://192.168.1.9:4001"
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("个人信息:" + result);
                UserInfoBean userInfoBean = new Gson().fromJson(result, new TypeToken<UserInfoBean>() {
                }.getType());
                LogUtil.e("userInfoBean:" + userInfoBean);
                if (userInfoBean != null) {
                    if (getView() != null) {
                        SPUtil.addParm(CommonResource.LEVEL,userInfoBean.getLevel());
                        SPUtil.addParm(CommonResource.LEVELID,userInfoBean.getLevelId());
                        SPUtil.addParm(CommonResource.BLANCE,userInfoBean.getBlance()+"");
                        SPUtil.addParm("lljl",userInfoBean.getLljlNum());
                        SPUtil.addParm("spsc",userInfoBean.getSpscNum());
                        getView().loadUserinfo(userInfoBean);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("个人信息" + errorCode + "---------" + errorMsg);
            }
        }));
    }

    public void goodsCollectionCount() {
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            Map userCode = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).build();
            Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getData(CommonResource.QUERYUSERFAVORITE, userCode);
            RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("商品收藏数量----->" + result);

                    List<GoodsCollectCountBean> goodsCollectCountBeans = JSON.parseArray(result, GoodsCollectCountBean.class);
//                    LogUtil.e("goodsCollectionRecBean----->" + goodsCollectCountBeans);
                    if (goodsCollectCountBeans.size() != 0) {

                        if (goodsCollectCountBeans.size() != 0 || goodsCollectCountBeans != null) {
                            if (getView() != null) {
                                getView().goodsCollectionCount(goodsCollectCountBeans.size());
                            }
                        } else {
                            if (getView() != null) {
                                getView().goodsCollectionCount(0);
                            }
                        }

                    } else {
                        if (getView() != null) {
                            getView().goodsCollectionCount(0);
                        }
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("商品收藏数量------->" + errorCode);
                }
            }));
        } else {
            if (getView() != null) {
                getView().goodsCollectionCount(0);
            }
        }

    }

    public void browsingHistoryCount() {
        Map build = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).addParms("page", 1).build();
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getData(CommonResource.QUERYUSERZUJI,build);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("浏览历史数量成功------------->");
                List<BrowsingBean> browsingBeans = JSON.parseArray(result, BrowsingBean.class);
                if (browsingBeans.size() != 0) {
                    if (getView() != null) {
                        getView().browsingHistoryCount(browsingBeans.size());
                    }
                } else {
                    getView().browsingHistoryCount(0);
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("浏览历史数量成功------------->" + errorMsg);
            }
        }));

    }

    //查询商家申请
    public void businessApplication() {
        Map build = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).build();
        Observable<ResponseBody> data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9003).getData(CommonResource.SELLERSTATE, build);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("mineFragmentResult--------------->" + result);
                ApplicationBean applicationBean = JSON.parseObject(result, new TypeReference<ApplicationBean>() {
                }.getType());
                if (applicationBean != null) {
                    String data1 = applicationBean.getData();
                    LogUtil.e("mineFragment" + data1);
                    if (data1.equals("2") || data1.equals("3")) {
                        ARouter.getInstance().build("/module_user_mine/BusinessApplicationActivity").navigation();
                    } else {
                        Toast.makeText(mContext, "您已经是商家了无需申请!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("mineFragmentErrorMsg--------------->" + errorMsg);
            }
        }));
    }

    //我的订单
    public void mineOrderAll() {
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.ORDERALL, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("OrderAllPresenterResult-------->" + result);

                    MineOrderCountBean mineOrderBean = new Gson().fromJson(result, MineOrderCountBean.class);
                    if (mineOrderBean != null && mineOrderBean.getOrderList().size() != 0) {
                        for (int i = 0; i < mineOrderBean.getOrderList().size(); i++) {
                            if (mineOrderBean.getOrderList().get(i).getStatus() == 2) {
                                //2待收货
                                count2++;
                            }
                            if (mineOrderBean.getOrderList().get(i).getStatus() == 6) {
                                //6待付款
                                count6++;
                            }
                            if (mineOrderBean.getOrderList().get(i).getStatus() == 3) {
                                //3待评论
                                count3++;
                            }
                            if (mineOrderBean.getOrderList().get(i).getStatus() == 1) {
                                //1待发货
                                count1++;
                            }
                        }
                        if (getView() != null) {
                            getView().daishouhuo(count2);
                            count2 = 0;
                            getView().daifukuan(count6);
                            count6 = 0;
                            getView().daipingjia(count3);
                            count3 = 0;
                            getView().daifahuo(count1);
                            count1 = 0;
                        }
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("OrderAllPresenterError-------->" + errorMsg);
                }
            }));
        } else {
            if (getView() != null) {
                getView().daishouhuo(count2);
                getView().daifukuan(count6);
                getView().daipingjia(count3);
                getView().daifahuo(count1);
            }
        }
    }

    public void getStar(){

    }

    public void popupwindow() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.GETZAN);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("点赞个数---------"+result);
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.pop_zan, null);
                TextView tvOk = inflate.findViewById(R.id.tv_ok);   //确认按钮
                TextView tv = inflate.findViewById(R.id.tv);   //确认按钮
                LinearLayout ll = inflate.findViewById(R.id.ll);   //确认按钮
                tv.setText("每完成一笔订单，集一颗星，每月达到"+result+"颗星，可免费到店兑换礼品。");
                final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setOutsideTouchable(false);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(new View(mContext), Gravity.CENTER, 0, 0);
                PopUtils.setTransparency(mContext, 0.3f);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        PopUtils.setTransparency(mContext, 1f);
                    }
                });
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));

    }

    public void updateVIP() {
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.VIPSTATE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("检查是否是vip------------"+result);
                ARouter.getInstance().build("/user_store/UpdateVipActivity").navigation();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                LogUtil.e(errorCode+"--------"+errorMsg);
            }
        }));
    }
}
