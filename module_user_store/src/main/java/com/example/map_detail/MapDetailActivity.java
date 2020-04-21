package com.example.map_detail;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.bean.LocalShopBean;
import com.example.mvp.BaseActivity;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.MyLocationListener;

import butterknife.BindView;

public class MapDetailActivity extends BaseActivity<MapDetailView, MapDetailPresenter> implements MapDetailView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.map_detail_mapview)
    MapView mapDetailMapview;

    private LocalShopBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_map_detail;
    }

    @Override
    public void initData() {
        includeTitle.setText("位置");
        Intent intent = getIntent();
        bean = (LocalShopBean) intent.getSerializableExtra("bean");

        LatLng latLng = new LatLng(MyLocationListener.latitude, MyLocationListener.longitude);
        MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(latLng);

        BaiduMapOptions options = new BaiduMapOptions();
        options.mapType(BaiduMap.MAP_TYPE_SATELLITE);

        mapDetailMapview.getMap().setMapStatus(statusUpdate);

        //设置地图缩放级别
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mapDetailMapview.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //开启地图的定位图层
        mapDetailMapview.getMap().setMyLocationEnabled(true);
        mapDetailMapview.getMap().setMyLocationData(MyLocationListener.locData);

        //定义Maker坐标点
        LatLng point = new LatLng(Double.valueOf(bean.getSeller_lat()), Double.valueOf(bean.getSeller_lon()));
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_weizhi);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .perspective(true)
                .draggable(false)
                .title(bean.getSeller_shop_name())
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mapDetailMapview.getMap().addOverlay(option);
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
    protected void onResume() {
        mapDetailMapview.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapDetailMapview.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapDetailMapview.getMap().setMyLocationEnabled(false);
        mapDetailMapview.onDestroy();
        mapDetailMapview = null;
        super.onDestroy();
    }

    @Override
    public MapDetailView createView() {
        return this;
    }

    @Override
    public MapDetailPresenter createPresenter() {
        return new MapDetailPresenter(this);
    }

}
