package com.example.user_manager_center;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.EquityBean;
import com.example.bean.QueryVipGoodsBean;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_manager_center.adapter.EquityAdapter;
import com.example.user_manager_center.adapter.QueryVipGoodsAdapter;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class UserManagerCenterPresenter extends BasePresenter<UserManagerCenterView> {

    private List<EquityBean> equityBeanList = new ArrayList<>();
    private EquityAdapter equityAdapter;

    public UserManagerCenterPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {
        EventBus.getDefault().unregister(mContext);
    }

    public void initView() {

        if (equityAdapter == null) {
            equityBeanList.add(new EquityBean(R.drawable.icon_gouwuche, "购物省钱", "全场低至1折起"));
            equityBeanList.add(new EquityBean(R.drawable.icon_fenxiang, "分享赚钱", "最高35%推广奖励"));
            equityBeanList.add(new EquityBean(R.drawable.icon_chuangye, "创业机会", "一键开店无需囤货"));
            equityBeanList.add(new EquityBean(R.drawable.icon_chaozhi, "超值好礼", "送399大礼包"));
            equityBeanList.add(new EquityBean(R.drawable.icon_daili, "代理特权", "海量品牌代理权"));
            equityBeanList.add(new EquityBean(R.drawable.icon_kaquan, "分享赚钱", "400元专享券"));
            equityBeanList.add(new EquityBean(R.drawable.icon_guanjia, "创业机会", "贴心服务开店无忧"));
            equityBeanList.add(new EquityBean(R.drawable.icon_shouhou, "超值好礼", "让您售后无忧"));
            equityAdapter = new EquityAdapter(mContext, equityBeanList, R.layout.item_equity);
            if (getView() != null) {
                getView().loadAdapter(equityAdapter);
            }
        } else {
            equityAdapter.notifyDataSetChanged();
        }
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
                        SPUtil.addParm(CommonResource.LEVEL, userInfoBean.getLevel());
                        SPUtil.addParm("lljl", userInfoBean.getLljlNum());
                        SPUtil.addParm("spsc", userInfoBean.getSpscNum());
                        SPUtil.addParm("firstCode", userInfoBean.getFirstClassCode());
                        getView().loadUserInfoBean(userInfoBean);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("个人信息" + errorCode + "---------" + errorMsg);
            }
        }));
    }

    public void selectUserProfit() {
        Map userCode = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postData(CommonResource.SELECTUSERPROFIT, userCode);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                UserInfoBean userInfoBean = new Gson().fromJson(result, new TypeToken<UserInfoBean>() {
                }.getType());
                LogUtil.e("收益预览:" + userInfoBean);
                if (userInfoBean != null) {
                    if (getView() != null) {
                        getView().loadUserProfit(userInfoBean);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    public void queryVipGoods() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getDataWithout(CommonResource.QUERYVIPGOODS);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("queryVipGoods" + result);
//                QueryVipGoodsBean queryVipGoodsBean = JSON.parseObject(result, new TypeReference<QueryVipGoodsBean>() {
//                }.getType());
                final List<QueryVipGoodsBean> queryVipGoodsBeans = JSON.parseArray(result, QueryVipGoodsBean.class);
                if (queryVipGoodsBeans != null) {
                    QueryVipGoodsAdapter queryVipGoodsAdapter = new QueryVipGoodsAdapter(mContext, queryVipGoodsBeans, R.layout.item_bottom_rec);
                    if (getView() != null) {
                        getView().loadAdapter(queryVipGoodsAdapter);
                    }
                    queryVipGoodsAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            if (!TextUtils.isEmpty(SPUtil.getToken())) {
                                ARouter.getInstance()
                                        .build("/module_user_store/GoodsDetailActivity")
                                        .withString("id", queryVipGoodsBeans.get(position).getId() + "")
                                        .withString("sellerId", queryVipGoodsBeans.get(position).getSellerId() + "")
                                        .withString("commendId", queryVipGoodsBeans.get(position).getProductCategoryId() + "")
                                        .withInt("type", 1)
                                        .navigation();
                            } else {
                                ARouter.getInstance().build("/mine/login").navigation();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }
}
