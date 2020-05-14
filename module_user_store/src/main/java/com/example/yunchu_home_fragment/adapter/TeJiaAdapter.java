package com.example.yunchu_home_fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.adapter.MyRecyclerAdapter;
import com.example.adapter.RecyclerViewHolder;
import com.example.bean.HotSaleBean;
import com.example.bean.TeJIaBean;
import com.example.user_store.R;
import com.example.utils.ArithUtil;
import com.example.utils.LogUtil;

import java.math.BigDecimal;
import java.util.List;

public class TeJiaAdapter  extends MyRecyclerAdapter<HotSaleBean.DataBean> {
    public TeJiaAdapter(Context context, List<HotSaleBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, HotSaleBean.DataBean data, int position) {
//        BigDecimal big = new BigDecimal(data.getPrice());
        LogUtil.e("价格---------"+data.getPrice());
        //double exact = ArithUtil.exact(data.getPrice(), 2);
        holder.setText(R.id.tv_price,"￥"+data.getPrice());
        ImageView img = holder.itemView.findViewById(R.id.img_tejia);
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(20);
        //通过RequestOptions扩展功]能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(data.getPic()).apply(options).into(img);

    }
}
