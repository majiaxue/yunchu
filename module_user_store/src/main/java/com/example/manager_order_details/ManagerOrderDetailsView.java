package com.example.manager_order_details;

import com.example.manager_order_details.adapter.ManagerOrderDetailsAdapter;
import com.example.mvp.IView;

public interface ManagerOrderDetailsView extends IView {
    void loadAdapter(ManagerOrderDetailsAdapter managerOrderDetailsAdapter);
}
