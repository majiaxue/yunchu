package com.example.updatevip;

import android.content.Intent;

import com.example.mvp.IView;
import com.example.updatevip.adapter.ImgShabgChuanAdapter;

public interface UpdateVipView extends IView {
    void photoAlbum(Intent intent);

    void takePhoto(Intent captureIntent);

    void imagePath(String s);

    void loadRv(ImgShabgChuanAdapter adapter);
}
