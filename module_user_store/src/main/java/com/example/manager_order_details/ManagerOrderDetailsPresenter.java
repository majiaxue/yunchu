package com.example.manager_order_details;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.bean.ManagerOrderDetailsBean;
import com.example.common.CommonResource;
import com.example.manager_order_details.adapter.ManagerOrderDetailsAdapter;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class ManagerOrderDetailsPresenter extends BasePresenter<ManagerOrderDetailsView> {

    private String[] strArray = new String[]{"全部", "未结算", "已结算"};
    private List<ManagerOrderDetailsBean> managerOrderDetailsBeans = new ArrayList<>();

    public ManagerOrderDetailsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout managerOrderDetailsTab) {
        for (String title : strArray) {
            managerOrderDetailsTab.addTab(managerOrderDetailsTab.newTab().setText(title));
        }

        initIndicator(managerOrderDetailsTab);

        managerOrderDetailsTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    selectAllOrder(3);
                } else if (tab.getPosition() == 1) {
                    selectAllOrder(2);
                } else {
                    selectAllOrder(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void selectAllOrder(final int type) {
        Map build = MapUtil.getInstance().addParms("type", type).addParms("userCode", SPUtil.getUserCode()).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).getData(CommonResource.SELECTALLORDER, build);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("订单明细成功" + result);
                List<ManagerOrderDetailsBean> beans = JSON.parseArray(result, ManagerOrderDetailsBean.class);
                managerOrderDetailsBeans.clear();
                managerOrderDetailsBeans.addAll(beans);
                ManagerOrderDetailsAdapter managerOrderDetailsAdapter = new ManagerOrderDetailsAdapter(mContext, managerOrderDetailsBeans, R.layout.item_manager_order_details, type);
                if (getView() != null) {
                    getView().loadAdapter(managerOrderDetailsAdapter);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("订单明细失败" + errorMsg);

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

}
