package com.example.intergation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.adapter.BaseVPAdapter;
import com.example.bean.PointsMingxiBean;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.intergation.adapter.PointsRecordAdapter;
import com.example.intergation.cashout_record.CashoutRecordFragment;
import com.example.intergation.points_record.PointsRecordFragment;
import com.example.module_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.order.adapter.OrderVPAdapter;

import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MyIntegrationPresenter extends BasePresenter<MyIntegrationView> {
    private String[] strArray = new String[]{"收入记录", "支出记录"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private PointsRecordFragment pointsRecordFragment;
    private CashoutRecordFragment cashoutRecordFragment;



    public void initTab(final TabLayout myIntegralTab) {
        for (String title : strArray) {
            myIntegralTab.addTab(myIntegralTab.newTab().setText(title));
        }
        initIndicator(myIntegralTab);
        myIntegralTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(13);
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_noselected));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    public void loadUserInfo() {
        LogUtil.e("token--->" + SPUtil.getToken());
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.GETUSERINFO, SPUtil.getToken());//"http://192.168.1.9:4001"
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("个人信息:" + result);
                UserInfoBean userInfoBean = new Gson().fromJson(result, new TypeToken<UserInfoBean>() {
                }.getType());
                LogUtil.e("userInfoBean:" + userInfoBean);
                if (userInfoBean != null) {
                    if (getView() != null) {
                      getView().loadUserInfo(userInfoBean);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("个人信息" + errorCode + "---------" + errorMsg);
            }
        }));
    }
    private void initIndicator(final TabLayout managerOrderDetailsTab) {
        managerOrderDetailsTab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //了解源码得知 线的宽度是根据 tabView的宽度来设置的
                    LinearLayout mTabStrip = (LinearLayout) managerOrderDetailsTab.getChildAt(0);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField =
                                tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding
                        // 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params =
                                (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    public MyIntegrationPresenter(Context context) {
        super(context);
    }

    public void initVp(FragmentManager fm) {
        pointsRecordFragment = new PointsRecordFragment();
        cashoutRecordFragment = new CashoutRecordFragment();
        fragmentList.add(pointsRecordFragment);
        fragmentList.add(cashoutRecordFragment);

        OrderVPAdapter vpAdapter = new OrderVPAdapter(fm, fragmentList, strArray);
        getView().updateVP(vpAdapter);
    }

    @Override
    protected void onViewDestroy() {

    }
}
