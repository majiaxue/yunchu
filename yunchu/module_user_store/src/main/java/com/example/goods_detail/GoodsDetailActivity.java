package com.example.goods_detail;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.adapter.GoodsImageAdapter;
import com.example.bean.AssessBean;
import com.example.bean.BannerBean;
import com.example.bean.UserGoodsDetail;
import com.example.goods_detail.adapter.GoodsAssessAdapter;
import com.example.goods_detail.adapter.GoodsCouponAdapter;
import com.example.mvp.BaseActivity;
import com.example.user_home.adapter.CommendAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.PopUtils;
import com.example.utils.RvItemDecoration;
import com.example.utils.SPUtil;
import com.example.utils.SpaceItemDecoration;
import com.example.utils.TxtUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 多用户商城商品详情
 */
@Route(path = "/module_user_store/GoodsDetailActivity")
public class GoodsDetailActivity extends BaseActivity<GoodsDetailView, GoodsDetailPresenter> implements GoodsDetailView, NestedScrollView.OnScrollChangeListener {
    @BindView(R2.id.goods_detail_xbanner)
    XBanner goodsDetailXbanner;
    @BindView(R2.id.goods_detail_name)
    TextView goodsDetailName;
    @BindView(R2.id.goods_detail_price)
    TextView goodsDetailPrice;
    @BindView(R2.id.goods_detail_attention_img)
    ImageView goodsDetailAttentionImg;
    @BindView(R2.id.goods_detail_attention)
    LinearLayout goodsDetailAttention;
    @BindView(R2.id.goods_detail_rv_coupon)
    RecyclerView goodsDetailRvCoupon;
    @BindView(R2.id.goods_detail_lingquan)
    RelativeLayout goodsDetailLingquan;
    @BindView(R2.id.goods_detail_ensure)
    LinearLayout goodsDetailEnsure;
    @BindView(R2.id.goods_detail_choose_goods)
    RelativeLayout goodsDetailChooseGoods;
    @BindView(R2.id.goods_detail_parms)
    RelativeLayout goodsDetailParms;
    @BindView(R2.id.goods_detail_title_assess)
    TextView goodsDetailTitleAssess;
    @BindView(R2.id.goods_detail_assess_count)
    TextView goodsDetailAssessCount;
    @BindView(R2.id.goods_detail_see_all)
    LinearLayout goodsDetailSeeAll;
    @BindView(R2.id.goods_detail_rv_assess)
    RecyclerView goodsDetailRvAssess;
    @BindView(R2.id.goods_detail_shop_img)
    ImageView goodsDetailShopImg;
    @BindView(R2.id.goods_detail_shop_name)
    TextView goodsDetailShopName;
    @BindView(R2.id.goods_detail_shop_call)
    TextView goodsDetailShopCall;
    @BindView(R2.id.goods_detail_shop_attention)
    TextView goodsDetailShopAttention;
    @BindView(R2.id.goods_detail_shop_goin)
    TextView goodsDetailShopGoin;
    @BindView(R2.id.goods_detail_title_detail)
    TextView goodsDetailTitleDetail;
    @BindView(R2.id.goods_detail_title_commend)
    TextView goodsDetailTitleCommend;
    @BindView(R2.id.goods_detail_rv_commend)
    RecyclerView goodsDetailRvCommend;
    @BindView(R2.id.goods_detail_back)
    ImageView goodsDetailBack;
    @BindView(R2.id.goods_detail_share)
    ImageView goodsDetailShare;
    @BindView(R2.id.goods_detail_touming)
    RelativeLayout goodsDetailTouming;
    @BindView(R2.id.goods_detail_back2)
    ImageView goodsDetailBack2;
    @BindView(R2.id.goods_detail_navbar_baby)
    TextView goodsDetailNavbarBaby;
    @BindView(R2.id.goods_detail_navbar_assess)
    TextView goodsDetailNavbarAssess;
    @BindView(R2.id.goods_detail_navbar_detail)
    TextView goodsDetailNavbarDetail;
    @BindView(R2.id.goods_detail_navbar_commend)
    TextView goodsDetailNavbarCommend;
    @BindView(R2.id.goods_detail_navbar)
    RelativeLayout goodsDetailNavbar;
    @BindView(R2.id.goods_detail_bottom_shop)
    LinearLayout goodsDetailBottomShop;
    @BindView(R2.id.goods_detail_bottom_serve)
    LinearLayout goodsDetailBottomServe;
    @BindView(R2.id.goods_detail_bottom_cart)
    LinearLayout goodsDetailBottomCart;
    @BindView(R2.id.goods_detail_add_cart)
    TextView goodsDetailAddCart;
    @BindView(R2.id.goods_detail_buy)
    TextView goodsDetailBuy;
    @BindView(R2.id.goods_detail_rv_img)
    RecyclerView goodsDetailRvImg;
    @BindView(R2.id.goods_detail_scroll)
    NestedScrollView goodsDetailScroll;
    @BindView(R2.id.goods_detail_temp1)
    TextView mTxt;
    @BindView(R2.id.goods_detail_total_specs)
    TextView mTotalSpecs;
    @BindView(R2.id.goods_detail_webview)
    WebView mWebView;
    @BindView(R2.id.goods_detail_not_opened)
    LinearLayout goodsDetailNotOpened;
    @BindView(R2.id.goods_detail_bottom_serve1)
    LinearLayout goodsDetailBottomServe1;
    @BindView(R2.id.goods_detail_bottom_go_opened)
    TextView goodsDetailBottomGoOpened;
    @BindView(R2.id.goods_detail_have_opened)
    LinearLayout goodsDetailHaveOpened;

    @Autowired(name = "id")
    String id;
    @Autowired(name = "sellerId")
    String sellerId;
    @Autowired(name = "commendId")
    String commendId;
    @Autowired(name = "from")
    String from;
    @Autowired(name = "type")
    int type;


    private List<BannerBean.RecordsBean> images = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        mWebView.getSettings().setJavaScriptEnabled(true);

        if (1 == type) {
            goodsDetailNotOpened.setVisibility(View.GONE);
            goodsDetailHaveOpened.setVisibility(View.VISIBLE);
            goodsDetailLingquan.setVisibility(View.GONE);
        } else {
            goodsDetailNotOpened.setVisibility(View.VISIBLE);
            goodsDetailHaveOpened.setVisibility(View.GONE);
            goodsDetailLingquan.setVisibility(View.GONE);
            //优惠券
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            goodsDetailRvCoupon.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.dp_12), 0, 0, 0));
            goodsDetailRvCoupon.setLayoutManager(linearLayoutManager);
        }

        //图片
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        goodsDetailRvImg.addItemDecoration(new SpaceItemDecoration(0, (int) getResources().getDimension(R.dimen.dp_15), 0, 0));
        goodsDetailRvImg.setLayoutManager(linearLayoutManager1);
        //评论
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        goodsDetailRvAssess.setLayoutManager(linearLayoutManager2);
        //推荐
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        goodsDetailRvCommend.setLayoutManager(staggeredGridLayoutManager);
        goodsDetailRvCommend.addItemDecoration(new RvItemDecoration((int) getResources().getDimension(R.dimen.dp_12), (int) getResources().getDimension(R.dimen.dp_12)));
        //轮播图
        goodsDetailXbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                SimpleDraweeView bannerImage = view.findViewById(R.id.banner_image);
//                bannerImage.setImageResource((int) images.get(position));
                bannerImage.setImageURI(Uri.parse(images.get(position).getXBannerUrl()));
//                Glide.with(GoodsDetailActivity.this).load(((BannerBean.RecordsBean) model).getXBannerUrl()).apply(RequestOptions.centerCropTransform()).into((ImageView) view);
            }
        });

        goodsDetailScroll.setOnScrollChangeListener(this);
        if (id == null) {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            sellerId = intent.getStringExtra("sellerId");
            commendId = intent.getStringExtra("commendId");
        }
        presenter.loadData(id, type);
       // presenter.loadCoupon(id, sellerId);
        presenter.loadAssess(id);
        if (1 == type) {
            goodsDetailRvCommend.setVisibility(View.GONE);
        } else {
            goodsDetailRvCommend.setVisibility(View.VISIBLE);
            presenter.loadCommend(commendId);
        }
    }

    @Override
    public void initClick() {
        goodsDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        goodsDetailBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //关注商品
        goodsDetailAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    presenter.toAttention();
                } else {
                    PopUtils.isLogin(GoodsDetailActivity.this);
                }
            }
        });
        //领优惠券
        goodsDetailLingquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.lingquan();
            }
        });

        //正品保障
        goodsDetailEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ensure();
            }
        });
        //详细参数
        goodsDetailParms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.detailParms();
            }
        });
        //选择商品弹窗
        goodsDetailChooseGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseGoodsPop(type);
            }
        });
        //跳转评论
        goodsDetailSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToAssess(id);
            }
        });
        //进入店铺
        goodsDetailShopGoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToShop();
            }
        });
        //开通超级会员  === 购买
        goodsDetailBottomGoOpened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToManager(type);
            }
        });

        //购买
        goodsDetailBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseOrJump(type);
            }
        });
        //加入购物车
        goodsDetailAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseOrAddCart(type);
            }
        });
        //进入店铺
        goodsDetailBottomShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToShop();
            }
        });

        goodsDetailShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToShop();
            }
        });

        goodsDetailShopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToShop();
            }
        });

        //跳转购入车
        goodsDetailBottomCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToCart(from);
            }
        });
//        String detailHtml = bean.getDetailHtml();
//        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
//        //替换img属性
//        buy2upWebview.loadDataWithBaseURL(null,varjs + detailHtml, "text/html", "UTF-8",null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        goodsDetailNavbarBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsDetailScroll.fullScroll(NestedScrollView.FOCUS_UP);
            }
        });

        goodsDetailNavbarAssess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsDetailScroll.scrollTo(0, goodsDetailTitleAssess.getTop() - goodsDetailNavbar.getHeight());
            }
        });

        goodsDetailNavbarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsDetailScroll.scrollTo(0, goodsDetailTitleDetail.getTop() - goodsDetailNavbar.getHeight());
            }
        });

        goodsDetailNavbarCommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsDetailScroll.scrollTo(0, goodsDetailTitleCommend.getTop() - goodsDetailNavbar.getHeight());
            }
        });

        goodsDetailBottomServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.callServe();
            }
        });

        goodsDetailBottomServe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.callServe();
            }
        });

        goodsDetailXbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                presenter.seeBigPicture(position);
            }
        });

    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY <= 300) {
            if (goodsDetailTouming.getVisibility() != View.VISIBLE) {
                goodsDetailTouming.setVisibility(View.VISIBLE);
            }
            goodsDetailTouming.setAlpha(((300 - scrollY) * 1.0f / 300));
        } else if (scrollY == 301) {
            goodsDetailTouming.setVisibility(View.GONE);
        }

        int[] int_baby = new int[2];
        goodsDetailName.getLocationOnScreen(int_baby);
        int y_baby = int_baby[1];
        if (y_baby - 400 <= goodsDetailNavbar.getHeight() && y_baby - goodsDetailNavbar.getHeight() >= 0) {
            if (goodsDetailNavbar.getVisibility() != View.VISIBLE) {
                goodsDetailNavbar.setVisibility(View.VISIBLE);
            }
//            goodsDetailNavbar.setAlpha(-(y_baby - goodsDetailNavbar.getHeight() - 400) * 1.0f / 400);
        } else if (y_baby - 400 >= goodsDetailNavbar.getHeight()) {
            goodsDetailNavbar.setVisibility(View.GONE);
        }
        int[] int_assess = new int[2];
        goodsDetailTitleAssess.getLocationOnScreen(int_assess);
        int y_assess = int_assess[1];

        int[] int_detail = new int[2];
        goodsDetailTitleDetail.getLocationOnScreen(int_detail);
        int y_detail = int_detail[1];

        int[] int_commend = new int[2];
        goodsDetailTitleCommend.getLocationOnScreen(int_commend);
        int y_commend = int_commend[1];

        if (y_baby - 400 - goodsDetailNavbar.getHeight() <= 0 && y_assess - goodsDetailNavbar.getHeight() > 0) {
            changeType(0);
        } else if (y_assess - goodsDetailNavbar.getHeight() <= 0 && y_detail - goodsDetailNavbar.getHeight() > 0) {
            changeType(1);
        } else if (y_detail - goodsDetailNavbar.getHeight() <= 0 && y_commend - goodsDetailNavbar.getHeight() > 0) {
            changeType(2);
        } else if (y_commend - goodsDetailNavbar.getHeight() <= 0) {
            changeType(3);
        }
    }

    private void changeType(int position) {
        goodsDetailNavbarBaby.setTextColor(Color.parseColor(position == 0 ? "#fd3c15" : "#333333"));
        goodsDetailNavbarAssess.setTextColor(Color.parseColor(position == 1 ? "#fd3c15" : "#333333"));
        goodsDetailNavbarDetail.setTextColor(Color.parseColor(position == 2 ? "#fd3c15" : "#333333"));
        goodsDetailNavbarCommend.setTextColor(Color.parseColor(position == 3 ? "#fd3c15" : "#333333"));
    }

    @Override
    public void loadUI(UserGoodsDetail data, int size) {
        goodsDetailName.setText(data.getName());
        goodsDetailPrice.setText(data.getPrice() + "");
        mTotalSpecs.setText("共" + size + "种" + data.getXsProductAttributes().get(0).getName() + "可选");
        Glide.with(this).load(data.getSellerLogo()).into(goodsDetailShopImg);
        goodsDetailShopName.setText(data.getSellerName());
        goodsDetailShopAttention.setText("店铺关注  " + TxtUtil.parse(data.getSellerFavoriteNum()));

        StringBuffer sb = new StringBuffer();
        sb.append("选择");
        for (int i = 0; i < data.getXsProductAttributes().size(); i++) {
            sb.append(data.getXsProductAttributes().get(i).getName() + "  ");
        }
        mTxt.setText(sb);
        if (data.getIsfavorite() == 1) {
            attention();
        } else {
            cancelAttention();
        }

        String detailHtml = data.getDetailHtml();
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        //替换img属性
        mWebView.loadData(varjs + detailHtml, "text/html", "UTF-8");
    }

    @Override
    public void loadCoupon(GoodsCouponAdapter adapter) {
        goodsDetailRvCoupon.setAdapter(adapter);
    }

    @Override
    public void loadImage(GoodsImageAdapter adapter) {
        goodsDetailRvImg.setAdapter(adapter);
    }

    @Override
    public void loadAssess(GoodsAssessAdapter adapter, AssessBean assessBean) {
        goodsDetailRvAssess.setAdapter(adapter);
        goodsDetailAssessCount.setText("评价(" + assessBean.getTotal() + ")");
    }

    @Override
    public void loadCommend(CommendAdapter adapter) {
        goodsDetailRvCommend.setAdapter(adapter);
    }

    @Override
    public void loadBanner(List<BannerBean.RecordsBean> list) {
        this.images = list;
        goodsDetailXbanner.setBannerData(R.layout.image_fresco, list);
    }

    @Override
    public void attention() {
        goodsDetailAttentionImg.setImageResource(R.drawable.icon_shoucang2);
    }

    @Override
    public void cancelAttention() {
        goodsDetailAttentionImg.setImageResource(R.drawable.icon_shoucang1);
    }

    @Override
    public void yixuanze(String attr) {
        mTxt.setText(attr);
    }

    @Override
    public void weixuanze(String str) {
        mTxt.setText("选择" + str);
    }

    @Override
    public GoodsDetailView createView() {
        return this;
    }

    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter(this);
    }

}
