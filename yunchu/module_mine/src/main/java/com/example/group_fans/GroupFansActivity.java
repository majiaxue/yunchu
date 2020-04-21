package com.example.group_fans;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bean.GroupFansPeopleBean;
import com.example.common.CommonResource;
import com.example.group_fans.adapter.GroupFansRvAdapter;
import com.example.module_mine.R;
import com.example.module_mine.R2;
import com.example.mvp.BaseActivity;
import com.example.order.adapter.OrderVPAdapter;
import com.example.utils.LogUtil;
import com.example.utils.SPUtil;
import butterknife.BindView;

@Route(path = "/mine/groupfans")
public class GroupFansActivity extends BaseActivity<GroupFansView, GroupFansPresenter> implements GroupFansView {
    @BindView(R2.id.group_fans_back)
    ImageView includeBack;
    @BindView(R2.id.group_fans_total)
    TextView groupFansTotal;
    @BindView(R2.id.group_fans_edit)
    EditText groupFansEdit;
    @BindView(R2.id.group_fans_search)
    TextView groupFansSearch;
    @BindView(R2.id.group_fans_zhitui)
    TextView groupFansZhitui;
    @BindView(R2.id.group_fans_xinzeng)
    TextView groupFansXinzeng;
    @BindView(R2.id.group_fans_tuijianren)
    TextView groupFansTuijianren;
    @BindView(R2.id.group_fans_rv)
    ViewPager groupFansRv;
    @BindView(R2.id.group_fans_header)
    ImageView mHeader;
    @BindView(R2.id.group_fans_name)
    TextView mName;
    @BindView(R2.id.tab)
    TabLayout mTab;
    private int totalPage = 1;
    private int page = 1;
    private boolean isSearch = false;
    private boolean isZhitui = true;
    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_fans;
    }

    @Override
    public void initData() {
        presenter.getTeam();
        presenter.initTabLayout(mTab);
        mTab.setupWithViewPager(groupFansRv);
        presenter.initVp(getSupportFragmentManager());

        mName.setText(SPUtil.getStringValue(CommonResource.USER_NAME));
        Glide.with(this).load(SPUtil.getStringValue(CommonResource.USER_PIC)).placeholder(R.drawable.vhjfg).apply(RequestOptions.circleCropTransform()).into(mHeader);


    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadFinish() {
    }

    @Override
    public void loadUI(int totalPage, int totalFans) {
        this.totalPage = totalPage;
        groupFansTotal.setText(totalFans + "");
    }

    @Override
    public void loadRv(GroupFansRvAdapter adapter) {
        //groupFansRv.setAdapter(adapter);

    }

    @Override
    public void getType(String type) {
        this.type=type;
        LogUtil.e("这是type--------"+type);
    }

    @Override
    public void updateVP(OrderVPAdapter vpAdapter) {
        groupFansRv.setAdapter(vpAdapter);
    }

    @Override
    public void loadCount(GroupFansPeopleBean peopleBean) {
        groupFansZhitui.setText(peopleBean.getFirstPerson() + "");
        groupFansXinzeng.setText(peopleBean.getTodayPerson() + "");
        groupFansTotal.setText(peopleBean.getAllPerson()+"");
        if (peopleBean.getMyRecommend() == null || "".equals(peopleBean.getMyRecommend())) {
            groupFansTuijianren.setText("无");
        } else {
            groupFansTuijianren.setText(peopleBean.getMyRecommend());
        }
    }

    @Override
    public void noFans() {
        this.totalPage = 0;
       // groupFansTotal.setText("0");
    }

    @Override
    public GroupFansView createView() {
        return this;
    }

    @Override
    public GroupFansPresenter createPresenter() {
        return new GroupFansPresenter(this);
    }
}
