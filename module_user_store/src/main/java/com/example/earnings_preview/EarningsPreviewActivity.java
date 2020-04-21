package com.example.earnings_preview;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.UserInfoBean;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;

import butterknife.BindView;

/**
 * 收益预览
 */
public class EarningsPreviewActivity extends BaseActivity<EarningsPreviewView, EarningsPreviewPrsenter> implements EarningsPreviewView {

    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.earnings_preview_price)
    TextView earningsPreviewPrice;
    @BindView(R2.id.earnings_preview_fukuan_jin)
    TextView earningsPreviewFukuanJin;
    @BindView(R2.id.earnings_preview_yugu_jin)
    TextView earningsPreviewYuguJin;
    @BindView(R2.id.earnings_preview_jiesuan_jin)
    TextView earningsPreviewJiesuanJin;
    @BindView(R2.id.earnings_preview_fukuan_zuo)
    TextView earningsPreviewFukuanZuo;
    @BindView(R2.id.earnings_preview_yugu_zuo)
    TextView earningsPreviewYuguZuo;
    @BindView(R2.id.earnings_preview_jiesuan_zuo)
    TextView earningsPreviewJiesuanZuo;
    @BindView(R2.id.earnings_preview_fukuan_ben)
    TextView earningsPreviewFukuanBen;
    @BindView(R2.id.earnings_preview_yugu_ben)
    TextView earningsPreviewYuguBen;
    @BindView(R2.id.earnings_preview_jiesuan_ben)
    TextView earningsPreviewJiesuanBen;
    @BindView(R2.id.earnings_preview_fukuan_shang)
    TextView earningsPreviewFukuanShang;
    @BindView(R2.id.earnings_preview_yugu_shang)
    TextView earningsPreviewYuguShang;
    @BindView(R2.id.earnings_preview_jiesuan_shang)
    TextView earningsPreviewJiesuanShang;

    @Override
    public int getLayoutId() {
        return R.layout.activity_earnings_preview;
    }

    @Override
    public void initData() {
        includeTitle.setText("收益预览");
        Intent intent = getIntent();
        double leijishouyi = intent.getDoubleExtra("leijishouyi", 0);
        earningsPreviewPrice.setText(leijishouyi + "");
        presenter.selectUserProfit();
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public EarningsPreviewView createView() {
        return this;
    }

    @Override
    public EarningsPreviewPrsenter createPresenter() {
        return new EarningsPreviewPrsenter(this);
    }

    @Override
    public void loadUserProfit(UserInfoBean userInfoBean) {
        earningsPreviewFukuanJin.setText(userInfoBean.getTodayPayNum() + "");
        earningsPreviewYuguJin.setText(userInfoBean.getTodayPredictEarnings() + "");
        earningsPreviewJiesuanJin.setText(userInfoBean.getTodayCloseEarnings() + "");

        earningsPreviewFukuanZuo.setText(userInfoBean.getYesterdayPayNum() + "");
        earningsPreviewYuguZuo.setText(userInfoBean.getYesterdayPredictEarnings() + "");
        earningsPreviewJiesuanZuo.setText(userInfoBean.getYesterdayCloseEarnings() + "");

        earningsPreviewFukuanBen.setText(userInfoBean.getCurrentMonthPayNum() + "");
        earningsPreviewYuguBen.setText(userInfoBean.getCurrentMonthPredictEarnings() + "");
        earningsPreviewJiesuanBen.setText(userInfoBean.getCurrentMonthCloseEarnings() + "");

        earningsPreviewFukuanShang.setText(userInfoBean.getLastMonthPayNum() + "");
        earningsPreviewYuguShang.setText(userInfoBean.getLastMonthPredictEarnings() + "");
        earningsPreviewJiesuanShang.setText(userInfoBean.getLastMonthCloseEarnings() + "");
    }
}
