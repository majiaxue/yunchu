package com.example.my_association;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.mvp.BaseActivity;
import com.example.search_friends.SearchFriendsActivity;
import com.example.user_store.R;
import com.example.user_store.R2;

import butterknife.BindView;

/**
 * 我的社群
 */
public class MyAssociationActivity extends BaseActivity<MyAssociationView, MyAssociationPresenter> implements MyAssociationView {


    @BindView(R2.id.my_association_back)
    ImageView myAssociationBack;
    @BindView(R2.id.my_association_search)
    ImageView myAssociationSearch;
    @BindView(R2.id.my_association_tab)
    TabLayout myAssociationTab;
    @BindView(R2.id.my_association_viewpager)
    ViewPager myAssociationViewpager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_association;
    }

    @Override
    public void initData() {
        presenter.initTab(myAssociationTab, myAssociationViewpager, getSupportFragmentManager());
    }

    @Override
    public void initClick() {
        myAssociationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //搜索好友
        myAssociationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAssociationActivity.this, SearchFriendsActivity.class));
            }
        });
    }

    @Override
    public MyAssociationView createView() {
        return this;
    }

    @Override
    public MyAssociationPresenter createPresenter() {
        return new MyAssociationPresenter(this);
    }

}
