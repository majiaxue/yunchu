package com.example.my_association.manager;

import com.example.mvp.IView;
import com.example.my_association.adapter.ManagerAdapter;

public interface ManagerView extends IView {
    void loadAdapter(ManagerAdapter managerAdapter);

    void refreshSuccess();

}
