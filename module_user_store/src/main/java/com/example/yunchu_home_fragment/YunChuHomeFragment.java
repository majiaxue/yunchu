package com.example.yunchu_home_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bean.BannerBean;
import com.example.bean.TabBean;
import com.example.bean.VisblityBean;
import com.example.common.CommonResource;
import com.example.mvp.BaseFragment;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_home.adapter.NavBarAdapter;
import com.example.user_home.adapter.SaleHotAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.example.utils.RvItemDecoration;
import com.example.view.CustomHeader;
import com.example.view.CustomeRecyclerView;
import com.example.view.MarqueeView;
import com.example.yunchu_home_fragment.adapter.TeJiaAdapter;
import com.example.yunchu_home_fragment.adapter.TuiJianAdapter;
import com.example.yunchu_home_fragment.adapter.VPApter;
import com.example.yunchu_home_fragment.tabfragment.TabListFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YunChuHomeFragment extends BaseFragment<YunChuHomeView, YunChuHomePresenter> implements YunChuHomeView {
    @BindView(R2.id.yunchu_search)
    TextView yunchuSearch;
    @BindView(R2.id.yunchu_home_xbanner)
    XBanner yunchuHomeXbanner;
    @BindView(R2.id.ll_you)
    LinearLayout llYou;
    @BindView(R2.id.home_marquee)
    MarqueeView homeMarquee;
    @BindView(R2.id.ll_gonggao)
    LinearLayout llGonggao;
    @BindView(R2.id.yunchu_home_top_rec)
    RecyclerView yunchuHomeTopRec;
    @BindView(R2.id.yunchu_img)
    ImageView yunchuImg;
    @BindView(R2.id.v1)
    View v1;
    @BindView(R2.id.tv_pingtai)
    TextView tvPingtai;
    @BindView(R2.id.tv_tuijiantehui)
    TextView tvTuijiantehui;
    @BindView(R2.id.img_shucai)
    ImageView imgShucai;
    @BindView(R2.id.price)
    TextView price;
    @BindView(R2.id.food)
    TextView food;
    @BindView(R2.id.youshi)
    TextView youshi;
    @BindView(R2.id.xianliang)
    TextView xianliang;
    @BindView(R2.id.ji_photo)
    ImageView jiPhoto;
    @BindView(R2.id.v2)
    View v2;
    @BindView(R2.id.shucai)
    TextView shucai;
    @BindView(R2.id.youji)
    TextView youji;
    @BindView(R2.id.ll1)
    LinearLayout ll1;
    @BindView(R2.id.re_yunchu_butie)
    RelativeLayout reYunchuButie;
    @BindView(R2.id.img_photo2)
    ImageView imgPhoto2;
    @BindView(R2.id.tv_more)
    TextView tvMore;
    @BindView(R2.id.img_more)
    ImageView imgMore;
    @BindView(R2.id.rec_more)
    RecyclerView recMore;
    @BindView(R2.id.ll_tejia)
    LinearLayout llTejia;
    @BindView(R2.id.yunchu_vp)
    ViewPager yunchuVp;
    @BindView(R2.id.yunchu_nested_scroll)
    NestedScrollView yunchuNestedScroll;
    @BindView(R2.id.yunchu_smart_refresh)
    SmartRefreshLayout yunchuSmartRefresh;
    @BindView(R2.id.tab)
    TabLayout tab2;
    @BindView(R2.id.rec_shou)
    RecyclerView recShou;
    @BindView(R2.id.xihongshi)
    ImageView xihongshi;
    @BindView(R2.id.ll_more)
    LinearLayout llMore;
    int teJiaIndex=1;
    private ArrayList<BaseFragment> fragments;
    private TabBean saiQu2Beans;
    int id;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_home;
    }

    @Override
    public void initData() {
        //presenter.getTab(tab2);
        presenter.setXBanner();
        presenter.getNav();
        presenter.getPicture();

        //公告滚动
        presenter.setViewSingleLine();

        presenter.setVisblity();
        presenter.getData(teJiaIndex);
        LogUtil.e("首页id=========="+id);
        //presenter.getTabList(id);

        //导航栏
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        yunchuHomeTopRec.setLayoutManager(gridLayoutManager);

        //天天特价
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recMore.setLayoutManager(layoutManager);

        //首页推荐
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recShou.setLayoutManager(staggeredGridLayoutManager);
        recShou.addItemDecoration(new RvItemDecoration((int) getContext().getResources().getDimension(R.dimen.dp_12), (int) getContext().getResources().getDimension(R.dimen.dp_12)));
        getTab();

        //下拉刷新样式
        CustomHeader customHeader = new CustomHeader(getActivity());
        customHeader.setPrimaryColors(getResources().getColor(R.color.colorTransparency));
        yunchuSmartRefresh.setRefreshHeader(customHeader);

//        yunchuNestedScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) getActivity());

    }

    private void getTab() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getDataWithout(CommonResource.TAB);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("这是类目的接口---------" + result);
                fragments = new ArrayList<>();
                saiQu2Beans = JSON.parseObject(result, TabBean.class);
                LogUtil.e("解析后---"+saiQu2Beans.toString());
                for (int i = 0; i < saiQu2Beans.getHomePageList().size(); i++) {
                    LogUtil.e("tab名称---"+saiQu2Beans.getHomePageList().get(i).getName());
                    tab2.addTab(tab2.newTab().setText(saiQu2Beans.getHomePageList().get(i).getName()));
                    fragments.add(new TabListFragment(saiQu2Beans.getHomePageList().get(i).getId()));
                }
                tab2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        yunchuVp.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                yunchuVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab2));
                VPApter adapter = new VPApter(getChildFragmentManager(), fragments);
                yunchuVp.setAdapter(adapter);
                tvPingtai.setText(saiQu2Beans.getPlatformSubsidyList().get(0).getName());
                Glide.with(getContext())
                        .load(saiQu2Beans.getPlatformSubsidyList().get(0).getIcon())
                        .into(imgShucai);
                food.setText(saiQu2Beans.getPlatformSubsidyList().get(1).getName());
                Glide.with(getContext())
                        .load(saiQu2Beans.getPlatformSubsidyList().get(1).getIcon())
                        .into(jiPhoto);
                shucai.setText(saiQu2Beans.getPlatformSubsidyList().get(2).getName());
                Glide.with(getContext())
                        .load(saiQu2Beans.getPlatformSubsidyList().get(2).getIcon())
                        .into(xihongshi);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("tablayout错了"+errorCode+"-----------------"+errorMsg);
            }
        }));

    }

    @Override
    public void initClick() {
        //搜索框跳转
        yunchuSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.jumpToSearch();
            }
        });
        llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/module_user_store/typeDetail").withString("rebateStatus  ","1").navigation();
            }
        });

        //设置上拉刷新下拉加载
        yunchuSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                teJiaIndex = 1;
                presenter.getData(teJiaIndex);
            }
        });
        yunchuSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                teJiaIndex++;
                presenter.getData(teJiaIndex);
            }
        });
        reYunchuButie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/module_user_store/typeDetail")
                        .withString("categoryId",saiQu2Beans.getPlatformSubsidyList().get(0).getId()+"")
                        .withString("recommandStatus","1").navigation();
            }
        });




    }

    @Override
    public YunChuHomeView createView() {
        return this;
    }

    @Override
    public YunChuHomePresenter createPresenter() {
        return new YunChuHomePresenter(getContext());
    }

    @Override
    public void loadBanner(final List<BannerBean.RecordsBean> beanList) {
        yunchuHomeXbanner.setBannerData(beanList);
        yunchuHomeXbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                RequestOptions requestOptions = RequestOptions.centerCropTransform();
                Glide.with(getContext()).load(((BannerBean.RecordsBean) model).getPicUrl()).apply(requestOptions).transform(new RoundedCorners((int) getContext().getResources().getDimension(R.dimen.dp_10))).into((ImageView) view);
            }
        });
        yunchuHomeXbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                LogUtil.e("内容----"+beanList.toString());
                if (beanList.get(position).getUrl().equals("")){
                    LogUtil.e("你点击了");
                }else if (beanList.get(position).getPid()!=null){
                    ARouter.getInstance()
                            .build("/module_user_store/GoodsDetailActivity")
                            .withString("id", beanList.get(position).getPid() + "")
//                            .withString("sellerId", beanList.get(position).getSellerId()+"")
//                            .withString("commendId", beanList.get(position).getPid() + "")
                            .navigation();
                } else {
                    ARouter.getInstance().build("/model_user_store/WebActivity")
                            .withString("url",beanList.get(position).getUrl())
                            .navigation();
                    LogUtil.e("点击了  "+position+"  图片");
                }

            }
        });
    }

    @Override
    public void lodeMarquee(List<View> views) {
        homeMarquee.setViews(views);
    }

    @Override
    public void loadNavBar(NavBarAdapter navBarAdapter) {
        yunchuHomeTopRec.setAdapter(navBarAdapter);
    }

    @Override
    public void getVisiblity(List<VisblityBean> visblityBeans) {
        //平台补贴区的特价区的显示隐藏
        //0==》关闭    1=====》开启
        if (visblityBeans.get(0).getStatus()==0){
            ll1.setVisibility(View.GONE);
        }else if (visblityBeans.get(0).getStatus()==1){
            ll1.setVisibility(View.VISIBLE);
        }else if (visblityBeans.get(1).getStatus()==0){
            reYunchuButie.setVisibility(View.GONE);
        }else if (visblityBeans.get(1).getStatus()==1){
            reYunchuButie.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadSaleHot(TeJiaAdapter saleHotAdapter) {
        recMore.setAdapter(saleHotAdapter);
    }

    @Override
    public void loadVP(ArrayList<BaseFragment> fragments) {

    }

    //平台补贴区域
    @Override
    public void getDate2(TabBean saiQu2Beans) {

    }

    @Override
    public void loadTuiJIanAdapter(TuiJianAdapter tuiJIanAdapter) {
        recShou.setAdapter(tuiJIanAdapter);
    }

    @Override
    public void getId(int id) {
        this.id=id;
    }

    @Override
    public void refresh() {
        yunchuSmartRefresh.finishRefresh();
        yunchuSmartRefresh.finishLoadMore();
    }

    @Override
    public void getImg(String picUrl) {
        Glide.with(getContext()).load(picUrl).into(yunchuImg);
    }
}
