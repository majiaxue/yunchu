package com.example.flashsale;

import com.example.flashsale.adapter.FlashSaleGoodsAdapter;
import com.example.flashsale.adapter.FlashSaleTimeAdapter;
import com.example.mvp.IView;

public interface FlashSaleView extends IView {

    void loadAdapter(FlashSaleTimeAdapter flashSaleTimeAdapter);

    void loadAdapter(FlashSaleGoodsAdapter flashSaleGoodsAdapter);

    void noGoods(boolean onGoods);

    void refreshSuccess();
}
