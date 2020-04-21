package com.example.intergation.cashout_record;

import com.example.intergation.adapter.PointsRecordAdapter;
import com.example.mvp.IView;


public interface CashoutRecordView extends IView {
    void loadRv(PointsRecordAdapter adapter);

    void loadFinish();
}
