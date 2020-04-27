package com.example.user_mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.entity.EventBusBean2;
import com.example.mvp.BaseFragment;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.example.utils.SPUtil;
import com.example.utils.StatusBarUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:商城我的页面
 */
public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {


    @BindView(R2.id.mine_header)
    SimpleDraweeView mineHeader;
    @BindView(R2.id.user_mine_name)
    TextView userMineName;
    @BindView(R2.id.user_mine_id)
    TextView userMineId;
    @BindView(R2.id.user_mine_setting)
    ImageView userMineSetting;
    @BindView(R2.id.goods_collection_count)
    TextView goodsCollectionCount;
    @BindView(R2.id.user_mine_goods_collection)
    LinearLayout userMineGoodsCollection;
    @BindView(R2.id.user_mine_my_order)
    LinearLayout userMineMyOrder;
    @BindView(R2.id.user_mine_daifukuan)
    LinearLayout userMineDaifukuan;
    @BindView(R2.id.user_mine_daifahuo)
    LinearLayout userMineDaifahuo;
    @BindView(R2.id.user_mine_daishouhuo)
    LinearLayout userMineDaishouhuo;
    @BindView(R2.id.user_mine_daipingjia)
    LinearLayout userMineDaipingjia;
    @BindView(R2.id.user_mine_shouhou)
    LinearLayout userMineShouhou;
    @BindView(R2.id.user_mine_shipping_address)
    LinearLayout userMineShippingAddress;
    @BindView(R2.id.user_mine_discount_coupon)
    LinearLayout userMineDiscountCoupon;
//    @BindView(R2.id.user_mine_opened_manager)
//    LinearLayout userMineOpenedManager;
    @BindView(R2.id.user_mine_browsing_history)
    LinearLayout userMineBrowsingHistory;
    @BindView(R2.id.browsing_history_count)
    TextView browsingHistoryCount;
    @BindView(R2.id.user_mine_my_service)
    LinearLayout userMineMyService;
    @BindView(R2.id.user_mine_my_intergation)
    LinearLayout userMineMyIntervigation;
    @BindView(R2.id.user_mine_my_intergation_count)
    TextView userMineMyIntervigationCount;
    @BindView(R2.id.user_mine_shipping_plan)
    LinearLayout userMineMyPlan;
    @BindView(R2.id.mine_level)
    TextView mineLevel;
    @BindView(R2.id.lijikaitong)
    TextView lijikaitong;
    //user_mine_my_team
    @BindView(R2.id.user_mine_my_team)
    LinearLayout myTeam;

    @BindView(R2.id.tv_yue)
    TextView tvYue;
    @BindView(R2.id.or_vip)
    TextView orVip;
    private boolean flag = false;
    private Badge badge1;
    private Badge badge2;
    private Badge badge3;
    private Badge badge4;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_mine;
    }

    @Override
    public void initData() {
        presenter.getStar();
        badge1 = new QBadgeView(getContext())
                .bindTarget(userMineDaifahuo)
                .setBadgeTextColor(Color.parseColor("#FFFFFF"))
                .stroke(Color.parseColor("#fd3c15"), 1, true)
                .setBadgeTextSize(9, true)
                .setShowShadow(false)
                .setGravityOffset(10, 0, true);

        badge2 = new QBadgeView(getContext())
                .bindTarget(userMineDaishouhuo)
                .setBadgeTextColor(Color.parseColor("#FFFFFF"))
                .stroke(Color.parseColor("#fd3c15"), 1, true)
                .setBadgeTextSize(9, true)
                .setShowShadow(false)
                .setGravityOffset(10, 0, true);

        badge3 = new QBadgeView(getContext())
                .bindTarget(userMineDaipingjia)
                .setBadgeTextColor(Color.parseColor("#FFFFFF"))
                .stroke(Color.parseColor("#fd3c15"), 1, true)
                .setBadgeTextSize(9, true)
                .setShowShadow(false)
                .setGravityOffset(10, 0, true);

        badge4 = new QBadgeView(getContext())
                .bindTarget(userMineDaifukuan)
                .setBadgeTextColor(Color.parseColor("#FFFFFF"))
                .stroke(Color.parseColor("#fd3c15"), 1, true)
                .setBadgeTextSize(9, true)
                .setShowShadow(false)
                .setGravityOffset(10, 0, true);

    }

    @Override
    public void initClick() {

        userMineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });

        mineHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });

        userMineSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                } else {
                    ARouter.getInstance().build("/mine/setting").navigation();
                }
            }
        });
        userMineMyIntervigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                presenter.popupwindow();
            }
        });

        //商品收藏
        userMineGoodsCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/GoodsCollectionActivity").navigation();
                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });

        //浏览记录
        userMineBrowsingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                } else {
                    ARouter.getInstance().build("/module_user_mine/BrowsingHistoryActivity").navigation();

                }
            }
        });

        //收货地址
        userMineShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/ShippingAddressActivity").navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
        //优惠劵
        userMineDiscountCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/CouponActivity").navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
        //全部订单
        userMineMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/MineOrderActivity").withInt("type", 0).navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
        //待付款
        userMineDaifukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/MineOrderActivity").withInt("type", 1).navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
        //待发货
        userMineDaifahuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/MineOrderActivity").withInt("type", 2).navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
        //待收货hoho
        userMineDaishouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/MineOrderActivity").withInt("type", 3).navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
        //待评价
        userMineDaipingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/MineOrderActivity").withInt("type", 4).navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
        //退货/售后
        userMineShouhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/module_user_mine/AlterationActivity").navigation();

                } else {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });
//        lijikaitong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                presenter.popupwindow();
//            }
//        });
        //点击开通
        lijikaitong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                } else {
                    ARouter.getInstance().build("/mine/intergation").navigation();
                }

            }
        });
        //系统公告
        userMineMyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                } else {
                    ARouter.getInstance().build("/mine/messagecenter").navigation();
                }

            }
        });
        //升级vip
        userMineMyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                } else {
                    presenter.updateVIP();

                }
            }
        });
        //我的客服
        myTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/mine/contactus").navigation();
            }
        });

    }


    @Override
    public MineView createView() {
        return this;
    }

    @Override
    public MinePresenter createPresenter() {
        return new MinePresenter(getContext());
    }


    @Override
    public void browsingHistoryCount(int count) {
        if (count > 99) {
            browsingHistoryCount.setText(count + "+");
        } else {
            browsingHistoryCount.setText(count + "");
        }
    }

    @Override
    public void goodsCollectionCount(int count) {
        goodsCollectionCount.setText(count + "");
    }

    @Override
    public void daifahuo(int count) {
        badge1.setBadgeNumber(count);
    }

    @Override
    public void daishouhuo(int count) {
        badge2.setBadgeNumber(count);
    }

    @Override
    public void daipingjia(int count) {
        badge3.setBadgeNumber(count);
    }

    @Override
    public void daifukuan(int count) {
        badge4.setBadgeNumber(count);
    }

    @Override
    public void loadUserinfo(UserInfoBean userInfoBean) {
        //赞的个数
        userMineMyIntervigationCount.setText(userInfoBean.getStar() + "");
        tvYue.setText("余额   "+"￥"+userInfoBean.getBlance()+"");

        LogUtil.e("++++++++"+userInfoBean.getLevel());
        if ("1".equals(userInfoBean.getLevelId())) {
            orVip.setText("普通会员");
        } else if ("2".equals(userInfoBean.getLevelId())) {
            orVip.setText("VIP会员");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            presenter.goodsCollectionCount();
//            presenter.browsingHistoryCount();
            StatusBarUtils.setAndroidNativeLightStatusBar(getActivity(), true);
//            presenter.loadUserInfo();

            if (!TextUtils.isEmpty(SPUtil.getToken())) {
                userMineName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
                userMineId.setText("UID：" + SPUtil.getStringValue(CommonResource.USER_INVITE));
                mineHeader.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
                browsingHistoryCount.setText(SPUtil.getIntValue("lljl") + "");
                goodsCollectionCount.setText(SPUtil.getIntValue("spsc") + "");
                //orVip.setVisibility(View.GONE);
                //userMineOpenedManager.setVisibility(View.VISIBLE);
            } else {
                userMineName.setText("请注册/登陆");
                userMineId.setText("点击登录，享受更多优惠");
                mineHeader.setImageResource(R.drawable.touxiang);
                browsingHistoryCount.setText(0 + "");
                goodsCollectionCount.setText(0 + "");
                userMineMyIntervigationCount.setText(0 + "");
                //orVip.setVisibility(View.GONE);
               // userMineOpenedManager.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.mineOrderAll();
        presenter.goodsCollectionCount();
        presenter.browsingHistoryCount();
        presenter.loadUserInfo();

        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            userMineName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
            userMineId.setText("UID：" + SPUtil.getStringValue(CommonResource.USER_INVITE));
            mineHeader.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
            browsingHistoryCount.setText(SPUtil.getIntValue("lljl") + "");
            goodsCollectionCount.setText(SPUtil.getIntValue("spsc") + "");
            //orVip.setVisibility(View.GONE);
            //userMineOpenedManager.setVisibility(View.VISIBLE);
        } else {
            userMineName.setText("请注册/登陆");
            userMineId.setText("点击登录，享受更多优惠");
            mineHeader.setImageResource(R.drawable.touxiang);
            browsingHistoryCount.setText(0 + "");
            goodsCollectionCount.setText(0 + "");
            userMineMyIntervigationCount.setText(0 + "");
            //orVip.setVisibility(View.GONE);
            //userMineOpenedManager.setVisibility(View.GONE);

        }
    }

}
