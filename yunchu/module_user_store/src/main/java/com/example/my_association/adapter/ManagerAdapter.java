package com.example.my_association.adapter;

import android.content.Context;

import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.ManagerBean;
import com.example.common.CommonResource;
import com.example.user_store.R;
import com.example.utils.SPUtil;

import java.util.List;

public class ManagerAdapter extends MyRecyclerAdapter<ManagerBean.RecordsBean> {

    public ManagerAdapter(Context context, List<ManagerBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ManagerBean.RecordsBean data, int position) {
        holder.setImageFresco(R.id.association_manager_icon, data.getIcon());
        holder.setText(R.id.association_manager_name, data.getUsername());
        holder.setText(R.id.association_manager_phone, data.getPhone());
        holder.setText(R.id.association_manager_referrer, "推荐人：" + SPUtil.getStringValue(CommonResource.USER_NAME));
    }
}
