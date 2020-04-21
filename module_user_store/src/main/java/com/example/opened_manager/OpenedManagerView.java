package com.example.opened_manager;

import com.example.bean.PostageBean;
import com.example.bean.ShippingAddressBean;
import com.example.mvp.IView;
import com.example.user_manager_center.adapter.EquityAdapter;

public interface OpenedManagerView extends IView {
    void loadAdapter(EquityAdapter equityAdapter);

    void loadAddress(ShippingAddressBean addressBean);

    void loadPostage(PostageBean postageBean);

    void noAddress();
}
