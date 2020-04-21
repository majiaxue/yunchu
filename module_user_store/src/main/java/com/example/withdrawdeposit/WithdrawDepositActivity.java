package com.example.withdrawdeposit;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.PhoneNumUtil;

import butterknife.BindView;

/**
 * 多用户提现
 */
public class WithdrawDepositActivity extends BaseActivity<WithdrawDepositView, WithdrawDepositPresenter> implements WithdrawDepositView {

    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.withdraw_deposit_money)
    EditText withdrawDepositMoney;
    @BindView(R2.id.withdraw_deposit_zhifubao)
    LinearLayout withdrawDepositZhifubao;
    @BindView(R2.id.withdraw_deposit_message)
    TextView withdrawDepositMessage;
    @BindView(R2.id.confirm_withdrawal)
    TextView confirmWithdrawal;
    @BindView(R2.id.withdraw_deposit_name)
    EditText withdrawDepositName;
    @BindView(R2.id.withdraw_deposit_linear_name)
    LinearLayout withdrawDepositLinearName;
    @BindView(R2.id.withdraw_deposit_alipay_account)
    EditText withdrawDepositAlipayAccount;
    @BindView(R2.id.withdraw_deposit_view)
    View withdrawDepositView;

    private double money;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw_deposit;
    }

    @Override
    public void initData() {
        includeTitle.setText("提现");
        Intent intent = getIntent();
        money = intent.getDoubleExtra("money", 0);
        withdrawDepositMoney.setHint("您当前可提现" + money + "元");
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //确认提现
        confirmWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(withdrawDepositMoney.getText().toString())) {
                    Toast.makeText(WithdrawDepositActivity.this, "请输入提现金额", Toast.LENGTH_SHORT).show();
                } else if (Double.valueOf(withdrawDepositMoney.getText().toString()) < 0.1) {
                    Toast.makeText(WithdrawDepositActivity.this, "提现金额不能小于0.1元", Toast.LENGTH_SHORT).show();
                } else if (Double.valueOf(withdrawDepositMoney.getText().toString()) > money) {
                    Toast.makeText(WithdrawDepositActivity.this, "提现金额不能大于可提现金额", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(withdrawDepositName.getText().toString())) {
                    Toast.makeText(WithdrawDepositActivity.this, "请填写姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(withdrawDepositAlipayAccount.getText().toString()) || !PhoneNumUtil.isMobileNO(withdrawDepositAlipayAccount.getText().toString())) {
                    Toast.makeText(WithdrawDepositActivity.this, "请检查支付宝账号", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.aliPayWithdraw(withdrawDepositName.getText().toString(), withdrawDepositAlipayAccount.getText().toString(), withdrawDepositMoney.getText().toString());
                }
            }
        });

    }

    @Override
    public WithdrawDepositView createView() {
        return this;
    }

    @Override
    public WithdrawDepositPresenter createPresenter() {
        return new WithdrawDepositPresenter(this);
    }

}
