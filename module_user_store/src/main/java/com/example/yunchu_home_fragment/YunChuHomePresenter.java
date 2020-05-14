package com.example.yunchu_home_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.BannerBean;
import com.example.bean.GonggaoBean;
import com.example.bean.HotSaleBean;
import com.example.bean.NavBarBean;
import com.example.bean.ShouBanner;
import com.example.bean.TabBean;
import com.example.bean.TeJIaBean;
import com.example.bean.TuiJIanBean;
import com.example.bean.VisblityBean;
import com.example.common.CommonResource;
import com.example.entity.EventBusBean2;
import com.example.goods_detail.GoodsDetailActivity;
import com.example.mvp.BaseFragment;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.OnTripartiteCallBack;
import com.example.net.RetrofitUtil;
import com.example.search.UserSearchActivity;
import com.example.user_home.adapter.NavBarAdapter;
import com.example.user_home.adapter.SaleHotAdapter;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.ProcessDialogUtil;
import com.example.yunchu_home_fragment.adapter.TeJiaAdapter;
import com.example.yunchu_home_fragment.adapter.TuiJianAdapter;
import com.example.yunchu_home_fragment.adapter.VPApter;
import com.example.yunchu_home_fragment.tabfragment.TabListFragment;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YunChuHomePresenter extends BasePresenter<YunChuHomeView> {

    private List<BannerBean.RecordsBean> beanList;
    private List<String> data=new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private List<HotSaleBean.DataBean> saleHotList = new ArrayList<>();
    private List<NavBarBean.RecordsBean> navbarList = new ArrayList<>();
    private TeJiaAdapter saleHotAdapter;
    private String[] split1;
    private List<String> list;
    private List<HotSaleBean.DataBean> dataBeans=new ArrayList<>();
    private TuiJianAdapter shouYeAdapter;
    private TabBean saiQu2Beans;

    public YunChuHomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTabLayout(final TabLayout tabLayout){

        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getDataWithout(CommonResource.TAB);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("这是类目的接口---------" + result);

                saiQu2Beans = JSON.parseObject(result, TabBean.class);
                LogUtil.e("解析后---"+saiQu2Beans.toString());
                for (int i = 0; i < saiQu2Beans.getHomePageList().size(); i++) {
                    LogUtil.e("tab名称---"+saiQu2Beans.getHomePageList().get(i).getName());
                    tabLayout.addTab(tabLayout.newTab().setText(saiQu2Beans.getHomePageList().get(i).getName()));
                }
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        for (int i = 0; i < saiQu2Beans.getHomePageList().size(); i++) {
                           loadData(saiQu2Beans.getHomePageList().get(i).getId(),1);
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

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("tablayout错了"+errorCode+"-----------------"+errorMsg);
            }
        }));

    }

    //首页第二个轮播
    public void getPicture(){
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9005).getDataWithout(CommonResource.SHOUBANNER);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("这是首页图片-----------"+result);
                ShouBanner shouBanner = JSON.parseObject(result, ShouBanner.class);
                LogUtil.e("首页图片解析后"+shouBanner.getRecords().toString());
                if (getView()!=null){
                    getView().getImg(shouBanner.getRecords().get(0).getPicUrl());
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    public void loadData(int id, final int pagrSize){
        {
            LogUtil.e("这是id-------------"+id);
            //天天特价
            Map map = MapUtil.getInstance().addParms("categoryId", id).addParms("newStatus", "1").addParms("pageNum",pagrSize).build();
            Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    getView().refresh();
                    LogUtil.e("首页推荐: " + result);
                    final HotSaleBean hotSaleBean = JSON.parseObject(result, new TypeReference<HotSaleBean>() {
                    }.getType());
                    if (hotSaleBean != null) {
                        if (pagrSize==1){
                            dataBeans.clear();
                        }
                        dataBeans.addAll(hotSaleBean.getData());
                        shouYeAdapter = new TuiJianAdapter(mContext, dataBeans, R.layout.item_tab_list);
                        if (getView()!=null){
                            getView().loadShouAdapter(shouYeAdapter);
                        }else {
                            saleHotAdapter.notifyDataSetChanged();
                        }

                        saleHotAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(RecyclerView parent, View view, int position) {
                                ARouter.getInstance()
                                        .build("/module_user_store/GoodsDetailActivity")
                                        .withString("id", dataBeans.get(position).getId() + "")
                                        .withString("sellerId", dataBeans.get(position).getSellerId()+"")
                                        .withString("commendId", dataBeans.get(position).getProductCategoryId() + "")
                                        .navigation();
                            }
                        });
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("热销商品: " + errorMsg);
                }
            }));

        }
    }


    //判断平台补贴区和特价区是否隐藏
    public void setVisblity(){
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.YINGCNAG);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("显示隐藏区域-------"+result);
                List<VisblityBean> visblityBeans = JSON.parseArray(result, VisblityBean.class);
                if (getView()!=null){
                    getView().getVisiblity(visblityBeans);
                }
            }
            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode+"--------"+errorMsg);
            }
        }));

    }

    //公告滚动效果
    public void setViewSingleLine() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9005).getDataWithout(CommonResource.GONGGAO);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("系统公告---------"+result);
//                String[] split = result.split(",");
//                result.split(",");
                GonggaoBean gonggaoBean = JSON.parseObject(result, GonggaoBean.class);
                data = gonggaoBean.getData();
                final List<String> list2=new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                   split1 = data.get(i).split(",");
                   list = Arrays.asList(split1);
                   LogUtil.e("分割hhh----"+list.toString());
                   list2.addAll(list);
                }

                LogUtil.e("分割---"+list.toString());
                views.clear();
                for (int i = 0; i < list2.size(); i++) {
                    final int position = i;
                    //设置滚动的单个布局
                    LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.txt, null);
                    //初始化布局的控件
                    TextView marqueeMessage = moreView.findViewById(R.id.txt1);

                    //进行对控件赋值
                    marqueeMessage.setText(list2.get(i));

                    //添加到循环滚动数组里面去
                    views.add(moreView);
                    if (getView() != null) {
                        getView().lodeMarquee(views);
                    }
                    final int finalI = i;
                    marqueeMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ARouter.getInstance().build("/model_user_store/SystemNoticeActivity")
                                    .withString("content",list2.get(finalI)).navigation();
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    //轮播图
    public void setXBanner() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9005).getDataWithout(CommonResource.USERSBANNER);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("轮播图：" + result);
                BannerBean records = JSON.parseObject(result, BannerBean.class);
                LogUtil.e("解析后：" + records);
                beanList = records.getRecords();
                if (getView() != null) {
                    getView().loadBanner(beanList);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    //搜索框跳转
    public void jumpToSearch() {
        mContext.startActivity(new Intent(mContext, UserSearchActivity.class));
    }

    //导航栏
    public void getNav(){
        Observable<ResponseBody> observable1 = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getDataWithout(CommonResource.TYPENAVBAR);
        RetrofitUtil.getInstance().toSubscribe(observable1, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("导航----->" + result);
                NavBarBean navBarBean = JSON.parseObject(result, new TypeReference<NavBarBean>() {
                }.getType());
                if (navBarBean != null) {
                    navbarList.addAll(navBarBean.getRecords());
                    NavBarAdapter navBarAdapter = new NavBarAdapter(mContext, navbarList, R.layout.rv_navbar);
                    navBarAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            EventBus.getDefault().post(new EventBusBean2(CommonResource.JUMP_CLASSIFY, navbarList.get(position).getId()));
                        }
                    });
                    if (getView() != null) {
                        getView().loadNavBar(navBarAdapter);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }



    public void getData(final int hotSaleIndex){
        //天天特价
        Map map = MapUtil.getInstance().addParms("pageNum", hotSaleIndex).addParms("saleDesc", "1").addParms("rebateStatus","1").build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("天天特价: " + result);
                HotSaleBean teJIaBean = JSON.parseObject(result, HotSaleBean.class);
                LogUtil.e("解析后帆帆帆帆-------------"+teJIaBean.toString());
                if (hotSaleIndex==1){
                    saleHotList.clear();
                }
                saleHotList.addAll(teJIaBean.getData());
                LogUtil.e("呵呵哒"+saleHotList.toString());
                if (saleHotAdapter==null){
                    LogUtil.e("走了1");
                    saleHotAdapter = new TeJiaAdapter(mContext, saleHotList, R.layout.item_tejia);
                    LogUtil.e("走了2");
                    getView().loadSaleHot(saleHotAdapter);
                    LogUtil.e("走了3");
                    getView().refresh();
                }else {
                    saleHotAdapter.notifyDataSetChanged();
                }
                    saleHotAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                            intent.putExtra("id", saleHotList.get(position).getId() + "");
                            intent.putExtra("commendId", saleHotList.get(position).getProductCategoryId() + "");
                            intent.putExtra("sellerId", saleHotList.get(position).getSellerId());
                            mContext.startActivity(intent);
                        }
                    });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("热销商品:-------------- " + errorMsg);
            }
        }));

    }
}
