package com.example.invite_friends;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bean.InviteBean;
import com.example.module_base.ModuleBaseApplication;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.UIHelper;
import com.kongzue.dialog.v3.WaitDialog;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import butterknife.BindView;

@Route(path = "/mine/invite_friends")
public class InviteFriendsActivity extends BaseActivity<InviteFriendsView, InviteFriendsPresenter> implements InviteFriendsView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.invite_friends_banner)
    XBanner inviteFriendsBanner;
    @BindView(R2.id.invite_friends_link)
    TextView inviteFriendsLink;
    @BindView(R2.id.invite_friends_bill)
    TextView inviteFriendsBill;
    @BindView(R2.id.invite_friends_erweima1)
    ImageView inviteFriendsErweima1;
    @BindView(R2.id.invite_friends_rela1)
    RelativeLayout inviteFriendsRela1;
    @BindView(R2.id.invite_friends_erweima2)
    ImageView inviteFriendsErweima2;
    @BindView(R2.id.invite_friends_rela2)
    RelativeLayout inviteFriendsRela2;
    @BindView(R2.id.invite_friends_erweima3)
    ImageView inviteFriendsErweima3;
    @BindView(R2.id.invite_friends_rela3)
    RelativeLayout inviteFriendsRela3;
    @BindView(R2.id.invite_friends_erweima4)
    ImageView inviteFriendsErweima4;
    @BindView(R2.id.invite_friends_rela4)
    RelativeLayout inviteFriendsRela4;
    @BindView(R2.id.include_right)
    ImageView includeRight;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_friends;
    }

    @Override
    public void initData() {
        includeTitle.setText("邀请好友");
        includeRight.setImageResource(R.drawable.icon_guize);
        includeRight.setVisibility(View.VISIBLE);
        ProcessDialogUtil.showProcessDialog(this);
//        WaitDialog.show(this,null);
        ModuleBaseApplication.initShare();
        presenter.initPic(inviteFriendsErweima1, inviteFriendsErweima2, inviteFriendsErweima3, inviteFriendsErweima4);

    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inviteFriendsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.shareLink();
            }
        });

        inviteFriendsBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.share(inviteFriendsBanner.getBannerCurrentItem());
            }
        });

        inviteFriendsBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                UIHelper.seeBigBitmap(InviteFriendsActivity.this, ((InviteBean) model).getXBannerUrl());
            }
        });

        includeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showRules(InviteFriendsActivity.this);
            }
        });
    }

    @Override
    public void loadBanner(List<InviteBean> beanList) {
        inviteFriendsBanner.setBannerData(beanList);

        inviteFriendsBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                RequestOptions requestOptions = RequestOptions.centerCropTransform();
                Glide.with(InviteFriendsActivity.this).load(((InviteBean) model).getXBannerUrl()).apply(requestOptions).transform(new RoundedCorners((int) getResources().getDimension(R.dimen.dp_10))).into((ImageView) view);
            }
        });
    }

    @Override
    public void loadQRCode() {
        presenter.createList(inviteFriendsRela1, inviteFriendsRela2, inviteFriendsRela3, inviteFriendsRela4);
    }

    @Override
    public InviteFriendsView createView() {
        return this;
    }

    @Override
    public InviteFriendsPresenter createPresenter() {
        return new InviteFriendsPresenter(this);
    }
}
