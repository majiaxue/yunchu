package com.example.user_manager_center.qrcode;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.CommonResource;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.DisplayUtil;
import com.example.utils.QRCode;
import com.example.utils.SPUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 面对面邀请
 */
public class QRCodeActivity extends AppCompatActivity {

    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.qr_code_user_icon)
    SimpleDraweeView qrCodeUserIcon;
    @BindView(R2.id.qr_code_user_name)
    TextView qrCodeUserName;
    @BindView(R2.id.qr_code_user_invitation_code)
    TextView qrCodeUserInvitationCode;
    @BindView(R2.id.qr_code_img)
    ImageView qrCodeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);

        initView();

        initClick();

    }

    private void initView() {
        includeTitle.setText("面对面邀请");
        if (!TextUtils.isEmpty(SPUtil.getStringValue(CommonResource.USER_PIC))) {
            qrCodeUserIcon.setImageURI(Uri.parse(SPUtil.getStringValue(CommonResource.USER_PIC)));
        } else {
            qrCodeUserIcon.setImageResource(R.drawable.touxiang);
        }
        qrCodeUserInvitationCode.setText("邀请码: " + SPUtil.getStringValue(CommonResource.USER_INVITE));
        qrCodeUserName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
        Bitmap qr = QRCode.createQRImage(SPUtil.getStringValue(CommonResource.USER_INVITE), DisplayUtil.dip2px(this, 161), DisplayUtil.dip2px(this, 161));
        qrCodeImg.setImageBitmap(qr);
    }

    private void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
