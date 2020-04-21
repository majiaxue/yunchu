package com.example.browsinghistory;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.bean.GoodsCollectioninfo;
import com.example.browsinghistory.adapter.BrowsingHistoryChildAdapter;
import com.example.bean.BrowsingHistoryBean;
import com.example.common.CommonResource;
import com.example.module_user_mine.R;
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
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class BrowsingHistoryPresenter extends BasePresenter<BrowsingHistoryView> {

    private List<BrowsingHistoryBean> beanList = new ArrayList<>();
    private BrowsingHistoryChildAdapter browsingHistoryChildAdapter;

    public BrowsingHistoryPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void browsingHistoryRec(final int nextPage) {
        Map build = MapUtil.getInstance().addParms("userCode", SPUtil.getUserCode()).addParms("page", nextPage).build();
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getData(CommonResource.QUERYUSERZUJI, build);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("浏览记录------------->" + result);
                List<BrowsingHistoryBean> browsingHistoryBeans = JSON.parseArray(result, BrowsingHistoryBean.class);

                if (browsingHistoryBeans.size() != 0) {
                    getView().noGoods(false);

                    if (nextPage == 1) {
                        beanList.clear();
                    }
                    beanList.addAll(browsingHistoryBeans);
                    if (browsingHistoryChildAdapter == null) {
                        browsingHistoryChildAdapter = new BrowsingHistoryChildAdapter(mContext, beanList, R.layout.item_browsing_history_child);
                        if (getView() != null) {
                            getView().loadAdapter(browsingHistoryChildAdapter);
                        }
                    } else {
                        browsingHistoryChildAdapter.notifyDataSetChanged();
                    }


                } else {
                    getView().noGoods(true);
                }

                getView().refreshSuccess();

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("浏览记录失败------------->" + errorMsg);
                getView().refreshSuccess();

            }
        }));
    }

    //删除
    public void deleteList() {

        List<String> deleteList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            deleteList.add(beanList.get(i).getId() + "");
        }
        GoodsCollectioninfo goodsCollectioninfo = new GoodsCollectioninfo();
        goodsCollectioninfo.setType("0");
        goodsCollectioninfo.setList(deleteList);
        String browsingHistoryStr = JSON.toJSONString(goodsCollectioninfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), browsingHistoryStr);

        Observable<ResponseBody> deleteGoodsCollection = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postDataWithBody(CommonResource.REMOVEZJSC, body);
        RetrofitUtil.getInstance().toSubscribe(deleteGoodsCollection, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("删除成功----->" + msg);
                for (int i = beanList.size() - 1; i >= 0; i--) {
                    beanList.remove(i);
                }
                browsingHistoryChildAdapter.notifyDataSetChanged();
                getView().noGoods(true);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("删除失败----->" + errorMsg);
            }
        }));


    }
}
