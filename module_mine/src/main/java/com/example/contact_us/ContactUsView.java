package com.example.contact_us;

import com.example.bean.ContactUsBean;
import com.example.mvp.IView;

public interface ContactUsView extends IView {
    void contactUs(ContactUsBean contactUsBean);

}
