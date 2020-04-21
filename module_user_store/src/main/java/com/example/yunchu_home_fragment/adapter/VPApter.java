package com.example.yunchu_home_fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mvp.BaseFragment;

import java.util.ArrayList;

public class VPApter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> mFragment;

    public VPApter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.mFragment = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragment.get(i);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
