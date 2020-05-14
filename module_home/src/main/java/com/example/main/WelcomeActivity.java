package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.CommonResource;
import com.example.module_home.R;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.PopUtils;
import com.example.utils.SPUtil;
import com.example.utils.StatusBarUtils;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 启动页
 */
public class WelcomeActivity extends Activity {
    private boolean isFirstIn = false;
    private static final int TIME = 1500;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", false);
        init();
        changeStatus();
    }

    private void changeStatus() {
        // 设置状态栏
        StatusBarUtils.transparencyBar(this);
        StatusBarUtils.setStatusTheme(this, true, true);
    }
    private void init() {
        LogUtil.e("heheda"+isFirstIn);
        if (isFirstIn) {
            handler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            SharedPreferences preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn", true);
            editor.commit();
            handler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
        }
    }

    private void goHome() {
//        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
//        startActivity(i);
        ARouter.getInstance().build("/module_user_store/UserActivity").navigation();
        finish();
    }

    private void goGuide() {
        popupwindow();
    }

    private void popupwindow() {
        View inflate = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.welcome_layout, null);
        TextView no = inflate.findViewById(R.id.no);   //确认按钮
        TextView agree = inflate.findViewById(R.id.agree);   //确认按钮
        TextView fuwuxieyi = inflate.findViewById(R.id.fuwuxieyi);
        TextView yinsi = inflate.findViewById(R.id.yinsi);
        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(new View(WelcomeActivity.this), Gravity.CENTER, 0, 0);
        PopUtils.setTransparency(WelcomeActivity.this, 0.3f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopUtils.setTransparency(WelcomeActivity.this, 1f);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstIn", false);
                editor.commit();
                finish();
            }
        });
        fuwuxieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/mine/agreement").withString("type", "zcxy").navigation();
            }
        });
        yinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/mine/agreement").withString("type", "yhxy").navigation();
            }
        });
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstIn", true);
                editor.commit();
                popupWindow.dismiss();
                Intent i = new Intent(WelcomeActivity.this, GuideActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
