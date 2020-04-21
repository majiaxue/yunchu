package com.example.businessapplication;

import android.content.Intent;
import android.net.Uri;

import com.example.mvp.IView;

import java.io.FileNotFoundException;

/**
 * Created by cuihaohao on 2019/5/25
 * Describe:
 */
public interface BusinessApplicationView extends IView {
    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void selectPhoto(Uri uri);

    void showHeader(String base64);

}
