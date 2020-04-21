package com.example.search_friends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.ManagerBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.my_association.adapter.ManagerAdapter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class SearchFriendsPresenter extends BasePresenter<SearchFriendsView> {

    private List<ManagerBean.RecordsBean> recordsBeanList = new ArrayList<>();

    public SearchFriendsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void searchFriend(String phoneNum) {
        Map map = MapUtil.getInstance().addParms("page", 1).addParms("userCode", SPUtil.getUserCode()).addParms("phoneNum", phoneNum).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postData(CommonResource.MYASSOCIATION, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("搜索好友成功" + result);
                ManagerBean managerBean = JSON.parseObject(result, new TypeReference<ManagerBean>() {
                }.getType());
                if (managerBean != null) {
                    recordsBeanList.clear();
                    recordsBeanList.addAll(managerBean.getRecords());
                    ManagerAdapter managerAdapter = new ManagerAdapter(mContext, recordsBeanList, R.layout.item_association_manager);
                    if (getView() != null) {
                        getView().loadAdapter(managerAdapter);
                    }
                    managerAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
//                            Toast.makeText(mContext, "暂时不能联系该用户", Toast.LENGTH_SHORT).show();
                            call(recordsBeanList.get(position).getPhone());
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("搜索好友失败" + errorMsg);
                Toast.makeText(mContext, "无此好友", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    //调起电话
    private void call(String call) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + call));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //开启系统拨号器
        mContext.startActivity(intent);
    }
}
