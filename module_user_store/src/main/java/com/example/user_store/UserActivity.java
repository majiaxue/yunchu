package com.example.user_store;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.CommonResource;
import com.example.entity.EventBusBean;
import com.example.entity.EventBusBean2;
import com.example.module_base.ModuleBaseApplication;
import com.example.mvp.BaseFragmentActivity;
import com.example.user_classify.ClassifyFragment;
import com.example.utils.LogUtil;
import com.example.view.WindowInsetsFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 多用户商城主界面
 */
@Route(path = "/module_user_store/UserActivity")
public class UserActivity extends BaseFragmentActivity<UserView, UserPresenter> implements UserView {

    private final String[] perms = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private final int REQUEST_CODE = 0xa123;

    @BindView(R2.id.user_frame)
    WindowInsetsFrameLayout userFrame;
    @BindView(R2.id.user_home)
    RadioButton userHome;
    @BindView(R2.id.user_classify)
    RadioButton userClassify;
    @BindView(R2.id.user_shopping_cart)
    RadioButton userShoppingCart;
    @BindView(R2.id.user_mine)
    RadioButton userMine;
    @BindView(R2.id.user_group)
    RadioGroup userGroup;
    @BindView(R2.id.user_local_shop)
    RadioButton userLocalShop;
    @BindView(R2.id.user_finish)
    ImageView mFinish;
    @BindView(R2.id.user_manager_center)
    RadioButton userManagerCenter;

    @Autowired(name = "go")
    String goLocalShop;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);

        EventBus.getDefault().register(this);
        presenter.loadData(getSupportFragmentManager(), R.id.user_frame);
        initPermission();
//        presenter.initNotification();
//        ModuleBaseApplication.mLocationClient.restart();
        if ("go".equals(goLocalShop)) {
            presenter.click(R.id.user_local_shop);
            userLocalShop.setChecked(true);
        }
    }

    @Override
    public void initClick() {
        userGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                presenter.click(checkedId);
            }
        });

        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPermission() {
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, perms, REQUEST_CODE);
            }
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String key = intent.getStringExtra("key");
        if (CommonResource.JUMP_CART.equals(key)) {
            presenter.click(R.id.user_shopping_cart);
            userShoppingCart.setChecked(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            presenter.setBack();
            presenter.exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean2 eventBusBean2) {
        if (CommonResource.USER_BACK.equals(eventBusBean2.getMsg())) {
            finish();
        } else if (CommonResource.JUMP_CLASSIFY.equals(eventBusBean2.getMsg())) {
            ClassifyFragment.position = eventBusBean2.getPosition();
            userClassify.setChecked(true);
            presenter.click(R.id.user_classify);
        }else if (CommonResource.JUMP_MANAGER.equals(eventBusBean2.getMsg())){
            userManagerCenter.setChecked(true);
            presenter.click(R.id.user_manager_center);
        }
    }

//    @Override
//    public void toHome() {
//        userHome.setChecked(true);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public UserView createView() {
        return this;
    }

    @Override
    public UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("本地商城可见");
        EventBus.getDefault().post(new EventBusBean("userLogin"));

//        ModuleBaseApplication.mLocationClient.restart();
    }
}
