package com.example.intergation.points_record;

import com.example.intergation.adapter.PointsRecordAdapter;
import com.example.mvp.IView;


public interface PointsRecordView extends IView {
    void loadRv(PointsRecordAdapter adapter);

    void loadFinish();

}
