package com.example.group_fans.fragment;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.bean.GroupFansBean;
import com.example.common.CommonResource;
import com.example.group_fans.adapter.GroupFansRvAdapter;
import com.example.module_mine.R;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class FansPresemter extends BasePresenter<FansView> {

    private List<GroupFansBean> groupFansBeans;
    private GroupFansRvAdapter adapter;
    private List<GroupFansBean> dateBeans=new ArrayList<>();

    public FansPresemter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
    public void loadData(final int page, String level) {
        LogUtil.e("这是type--------" + level);
        LogUtil.e("团队粉丝的page------"+page);
        LogUtil.e("usercode------"+SPUtil.getStringValue(CommonResource.USERCODE));
        Map map = MapUtil.getInstance().addParms("page", page).addParms("userCode", SPUtil.getStringValue(CommonResource.USERCODE)).addParms("type", level).build();
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.TEAMLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("团队粉丝：" + result);
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
                        adapter.notifyDataSetChanged();
                    }
                }

//                try {
//                    if (result != null) {
//                        groupFansBeans = JSON.parseArray(result, GroupFansBean.class);
//                        dateBeans.addAll(groupFansBeans);
//                        if (dateBeans.size()>0) {
//                            adapter = new GroupFansRvAdapter(mContext, groupFansBeans, R.layout.rv_group_fans);
//                            getView().loadRv(adapter);
//                        }else {
//                            adapter.notifyDataSetChanged();
//                        }
//                        if (getView() != null) {
//                            getView().loadFinish();
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "-------" + errorMsg);
            }
        }));

    }
}
