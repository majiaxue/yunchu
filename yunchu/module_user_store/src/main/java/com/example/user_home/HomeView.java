package com.example.user_home;

import com.example.bean.BannerBean;
import com.example.flashsale.adapter.FlashSaleGoodsAdapter;
import com.example.mvp.IView;
import com.example.user_home.adapter.CommendAdapter;
import com.example.user_home.adapter.FlashSaleAdapter;
import com.example.user_home.adapter.NavBarAdapter;
import com.example.user_home.adapter.SaleHotAdapter;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:
 */
public interface HomeView extends IView {

    void loadNavBar(NavBarAdapter adapter);

    void loadSaleHot(SaleHotAdapter adapter);

    void loadCommend(CommendAdapter adapter);

    void loadAdapter(FlashSaleAdapter flashSaleAdapter);

    void loadBanner(List<BannerBean.RecordsBean> beanList);

    void refreshSuccess();
}
