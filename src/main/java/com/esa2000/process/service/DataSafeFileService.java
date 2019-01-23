package com.esa2000.process.service;

import com.esa2000.process.entity.DataSafeFile;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface DataSafeFileService extends IService<DataSafeFile>{
    List<DataSafeFile> selectdataSafeFiles(String userId);
    void updataBatchDataSafeFile(List<DataSafeFile> dataSafeFiles,Map<String,String> result);
}
