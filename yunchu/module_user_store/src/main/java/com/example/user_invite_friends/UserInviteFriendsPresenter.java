package com.example.user_invite_friends;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.EquityBean;
import com.example.common.CommonResource;
import com.example.dbflow.ShareOperationUtil;
import com.example.mvp.BasePresenter;
import com.example.user_manager_center.adapter.EquityAdapter;
import com.example.user_store.R;
import com.example.utils.DisplayUtil;
import com.example.utils.LogUtil;
import com.example.utils.QRCode;
import com.example.utils.SPUtil;
import com.example.utils.ViewToBitmap;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserInviteFriendsPresenter extends BasePresenter<UserInviteFriendsView> {

    private List<EquityBean> equityBeanList = new ArrayList<>();

    public UserInviteFriendsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initView() {
        equityBeanList.add(new EquityBean(R.drawable.icon_gouwuche, "购物省钱", "全场低至1折起"));
        equityBeanList.add(new EquityBean(R.drawable.icon_fenxiang, "分享赚钱", "最高35%推广奖励"));
        equityBeanList.add(new EquityBean(R.drawable.icon_chuangye, "创业机会", "一键开店无需囤货"));
        equityBeanList.add(new EquityBean(R.drawable.icon_chaozhi, "超值好礼", "送399大礼包"));
        equityBeanList.add(new EquityBean(R.drawable.icon_daili, "代理特权", "海量品牌代理权"));
        equityBeanList.add(new EquityBean(R.drawable.icon_kaquan, "分享赚钱", "400元专享券"));
        equityBeanList.add(new EquityBean(R.drawable.icon_guanjia, "创业机会", "贴心服务开店无忧"));
        equityBeanList.add(new EquityBean(R.drawable.icon_shouhou, "超值好礼", "让您售后无忧"));

        EquityAdapter equityAdapter = new EquityAdapter(mContext, equityBeanList, R.layout.item_equity);
        if (getView() != null) {
            getView().loadAdapter(equityAdapter);
        }

    }

    //加载生成图片布局
    public void viewToImage(NestedScrollView userInviteFriendsNested, String downloadLink, ImageView userInviteFriendsQrCode) {
        Bitmap bitmap = ViewToBitmap.createBitmap3(userInviteFriendsNested, userInviteFriendsNested.getWidth(), userInviteFriendsNested.getHeight());

        Bitmap qr = QRCode.createQRImage(downloadLink, DisplayUtil.dip2px(mContext, 300), DisplayUtil.dip2px(mContext, 300));
        userInviteFriendsQrCode.setImageBitmap(qr);
        Bitmap qrBitmap = ViewToBitmap.createBitmap3(userInviteFriendsQrCode, userInviteFriendsQrCode.getWidth(), userInviteFriendsQrCode.getHeight());
        Bitmap syBitmap = addImageWatermark(bitmap, qrBitmap, DisplayUtil.dip2px(mContext,285),  DisplayUtil.dip2px(mContext,15));
        share(syBitmap);
    }

    //分享
    private void share(Bitmap bitmap) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setTitleText("分享到")
                .setTitleTextColor(Color.parseColor("#222222"))
                .setMenuItemTextColor(Color.parseColor("#666666"))
                .setMenuItemIconPressedColor(Color.parseColor("#000000"))
//                .setMenuItemBackgroundColor(Color.parseColor("#fd3c15"),Color.parseColor("#008577"))
                .setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE, (int) mContext.getResources().getDimension(R.dimen.dp_20));
//                .setCancelButtonText("您取消了分享");


        new ShareAction((Activity) mContext)
                .withMedia(new UMImage(mContext, bitmap))
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)// SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                .setCallback(shareListener).open(config);
    }

    public void shareLink() {
        UMWeb umWeb = new UMWeb("https://www.pgyer.com/5e1V");
        umWeb.setTitle("您有一个邀请信息");
        umWeb.setThumb(new UMImage(mContext, R.drawable.icon_app));
        umWeb.setDescription("赶紧加入领取高佣吧！！！");
        new ShareAction((Activity) mContext).withMedia(umWeb)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setCallback(shareListener).open();

    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LogUtil.e("start:" + share_media.toString());
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            LogUtil.e("result:" + share_media.toString());
            ShareOperationUtil.getShareOperationUtil().createOrUpdate();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
        }
    };

    /**
     * 添加图片水印。
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(Bitmap src, Bitmap watermark, int x, int y) {
        Bitmap retBmp = src.copy(src.getConfig(), true);
        Canvas canvas = new Canvas(retBmp);
        canvas.drawBitmap(watermark, x, y, null);
        return retBmp;
    }
}
