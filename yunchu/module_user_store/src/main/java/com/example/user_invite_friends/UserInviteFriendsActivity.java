package com.example.user_invite_friends;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.CommonResource;
import com.example.mvp.BaseActivity;
import com.example.user_manager_center.adapter.EquityAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.SPUtil;
import com.example.utils.StatusBarUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店长邀请好友
 */
public class UserInviteFriendsActivity extends BaseActivity<UserInviteFriendsView, UserInviteFriendsPresenter> implements UserInviteFriendsView {

    @BindView(R2.id.user_invite_friends_back)
    ImageView userInviteFriendsBack;
    @BindView(R2.id.user_invite_friends_icon)
    SimpleDraweeView userInviteFriendsIcon;
    @BindView(R2.id.user_invite_friends_name)
    TextView userInviteFriendsName;
    @BindView(R2.id.user_invite_friends_code)
    TextView userInviteFriendsCode;
    @BindView(R2.id.user_invite_friends_copy)
    TextView userInviteFriendsCopy;
    @BindView(R2.id.user_invite_friends_img)
    ImageView userInviteFriendsImg;
    @BindView(R2.id.user_invite_friends_txt)
    TextView userInviteFriendsTxt;
    @BindView(R2.id.user_invite_friends_rec)
    RecyclerView userInviteFriendsRec;
    @BindView(R2.id.user_invite_friends_haibao)
    TextView userInviteFriendsHaibao;
    @BindView(R2.id.user_invite_friends_lianjie)
    TextView userInviteFriendsLianjie;
    @BindView(R2.id.user_invite_friends_nested)
    NestedScrollView userInviteFriendsNested;
    @BindView(R2.id.user_invite_friends_qr_code)
    ImageView userInviteFriendsQrCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_invite_friends;
    }

    @Override
    public void initData() {
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
        if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
            userInviteFriendsIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
        } else {
            userInviteFriendsIcon.setImageResource(R.drawable.touxiang);
        }
        userInviteFriendsCode.setText("邀请码: " + SPUtil.getStringValue(CommonResource.USER_INVITE));
        userInviteFriendsName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        userInviteFriendsRec.setLayoutManager(gridLayoutManager);
        presenter.initView();
    }

    @Override
    public void initClick() {
        userInviteFriendsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //分享海报
        userInviteFriendsHaibao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.viewToImage(userInviteFriendsNested, "https://www.pgyer.com/5e1V", userInviteFriendsQrCode);
            }
        });
        //分享链接
        userInviteFriendsLianjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.shareLink();
            }
        });

    }

    @Override
    public void loadAdapter(EquityAdapter equityAdapter) {
        userInviteFriendsRec.setAdapter(equityAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public UserInviteFriendsView createView() {
        return this;
    }

    @Override
    public UserInviteFriendsPresenter createPresenter() {
        return new UserInviteFriendsPresenter(this);
    }
}
