package com.example.user_home;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bean.BannerBean;
import com.example.mvp.BaseFragment;
import com.example.user_home.adapter.CommendAdapter;
import com.example.user_home.adapter.FlashSaleAdapter;
import com.example.user_home.adapter.NavBarAdapter;
import com.example.user_home.adapter.SaleHotAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.example.utils.RvItemDecoration;
import com.example.utils.SpaceItemDecoration;
import com.example.view.CustomHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:商城首页
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView, NestedScrollView.OnScrollChangeListener {
    @BindView(R2.id.user_home_back)
    ImageView userHomeBack;
    @BindView(R2.id.user_home_search)
    TextView userHomeSearch;
    @BindView(R2.id.user_home_msg_img)
    ImageView userHomeMsgImg;
    @BindView(R2.id.user_home_msg)
    LinearLayout userHomeMsg;
    @BindView(R2.id.user_home_xbanner)
    XBanner userHomeXbanner;
    @BindView(R2.id.user_home_rv_navbar)
    RecyclerView userHomeRvNavbar;
    @BindView(R2.id.user_home_more)
    LinearLayout userHomeMore;
    @BindView(R2.id.user_home_rv_hot)
    RecyclerView userHomeRvHot;
    @BindView(R2.id.user_home_rv_goods)
    RecyclerView userHomeRvGoods;
    @BindView(R2.id.user_home_refresh)
    SmartRefreshLayout userHomeRefresh;
    @BindView(R2.id.user_home_nescroll)
    NestedScrollView userHomeNescroll;
    @BindView(R2.id.user_home_gotop)
    ImageView mGoTop;
    @BindView(R2.id.user_home_see_more)
    TextView userHomeSeeMore;
    @BindView(R2.id.user_home_time)
    TextView userHomeTime;
    @BindView(R2.id.user_home_count_down)
    TextView userHomeCountDown;
    @BindView(R2.id.user_home_flash_sale_rec)
    RecyclerView userHomeFlashSaleRec;

    private int newGoodsIndex = 1;
    private int hotSaleIndex = 1;
    private CountDownTimer countDownTimer;
    private String format1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_home;
    }

    @Override
    public void initData() {
        time();
        //导航栏
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        userHomeRvNavbar.setLayoutManager(gridLayoutManager);
        //热销产品
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        userHomeRvHot.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.dp_15), (int) getResources().getDimension(R.dimen.dp_5), 0, 0));
        userHomeRvHot.setLayoutManager(linearLayoutManager);
        //限时商品
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        userHomeFlashSaleRec.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.dp_15), (int) getResources().getDimension(R.dimen.dp_5), 0, 0));
        userHomeFlashSaleRec.setLayoutManager(linearLayoutManager1);

        //新品推荐
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        userHomeRvGoods.setLayoutManager(staggeredGridLayoutManager);
        userHomeRvGoods.addItemDecoration(new RvItemDecoration((int) getContext().getResources().getDimension(R.dimen.dp_12), (int) getContext().getResources().getDimension(R.dimen.dp_12)));
        presenter.loadData(hotSaleIndex);
        presenter.setXBanner();
        presenter.getNewRecommend(newGoodsIndex);
        presenter.initGoods(format1);
        //下拉刷新样式
        CustomHeader customHeader = new CustomHeader(getActivity());
        customHeader.setPrimaryColors(getResources().getColor(R.color.colorTransparency));
        userHomeRefresh.setRefreshHeader(customHeader);

        userHomeNescroll.setOnScrollChangeListener(this);
    }

    @Override
    public void initClick() {
//        userHomeBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new EventBusBean2(CommonResource.USER_BACK, 0));
//            }
//        });

        userHomeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToSearch();
            }
        });

        userHomeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/mine/messagecenter").navigation();
            }
        });

        //监听广告 item 的单击事件
        userHomeXbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
//                Toast.makeText(getContext(), "点击了第" + position + "图片", Toast.LENGTH_SHORT).show();
            }
        });

        //设置上拉刷新下拉加载
        userHomeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                newGoodsIndex = 1;
                presenter.getNewRecommend(newGoodsIndex);
            }
        });
        userHomeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                newGoodsIndex++;
                presenter.getNewRecommend(newGoodsIndex);
            }
        });

        userHomeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/module_user_store/typeDetail").withBoolean("hotSale", true).navigation();
            }
        });

        mGoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userHomeNescroll.fullScroll(NestedScrollView.FOCUS_UP);
            }
        });

        userHomeSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/module_user_store/FlashSaleActivity").navigation();
            }
        });
    }

    @Override
    public void loadNavBar(NavBarAdapter adapter) {
        userHomeRvNavbar.setAdapter(adapter);
    }

    @Override
    public void loadSaleHot(SaleHotAdapter adapter) {
        userHomeRvHot.setAdapter(adapter);
    }

    @Override
    public void loadCommend(CommendAdapter adapter) {
        userHomeRefresh.finishLoadMore();
        userHomeRvGoods.setAdapter(adapter);
        presenter.commendClick();
    }

    @Override
    public void loadAdapter(FlashSaleAdapter flashSaleAdapter) {
        userHomeFlashSaleRec.setAdapter(flashSaleAdapter);
    }

    @Override
    public void loadBanner(final List<BannerBean.RecordsBean> beanList) {
        userHomeXbanner.setBannerData(beanList);
        userHomeXbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                RequestOptions requestOptions = RequestOptions.centerCropTransform();
                Glide.with(getContext()).load(((BannerBean.RecordsBean) model).getXBannerUrl()).apply(requestOptions).transform(new RoundedCorners((int) getContext().getResources().getDimension(R.dimen.dp_10))).into((ImageView) view);
            }
        });

        //banner切换image也切换
//        userHomeXbanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                if (!getActivity().isFinishing()) {
//                    Glide.with(getContext()).load(beanList.get(i).getPicBackUrl()).into(userHomeTopImg);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
    }

    @Override
    public void refreshSuccess() {
        userHomeRefresh.finishLoadMore();
        userHomeRefresh.finishRefresh();
    }

    @Override
    public HomeView createView() {
        return this;
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(getContext());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //隐藏
            userHomeXbanner.stopAutoPlay();
            countDownTimer.onFinish();
        } else {
            //显示
            userHomeXbanner.startAutoPlay();
            time();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e("HomeFragment" + "不可见");
        userHomeXbanner.stopAutoPlay();
        countDownTimer.onFinish();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("HomeFragment" + "可见");
        userHomeXbanner.startAutoPlay();
        time();
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int[] ints = new int[2];
        userHomeRvGoods.getLocationOnScreen(ints);
        int y = ints[1];
        if (y <= 0) {
            mGoTop.setVisibility(View.VISIBLE);
        } else {
            mGoTop.setVisibility(View.GONE);
        }
    }

    private void time() {
        //获取当前时间
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH");// yyyy年MM月dd日 HH:mm:ss
        Date date1 = new Date(System.currentTimeMillis());
        format1 = simpleDateFormat1.format(date1);
        userHomeTime.setText(format1 + "点场");
        //获取之后的一个小时
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");// yyyy年MM月dd日 HH:mm:ss
        Date date = new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000);
        String format = simpleDateFormat.format(date);
        long afterTime = getTimeStamp(format + ":00:00", "yyyy-MM-dd HH:mm:ss");
        LogUtil.e("时间" + format + "-----------" + afterTime + "--------------" + (afterTime - System.currentTimeMillis()));
        //第一个参数表示总时间，第二个参数表示间隔时间。
        countDownTimer = new CountDownTimer(afterTime - System.currentTimeMillis(), 1000) {//第一个参数表示总时间，第二个参数表示间隔时间。
            @Override
            public void onTick(long millisUntilFinished) {
                SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
                String dateString = formatter.format(millisUntilFinished);
                userHomeCountDown.setText("00:" + dateString);

//                LogUtil.e("限时时间" + userHomeCountDown.getText().toString());
                if (userHomeCountDown.getText().toString().contains("00:00:00")) {
                    time();
                    presenter.initGoods(format1);
                }
            }

            @Override
            public void onFinish() {
                LogUtil.e("结束");
            }
        }.start();

    }

    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     * @return
     */
    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
