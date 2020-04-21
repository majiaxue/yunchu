package com.example.user_home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.BannerBean;
import com.example.bean.FlashSaleGoodsBean;
import com.example.bean.HotSaleBean;
import com.example.bean.NavBarBean;
import com.example.bean.Records;
import com.example.bean.ZhongXBannerBean;
import com.example.common.CommonResource;
import com.example.entity.EventBusBean2;
import com.example.flashsale.adapter.FlashSaleGoodsAdapter;
import com.example.goods_detail.GoodsDetailActivity;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.search.UserSearchActivity;
import com.example.shop_home.ShopHomeActivity;
import com.example.user_home.adapter.CommendAdapter;
import com.example.user_home.adapter.FlashSaleAdapter;
import com.example.user_home.adapter.NavBarAdapter;
import com.example.user_home.adapter.SaleHotAdapter;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.PopUtils;
import com.example.utils.SPUtil;
import com.example.view.animation.RotateYTransformer;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private List<BannerBean.RecordsBean> beanList = new ArrayList<>();
    private List<NavBarBean.RecordsBean> navbarList = new ArrayList<>();
    private List<HotSaleBean.DataBean> saleHotList = new ArrayList<>();
    private List<HotSaleBean.DataBean> commendList = new ArrayList<>();
    private CommendAdapter commendAdapter;
    private PagerAdapter mAdapter;
    private List<String> images = new ArrayList<>();
    private List<FlashSaleGoodsBean> flashSaleGoodsBeans;
    private FlashSaleAdapter flashSaleAdapter;

    public HomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(int hotSaleIndex) {
        //热销
        Map map = MapUtil.getInstance().addParms("pageNum", hotSaleIndex + "").addParms("saleDesc", "1").build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("热销商品: " + result);
                HotSaleBean hotSaleBean = JSON.parseObject(result, new TypeReference<HotSaleBean>() {
                }.getType());
                if (hotSaleBean != null) {
                    saleHotList.addAll(hotSaleBean.getData());
                    SaleHotAdapter saleHotAdapter = new SaleHotAdapter(mContext, saleHotList, R.layout.rv_hot);
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
                    if (getView() != null) {
                        getView().loadSaleHot(saleHotAdapter);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("热销商品: " + errorMsg);
            }
        }));

        //导航栏
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

    public void setZhongXBanner(final ViewPager homeZhongXbanner) {

        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9005).getDataWithout(CommonResource.HOMEADVERTISEBOTTOM);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("HomePresenterResult" + result);

                ZhongXBannerBean zhongXBannerBean = JSON.parseObject(result, new TypeReference<ZhongXBannerBean>() {
                }.getType());
                if (zhongXBannerBean != null) {
                    for (int i = 0; i < zhongXBannerBean.getRecords().size(); i++) {
                        images.add(zhongXBannerBean.getRecords().get(i).getPicUrl());
                    }
                    homeZhongXbanner.setPageMargin(16);
                    homeZhongXbanner.setOffscreenPageLimit(3);
                    homeZhongXbanner.setPageTransformer(true, new RotateYTransformer());
                    homeZhongXbanner.setAdapter(mAdapter = new PagerAdapter() {
                        @Override
                        public Object instantiateItem(ViewGroup container, int position) {
                            SimpleDraweeView view = new SimpleDraweeView(mContext);
                            view.setScaleType(SimpleDraweeView.ScaleType.FIT_XY);
                            final int realPosition = getRealPosition(position);
                            view.setImageURI(Uri.parse(images.get(realPosition)));
                            container.addView(view);
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (realPosition == 0) {

                                    } else if (realPosition == 1) {
                                        if (!TextUtils.isEmpty(SPUtil.getToken())) {
                                            ARouter.getInstance().build("/module_home/PunchSignActivity").navigation();
                                        } else {
                                            //是否登录
                                            PopUtils.isLogin(mContext);
                                        }
                                    } else {
                                        ARouter.getInstance().build("/mine/invite_friends").navigation();
                                    }
                                }
                            });
                            return view;
                        }


                        @Override
                        public int getItemPosition(Object object) {
                            return POSITION_NONE;
                        }

                        @Override
                        public void destroyItem(ViewGroup container, int position, Object object) {
                            container.removeView((View) object);
                        }

                        @Override
                        public int getCount() {
                            return Integer.MAX_VALUE;
                        }

                        @Override
                        public boolean isViewFromObject(View view, Object o) {
                            return view == o;
                        }

                        //
                        @Override
                        public void startUpdate(ViewGroup container) {
                            super.startUpdate(container);
                            ViewPager viewPager = (ViewPager) container;
                            int position = viewPager.getCurrentItem();
                            if (position == 0) {
                                position = getFirstItemPosition();
                            } else if (position == getCount() - 1) {
                                position = getLastItemPosition();
                            }
                            viewPager.setCurrentItem(position, false);

                        }

                        //
                        private int getRealCount() {
                            return images.size();
                        }

                        //
                        private int getRealPosition(int position) {
                            return position % getRealCount();
                        }

                        //
                        private int getFirstItemPosition() {
                            return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount();
                        }

                        private int getLastItemPosition() {
                            return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount() - 1;
                        }
                    });

                    homeZhongXbanner.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % images.size()));//设置首个轮播显示的位置   实现左右滑动 且首页面对应的是第一个数据

                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("HomePresenterErrorMsg" + errorMsg);
            }
        }));


    }

    private void jumpToShop(int position) {
        Intent intent = new Intent(mContext, ShopHomeActivity.class);
        intent.putExtra("shop_id", commendList.get(position).getSellerId());
        mContext.startActivity(intent);
    }

    public void jumpToGoodsDetail(int position) {
        Intent intent = new Intent(mContext, GoodsDetailActivity.class);
        intent.putExtra("id", commendList.get(position).getId() + "");
        intent.putExtra("commendId", commendList.get(position).getProductCategoryId() + "");
        intent.putExtra("sellerId", commendList.get(position).getSellerId());
        mContext.startActivity(intent);
    }

    public void setXBanner() {
        //轮播图
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

    public void jumpToSearch() {
        mContext.startActivity(new Intent(mContext, UserSearchActivity.class));
    }

    public void getNewRecommend(final int newGoodsIndex) {
        //新品推荐
        Map map = MapUtil.getInstance().addParms("pageNum", newGoodsIndex + "").addParms("saleDesc", "1").addParms("newStatus","1").build();
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("新品推荐：" + result);
                HotSaleBean hotSaleBean = JSON.parseObject(result, new TypeReference<HotSaleBean>() {
                }.getType());
                if (hotSaleBean != null) {
                    if (newGoodsIndex == 1) {
                        commendList.clear();
                    }
                    commendList.addAll(hotSaleBean.getData());
                    if (commendAdapter == null) {
                        commendAdapter = new CommendAdapter(mContext, commendList, R.layout.rv_commend);
                        if (getView() != null) {
                            getView().loadCommend(commendAdapter);
                        }
                    } else {
                        commendAdapter.notifyDataSetChanged();
                        getView().refreshSuccess();
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                if (getView() != null) {
                    getView().refreshSuccess();
                }
            }
        }));
    }

    public void commendClick() {
        commendAdapter.setViewTwoOnClickListener(new MyRecyclerAdapter.ViewTwoOnClickListener() {
            @Override
            public void ViewTwoOnClick(View view1, View view2, final int position) {
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToGoodsDetail(position);
                    }
                });

                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToShop(position);
                    }
                });
            }
        });
    }

    public void initGoods(final String time) {
        Map map = MapUtil.getInstance().addParms("shiduan", time).addParms("page", 1).build();
        final Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.QUERYXSQGSPLIST, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("限时商品" + result);
                flashSaleGoodsBeans = JSON.parseArray(result, FlashSaleGoodsBean.class);
                if (flashSaleGoodsBeans.size() != 0) {
                    if (flashSaleAdapter == null) {
                        flashSaleAdapter = new FlashSaleAdapter(mContext, flashSaleGoodsBeans, R.layout.rv_hot);
                        if (getView() != null) {
                            getView().loadAdapter(flashSaleAdapter);
                        }
                    } else {
                        flashSaleAdapter.notifyDataSetChanged();
                    }

                    flashSaleAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            ARouter.getInstance()
                                    .build("/module_user_store/GoodsDetailActivity")
                                    .withString("id", flashSaleGoodsBeans.get(position).getId() + "")
                                    .withString("sellerId", flashSaleGoodsBeans.get(position).getSeller_id() + "")
                                    .withString("commendId", flashSaleGoodsBeans.get(position).getProduct_category_id() + "")
                                    .navigation();
                        }
                    });

                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("限时失败" + errorCode + "-----------" + errorMsg);
            }
        }));
    }
}
