package com.example.browsinghistory;

import com.example.browsinghistory.adapter.BrowsingHistoryChildAdapter;
import com.example.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public interface BrowsingHistoryView extends IView {
    void noGoods(boolean noGoods);

    void refreshSuccess();

    void loadAdapter(BrowsingHistoryChildAdapter browsingHistoryChildAdapter);

}
