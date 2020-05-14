package com.example.systemnotice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/model_user_store/SystemNoticeActivity")
public class SystemNoticeActivity extends BaseActivity<SystemNoticeView, SystemNoticePresenter> implements SystemNoticeView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.include_right)
    ImageView includeRight;
    @BindView(R2.id.include_right_btn)
    TextView includeRightBtn;
    @BindView(R2.id.message_detail_title)
    TextView messageDetailTitle;
//    @BindView(R2.id.message_detail_time)
//    TextView messageDetailTime;
    @Autowired(name = "content")
    String content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_system_notice;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        includeTitle.setText("系统消息");
        messageDetailTitle.setText(content);

    }

    @Override
    public void initClick() {

        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public SystemNoticeView createView() {
        return this;
    }

    @Override
    public SystemNoticePresenter createPresenter() {
        return new SystemNoticePresenter(this);
    }
}
