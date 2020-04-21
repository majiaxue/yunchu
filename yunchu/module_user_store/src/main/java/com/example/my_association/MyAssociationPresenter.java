package com.example.my_association;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mvp.BasePresenter;
import com.example.my_association.manager.ManagerFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyAssociationPresenter extends BasePresenter<MyAssociationView> {

    private String[] strArray = new String[]{"普通会员", "超级会员"};
    private List<Fragment> fragments = new ArrayList<>();
    private int[] index = new int[]{0, 1};

    public MyAssociationPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout myAssociationTab, ViewPager myAssociationViewpager, FragmentManager fm) {
        for (int i = 0; i < strArray.length; i++) {
            myAssociationTab.addTab(myAssociationTab.newTab().setText(strArray[i]));
            Fragment fragment = ManagerFragment.newInstance(index[i]);
            fragments.add(fragment);
        }

        initIndicator(myAssociationTab);

        IndexPagerAdapter indexPagerAdapter = new IndexPagerAdapter(fm, strArray, fragments);
        myAssociationViewpager.setAdapter(indexPagerAdapter);

        myAssociationTab.setupWithViewPager(myAssociationViewpager);


    }

    class IndexPagerAdapter extends FragmentPagerAdapter {
        private String[] titleList;
        private List<Fragment> fragmentList;

        public IndexPagerAdapter(FragmentManager fm, String[] titleList, List<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList[position];
        }
    }

    private void initIndicator(final TabLayout myAssociationTab) {
        myAssociationTab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //了解源码得知 线的宽度是根据 tabView的宽度来设置的
                    LinearLayout mTabStrip = (LinearLayout) myAssociationTab.getChildAt(0);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField =
                                tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding
                        // 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params =
                                (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (Exception e) {

                }
            }
        });
    }
}
