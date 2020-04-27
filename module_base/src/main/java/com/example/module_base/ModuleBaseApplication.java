package com.example.module_base;

import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.common.CommonResource;
import com.example.utils.AppManager;
import com.example.utils.CitySPUtil;
import com.example.utils.ForegroundCallbacks;
import com.example.utils.JpushUtil;
import com.example.utils.LogUtil;
import com.example.utils.MyLocationListener;
import com.example.utils.SPUtil;
import com.example.utils.TxtUtil;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

public class ModuleBaseApplication extends MultiDexApplication {
    public static LocationClient mLocationClient = null;
    public static boolean isDingWei = false;

    public static final int MAX_DISK_SIZE = 20*ByteConstants.MB;
    public static final int MAX_DISK_SIZE_ON_LOW_DISK_SPACE = 10*ByteConstants.MB;
    public static final int MAX_DISK_SIZE_ON_VERY_LOW_DISK_SPACE = 5*ByteConstants.MB;
    public static final String FRESCO_CACHE_DIR = "fresco_cache";

    @Override
    public void onCreate() {
        super.onCreate();
        if (LogUtil.isDebug(this)) {
            ARouter.openLog();  //开启打印日志
            ARouter.openDebug();// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        SPUtil.getInstance(this);
        CitySPUtil.getInstance(this);
        //初始化DBFLOW
        FlowManager.init(this);
        //初始化fresco
        initFresco();

        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, CommonResource.WXAPPID, false);
        wxapi.registerApp(CommonResource.WXAPPID);
        //C:\Users\Administrator\Desktop\SingleUserMall-android\app\key2.jks
        //友盟
        UMConfigure.init(this, CommonResource.U_APPKEY, "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        initShare();
        UMConfigure.setLogEnabled(true);

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JpushUtil.getInstance(this);

        //百度地图
//        initLocationClient();

//        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
//            @Override
//            public void onSuccess() {
//                LogUtil.e("阿里百川初始化成功");
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                LogUtil.e("阿里百川：" + code + "-------" + msg);
//            }
//        });

        //应用回到前台监听
//        initAppStatusListener();

//        //京东开普勒
//        KeplerApiManager.asyncInitSdk(this, "6440db418d9c43817a129e1edf928202", "0f7f4a04dcfd4f44a934e70f7e37fe28",
//                new AsyncInitListener() {
//                    @Override
//                    public void onSuccess() {
//                        LogUtil.e("京东开普勒" + "Kepler asyncInitSdk onSuccess ");
//                    }
//
//                    @Override
//                    public void onFailure() {
//
//                        LogUtil.e("京东开普勒" +
//                                "Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
//
//                    }
//                });
    }

    private void initFresco() {

//        //磁盘内存配置
//        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
//                .setBaseDirectoryName(FRESCO_CACHE_DIR)
//                .setBaseDirectoryPath(getCacheDir())
//                .setMaxCacheSize(MAX_DISK_SIZE)
//                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_SIZE_ON_LOW_DISK_SPACE)
//                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_SIZE_ON_VERY_LOW_DISK_SPACE)
//                .build();
//
//        //对ImagePipelineConfig进行一些配置
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)              // 对图片进行自动缩放
                .setResizeAndRotateEnabledForNetwork(true) // 对网络图片进行resize处理，减少内存消耗
                .setBitmapsConfig(Bitmap.Config.RGB_565) //图片设置RGB_565，减小内存开销 fresco默认情况下是RGB_8888
//                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this, config);

//        Fresco.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    public static void initShare() {
        //YigoushangchengYigoushangcheng11
        PlatformConfig.setWeixin(CommonResource.WXAPPID, "989a80533a141046150808a636463cd1");
        PlatformConfig.setQQZone("101850298", "a816d304b5ce5440747c47629b6cea51");
//        PlatformConfig.setWeixin("wx7df9caffc7db4493", "abd4af996218993f30493a732b2f964f");
    }

    public void initLocationClient() {
        SDKInitializer.initialize(this);
        MyLocationListener myListener = new MyLocationListener();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(false);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
    }

    private void initAppStatusListener() {
        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                TxtUtil.hasClipboard(AppManager.getInstance().getCurrentActivity(), false);
            }

            @Override
            public void onBecameBackground() {

            }
        });
    }
}
