package com.example.user_manager_center;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.earnings_preview.EarningsPreviewActivity;
import com.example.entity.EventBusBean;
import com.example.manager_order_details.ManagerOrderDetailsActivity;
import com.example.mvp.BaseFragment;
import com.example.my_association.MyAssociationActivity;
import com.example.opened_manager.OpenedManagerActivity;
import com.example.user_invite_friends.UserInviteFriendsActivity;
import com.example.user_manager_center.adapter.EquityAdapter;
import com.example.user_manager_center.adapter.QueryVipGoodsAdapter;
import com.example.user_manager_center.qrcode.QRCodeActivity;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.example.utils.SPUtil;
import com.example.utils.StatusBarUtils;
import com.example.withdrawdeposit.WithdrawDepositActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import mtopsdk.common.util.StringUtils;

/**
 * 店长中心
 */
public class UserManagerCenterFragment extends BaseFragment<UserManagerCenterView, UserManagerCenterPresenter> implements UserManagerCenterView {

    @BindView(R2.id.not_opened_icon)
    SimpleDraweeView notOpenedIcon;
    @BindView(R2.id.not_opened_name)
    TextView notOpenedName;
    @BindView(R2.id.not_opened_code)
    TextView notOpenedCode;
    @BindView(R2.id.not_opened_copy)
    TextView notOpenedCopy;
    @BindView(R2.id.not_opened_rec)
    RecyclerView notOpenedRec;
    @BindView(R2.id.not_opened_opened_manager)
    TextView notOpenedOpenedManager;
    @BindView(R2.id.not_opened_bottom_rec)
    RecyclerView notOpenedBottomRec;
    @BindView(R2.id.have_opened_icon)
    SimpleDraweeView haveOpenedIcon;
    @BindView(R2.id.have_opened_name)
    TextView haveOpenedName;
    @BindView(R2.id.have_opened_code)
    TextView haveOpenedCode;
    @BindView(R2.id.have_opened_copy)
    TextView haveOpenedCopy;
    @BindView(R2.id.have_opened_qr_code)
    ImageView haveOpenedQrCode;
    @BindView(R2.id.have_opened_more)
    LinearLayout haveOpenedMore;
    @BindView(R2.id.have_opened_fukuan_order)
    TextView haveOpenedFukuanOrder;
    @BindView(R2.id.have_opened_yugushouyi)
    TextView haveOpenedYugushouyi;
    @BindView(R2.id.have_opened_jiesuanshouyi)
    TextView haveOpenedJiesuanshouyi;
    @BindView(R2.id.have_opened_invite_friends)
    LinearLayout haveOpenedInviteFriends;
    @BindView(R2.id.have_opened_association)
    LinearLayout haveOpenedAssociation;
    @BindView(R2.id.have_opened_order_details)
    LinearLayout haveOpenedOrderDetails;
    @BindView(R2.id.have_opened_tixian)
    ImageView haveOpenedTixian;
    @BindView(R2.id.manager_center_not_opened)
    LinearLayout managerCenterNotOpened;
    @BindView(R2.id.manager_center_have_opened)
    LinearLayout managerCenterHaveOpened;
    @BindView(R2.id.have_opened_leijishouyi)
    TextView haveOpenedLeijishouyi;
    @BindView(R2.id.have_opened_tixianshouyi)
    TextView haveOpenedTixianshouyi;
    @BindView(R2.id.have_opened_jijiangdaozhang)
    TextView haveOpenedJijiangdaozhang;
    @BindView(R2.id.have_opened_recommend_icon)
    SimpleDraweeView haveOpenedRecommendIcon;
    @BindView(R2.id.have_opened_recommend_relative)
    RelativeLayout haveOpenedRecommendRelative;
    @BindView(R2.id.have_opened_top_linear)
    LinearLayout haveOpenedTopLinear;

    private double leiji;
    private double blance;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_manager_center;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false);
        notOpenedRec.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        notOpenedBottomRec.setLayoutManager(gridLayoutManager1);
    }

    @Override
    public void initClick() {
        notOpenedCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", haveOpenedCode.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "复制成功", Toast.LENGTH_SHORT).show();
            }
        });

        notOpenedName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });

        notOpenedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    ARouter.getInstance().build("/mine/login").navigation();
                }
            }
        });

        //点击复制内容到粘贴板
        haveOpenedCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", haveOpenedCode.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "复制成功", Toast.LENGTH_SHORT).show();
            }
        });

        //面对面邀请
        haveOpenedQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QRCodeActivity.class));
            }
        });
        //收益预览
        haveOpenedMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EarningsPreviewActivity.class);
                intent.putExtra("leijishouyi", leiji);
                startActivity(intent);
            }
        });
        //订单明细
        haveOpenedOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ManagerOrderDetailsActivity.class));
            }
        });
        //我的社群
        haveOpenedAssociation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyAssociationActivity.class));
            }
        });
        //邀请好友
        haveOpenedInviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserInviteFriendsActivity.class));
            }
        });
        //提现
        haveOpenedTixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WithdrawDepositActivity.class);
                intent.putExtra("money",blance);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean eventBusBean) {
        if ("userLogin".equals(eventBusBean.getMsg())) {
            presenter.loadUserInfo();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LogUtil.e("level------------->" + SPUtil.getStringValue(CommonResource.LEVEL));
            StatusBarUtils.setAndroidNativeLightStatusBar(getActivity(), false);
            if (!TextUtils.isEmpty(SPUtil.getToken())) {
                presenter.loadUserInfo();
                if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.LEVEL)) && "0".equals(SPUtil.getStringValue(CommonResource.LEVEL))) {
                    managerCenterNotOpened.setVisibility(View.VISIBLE);
                    managerCenterHaveOpened.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
                        notOpenedIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
                    } else {
                        notOpenedIcon.setImageResource(R.drawable.touxiang);
                    }
                    notOpenedCode.setText("邀请码: " + SPUtil.getStringValue(CommonResource.USER_INVITE));
                    notOpenedName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
                    notOpenedCopy.setVisibility(View.VISIBLE);
                    notOpenedCode.setVisibility(View.VISIBLE);
                    presenter.initView();
                    presenter.queryVipGoods();
                } else {
                    managerCenterNotOpened.setVisibility(View.GONE);
                    managerCenterHaveOpened.setVisibility(View.VISIBLE);

                    if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
                        haveOpenedIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
                    } else {
                        haveOpenedIcon.setImageResource(R.drawable.touxiang);
                    }
                    haveOpenedCode.setText("邀请码: " + SPUtil.getStringValue(CommonResource.USER_INVITE));
                    haveOpenedName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
                    presenter.selectUserProfit();
                }
            } else {
                if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
                    notOpenedIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
                } else {
                    notOpenedIcon.setImageResource(R.drawable.touxiang);
                }
                managerCenterNotOpened.setVisibility(View.VISIBLE);
                managerCenterHaveOpened.setVisibility(View.GONE);
                notOpenedCode.setVisibility(View.GONE);
                notOpenedCopy.setVisibility(View.GONE);
                notOpenedName.setText("登陆/注册");
                presenter.initView();
                presenter.queryVipGoods();
            }

        }
    }

    @Override
    public UserManagerCenterView createView() {
        return this;
    }

    @Override
    public UserManagerCenterPresenter createPresenter() {
        return new UserManagerCenterPresenter(getContext());
    }

    @Override
    public void loadAdapter(EquityAdapter equityAdapter) {
        notOpenedRec.setAdapter(equityAdapter);
    }

    @Override
    public void loadAdapter(QueryVipGoodsAdapter queryVipGoodsAdapter) {
        notOpenedBottomRec.setAdapter(queryVipGoodsAdapter);
    }

    @Override
    public void loadUserInfoBean(UserInfoBean userInfoBean) {
        this.blance = userInfoBean.getBlance();
        if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.LEVEL)) && "0".equals(SPUtil.getStringValue(CommonResource.LEVEL))) {

        } else {

            haveOpenedLeijishouyi.setText(userInfoBean.getTotalBackMoney() + "");
            haveOpenedTixianshouyi.setText(userInfoBean.getBlance() + "");
            haveOpenedJijiangdaozhang.setText(userInfoBean.getEstimatedIncome() + "");
            if (!StringUtils.isNotBlank(userInfoBean.getFirstClassPic())) {
                haveOpenedRecommendRelative.setVisibility(View.GONE);
            } else {
                haveOpenedRecommendRelative.setVisibility(View.VISIBLE);
                haveOpenedRecommendIcon.setImageURI(Uri.parse(userInfoBean.getFirstClassPic()));
            }
        }
    }

    @Override
    public void loadUserProfit(UserInfoBean userInfoBean) {
        this.leiji = userInfoBean.getTotalBackMoney();
        haveOpenedFukuanOrder.setText(userInfoBean.getTodayPayNum());
        haveOpenedYugushouyi.setText(userInfoBean.getTodayPredictEarnings() + "");
        haveOpenedJiesuanshouyi.setText(userInfoBean.getTodayCloseEarnings() + "");
    }

    private SpannableString spanText(String price) {
        SpannableString sp = new SpannableString("￥" + price);
        sp.setSpan(new AbsoluteSizeSpan(12, true), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(18, true), 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(12, true), 3, price.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return sp;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("店长中心可见");
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.LEVEL)) && "0".equals(SPUtil.getStringValue(CommonResource.LEVEL))) {
                managerCenterNotOpened.setVisibility(View.VISIBLE);
                managerCenterHaveOpened.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
                    notOpenedIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
                } else {
                    notOpenedIcon.setImageResource(R.drawable.touxiang);
                }
                notOpenedCode.setText("邀请码: " + SPUtil.getStringValue(CommonResource.USER_INVITE));
                notOpenedName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
                notOpenedCopy.setVisibility(View.VISIBLE);
                notOpenedCode.setVisibility(View.VISIBLE);
                presenter.initView();
                presenter.queryVipGoods();
            } else {
                managerCenterNotOpened.setVisibility(View.GONE);
                managerCenterHaveOpened.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
                    haveOpenedIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
                } else {
                    haveOpenedIcon.setImageResource(R.drawable.touxiang);
                }
                haveOpenedCode.setText("邀请码: " + SPUtil.getStringValue(CommonResource.USER_INVITE));
                haveOpenedName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
                presenter.selectUserProfit();
            }
        } else {
            if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
                notOpenedIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
            } else {
                notOpenedIcon.setImageResource(R.drawable.touxiang);
            }
            managerCenterNotOpened.setVisibility(View.VISIBLE);
            managerCenterHaveOpened.setVisibility(View.GONE);
            notOpenedCode.setVisibility(View.GONE);
            notOpenedCopy.setVisibility(View.GONE);
            notOpenedName.setText("登陆/注册");
            presenter.initView();
            presenter.queryVipGoods();
        }
    }

}
