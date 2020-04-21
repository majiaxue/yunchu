package com.example.group_fans.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.GroupFansBean;
import com.example.bean.UserInfoBean;
import com.example.module_mine.R;

import java.util.List;

public class GroupFansRvAdapter extends MyRecyclerAdapter<GroupFansBean> {
    public GroupFansRvAdapter(Context context, List<GroupFansBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, GroupFansBean data, int position) {
        holder.setText(R.id.rv_group_fans_name, data.getNickname())
                .setText(R.id.rv_group_fans_time, "时间：" + data.getCreateTime())
                .setImageUrlCircular(R.id.rv_group_fans_img, data.getIcon());
    }
}
