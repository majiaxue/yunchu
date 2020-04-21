package com.example.search_friends;

import com.example.mvp.IView;
import com.example.my_association.adapter.ManagerAdapter;

public interface SearchFriendsView extends IView {
    void loadAdapter(ManagerAdapter managerAdapter);
}
