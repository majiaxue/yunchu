package com.example.updatevip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.mvp.BaseActivity;
import com.example.updatevip.adapter.ImgShabgChuanAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//升级vip界面
@Route(path = "/user_store/UpdateVipActivity")
public class UpdateVipActivity extends BaseActivity<UpdateVipView, UpdateVipPresenter> implements UpdateVipView {
    @BindView(R2.id.img_back)
    ImageView imgBack;
    @BindView(R2.id.ed_text)
    EditText edText;
    @BindView(R2.id.tv_number)
    TextView tvNumber;
    @BindView(R2.id.photo_rec)
    RecyclerView photoRec;
    @BindView(R2.id.btn_tijiao)
    Button btnTijiao;
    int MAX_NUM=200;
    private TextWatcher watcher;
    private final int PHOTO_ALBUM_CODE = 0x222;
    private final int TAKE_PHOTO_CODE = 0x111;
    private List<String> imgList=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    public void initData() {
        presenter.loadData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        photoRec.setLayoutManager(gridLayoutManager);
        photoRec.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.dp_15), 0, 0, (int) getResources().getDimension(R.dimen.dp_10)));
    }

    @Override
    public void initClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //监听edittext输入字数
        watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
                Log.i("onTextChanged", s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
                Log.i("beforeTextChanged", s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
                Log.i("afterTextChanged", s.toString());
                if (s.length() > MAX_NUM) {
                    s.delete(MAX_NUM, s.length());
                }
                int num = MAX_NUM - s.length();
                tvNumber.setText((MAX_NUM-num)+"/200");
            }
        };

        edText.addTextChangedListener(watcher);

        btnTijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.TiJiao(edText.getText().toString());
            }
        });


    }

    @Override
    public UpdateVipView createView() {
        return this;
    }

    @Override
    public UpdateVipPresenter createPresenter() {
        return new UpdateVipPresenter(this);
    }

    @Override
    public void photoAlbum(Intent intent) {
        startActivityForResult(intent, PHOTO_ALBUM_CODE);
    }

    @Override
    public void takePhoto(Intent captureIntent) {
        startActivityForResult(captureIntent, TAKE_PHOTO_CODE);
    }

    @Override
    public void imagePath(String s) {
        imgList.add(s);
    }

    @Override
    public void loadRv(ImgShabgChuanAdapter adapter) {
        photoRec.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO_CODE:
                presenter.updateList();
                break;
            case PHOTO_ALBUM_CODE:
                presenter.parseUri(data);
                break;
        }
    }
}
