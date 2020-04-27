package com.example.yunchu_home_fragment.tabfragment;

import com.example.mvp.IView;
import com.example.yunchu_home_fragment.adapter.TuiJianAdapter;

public interface TabListView  extends IView {
    void loadSaleHot(TuiJianAdapter saleHotAdapter);

    void refresh();
}
