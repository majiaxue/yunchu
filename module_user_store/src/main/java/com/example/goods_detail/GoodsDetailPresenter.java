package com.example.goods_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.adapter.GoodsImageAdapter;
import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.PopFlowLayoutAdapter;
import com.example.adapter.SecondFlowAdapter;
import com.example.assess.AssessActivity;
import com.example.bean.AddCartBean;
import com.example.bean.AssessBean;
import com.example.bean.BannerBean;
import com.example.bean.ChooseInsideBean;
import com.example.bean.ContactUsBean;
import com.example.bean.HotSaleBean;
import com.example.bean.OrderConfirmBean;
import com.example.bean.ParmsBean;
import com.example.bean.UserCouponBean;
import com.example.bean.UserGoodsDetail;
import com.example.common.CommonResource;
import com.example.entity.EventBusBean;
import com.example.goods_detail.adapter.GoodsAssessAdapter;
import com.example.goods_detail.adapter.GoodsCouponAdapter;
import com.example.goods_detail.adapter.PopLingQuanAdapter;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.order_confirm.OrderConfirmActivity;
import com.example.shop_home.ShopHomeActivity;
import com.example.user_home.adapter.CommendAdapter;
import com.example.user_store.R;
import com.example.user_store.UserActivity;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.OnAdapterListener;
import com.example.utils.PopUtil;
import com.example.utils.PopUtils;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.example.utils.TxtUtil;
import com.example.view.flowLayout.FlowLayout;
import com.example.view.flowLayout.TagFlowLayout;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailView> {
    //选择商品列表
    private List<UserGoodsDetail.SkuStockListBean> dataList;
    //流式布局--颜色
    private TagFlowLayout flow1;
    //流式布局--尺码
    private TagFlowLayout flow2;

    private TagFlowLayout flow3;
    //颜色选中下标
    private int sp1Position = -1;
    //尺码选中下标
    private int sp2Position = -1;

    private int sp3Position = -1;

    private long quantity = 1;

    private boolean isChoose = false;

    //尺码列表
//    private List<UserGoodsDetail.StoInfoBean.RecordsBean.ListBean> sp2List = new ArrayList<>();
    //缩略图
    private List imgList = new ArrayList();
    private List<ChooseInsideBean> sp1List = new ArrayList<>();
    private List<ChooseInsideBean> sp2List = new ArrayList<>();
    private List<ChooseInsideBean> sp3List = new ArrayList<>();

    private List<Integer> canotClick1 = new ArrayList<>();
    private List<Integer> canotClick2 = new ArrayList<>();
    private List<Integer> canotClick3 = new ArrayList<>();

    //为你推荐
    List<HotSaleBean.DataBean> commendList = new ArrayList<>();

    private UserGoodsDetail userGoodsDetail;
    private List<UserCouponBean> couponBeanList;
    private PopFlowLayoutAdapter sp1Adapter;
    private SecondFlowAdapter sp2Adapter;
    private SecondFlowAdapter sp3Adapter;

    private int attrSize = 1;
    private List<BannerBean.RecordsBean> bannerList;


    public GoodsDetailPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(String id, final int type) {
        LogUtil.e("id" + id);
        ProcessDialogUtil.showProcessDialog(mContext);
//        WaitDialog.show((AppCompatActivity) mContext, null);

        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getHeadWithout(CommonResource.GETGOODSDETAIL + "/" + id, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("商品详情：" + result);
                userGoodsDetail = JSON.parseObject(result, new TypeReference<UserGoodsDetail>() {
                }.getType());
                LogUtil.e("++++++++详情userGoodsDetail++++"+userGoodsDetail);

                dataList = userGoodsDetail.getSkuStockList();

                //规格缩略图
                String imgTemp = "";
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getSp1() != null && !imgTemp.equals(dataList.get(i).getSp1())) {
                        imgList.add(dataList.get(i).getPic());
                        imgTemp = dataList.get(i).getSp1();
                        if (dataList.get(i).getStock() > 0) {
                            sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock(), true,dataList.get(i).getVipPrice()));
                        } else {
                            sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock(), false,dataList.get(i).getVipPrice()));
                        }
                    }
                }

                if (dataList != null && dataList.size() > 0) {
                    if (dataList.get(0).getSp2() == null) {
                        attrSize = 1;
                    } else if (dataList.get(0).getSp3() == null) {
                        attrSize = 2;
                    } else {
                        attrSize = 3;
                    }
                }

                if (dataList.size() > 0 && attrSize > 1) {
                    for (int i = 0; i < dataList.size(); i++) {
                        boolean isHas = false;
                        if (dataList.get(i).getSp1().equals(sp1List.get(0).getContent())) {
                            for (int j = 0; j < sp2List.size(); j++) {
                                if (sp2List.get(j).getContent().equals(dataList.get(i).getSp2())) {
                                    isHas = true;
                                }
                            }
                            if (!isHas) {
                                sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            }
                        }
                    }
//                    String[] split = userGoodsDetail.getXsProductAttributes().get(1).getInputList().split(",");
//                    for (int i = 0; i < split.length; i++) {
//                        sp2List.add(new ChooseInsideBean(split[i]));
//                    }
                }
                if (dataList.size() > 0 && attrSize > 2) {
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).getSp1().equals(sp1List.get(0).getContent()) && dataList.get(i).getSp2().equals(sp2List.get(0).getContent())) {
                            sp3List.add(new ChooseInsideBean(dataList.get(i).getSp3(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                        }
                    }

//                    String[] split = userGoodsDetail.getXsProductAttributes().get(2).getInputList().split(",");
//                    for (int i = 0; i < split.length; i++) {
//                        sp3List.add(new ChooseInsideBean(split[i]));
//                    }
                }
                GoodsImageAdapter goodsImageAdapter = new GoodsImageAdapter(mContext, imgList, R.layout.rv_goods_choose_image);
                goodsImageAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        chooseGoodsPop(type);
                    }
                });

                if (getView() != null) {
                    getView().loadImage(goodsImageAdapter);
                    getView().loadUI(userGoodsDetail, sp1List.size());
                }

                //轮播图
                String albumPics = userGoodsDetail.getAlbumPics();
                bannerList = new ArrayList<>();
                String[] split = albumPics.split(",");
                for (int i = 0; i < split.length; i++) {
                    bannerList.add(new BannerBean.RecordsBean(split[i]));
                }
                if (getView() != null) {
                    getView().loadBanner(bannerList);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("商品详情：" + errorMsg);
            }
        }));

    }

    public void loadAssess(final String id) {
        Map map = MapUtil.getInstance().addParms("current", 1).addParms("productId", id).build();
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).getData(CommonResource.GETUSERASSESS, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("评论：" + result);
                AssessBean assessBean = JSON.parseObject(result, AssessBean.class);
                LogUtil.e("++++++++++评论assessBean++++++++"+assessBean);
                GoodsAssessAdapter goodsAssessAdapter = new GoodsAssessAdapter(mContext, assessBean.getRecords(), R.layout.rv_goods_assess);
                goodsAssessAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        jumpToAssess(id);
                    }
                });
                if (getView() != null) {
                    getView().loadAssess(goodsAssessAdapter, assessBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("评论onError：" + errorCode);
            }
        }));
    }

    public void loadCommend(String keyWords) {
        Map map = MapUtil.getInstance().addParms("categoryId", keyWords).build();
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getHead(CommonResource.HOTNEWSEARCH, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                HotSaleBean hotSaleBean = JSON.parseObject(result, new TypeReference<HotSaleBean>() {
                }.getType());
                LogUtil.e("++++++++热销hotSaleBean++++++"+hotSaleBean);
                commendList.clear();
                commendList.addAll(hotSaleBean.getData());
                CommendAdapter commendAdapter = new CommendAdapter(mContext, commendList, R.layout.rv_commend);
                if (getView() != null) {
                    getView().loadCommend(commendAdapter);
                }
                commendAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        ARouter.getInstance()
                                .build("/module_user_store/GoodsDetailActivity")
                                .withString("id", commendList.get(position).getId() + "")
                                .withString("sellerId", commendList.get(position).getSellerId())
                                .withString("commendId", commendList.get(position).getProductCategoryId() + "")
                                .navigation();
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    public void toAttention() {
        Map map = MapUtil.getInstance().addParms("productId", userGoodsDetail.getId()).addParms("userCode", SPUtil.getUserCode()).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getData(CommonResource.SCSP, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("收藏成功：" + result);
                if ("true".equals(result)) {
                    getView().attention();
                } else {
                    getView().cancelAttention();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("收藏失败：" + errorMsg);
            }
        }));
    }

    public void lingquan() {
        if (SPUtil.getToken() != null && !"".equals(SPUtil.getToken())) {
            if (couponBeanList != null && couponBeanList.size() > 0) {
                PopUtil.lingquanPop(mContext, couponBeanList, new OnAdapterListener() {
                    @Override
                    public void setOnAdapterListener(final PopupWindow popupWindow, final PopLingQuanAdapter adapter) {
                        adapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                            @Override
                            public void ViewOnClick(View view, final int index) {
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Map map = MapUtil.getInstance().addParms("couponID", couponBeanList.get(index).getId()).addParms("userID", SPUtil.getUserCode()).addParms("userNickName", SPUtil.getStringValue(CommonResource.USER_NAME)).build();
                                        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9003).getData(CommonResource.LINGCOUPON, map);
                                        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                                            @Override
                                            public void onSuccess(String result, String msg) {
                                                LogUtil.e("领取：" + result);
                                                Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
                                                couponBeanList.get(index).setHas(true);
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError(String errorCode, String errorMsg) {
                                                LogUtil.e(errorCode + "------------" + errorMsg);
                                                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                            }
                                        }));
                                    }
                                });
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(mContext, "无可领优惠券", Toast.LENGTH_SHORT).show();
            }
        } else {
            PopUtils.isLogin(mContext);
        }
    }

    public void ensure() {
        PopUtil.ensurePop(mContext);
    }

    //产品参数
    public void detailParms() {
        List<UserGoodsDetail.XsProductAttributesBean> xsProductAttributes = userGoodsDetail.getXsProductAttributes();
        List<ParmsBean> list=new ArrayList<>();
        for (int i = 0; i < xsProductAttributes.size(); i++) {
            list.add(new ParmsBean(xsProductAttributes.get(i).getName(),xsProductAttributes.get(i).getInputList()));
        }
        PopUtil.parmsPop(mContext, list);

    }

    public void chooseGoodsPop(final int flag) {
        try {

            if (userGoodsDetail != null && userGoodsDetail.getXsProductAttributes().size() > 0 && dataList.size() > 0) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.pop_choose_goods, null);
                final ImageView img = view.findViewById(R.id.pop_choose_goods_img);
                final TextView price = view.findViewById(R.id.pop_choose_goods_price);
                final TextView type = view.findViewById(R.id.pop_choose_goods_type);
                ImageView cancel = view.findViewById(R.id.pop_choose_goods_cancel);
                flow1 = view.findViewById(R.id.pop_choose_goods_flow1);
                flow2 = view.findViewById(R.id.pop_choose_goods_flow2);
                flow3 = view.findViewById(R.id.pop_choose_goods_flow3);
                TextView minus = view.findViewById(R.id.pop_choose_goods_minus);
                TextView add = view.findViewById(R.id.pop_choose_goods_add);
                final TextView count = view.findViewById(R.id.pop_choose_goods_count);
                TextView shopCart = view.findViewById(R.id.pop_choose_goods_cart);
                TextView buy = view.findViewById(R.id.pop_choose_goods_buy);
                TextView title1 = view.findViewById(R.id.pop_choose_goods_title1);
                TextView title2 = view.findViewById(R.id.pop_choose_goods_title2);
                TextView title3 = view.findViewById(R.id.pop_choose_goods_title3);
                if (flag == 1) {
                    minus.setVisibility(View.GONE);
                    add.setVisibility(View.GONE);
                    shopCart.setVisibility(View.GONE);
                } else {
                    minus.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    shopCart.setVisibility(View.VISIBLE);
                }
//            RecyclerView rv = view.findViewById(R.id.pop_choose_goods_rv);

                if (attrSize == 1) {
                    title2.setVisibility(View.GONE);
                    title3.setVisibility(View.GONE);
                    flow2.setVisibility(View.GONE);
                    flow3.setVisibility(View.GONE);
                    title1.setText(userGoodsDetail.getXsProductAttributes().get(0).getName());
                } else if (attrSize == 2) {
                    title3.setVisibility(View.GONE);
                    flow3.setVisibility(View.GONE);
                    title2.setText(userGoodsDetail.getXsProductAttributes().get(1).getName());
                    title1.setText(userGoodsDetail.getXsProductAttributes().get(0).getName());
                } else {
                    title1.setText(userGoodsDetail.getXsProductAttributes().get(0).getName());
                    title2.setText(userGoodsDetail.getXsProductAttributes().get(1).getName());
                    title3.setText(userGoodsDetail.getXsProductAttributes().get(2).getName());
                }

                sp1Adapter = new PopFlowLayoutAdapter(sp1List, mContext);

                for (int i = 0; i < sp1List.size(); i++) {
                    if (sp1List.get(i).getStock() <= 0) {
                        canotClick1.add(i);
                    }
                }

                flow1.setAdapter(sp1Adapter);
                if (canotClick1.size() > 0) {
                    flow1.setNoCheckList(canotClick1);
                }
                flow1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        boolean isCan = true;
                        for (int i = 0; i < canotClick1.size(); i++) {
                            if (canotClick1.get(i) == position) {
                                isCan = false;
                            }
                        }
                        if (isCan){
                            if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                                sp1Position = position;
                                LogUtil.e("这是图片---------"+sp1List.get(sp1Position).getPicUrl());
                                Glide.with(mContext).load(sp1List.get(sp1Position).getPicUrl()).into(img);
                                if (attrSize == 1) {
                                    price.setText("￥" + sp1List.get(sp1Position).getVipPrice());
                                    LogUtil.e("vip价----"+sp1List.get(sp1Position).getVipPrice());
                                    isChoose = true;
                                    type.setText("已选择：" + sp1List.get(sp1Position).getContent());
                                } else if (attrSize == 2) {
                                    if (sp2Position == -1) {
                                        initSizeList(1);
                                        type.setText("已选择：" + sp1List.get(sp1Position).getContent());
                                    } else {
                                        isChoose = true;
                                        initSizeList(1);
                                        price.setText("￥" + stock1(sp1List.get(sp1Position).getContent(), sp2Position).getVipPrice());
                                        type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent());
                                    }
                                } else {
                                    try {

                                        initThirdList(1);
                                        StringBuffer sb = new StringBuffer();
                                        sb.append("已选择：" + sp1List.get(sp1Position).getContent());
                                        if (sp2Position != -1) {
                                            sb.append("、" + sp2List.get(sp2Position).getContent());
                                        }
                                        if (sp3Position != -1) {
                                            sb.append("、" + sp3List.get(sp3Position).getContent());
                                        }
                                        if (sp2Position != -1 && sp3Position != -1) {
                                            isChoose = true;
                                            price.setText("￥" + stock23(sp3List.get(sp3Position).getContent(), sp2List.get(sp2Position).getContent(), sp1Position).getVipPrice());
                                        }
                                        type.setText(sb.toString());
                                    } catch (Exception e) {
                                        Toast.makeText(mContext, "商品属性异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else if (SPUtil.getStringValue(CommonResource.LEVELID).equals("1")){

                                sp1Position = position;
                                Glide.with(mContext).load(sp1List.get(sp1Position).getPicUrl()).into(img);
                                if (attrSize == 1) {
                                    price.setText("￥" + sp1List.get(sp1Position).getPrice());
                                    isChoose = true;
                                    type.setText("已选择：" + sp1List.get(sp1Position).getContent());
                                } else if (attrSize == 2) {
                                    if (sp2Position == -1) {
                                        initSizeList(1);
                                        type.setText("已选择：" + sp1List.get(sp1Position).getContent());
                                    } else {
                                        isChoose = true;
                                        initSizeList(1);
                                        price.setText("￥" + stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                                        type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent());
                                    }
                                } else {
                                    try {

                                        initThirdList(1);
                                        StringBuffer sb = new StringBuffer();
                                        sb.append("已选择：" + sp1List.get(sp1Position).getContent());
                                        if (sp2Position != -1) {
                                            sb.append("、" + sp2List.get(sp2Position).getContent());
                                        }
                                        if (sp3Position != -1) {
                                            sb.append("、" + sp3List.get(sp3Position).getContent());
                                        }
                                        if (sp2Position != -1 && sp3Position != -1) {
                                            isChoose = true;
                                            price.setText("￥" + stock23(sp3List.get(sp3Position).getContent(), sp2List.get(sp2Position).getContent(), sp1Position).getPrice());
                                        }
                                        type.setText(sb.toString());
                                    } catch (Exception e) {
                                        Toast.makeText(mContext, "商品属性异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        //1 普通会员  2vip


                        return false;
                    }
                });

                if (attrSize > 1) {

                    sp2Adapter = new SecondFlowAdapter(sp2List, mContext);
                    flow2.setAdapter(sp2Adapter);
                    flow2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            boolean isCan = true;
                            for (int i = 0; i < canotClick2.size(); i++) {
                                if (position == canotClick2.get(i)) {
                                    isCan = false;
                                }
                            }
                            if (!isCan) {
                                return false;
                            }

                            sp2Position = position;
                            if (attrSize == 2) {
                                if (sp1Position == -1) {
                                    initSizeList(2);
                                    type.setText("已选择：" + sp2List.get(sp2Position).getContent());
                                } else {
                                    if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                                        isChoose = true;
                                        initSizeList(2);
                                        price.setText("￥" + stock1(sp1List.get(sp1Position).getContent(), sp2Position).getVipPrice());
                                        type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent());
                                    }else if (SPUtil.getStringValue(CommonResource.LEVELID).equals("1")){
                                        isChoose = true;
                                        initSizeList(2);
                                        price.setText("￥" + stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                                        type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent());
                                    }

                                }
                            } else if (attrSize == 3) {
                                try {

                                    initThirdList(2);
                                    StringBuffer sb = new StringBuffer();
                                    sb.append("已选择：");
                                    if (sp1Position == -1) {
                                        sb.append(sp2List.get(sp2Position).getContent());
                                    } else {
                                        sb.append(sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent());
                                    }
                                    if (sp3Position != -1) {
                                        sb.append("、" + sp3List.get(sp3Position).getContent());
                                    }
                                    if (sp1Position != -1 && sp3Position != -1) {
                                        if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                                            isChoose = true;
                                            price.setText("￥" + stock13(sp1List.get(sp1Position).getContent(), sp3List.get(sp3Position).getContent(), sp2Position).getVipPrice());
                                        }else if (SPUtil.getStringValue(CommonResource.LEVELID).equals("1")){
                                            isChoose = true;
                                            price.setText("￥" + stock13(sp1List.get(sp1Position).getContent(), sp3List.get(sp3Position).getContent(), sp2Position).getPrice());
                                        }

                                    }
                                    type.setText(sb.toString());
                                } catch (Exception e) {
                                    Toast.makeText(mContext, "商品属性异常", Toast.LENGTH_SHORT).show();
                                }
                            }
                            return false;
                        }
                    });
                }
                if (attrSize > 2) {
                    sp3Adapter = new SecondFlowAdapter(sp3List, mContext);
                    flow3.setAdapter(sp3Adapter);
                    flow3.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            boolean isCan = true;
                            for (int i = 0; i < canotClick3.size(); i++) {
                                if (position == canotClick3.get(i)) {
                                    isCan = false;
                                }
                            }

                            if (!isCan) {
                                return false;
                            }
                            sp3Position = position;
                            initThirdList(3);
                            if (sp1Position != -1 && sp2Position == -1) {
                                type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp3List.get(sp3Position).getContent());
                            } else if (sp1Position == -1 && sp2Position != -1) {
                                type.setText("已选择：" + sp2List.get(sp2Position).getContent() + "、" + sp3List.get(sp3Position).getContent());
                            } else if (sp1Position != -1 && sp2Position != -1) {
                                isChoose = true;
                                type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent() + "、" + sp3List.get(sp3Position).getContent());
                                price.setText("￥" + stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position));
                            }

                            if (sp1Position != -1 && sp2Position != -1) {
                                isChoose = true;
                                sp3Position = position;
                                type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent() + "、" + sp3List.get(sp3Position).getContent());
                                if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                                    price.setText("￥" + sp3List.get(position).getVipPrice());
                                }else if (SPUtil.getStringValue(CommonResource.LEVELID).equals("1")){
                                    price.setText("￥" + sp3List.get(position).getPrice());
                                }

                            }
                            return false;
                        }
                    });
                }


                final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.dp_444), true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setAnimationStyle(R.style.pop_bottom_anim);
                popupWindow.showAtLocation(new View(mContext), Gravity.BOTTOM, 0, 0);

                final StringBuffer sb = new StringBuffer();
                sb.append("请选择");
                for (int i = 0; i < attrSize; i++) {
                    sb.append(userGoodsDetail.getXsProductAttributes().get(i).getName() + "  ");
                }
                type.setText(sb);
                LogUtil.e("限购"+userGoodsDetail.getLimitNum());
                if (userGoodsDetail.getLimitNum()!=0&&quantity>userGoodsDetail.getLimitNum()){
                    Toast.makeText(mContext, "限购"+userGoodsDetail.getLimitNum()+"件", Toast.LENGTH_SHORT).show();
                }else {
                    count.setText("" + quantity);
                }


                if (isChoose) {
                    if (attrSize == 1) {
                        sp1Adapter.setSelectedList(sp1Position);
                        type.setText("已选择：" + sp1List.get(sp1Position).getContent());
                        if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                            price.setText("￥" + sp1List.get(sp1Position).getVipPrice());
                        }else {
                            price.setText("￥" + sp1List.get(sp1Position).getPrice());
                        }

                    } else if (attrSize == 2) {
                        sp1Adapter.setSelectedList(sp1Position);
                        sp2Adapter.setSelectedList(sp2Position);
                        type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent());
                        if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                            price.setText("￥" + sp2List.get(sp2Position).getVipPrice());
                        }else {
                            price.setText("￥" + sp2List.get(sp2Position).getPrice());
                        }
                    } else {
                        sp1Adapter.setSelectedList(sp1Position);
                        sp2Adapter.setSelectedList(sp2Position);
                        sp3Adapter.setSelectedList(sp3Position);
                        type.setText("已选择：" + sp1List.get(sp1Position).getContent() + "、" + sp2List.get(sp2Position).getContent() + "、" + sp3List.get(sp3Position).getContent());
                        if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                            price.setText("￥" + sp3List.get(sp3Position).getVipPrice());
                        }else {
                            price.setText("￥" + sp3List.get(sp3Position).getPrice());
                        }

                    }
                    Glide.with(mContext).load(sp1List.get(sp1Position).getPicUrl()).into(img);
                } else {
                    Glide.with(mContext).load(userGoodsDetail.getPic()).into(img);
                }

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUtils.seeBigImg(mContext, Uri.parse(sp1Position == -1 ? userGoodsDetail.getPic() : sp1List.get(sp1Position).getPicUrl()));
                    }
                });

                PopUtil.setTransparency(mContext, 0.3f);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        PopUtil.setTransparency(mContext, 1f);
                        if (attrSize == 1) {
                            if (sp1Position == -1) {
                                getView().weixuanze(sb.toString().replace("请选择", ""));
                            } else {
                                getView().yixuanze(type.getText().toString());
                            }
                        } else if (attrSize == 2) {
                            if (sp1Position == -1 || sp2Position == -1) {
                                getView().weixuanze(sb.toString().replace("请选择", ""));
                            } else {
                                getView().yixuanze(type.getText().toString());
                            }
                        } else {
                            if (sp1Position == -1 || sp2Position == -1 || sp3Position == -1) {
                                getView().weixuanze(sb.toString().replace("请选择", ""));
                            } else {
                                getView().yixuanze(type.getText().toString());
                            }
                        }
                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attrSize == 1) {
                            if (sp1Position == -1) {
                                Toast.makeText(mContext, "请先选择商品", Toast.LENGTH_SHORT).show();
                            } else {
                                if (quantity >= dataList.get(sp1Position).getStock()) {
                                    quantity = dataList.get(sp1Position).getStock();
                                } else if (quantity==userGoodsDetail.getLimitNum()){
                                    Toast.makeText(mContext, "限购"+userGoodsDetail.getLimitNum()+"件", Toast.LENGTH_SHORT).show();
                                }else {
                                    quantity++;
                                }
                            }
                        } else if (attrSize == 2) {
                            if (sp1Position == -1 || sp2Position == -1) {
                                Toast.makeText(mContext, "请先选择商品", Toast.LENGTH_SHORT).show();
                            } else {
                                if (quantity >= stock1(sp1List.get(sp1Position).getContent(), sp2Position).getStock()) {
                                    quantity = stock1(sp1List.get(sp1Position).getContent(), sp2Position).getStock();
                                } else if (quantity==userGoodsDetail.getLimitNum()){
                                    Toast.makeText(mContext, "限购"+userGoodsDetail.getLimitNum()+"件", Toast.LENGTH_SHORT).show();
                                }else {
                                    quantity++;
                                }
                            }
                        } else {
                            if (sp1Position == -1 || sp2Position == -1 || sp3Position == -1) {
                                Toast.makeText(mContext, "请先选择商品", Toast.LENGTH_SHORT).show();
                            } else {
                                if (quantity >= stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getStock()) {
                                    quantity = stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getStock();
                                } else if (quantity==userGoodsDetail.getLimitNum()){
                                    Toast.makeText(mContext, "限购"+userGoodsDetail.getLimitNum()+"件", Toast.LENGTH_SHORT).show();
                                }else {
                                    quantity++;
                                }
                            }
                        }
                        count.setText("" + quantity);
                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (quantity <= 1) {
                            quantity = 1;
                        } else {
                            quantity--;
                        }
                        count.setText("" + quantity);
                    }
                });

                shopCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SPUtil.getToken() == null || "".equals(SPUtil.getToken())) {
                            PopUtils.isLogin(mContext);
                        } else {
                            if (attrSize == 1) {
                                if (sp1Position == -1) {
                                    Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
                                } else {
                                    popupWindow.dismiss();
                                    chooseOrAddCart(flag);
                                }
                            } else if (attrSize == 2) {
                                if (sp1Position == -1 || sp2Position == -1) {
                                    Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
                                } else {
                                    popupWindow.dismiss();
                                    chooseOrAddCart(flag);
                                }
                            } else {
                                if (sp1Position == -1 || sp2Position == -1 || sp3Position == -1) {
                                    Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
                                } else {
                                    popupWindow.dismiss();
                                    chooseOrAddCart(flag);
                                }
                            }
                        }
                    }
                });

                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SPUtil.getToken() == null || "".equals(SPUtil.getToken())) {
                            PopUtils.isLogin(mContext);
                        } else {
                            if (attrSize == 1) {
                                if (sp1Position == -1) {
                                    Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
                                } else {
                                    popupWindow.dismiss();
                                    if (1 == flag) {
                                        jumpToManager(flag);
                                    } else {
                                        chooseOrJump(flag);
                                    }
                                }
                            } else if (attrSize == 2) {
                                if (sp1Position == -1 || sp2Position == -1) {
                                    Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
                                } else {
                                    popupWindow.dismiss();
                                    if (1 == flag) {
                                        jumpToManager(flag);
                                    } else {
                                        chooseOrJump(flag);
                                    }
                                }
                            } else {
                                if (sp1Position == -1 || sp2Position == -1 || sp3Position == -1) {
                                    Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
                                } else {
                                    popupWindow.dismiss();
                                    if (1 == flag) {
                                        jumpToManager(flag);
                                    } else {
                                        chooseOrJump(flag);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "商品属性异常", Toast.LENGTH_SHORT).show();
        }
    }

    private UserGoodsDetail.SkuStockListBean stock1(String str, int position) {
        List<UserGoodsDetail.SkuStockListBean> list = new ArrayList<>();
        if (str != null) {
            for (int i = 0; i < dataList.size(); i++) {
                if (str.equals(dataList.get(i).getSp1())) {
                    list.add(dataList.get(i));
                }
            }
            return list.get(position);
        } else {
            return new UserGoodsDetail.SkuStockListBean();
        }
    }

    private UserGoodsDetail.SkuStockListBean stock12(String str, String str2, int position) {
        List<UserGoodsDetail.SkuStockListBean> list = new ArrayList<>();
        if (str != null && str2 != null) {
            for (int i = 0; i < dataList.size(); i++) {
                if (str.equals(dataList.get(i).getSp1()) && str2.equals(dataList.get(i).getSp2())) {
                    list.add(dataList.get(i));
                }
            }
            return list.get(position);
        } else {
            return new UserGoodsDetail.SkuStockListBean();
        }
    }

    private UserGoodsDetail.SkuStockListBean stock13(String str, String str3, int position) {
        List<UserGoodsDetail.SkuStockListBean> list = new ArrayList<>();
        if (str != null && str3 != null) {
            for (int i = 0; i < dataList.size(); i++) {
                if (str.equals(dataList.get(i).getSp1()) && str3.equals(dataList.get(i).getSp3())) {
                    list.add(dataList.get(i));
                }
            }
            return list.get(position);
        } else {
            return new UserGoodsDetail.SkuStockListBean();
        }
    }

    private UserGoodsDetail.SkuStockListBean stock23(String str3, String str2, int position) {
        List<UserGoodsDetail.SkuStockListBean> list = new ArrayList<>();
        if (str2 != null && str3 != null) {
            for (int i = 0; i < dataList.size(); i++) {
                if (str3.equals(dataList.get(i).getSp3()) && str2.equals(dataList.get(i).getSp2())) {
                    list.add(dataList.get(i));
                }
            }
            return list.get(position);
        } else {
            return new UserGoodsDetail.SkuStockListBean();
        }
    }


    private void initSizeList(int clickPos) {
        try {


            if (clickPos == 1) {
                sp2List.clear();
                canotClick2.clear();
                int temp = 0;
                for (int i = 0; i < dataList.size(); i++) {
                    if (sp1List.get(sp1Position).getContent().equals(dataList.get(i).getSp1())) {
                        boolean isHas = false;
                        for (int j = 0; j < sp2List.size(); j++) {
                            if (sp2List.get(j).getContent().equals(dataList.get(i).getSp2())) {
                                isHas = true;
                            }
                        }
                        if (!isHas) {
                            if (dataList.get(i).getStock() > 0) {
                                sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            } else {
                                canotClick2.add(temp);
                                sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPrice(), false,dataList.get(i).getVipPrice()));
                            }
                            temp++;
                        }
                    }
                }

                flow2.setNoCheckList(canotClick2);
                sp2Adapter.notifyDataChanged();
                if (sp2Position != -1) {
                    sp2Adapter.setSelectedList(sp2Position);
                }
            } else if (clickPos == 2) {
                sp1List.clear();
                canotClick1.clear();
                int temp = 0;
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getSp2().equals(sp2List.get(sp2Position).getContent())) {
                        if (dataList.get(i).getStock() > 0) {
                            sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                        } else {
                            canotClick1.add(temp);
                            sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), false,dataList.get(i).getVipPrice()));
                        }
                        temp++;
                    }
                }

                flow1.setNoCheckList(canotClick1);
                sp1Adapter.notifyDataChanged();
                if (sp1Position != -1) {
                    sp1Adapter.setSelectedList(sp1Position);
                }
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "商品属性异常", Toast.LENGTH_SHORT).show();
        }
    }

    private void initThirdList(int clickPos) {
        try {

            if (clickPos == 1) {
                int temp2 = 0;
                int temp3 = 0;
                canotClick2.clear();
                canotClick3.clear();
                if (sp2Position == -1 && sp3Position == -1) {
                    sp2List.clear();
                    sp3List.clear();
                    for (int i = 0; i < dataList.size(); i++) {

                        if (dataList.get(i).getSp1().equals(sp1List.get(sp1Position).getContent())) {
                            if (sp2List.size() == 0 && dataList.get(i).getSp2() != null) {
                                sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            } else {
                                boolean isHas = false;
                                for (int j = 0; j < sp2List.size(); j++) {
                                    if (dataList.get(i).getSp2().equals(sp2List.get(j).getContent())) {
                                        isHas = true;
                                        break;
                                    }
                                }
                                if (!isHas) {
                                    sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                                }
                            }

                            if (sp3List.size() == 0 && dataList.get(i).getSp3() != null) {
                                sp3List.add(new ChooseInsideBean(dataList.get(i).getSp3(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            } else {
                                boolean isHas = false;
                                for (int j = 0; j < sp3List.size(); j++) {
                                    if (dataList.get(i).getSp3().equals(sp3List.get(j).getContent())) {
                                        isHas = true;
                                        break;
                                    }
                                }
                                if (!isHas) {
                                    sp3List.add(new ChooseInsideBean(dataList.get(i).getSp3(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                                }
                            }
                        }
                    }

                    for (int j = 0; j < sp2List.size(); j++) {
                        boolean hasStock = true;
                        for (int k = 0; k < dataList.size(); k++) {
                            if (dataList.get(k).getSp1().equals(sp1List.get(sp1Position).getContent()) && dataList.get(k).getSp2().equals(sp2List.get(j).getContent())) {
                                if (dataList.get(k).getStock() <= 0) {
                                    hasStock = false;
                                    break;
                                }
                            }
                        }
                        if (!hasStock) {
                            sp2List.get(j).setCanClick(hasStock);
                            canotClick2.add(j);
                        }
                    }

                    for (int j = 0; j < sp3List.size(); j++) {
                        boolean hasStock = true;
                        for (int k = 0; k < dataList.size(); k++) {
                            if (dataList.get(k).getSp1().equals(sp1List.get(sp1Position).getContent()) && dataList.get(k).getSp3().equals(sp3List.get(j).getContent())) {
                                if (dataList.get(k).getStock() <= 0) {
                                    hasStock = false;
                                    break;
                                }
                            }
                        }
                        if (!hasStock) {
                            sp3List.get(j).setCanClick(hasStock);
                            canotClick3.add(j);
                        }
                    }
                    flow2.setNoCheckList(canotClick2);
                    flow3.setNoCheckList(canotClick3);
                    sp2Adapter.notifyDataChanged();
                    sp3Adapter.notifyDataChanged();
                } else if (sp2Position != -1 && sp3Position == -1) {
                    sp3List.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).getSp1().equals(sp1List.get(sp1Position).getContent()) && dataList.get(i).getSp2().equals(sp2List.get(sp2Position).getContent())) {
                            sp3List.add(new ChooseInsideBean(dataList.get(i).getSp3(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock() > 0,dataList.get(i).getVipPrice()));
                            if (dataList.get(i).getStock() <= 0) {
                                canotClick3.add(temp3);
                                temp3++;
                            }
                        }
                    }

                    for (int i = 0; i < sp2List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp1().equals(sp1List.get(sp1Position).getContent()) && dataList.get(j).getSp2().equals(sp2List.get(i).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp2List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick2.add(i);
                        }
                    }
                    flow2.setNoCheckList(canotClick2);
                    flow3.setNoCheckList(canotClick3);
                    sp3Adapter.notifyDataChanged();
                    sp2Adapter.notifyDataChanged();
                    sp2Adapter.setSelectedList(sp2Position);
                } else if (sp2Position == -1 && sp3Position != -1) {
                    sp2List.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).getSp1().equals(sp1List.get(sp1Position).getContent()) && dataList.get(i).getSp3().equals(sp3List.get(sp3Position).getContent())) {
                            sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock() > 0,dataList.get(i).getVipPrice()));
                            if (dataList.get(i).getStock() <= 0) {
                                canotClick2.add(temp2);
                                temp2++;
                            }
                        }
                    }

                    for (int i = 0; i < sp3List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp3().equals(sp3List.get(i).getContent()) && dataList.get(j).getSp1().equals(sp1List.get(sp1Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp3List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick3.add(i);
                        }
                    }
                    flow2.setNoCheckList(canotClick2);
                    flow3.setNoCheckList(canotClick3);
                    sp2Adapter.notifyDataChanged();
                    sp3Adapter.notifyDataChanged();
                    sp3Adapter.setSelectedList(sp3Position);
                } else if (sp2Position != -1 && sp3Position != -1) {
                    for (int i = 0; i < sp2List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp2().equals(sp2List.get(i).getContent()) && dataList.get(j).getSp1().equals(sp1List.get(sp1Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp2List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick2.add(i);
                        }
                    }

                    for (int i = 0; i < sp3List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp3().equals(sp3List.get(i).getContent()) && dataList.get(j).getSp1().equals(sp1List.get(sp1Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp3List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick3.add(i);
                        }
                    }

                    flow2.setNoCheckList(canotClick2);
                    flow3.setNoCheckList(canotClick3);
                    sp2Adapter.notifyDataChanged();
                    sp3Adapter.notifyDataChanged();
                    sp2Adapter.setSelectedList(sp2Position);
                    sp3Adapter.setSelectedList(sp3Position);
                }


            } else if (clickPos == 2) {
                int temp1 = 0;
                int temp3 = 0;
                canotClick1.clear();
                canotClick3.clear();
                if (sp1Position == -1 && sp3Position == -1) {
                    sp1List.clear();
                    sp3List.clear();
                    for (int i = 0; i < dataList.size(); i++) {

                        if (dataList.get(i).getSp2().equals(sp2List.get(sp2Position).getContent())) {
                            if (sp1List.size() == 0 && dataList.get(i).getSp1() != null) {
                                sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            } else {
                                boolean isHas = false;
                                for (int j = 0; j < sp1List.size(); j++) {
                                    if (dataList.get(i).getSp1().equals(sp1List.get(j).getContent())) {
                                        isHas = true;
                                        break;
                                    }
                                }
                                if (!isHas) {
                                    sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                                }
                            }

                            if (sp3List.size() == 0 && dataList.get(i).getSp3() != null) {
                                sp3List.add(new ChooseInsideBean(dataList.get(i).getSp3(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            } else {
                                boolean isHas = false;
                                for (int j = 0; j < sp3List.size(); j++) {
                                    if (dataList.get(i).getSp3().equals(sp3List.get(j).getContent())) {
                                        isHas = true;
                                        break;
                                    }
                                }
                                if (!isHas) {
                                    sp3List.add(new ChooseInsideBean(dataList.get(i).getSp3(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                                }
                            }
                        }
                    }

                    for (int j = 0; j < sp1List.size(); j++) {
                        boolean hasStock = true;
                        for (int k = 0; k < dataList.size(); k++) {
                            if (dataList.get(k).getSp2().equals(sp2List.get(sp2Position).getContent()) && dataList.get(k).getSp1().equals(sp1List.get(j).getContent())) {
                                if (dataList.get(k).getStock() <= 0) {
                                    hasStock = false;
                                    break;
                                }
                            }
                        }
                        if (!hasStock) {
                            sp1List.get(j).setCanClick(hasStock);
                            canotClick1.add(j);
                        }
                    }

                    for (int j = 0; j < sp3List.size(); j++) {
                        boolean hasStock = true;
                        for (int k = 0; k < dataList.size(); k++) {
                            if (dataList.get(k).getSp2().equals(sp2List.get(sp2Position).getContent()) && dataList.get(k).getSp3().equals(sp3List.get(j).getContent())) {
                                if (dataList.get(k).getStock() <= 0) {
                                    hasStock = false;
                                    break;
                                }
                            }
                        }
                        if (!hasStock) {
                            sp3List.get(j).setCanClick(hasStock);
                            canotClick3.add(j);
                        }
                    }
                    flow1.setNoCheckList(canotClick1);
                    flow3.setNoCheckList(canotClick3);
                    sp1Adapter.notifyDataChanged();
                    sp3Adapter.notifyDataChanged();
                } else if (sp1Position != -1 && sp3Position == -1) {
                    sp3List.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).getSp2().equals(sp2List.get(sp2Position).getContent()) && dataList.get(i).getSp1().equals(sp1List.get(sp1Position).getContent())) {
                            sp3List.add(new ChooseInsideBean(dataList.get(i).getSp3(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock() > 0,dataList.get(i).getVipPrice()));
                            if (dataList.get(i).getStock() <= 0) {
                                canotClick3.add(temp3);
                                temp3++;
                            }
                        }
                    }

                    for (int i = 0; i < sp1List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp2().equals(sp2List.get(sp2Position).getContent()) && dataList.get(j).getSp1().equals(sp1List.get(i).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp1List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick1.add(i);
                        }
                    }
                    flow1.setNoCheckList(canotClick1);
                    flow3.setNoCheckList(canotClick3);
                    sp3Adapter.notifyDataChanged();
                    sp1Adapter.notifyDataChanged();
                    sp1Adapter.setSelectedList(sp1Position);
                } else if (sp1Position == -1 && sp3Position != -1) {
                    sp1List.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).getSp2().equals(sp2List.get(sp2Position).getContent()) && dataList.get(i).getSp3().equals(sp3List.get(sp3Position).getContent())) {
                            sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock() > 0,dataList.get(i).getVipPrice()));
                            if (dataList.get(i).getStock() <= 0) {
                                canotClick1.add(temp1);
                                temp1++;
                            }
                        }
                    }

                    for (int i = 0; i < sp3List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp3().equals(sp3List.get(i).getContent()) && dataList.get(j).getSp2().equals(sp2List.get(sp2Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp3List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick3.add(i);
                        }
                    }
                    flow1.setNoCheckList(canotClick1);
                    flow3.setNoCheckList(canotClick3);
                    sp1Adapter.notifyDataChanged();
                    sp3Adapter.notifyDataChanged();
                    sp3Adapter.setSelectedList(sp3Position);
                } else if (sp1Position != -1 && sp3Position != -1) {
                    for (int i = 0; i < sp1List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp1().equals(sp1List.get(i).getContent()) && dataList.get(j).getSp2().equals(sp2List.get(sp2Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp1List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick1.add(i);
                        }
                    }

                    for (int i = 0; i < sp3List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp3().equals(sp3List.get(i).getContent()) && dataList.get(j).getSp2().equals(sp2List.get(sp2Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp3List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick3.add(i);
                        }
                    }

                    flow1.setNoCheckList(canotClick1);
                    flow3.setNoCheckList(canotClick3);
                    sp1Adapter.notifyDataChanged();
                    sp3Adapter.notifyDataChanged();
                    sp1Adapter.setSelectedList(sp1Position);
                    sp3Adapter.setSelectedList(sp3Position);
                }
            } else if (clickPos == 3) {
                int temp2 = 0;
                int temp1 = 0;
                canotClick1.clear();
                canotClick2.clear();
                if (sp2Position == -1 && sp1Position == -1) {
                    sp2List.clear();
                    sp1List.clear();
                    for (int i = 0; i < dataList.size(); i++) {

                        if (dataList.get(i).getSp3().equals(sp3List.get(sp3Position).getContent())) {
                            if (sp2List.size() == 0 && dataList.get(i).getSp2() != null) {
                                sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            } else {
                                boolean isHas = false;
                                for (int j = 0; j < sp2List.size(); j++) {
                                    if (dataList.get(i).getSp2().equals(sp2List.get(j).getContent())) {
                                        isHas = true;
                                        break;
                                    }
                                }
                                if (!isHas) {
                                    sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                                }
                            }

                            if (sp1List.size() == 0 && dataList.get(i).getSp1() != null) {
                                sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                            } else {
                                boolean isHas = false;
                                for (int j = 0; j < sp1List.size(); j++) {
                                    if (dataList.get(i).getSp1().equals(sp1List.get(j).getContent())) {
                                        isHas = true;
                                        break;
                                    }
                                }
                                if (!isHas) {
                                    sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), true,dataList.get(i).getVipPrice()));
                                }
                            }
                        }
                    }

                    for (int j = 0; j < sp2List.size(); j++) {
                        boolean hasStock = true;
                        for (int k = 0; k < dataList.size(); k++) {
                            if (dataList.get(k).getSp3().equals(sp3List.get(sp3Position).getContent()) && dataList.get(k).getSp2().equals(sp2List.get(j).getContent())) {
                                if (dataList.get(k).getStock() <= 0) {
                                    hasStock = false;
                                    break;
                                }
                            }
                        }
                        if (!hasStock) {
                            sp2List.get(j).setCanClick(hasStock);
                            canotClick2.add(j);
                        }
                    }

                    for (int j = 0; j < sp1List.size(); j++) {
                        boolean hasStock = true;
                        for (int k = 0; k < dataList.size(); k++) {
                            if (dataList.get(k).getSp3().equals(sp3List.get(sp3Position).getContent()) && dataList.get(k).getSp1().equals(sp1List.get(j).getContent())) {
                                if (dataList.get(k).getStock() <= 0) {
                                    hasStock = false;
                                    break;
                                }
                            }
                        }
                        if (!hasStock) {
                            sp1List.get(j).setCanClick(hasStock);
                            canotClick1.add(j);
                        }
                    }
                    flow2.setNoCheckList(canotClick2);
                    flow1.setNoCheckList(canotClick1);
                    sp2Adapter.notifyDataChanged();
                    sp1Adapter.notifyDataChanged();
                } else if (sp2Position != -1 && sp1Position == -1) {
                    sp1List.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).getSp3().equals(sp3List.get(sp3Position).getContent()) && dataList.get(i).getSp2().equals(sp2List.get(sp2Position).getContent())) {
                            sp1List.add(new ChooseInsideBean(dataList.get(i).getSp1(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock() > 0,dataList.get(i).getVipPrice()));
                            if (dataList.get(i).getStock() <= 0) {
                                canotClick1.add(temp1);
                                temp1++;
                            }
                        }
                    }

                    for (int i = 0; i < sp2List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp3().equals(sp3List.get(sp3Position).getContent()) && dataList.get(j).getSp2().equals(sp2List.get(i).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp2List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick2.add(i);
                        }
                    }
                    flow2.setNoCheckList(canotClick2);
                    flow1.setNoCheckList(canotClick1);
                    sp1Adapter.notifyDataChanged();
                    sp2Adapter.notifyDataChanged();
                    sp2Adapter.setSelectedList(sp2Position);
                } else if (sp2Position == -1 && sp1Position != -1) {
                    sp2List.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).getSp3().equals(sp3List.get(sp3Position).getContent()) && dataList.get(i).getSp1().equals(sp1List.get(sp1Position).getContent())) {
                            sp2List.add(new ChooseInsideBean(dataList.get(i).getSp2(), dataList.get(i).getPic(), dataList.get(i).getPrice(), dataList.get(i).getStock() > 0,dataList.get(i).getVipPrice()));
                            if (dataList.get(i).getStock() <= 0) {
                                canotClick2.add(temp2);
                                temp2++;
                            }
                        }
                    }

                    for (int i = 0; i < sp1List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp1().equals(sp1List.get(i).getContent()) && dataList.get(j).getSp3().equals(sp3List.get(sp3Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp1List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick1.add(i);
                        }
                    }
                    flow2.setNoCheckList(canotClick2);
                    flow1.setNoCheckList(canotClick1);
                    sp2Adapter.notifyDataChanged();
                    sp1Adapter.notifyDataChanged();
                    sp1Adapter.setSelectedList(sp1Position);
                } else if (sp2Position != -1 && sp1Position != -1) {
                    for (int i = 0; i < sp2List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp2().equals(sp2List.get(i).getContent()) && dataList.get(j).getSp3().equals(sp3List.get(sp3Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp2List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick2.add(i);
                        }
                    }

                    for (int i = 0; i < sp1List.size(); i++) {
                        boolean isStock = false;
                        for (int j = 0; j < dataList.size(); j++) {
                            if (dataList.get(j).getSp1().equals(sp1List.get(i).getContent()) && dataList.get(j).getSp3().equals(sp3List.get(sp3Position).getContent())) {
                                if (dataList.get(j).getStock() > 0) {
                                    isStock = true;
                                }
                            }
                        }
                        sp1List.get(i).setCanClick(isStock);
                        if (!isStock) {
                            canotClick1.add(i);
                        }
                    }

                    flow2.setNoCheckList(canotClick2);
                    flow1.setNoCheckList(canotClick1);
                    sp2Adapter.notifyDataChanged();
                    sp1Adapter.notifyDataChanged();
                    sp2Adapter.setSelectedList(sp2Position);
                    sp1Adapter.setSelectedList(sp1Position);
                }
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "商品属性异常", Toast.LENGTH_SHORT).show();
        }
    }

    public void jumpToAssess(String id) {
        Intent intent = new Intent(mContext, AssessActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }

    public void jumpToShop() {
        Intent intent = new Intent(mContext, ShopHomeActivity.class);
        intent.putExtra("shop_name", userGoodsDetail.getSellerName());
        intent.putExtra("shop_icon", userGoodsDetail.getSellerLogo());
        intent.putExtra("shop_id", userGoodsDetail.getSellerId());
        intent.putExtra("number", TxtUtil.parse(userGoodsDetail.getSellerFavoriteNum()));
        mContext.startActivity(intent);
    }

    public void chooseOrJump(int type) {
        if (SPUtil.getToken() == null || "".equals(SPUtil.getToken())) {
            PopUtils.isLogin(mContext);
        } else {
            if (isChoose) {
                OrderConfirmBean orderConfirmBean = new OrderConfirmBean();
                if (attrSize == 1) {
                    orderConfirmBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    orderConfirmBean.setSellerName(userGoodsDetail.getSellerName());
                    orderConfirmBean.setProductSkuId(dataList.get(sp1Position).getId() + "");
                    orderConfirmBean.setQuantity((int) quantity);
                    orderConfirmBean.setSp1(sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setPrice(sp1List.get(sp1Position).getPrice());
                    orderConfirmBean.setSourceType(1);
                    orderConfirmBean.setPic(userGoodsDetail.getPic());
                    orderConfirmBean.setProductName(userGoodsDetail.getName());
                    orderConfirmBean.setFeightTemplateId((long) userGoodsDetail.getFeightTemplateId());
                    orderConfirmBean.setStock(dataList.get(sp1Position).getStock());
                    orderConfirmBean.setProductId(userGoodsDetail.getId() + "");
                    orderConfirmBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    orderConfirmBean.setProductSn(userGoodsDetail.getProductSn());
                    orderConfirmBean.setPromotionPrice(sp1List.get(sp1Position).getPrice());
                    orderConfirmBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + ":" + sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setSellerType(userGoodsDetail.getSellerType());
                    orderConfirmBean.setUseIntegration(0);
                    orderConfirmBean.setGiftGrowth(userGoodsDetail.getGiftPoint() * quantity);
                    orderConfirmBean.setIntegration(userGoodsDetail.getSelfBuy() * quantity);
                    orderConfirmBean.setLimitNum(userGoodsDetail.getLimitNum());
                    double vipPrice = sp1List.get(sp1Position).getVipPrice();
                    orderConfirmBean.setVipPrice(Double.valueOf(vipPrice));
                    if (vipPrice!=0&&SPUtil.getStringValue(CommonResource.LEVELID).equals("2")) {
                        orderConfirmBean.setProductPrice(Double.valueOf(vipPrice));
                    }else {
                        orderConfirmBean.setProductPrice(sp1List.get(sp1Position).getPrice());
                    }

                } else if (attrSize == 2) {
                    orderConfirmBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    orderConfirmBean.setSellerName(userGoodsDetail.getSellerName());
                    orderConfirmBean.setProductSkuId(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getId() + "");
                    orderConfirmBean.setQuantity((int) quantity);
                    orderConfirmBean.setSp1(sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setSp2(sp2List.get(sp2Position).getContent());
                    orderConfirmBean.setPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                    orderConfirmBean.setSourceType(1);
                    orderConfirmBean.setPic(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPic());
                    orderConfirmBean.setProductName(userGoodsDetail.getName());
                    orderConfirmBean.setFeightTemplateId((long) userGoodsDetail.getFeightTemplateId());
                    orderConfirmBean.setStock(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getStock());
                    orderConfirmBean.setProductId(userGoodsDetail.getId() + "");
                    orderConfirmBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    //orderConfirmBean.setProductPrice(userGoodsDetail.getPrice());
                    orderConfirmBean.setProductSn(userGoodsDetail.getProductSn());
                    orderConfirmBean.setPromotionPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                    orderConfirmBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + "：" + sp1List.get(sp1Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(1).getName() + "：" + sp2List.get(sp2Position).getContent());
                    orderConfirmBean.setSellerType(userGoodsDetail.getSellerType());
                    orderConfirmBean.setUseIntegration(0);
                    orderConfirmBean.setLimitNum(userGoodsDetail.getLimitNum());
                    orderConfirmBean.setGiftGrowth(userGoodsDetail.getGiftPoint() * quantity);
                    orderConfirmBean.setIntegration(userGoodsDetail.getSelfBuy() * quantity);
                    double vipPrice = sp1List.get(sp1Position).getVipPrice();
                    orderConfirmBean.setVipPrice(Double.valueOf(vipPrice));
                    if (vipPrice!=0&&SPUtil.getStringValue(CommonResource.LEVELID).equals("2")) {
                        orderConfirmBean.setProductPrice(Double.valueOf(vipPrice));
                    }else {
                        orderConfirmBean.setProductPrice(sp1List.get(sp1Position).getPrice());
                    }

                } else {
                    orderConfirmBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    orderConfirmBean.setSellerName(userGoodsDetail.getSellerName());
                    orderConfirmBean.setProductSkuId(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getId() + "");
                    orderConfirmBean.setQuantity((int) quantity);
                    orderConfirmBean.setSp1(sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setSp2(sp2List.get(sp2Position).getContent());
                    orderConfirmBean.setSp2(sp3List.get(sp3Position).getContent());
                    orderConfirmBean.setPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPrice());
                    orderConfirmBean.setSourceType(1);
                    orderConfirmBean.setPic(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPic());
                    orderConfirmBean.setProductName(userGoodsDetail.getName());
                    orderConfirmBean.setFeightTemplateId((long) userGoodsDetail.getFeightTemplateId());
                    orderConfirmBean.setStock(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getStock());
                    orderConfirmBean.setProductId(userGoodsDetail.getId() + "");
                    orderConfirmBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    //orderConfirmBean.setProductPrice(userGoodsDetail.getPrice());
                    orderConfirmBean.setProductSn(userGoodsDetail.getProductSn());
                    orderConfirmBean.setPromotionPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPrice());
                    orderConfirmBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + "：" + sp1List.get(sp1Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(1).getName() + "：" + sp2List.get(sp2Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(2).getName() + ":" + sp3List.get(sp3Position).getContent());
                    orderConfirmBean.setSellerType(userGoodsDetail.getSellerType());
                    orderConfirmBean.setUseIntegration(0);
                    orderConfirmBean.setGiftGrowth(userGoodsDetail.getGiftPoint() * quantity);
                    orderConfirmBean.setIntegration(userGoodsDetail.getSelfBuy() * quantity);
                    orderConfirmBean.setLimitNum(userGoodsDetail.getLimitNum());
                    double vipPrice = sp1List.get(sp1Position).getVipPrice();
                    orderConfirmBean.setVipPrice(Double.valueOf(vipPrice));
                    if (vipPrice!=0&&SPUtil.getStringValue(CommonResource.LEVELID).equals("2")) {
                        orderConfirmBean.setProductPrice(Double.valueOf(vipPrice));
                    }else {
                        orderConfirmBean.setProductPrice(sp1List.get(sp1Position).getPrice());
                    }
                }
                Intent intent = new Intent(mContext, OrderConfirmActivity.class);
                intent.putExtra("order", orderConfirmBean);
                mContext.startActivity(intent);
            } else {
                chooseGoodsPop(type);
            }
        }
    }

    public void jumpToManager(int type) {
        if (SPUtil.getToken() == null || "".equals(SPUtil.getToken())) {
            PopUtils.isLogin(mContext);
        } else {
            if (isChoose) {
                OrderConfirmBean orderConfirmBean = new OrderConfirmBean();
                if (attrSize == 1) {
                    orderConfirmBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    orderConfirmBean.setSellerName(userGoodsDetail.getSellerName());
                    orderConfirmBean.setProductSkuId(dataList.get(sp1Position).getId() + "");
                    orderConfirmBean.setQuantity((int) quantity);
                    orderConfirmBean.setSp1(sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setPrice(sp1List.get(sp1Position).getPrice());
                    orderConfirmBean.setSourceType(1);
                    orderConfirmBean.setPic(userGoodsDetail.getPic());
                    orderConfirmBean.setProductName(userGoodsDetail.getName());
                    orderConfirmBean.setFeightTemplateId((long) userGoodsDetail.getFeightTemplateId());
                    orderConfirmBean.setStock(dataList.get(sp1Position).getStock());
                    orderConfirmBean.setProductId(userGoodsDetail.getId() + "");
                    orderConfirmBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    orderConfirmBean.setProductSn(userGoodsDetail.getProductSn());
                    orderConfirmBean.setPromotionPrice(sp1List.get(sp1Position).getPrice());
                    orderConfirmBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + ":" + sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setSellerType(userGoodsDetail.getSellerType());
                    orderConfirmBean.setUseIntegration(0);
                    orderConfirmBean.setGiftGrowth(userGoodsDetail.getGiftPoint() == 0 ? 0 : userGoodsDetail.getGiftPoint() * quantity);
                    orderConfirmBean.setIntegration(userGoodsDetail.getSelfBuy() == 0 ? 0 : userGoodsDetail.getSelfBuy() * quantity);
                    orderConfirmBean.setVipPrice(Double.valueOf(userGoodsDetail.getVipPrice()));
                    orderConfirmBean.setLimitNum(userGoodsDetail.getLimitNum());
                    if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                        orderConfirmBean.setProductPrice(Double.valueOf(userGoodsDetail.getVipPrice()));
                    }else {
                        orderConfirmBean.setProductPrice(Double.valueOf(userGoodsDetail.getVipPrice()));
                    }
                } else if (attrSize == 2) {
                    orderConfirmBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    orderConfirmBean.setSellerName(userGoodsDetail.getSellerName());
                    orderConfirmBean.setProductSkuId(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getId() + "");
                    orderConfirmBean.setQuantity((int) quantity);
                    orderConfirmBean.setSp1(sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setSp2(sp2List.get(sp2Position).getContent());
                    orderConfirmBean.setPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                    orderConfirmBean.setSourceType(1);
                    orderConfirmBean.setPic(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPic());
                    orderConfirmBean.setProductName(userGoodsDetail.getName());
                    orderConfirmBean.setFeightTemplateId((long) userGoodsDetail.getFeightTemplateId());
                    orderConfirmBean.setStock(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getStock());
                    orderConfirmBean.setProductId(userGoodsDetail.getId() + "");
                    orderConfirmBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    //orderConfirmBean.setProductPrice(userGoodsDetail.getPrice());
                    orderConfirmBean.setProductSn(userGoodsDetail.getProductSn());
                    orderConfirmBean.setPromotionPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                    orderConfirmBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + "：" + sp1List.get(sp1Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(1).getName() + "：" + sp2List.get(sp2Position).getContent());
                    orderConfirmBean.setSellerType(userGoodsDetail.getSellerType());
                    orderConfirmBean.setUseIntegration(0);
                    orderConfirmBean.setVipPrice(Double.valueOf(userGoodsDetail.getVipPrice()));
                    orderConfirmBean.setLimitNum(userGoodsDetail.getLimitNum());
                    orderConfirmBean.setGiftGrowth(userGoodsDetail.getGiftPoint() == 0 ? 0 : userGoodsDetail.getGiftPoint() * quantity);
                    orderConfirmBean.setIntegration(userGoodsDetail.getSelfBuy() == 0 ? 0 : userGoodsDetail.getSelfBuy() * quantity);
                    if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")) {
                        orderConfirmBean.setProductPrice(Double.valueOf(userGoodsDetail.getVipPrice()));
                    }else {
                        orderConfirmBean.setProductPrice(userGoodsDetail.getPrice());
                    }
                } else {
                    orderConfirmBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    orderConfirmBean.setSellerName(userGoodsDetail.getSellerName());
                    orderConfirmBean.setProductSkuId(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getId() + "");
                    orderConfirmBean.setQuantity((int) quantity);
                    orderConfirmBean.setSp1(sp1List.get(sp1Position).getContent());
                    orderConfirmBean.setSp2(sp2List.get(sp2Position).getContent());
                    orderConfirmBean.setSp2(sp3List.get(sp3Position).getContent());
                    orderConfirmBean.setPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPrice());
                    orderConfirmBean.setSourceType(1);
                    orderConfirmBean.setPic(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPic());
                    orderConfirmBean.setProductName(userGoodsDetail.getName());
                    orderConfirmBean.setFeightTemplateId((long) userGoodsDetail.getFeightTemplateId());
                    orderConfirmBean.setStock(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getStock());
                    orderConfirmBean.setProductId(userGoodsDetail.getId() + "");
                    orderConfirmBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                   // orderConfirmBean.setProductPrice(userGoodsDetail.getPrice());
                    orderConfirmBean.setProductSn(userGoodsDetail.getProductSn());
                    orderConfirmBean.setPromotionPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPrice());
                    orderConfirmBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + "：" + sp1List.get(sp1Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(1).getName() + "：" + sp2List.get(sp2Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(2).getName() + ":" + sp3List.get(sp3Position).getContent());
                    orderConfirmBean.setSellerType(userGoodsDetail.getSellerType());
                    orderConfirmBean.setUseIntegration(0);
                    orderConfirmBean.setVipPrice(Double.valueOf(userGoodsDetail.getVipPrice()));
                    orderConfirmBean.setGiftGrowth(userGoodsDetail.getGiftPoint() == 0 ? 0 : userGoodsDetail.getGiftPoint() * quantity);
                    orderConfirmBean.setIntegration(userGoodsDetail.getSelfBuy() == 0 ? 0 : userGoodsDetail.getSelfBuy() * quantity);
                    orderConfirmBean.setLimitNum(userGoodsDetail.getLimitNum());
                    if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")) {
                        orderConfirmBean.setProductPrice(Double.valueOf(userGoodsDetail.getVipPrice()));
                    }else {
                        orderConfirmBean.setProductPrice(userGoodsDetail.getPrice());
                    }

                }

//                Intent intent = new Intent(mContext, OrderConfirmActivity.class);
//                intent.putExtra("order", orderConfirmBean);
//                mContext.startActivity(intent);
                ARouter.getInstance().build("/module_user_store/OpenedManagerActivity")
                        .withSerializable("order", orderConfirmBean)
//                        .withDouble("price",userGoodsDetail.getPrice())
                        .navigation();
            } else {
                chooseGoodsPop(type);
            }
        }

    }

    public void chooseOrAddCart(int type) {
        if (SPUtil.getToken() == null || "".equals(SPUtil.getToken())) {
            PopUtils.isLogin(mContext);
        } else {
            if (isChoose) {
                AddCartBean cartBean = new AddCartBean();
                if (attrSize == 1) {
                    cartBean.setChecked(0);
                    cartBean.setPrice(sp1List.get(sp1Position).getPrice());
                    cartBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + ":" + sp1List.get(sp1Position).getContent());
                    cartBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    cartBean.setProductId(userGoodsDetail.getId() + "");
                    cartBean.setProductSkuId(dataList.get(sp1Position).getId() + "");
                    cartBean.setProductName(userGoodsDetail.getName());
                    //cartBean.setProductPic(dataList.get(sp1Position).getPic());
                    cartBean.setProductPic(userGoodsDetail.getPic());
                    cartBean.setProductSn(userGoodsDetail.getProductSn());
                    cartBean.setProductSubTitle(userGoodsDetail.getSubTitle());
                    cartBean.setQuantity((int) quantity);
                    cartBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    cartBean.setSellerName(userGoodsDetail.getSellerName());
                    cartBean.setSp1(sp1List.get(sp1Position).getContent());
                    cartBean.setUserId(SPUtil.getUserCode());
                    cartBean.setSellerType(userGoodsDetail.getSellerType()+"");
                    cartBean.setLimitNum(userGoodsDetail.getLimitNum());
                    double vipPrice= sp1List.get(sp1Position).getVipPrice();
                    if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                        if (sp1List.get(sp1Position).getVipPrice()==0){
                            cartBean.setPrice(sp1List.get(sp1Position).getPrice());
                        }else {
                            cartBean.setPrice(sp1List.get(sp1Position).getVipPrice());
                        }
                    }else {
                        cartBean.setPrice(sp1List.get(sp1Position).getPrice());
                    }
                } else if (attrSize == 2) {
                    cartBean.setChecked(0);
                    cartBean.setPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                    cartBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + "：" + sp1List.get(sp1Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(1).getName() + "：" + sp2List.get(sp2Position).getContent());
                    cartBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    cartBean.setProductId(userGoodsDetail.getId() + "");
                    cartBean.setProductSkuId(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getId() + "");
                    cartBean.setProductName(userGoodsDetail.getName());
                   // cartBean.setProductPic(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPic());
                    cartBean.setProductPic(userGoodsDetail.getPic());
                    cartBean.setProductSn(userGoodsDetail.getProductSn());
                    cartBean.setProductSubTitle(userGoodsDetail.getSubTitle());
                    cartBean.setQuantity((int) quantity);
                    cartBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    cartBean.setSellerName(userGoodsDetail.getSellerName());
                    cartBean.setSp1(sp1List.get(sp1Position).getContent());
                    cartBean.setSp2(sp2List.get(sp2Position).getContent());
                    cartBean.setUserId(SPUtil.getUserCode());
                    cartBean.setSellerType(userGoodsDetail.getSellerType()+"");
                    if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                        if (stock1(sp1List.get(sp1Position).getContent(), sp2Position).getVipPrice()==0){
                            cartBean.setPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());

                        }else {
                            cartBean.setPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getVipPrice());
                        }
                    }else {
                        cartBean.setPrice(stock1(sp1List.get(sp1Position).getContent(), sp2Position).getPrice());
                    }

                } else {
                    cartBean.setChecked(0);
                   // cartBean.setPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPrice());
                    cartBean.setProductAttr(userGoodsDetail.getXsProductAttributes().get(0).getName() + "：" + sp1List.get(sp1Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(1).getName() + "：" + sp2List.get(sp2Position).getContent() + "、" + userGoodsDetail.getXsProductAttributes().get(2).getName() + ":" + sp3List.get(sp3Position).getContent());
                    cartBean.setProductCategoryId(userGoodsDetail.getProductCategoryId() + "");
                    cartBean.setProductId(userGoodsDetail.getId() + "");
                    cartBean.setProductSkuId(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getId() + "");
                    cartBean.setProductName(userGoodsDetail.getName());
                    //cartBean.setProductPic(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPic());
                    cartBean.setProductPic(userGoodsDetail.getPic());
                    cartBean.setProductSn(userGoodsDetail.getProductSn());
                    cartBean.setProductSubTitle(userGoodsDetail.getSubTitle());
                    cartBean.setQuantity((int) quantity);
                    cartBean.setSellerId(userGoodsDetail.getSellerId() + "");
                    cartBean.setSellerName(userGoodsDetail.getSellerName());
                    cartBean.setSp1(sp1List.get(sp1Position).getContent());
                    cartBean.setSp2(sp2List.get(sp2Position).getContent());
                    cartBean.setSp3(sp3List.get(sp3Position).getContent());
                    cartBean.setUserId(SPUtil.getUserCode());
                    cartBean.setSellerType(userGoodsDetail.getSellerType()+"");
                    if (SPUtil.getStringValue(CommonResource.LEVELID).equals("2")){
                        if (stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getVipPrice()==0){
                            cartBean.setPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPrice());

                        }else {
                            cartBean.setPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getVipPrice());
                        }
                    }else {
                        cartBean.setPrice(stock12(sp1List.get(sp1Position).getContent(), sp2List.get(sp2Position).getContent(), sp3Position).getPrice());
                    }
                }
                LogUtil.e("添加购物车的bean---------"+cartBean.toString());
                String jsonString = JSON.toJSONString(cartBean);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
                Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHeadWithBody(CommonResource.ADD_CART, requestBody, SPUtil.getToken());
                RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        Toast.makeText(mContext, "添加成功，可前往购物车查看", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {

                    }
                }));
            } else {
                chooseGoodsPop(type);
            }
        }
    }

    public void jumpToCart(String from) {
        if ("cart".equals(from)) {
            EventBus.getDefault().post(new EventBusBean(CommonResource.CART_REFRESH));
        }
        Intent intent = new Intent(mContext, UserActivity.class);
        intent.putExtra("key", CommonResource.JUMP_CART);
        mContext.startActivity(intent);
    }

    public void loadCoupon(String id, String sellerId) {
        Map map = MapUtil.getInstance().addParms("sellerId", sellerId).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9003).getHead(CommonResource.COUPON_KELING, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("优惠券：" + result);
                couponBeanList = JSON.parseArray(result, UserCouponBean.class);
                LogUtil.e("++++++++youhuiquancouponBeanList++++++++++"+couponBeanList);
                GoodsCouponAdapter goodsCouponAdapter = new GoodsCouponAdapter(mContext, couponBeanList, R.layout.rv_goods_coupon);

                goodsCouponAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        if (SPUtil.getToken() == null || "".equals(SPUtil.getToken())) {
                            PopUtils.isLogin(mContext);
                        } else {
                            lingquan();
                        }
                    }
                });
                if (getView() != null) {
                    getView().loadCoupon(goodsCouponAdapter);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("优惠券：" + errorCode + "--------------" + errorMsg);
            }
        }));
    }

    public void callServe() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.PHOTO);
        RetrofitUtil.getInstance().toSubscribe(dataWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("客服的接口------------"+result);
                ContactUsBean contactUsBean = JSON.parseObject(result, ContactUsBean.class);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + contactUsBean.getInfo());
                intent.setData(data);
                mContext.startActivity(intent);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));

    }

    public void seeBigPicture(int position) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            list.add(bannerList.get(i).getPicUrl());
        }
        PopUtils.popAssessBigPic(mContext, list, position);
    }
}
