package com.example.mima;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;

import butterknife.BindView;

public class MiMaActivity extends BaseActivity<MinMaView,MinMaPresenter> implements MinMaView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.password_new)
    EditText passwordNew;
    @BindView(R2.id.password_new_second)
    EditText passwordNewSecond;
    @BindView(R2.id.password_btn)
    TextView passwordBtn;
    @BindView(R2.id.password_old)
    EditText passwordOld;
    @BindView(R2.id.password_new_img)
    ImageView imgNew;
    @BindView(R2.id.password_new_second_img)
    ImageView imgNewSecond;
    @BindView(R2.id.password_temp1)
    View mTemp1;

    private boolean firstShow = false;
    private boolean secondShow = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mima2;
    }

    @Override
    public void initData() {
        includeTitle.setText("修改密码");

    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pasold = passwordOld.getText().toString();
                String second = passwordNewSecond.getText().toString();
                String newpas = passwordNew.getText().toString();
                presenter.gaiMiMa(pasold,newpas,second);
            }
        });
        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstShow = !firstShow;
                passwordNew.setTransformationMethod(firstShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                imgNew.setImageResource(firstShow ? R.drawable.showpassword : R.drawable.hidepassword);
                passwordNew.setSelection(passwordNew.getText().length());
            }
        });

        imgNewSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondShow = !secondShow;
                passwordNewSecond.setTransformationMethod(secondShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                imgNewSecond.setImageResource(secondShow ? R.drawable.showpassword : R.drawable.hidepassword);
                passwordNewSecond.setSelection(passwordNewSecond.getText().length());
            }
        });
    }

    @Override
    public MinMaView createView() {
        return this;
    }

    @Override
    public MinMaPresenter createPresenter() {
        return new MinMaPresenter(this);
    }
}
