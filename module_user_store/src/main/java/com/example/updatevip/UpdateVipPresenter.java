package com.example.updatevip;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.PhotoBean;
import com.example.bean.ShangChuanBean;
import com.example.common.CommonResource;
import com.example.mvp.BasePresenter;
import com.example.net.OnDataListener;
import com.example.net.OnMyCallBack;
import com.example.net.RetrofitUtil;
import com.example.updatevip.adapter.ImgShabgChuanAdapter;
import com.example.user_store.R;
import com.example.utils.LogUtil;
import com.example.utils.MapUtil;
import com.example.utils.OnChangeHeaderListener;
import com.example.utils.PopUtils;
import com.example.utils.SPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class UpdateVipPresenter extends BasePresenter<UpdateVipView> {

    private Uri fileUri;
    private String filePath = Environment.getExternalStorageDirectory() + "/fltk/image";
    private int flag = 0;
    private File file1;
    private List<String> uriList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private RequestBody imgBody;
    private MultipartBody.Part filePart;
    private ImgShabgChuanAdapter adapter;
    private Uri parse;
    private String fileName1;

    public UpdateVipPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void TiJiao(String content){
        ShangChuanBean bean=new ShangChuanBean();
        if (content!=null&&fileName1!=null){
            LogUtil.e("filename-----"+fileName1);
            LogUtil.e("content----"+content);
            bean.setNote(content);
            bean.setPhoto(fileName1);
            bean.setUserCode(SPUtil.getStringValue(CommonResource.USERCODE));
            LogUtil.e("usercode------"+SPUtil.getStringValue(CommonResource.USERCODE));
            String jsonString = JSON.toJSONString(bean);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postDataWithBody(CommonResource.UPDATEVIP, requestBody);
            RetrofitUtil.getInstance().toSubscribe(observable,new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("提交资料------------"+result);
                    ((Activity) mContext).finish();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e(errorCode+"-------------"+errorMsg);
                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }));
        }else {
            Toast.makeText(mContext, "请将说明或图片添加完整", Toast.LENGTH_SHORT).show();
        }

    }



    public void addPic(){
        PopUtils.changeHeader(mContext, new OnChangeHeaderListener() {
            @Override
            public void setOnChangeHeader(final PopupWindow pop, TextView camera, TextView album) {
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                        pop.dismiss();
                    }
                });

                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openPhotoAlbum();
                        pop.dismiss();
                    }
                });
            }
        });

    }

    public void loadData() {
        parse = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.drawable.vipimg);
        uriList.add(String.valueOf(parse));
        adapter = new ImgShabgChuanAdapter(mContext, uriList, R.layout.item_photo_shangchuan);
        if (getView() != null) {
            getView().loadRv(adapter);
        }
        adapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
            @Override
            public void ViewOnClick(View view, final int index) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uriList.remove(index);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        adapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if (position == uriList.size() - 1) {
                    addPic();
                } else {
                    PopUtils.seeBigImg2(mContext, imgList.get(position));
                }
            }
        });

    }

    private void openPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        getView().photoAlbum(intent);
        flag = 2;
    }

    private void openCamera() {
        File file0 = new File(filePath);
        if (!file0.exists()) {
            file0.mkdirs();
        }
        file1 = new File(filePath, System.currentTimeMillis() + ".jpg");

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(mContext.getApplicationContext(), mContext.getPackageName(), file1);
            captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file1);
        }
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        getView().takePhoto(captureIntent);
        flag = 1;
    }

    public void updateList() {
        uploadPictures();
    }

    private void uploadPictures() {
        uriList.remove(uriList.size() - 1);
        LogUtil.e("图片1111" + fileUri);
        if (1 == flag) {
            imgBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            filePart = MultipartBody.Part.createFormData("file", file1.getName(), imgBody);

        } else if (2 == flag) {
            String realFilePath = getRealFilePath(mContext, fileUri);
            File file = new File(realFilePath);
            //        LogUtil.e("图片2222" + realFilePath);
            //将文件转化为RequestBody对象
            //需要在表单中进行文件上传时，就需要使用该格式：multipart/form-data
            imgBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //将文件转化为MultipartBody.Part
            //第一个参数：上传文件的key；第二个参数：文件名；第三个参数：RequestBody对象
            filePart = MultipartBody.Part.createFormData("file", file.getName(), imgBody);
        }
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4000).postFile(CommonResource.UPLOADORDER, filePart);
        RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("上传图片" + result);
                PhotoBean photoBean = JSON.parseObject(result, PhotoBean.class);
                fileName1 = photoBean.getFileName();

                JSONObject jsonObject = JSON.parseObject(result);
//                String ngUrl = jsonObject.getString("ngUrl");
                String bucketName = jsonObject.getString("bucketName");
                String fileName = jsonObject.getString("fileName");
                if (getView() != null) {
                    getView().imagePath(CommonResource.BASEURL_8083 + "/" + bucketName + "/" + fileName);
                }
                uriList.add(CommonResource.BASEURL_8083 + "/" + bucketName + "/" + fileName);
                imgList.add(CommonResource.BASEURL_8083 + "/" + bucketName + "/" + fileName);
                if (uriList.size() < 9) {
                    uriList.add(String.valueOf(parse));
                }
                adapter.notifyDataSetChanged();
                fileUri = null;
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("上传图片" + errorCode + errorMsg);
            }
        }));
    }

    private String getRealFilePath(Context mContext, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = mContext.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void parseUri(Intent intent) {
        fileUri = intent.getData();
        String type = intent.getType();
        if (fileUri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = fileUri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = mContext.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        fileUri = uri_temp;
                    }
                }
            }
        }
        updateList();
    }
}
