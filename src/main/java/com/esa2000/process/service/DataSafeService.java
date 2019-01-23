package com.esa2000.process.service;

import com.esa2000.process.entity.DataSafe;

import java.util.List;
import java.util.Map;

public interface DataSafeService extends IService<DataSafe> {
    List<DataSafe> selectDataSafes(String userId);

    void updataBatchDataSafe(List<DataSafe> dataSafes,Map<String,String> result);
}
