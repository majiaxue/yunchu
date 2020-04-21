package com.example.user_shopping_cart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.CartBean;
import com.example.bean.HotSaleBean;
import com.example.common.CommonResource;
import com.example.confirm_order.ConfirmOrderActivity;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.user_shopping_cart.adapter.CartChildRecAdapter;
import com.example.user_store.R;
import com.example.utils.ArithUtil;
import com.example.utils.LogUtil;
import com.example.utils.PopUtils;
import com.example.utils.ProcessDialogUtil;
import com.example.utils.SPUtil;
import com.example.utils.SpaceItemDecorationLeftAndRight;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:
 */
public class ShoppingCartPresenter extends BasePresenter<ShoppingCartView> {

    private SpaceItemDecorationLeftAndRight spaceItemDecorationLeftAndRight;
    private List<HotSaleBean.DataBean> commendList = new ArrayList<>();
    private List<CartBean> cartBeanList = new ArrayList<>();
    private boolean flag = true;
    private boolean isCheckAllParentAll;
    private boolean compileStatus;
    private PopupWindow popupWindow;
    private CartChildRecAdapter cartChildRecAdapter;


    public ShoppingCartPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {
        EventBus.getDefault().unregister(mContext);
    }

    public void setShoppingCartRec() {
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            ProcessDialogUtil.showProcessDialog(mContext);
//            WaitDialog.show((AppCompatActivity)mContext,null);
            final Observable<ResponseBody> cart = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).getDataWithout(CommonResource.CARTLIST + "/" + SPUtil.getUserCode());
            RetrofitUtil.getInstance().toSubscribe(cart, new OnMyCallBack(new OnDataListener() {

                @Override
                public void onSuccess(final String result, String msg) {
                    getView().loadSuccess();
                    LogUtil.e("cart------>" + result);
                    final List<CartBean> cartBeans = JSON.parseArray(result, CartBean.class);
                    if (cartBeans.size() != 0) {
                        cartBeanList.clear();
                        cartBeanList.addAll(cartBeans);
//                updateIsAll();
                        totalPrice();
                        getView().isHide(false);
                        if (cartChildRecAdapter == null) {
                            cartChildRecAdapter = new CartChildRecAdapter(mContext, cartBeanList, R.layout.item_cart_child);
                            if (getView() != null) {
                                getView().loadCartRv(cartChildRecAdapter);
                            }
                        } else {
                            cartChildRecAdapter.notifyDataSetChanged();
                        }

                        cartChildRecAdapter.setOnCartListChangeListener(new CartChildRecAdapter.OnCartListChangeListener() {
                            @Override
                            public void onProductNumberChange(int childPosition, int number) {
                                cartBeanList.get(childPosition).setQuantity(number);
                                reviseStutas();
                            }
                        });

                        cartChildRecAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                            @Override
                            public void ViewOnClick(View view, final int index) {
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        flag = true;
                                        check(index);
                                        reviseStutas();
                                    }
                                });
                            }
                        });
                    } else {
                        getView().isHide(true);
                    }

                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("cart-------->" + errorCode + "        " + errorMsg);
                    getView().loadSuccess();
                    getView().isHide(true);
                }
            }));
        } else {
            if (getView() != null) {
                getView().loadSuccess();
                getView().isHide(true);
            }
        }

    }

    //选中recycler的item
    private void check(int position) {

        if (cartBeanList.get(position).getChecked() == 0) {
            cartBeanList.get(position).setChecked(1);
        } else {
            cartBeanList.get(position).setChecked(0);
        }

        cartChildRecAdapter.notifyDataSetChanged();

        for (int i = 0; i < cartBeanList.size(); i++) {
            if (cartBeanList.get(i).getChecked() != 0) {
                flag = false;
            }
        }

        if (getView() != null) {
            getView().isCheckAll(flag);
        }

    }

    //选中parent全部的checkbox
    public void checkAllParent(boolean isCheckAllParent) {//false
        this.isCheckAllParentAll = isCheckAllParent;

        if (isCheckAllParent) {
            for (int i = 0; i < cartBeanList.size(); i++) {
                cartBeanList.get(i).setChecked(1);
            }
        } else {
            for (int i = 0; i < cartBeanList.size(); i++) {
                cartBeanList.get(i).setChecked(0);
            }
        }
        reviseStutas();
        cartChildRecAdapter.notifyDataSetChanged();

    }


    public void popupDelete() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_delete, null, false);
        TextView confirm = view.findViewById(R.id.popup_delete_confirm);
        TextView cancel = view.findViewById(R.id.popup_delete_cancel);

        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.example.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(mContext), Gravity.CENTER, 0, 0);
        PopUtils.setTransparency(mContext, 0.3f);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopUtils.setTransparency(mContext, 1f);
            }
        });
    }

    private void reviseStutas() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHeadWithList(CommonResource.REVISE_CART_ITEM, cartBeanList, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("修改状态：" + result);
                totalPrice();
//                getView().totalPrice(totalPrice);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    public void editOrDelete(boolean compileStatus) {
        this.compileStatus = compileStatus;
    }

    private void delete() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHeadWithout(CommonResource.DELETE_CART, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("删除购物车：" + result);
                setShoppingCartRec();
//                reviseStutas();
                getView().deleteSuccess();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    public void click() {
        cartChildRecAdapter.setViewTwoOnClickListener(new MyRecyclerAdapter.ViewTwoOnClickListener() {
            @Override
            public void ViewTwoOnClick(View view1, View view2, final int position) {
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag = true;
//                        checkAll(position);
                        cartBeanList.clear();
                        cartBeanList.add(cartBeanList.get(position));
                        reviseStutas();
                    }
                });

                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    private void totalPrice() {
        int count = 0;
        double totalPrice = 0;
        boolean bn = true;
        for (int j = 0; j < cartBeanList.size(); j++) {
            if (0 == cartBeanList.get(j).getChecked()) {
                count++;
                totalPrice += ArithUtil.mulRound(cartBeanList.get(j).getQuantity() * 1.0, cartBeanList.get(j).getPrice());
            } else if (1 == cartBeanList.get(j).getChecked()) {
                bn = false;
            }
        }
        if (cartBeanList.size() == 0) {
            bn = false;
        }
        if (getView() != null) {
            getView().updateCount(count);
            getView().isCheckAll(bn);
            getView().totalPrice(totalPrice);
        }
    }

    public void jiesuan() {
        try {
            List<CartBean> parentList = new ArrayList<>();
            for (int i = 0; i < cartBeanList.size(); i++) {
                if (0 == cartBeanList.get(i).getChecked()) {
                    parentList.add(cartBeanList.get(i));
                }
            }
            if (parentList.size() > 0) {
                String jsonString = JSON.toJSONString(parentList);
                Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                intent.putExtra("bean", jsonString);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
