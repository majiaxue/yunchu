package com.example.group_fans;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.bean.GroupFansBean;
import com.example.bean.GroupFansPeopleBean;
import com.example.bean.UserInfoBean;
import com.example.common.CommonResource;
import com.example.group_fans.adapter.GroupFansRvAdapter;
import com.example.group_fans.fragment.FansFragment;
import com.example.group_fans.fragment2.Fans2Fragment;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class GroupFansPresenter extends BasePresenter<GroupFansView> {
    private List<UserInfoBean> dataList = new ArrayList<>();
    private GroupFansRvAdapter adapter;
    private int pages;
    private List<GroupFansBean> groupFansBeans;
    private String[] titleArr = {"直推粉丝", "间推粉丝"};
    private Fans2Fragment fan2;
    private FansFragment fan1;
    private List<Fragment> fragmentList = new ArrayList<>();;


    public GroupFansPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTabLayout(final TabLayout tabLayout) {

        for (String title : titleArr) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        initIndicator(tabLayout);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                ProcessDialogUtil.showProcessDialog(mContext);
//                if (tab.getPosition() == 0) {
//                    loadData(1, "", "1");
//                    getView().getType("1");
//                } else if (tab.getPosition() == 1) {
//                    loadData(1, "", "2");
//                    getView().getType("2");
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initVp(FragmentManager fm) {
        fan1 = new FansFragment();
        fan2 = new Fans2Fragment();
        fragmentList.add(fan1);
        fragmentList.add(fan2);
        OrderVPAdapter vpAdapter = new OrderVPAdapter(fm, fragmentList, titleArr);
        getView().updateVP(vpAdapter);
    }

    public void getTeam() {
        Map userCode = MapUtil.getInstance().addParms("userCode", SPUtil.getStringValue(CommonResource.USERCODE)).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getData(CommonResource.TEAM, userCode);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("推荐人信息" + result);
                GroupFansPeopleBean groupFansPeopleBean = JSON.parseObject(result, GroupFansPeopleBean.class);
                if (getView() != null) {
                    getView().loadCount(groupFansPeopleBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "-------------" + errorMsg);
            }
        }));
    }

    public void loadData(final int page, String content, String level) {
        LogUtil.e("这是level--------" + level);
        Map map = MapUtil.getInstance().addParms("page", page).addParms("userCode", SPUtil.getStringValue(CommonResource.USERCODE)).addParms("type", level).build();
        LogUtil.e("usercode"+SPUtil.getStringValue(CommonResource.USERCODE));

        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.TEAMLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                if (getView() != null) {
                    getView().loadFinish();
                }
                LogUtil.e("团队粉丝：" + result);
                groupFansBeans = JSON.parseArray(result, GroupFansBean.class);
                if (groupFansBeans.size() != 0) {
                    adapter = new GroupFansRvAdapter(mContext, groupFansBeans, R.layout.rv_group_fans);
                    if (getView() != null) {
                        getView().loadRv(adapter);
                    } else {
                        groupFansBeans.clear();
                        adapter.notifyDataSetChanged();
                    }
                }


            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "-------" + errorMsg);
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
