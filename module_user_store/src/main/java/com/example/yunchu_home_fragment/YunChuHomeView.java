package com.example.yunchu_home_fragment;

import android.view.View;

import com.example.bean.BannerBean;
import com.example.bean.TabBean;
import com.example.bean.VisblityBean;
import com.example.mvp.BaseFragment;
import com.example.mvp.IView;
import com.example.user_home.adapter.NavBarAdapter;
import com.example.user_home.adapter.SaleHotAdapter;
import com.example.yunchu_home_fragment.adapter.TeJiaAdapter;
import com.example.yunchu_home_fragment.adapter.TuiJianAdapter;

import java.util.ArrayList;
import java.util.List;

public interface YunChuHomeView extends IView {
    void loadBanner(List<BannerBean.RecordsBean> beanList);

    void lodeMarquee(List<View> views);

    void loadNavBar(NavBarAdapter navBarAdapter);

    void getVisiblity(List<VisblityBean> visblityBeans);

    void loadSaleHot(TeJiaAdapter saleHotAdapter);

    void loadVP(ArrayList<BaseFragment> fragments);

    void getDate2(TabBean saiQu2Beans);

    void loadTuiJIanAdapter(TuiJianAdapter tuiJIanAdapter);

    void getId(int id);

    void refresh();

    void getImg(String picUrl);

    void loadShouAdapter(TuiJianAdapter shouYeAdapter);
}
