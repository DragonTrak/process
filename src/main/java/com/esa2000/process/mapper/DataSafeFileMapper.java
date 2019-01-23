package com.esa2000.process.mapper;

import com.esa2000.process.base.mapper.MyMapper;
import com.esa2000.process.entity.DataSafeFile;

import java.util.List;
import java.util.Map;

public interface DataSafeFileMapper extends MyMapper<DataSafeFile> {
    List<DataSafeFile> queryDataSafeFile(Map<String,String> parm);
}