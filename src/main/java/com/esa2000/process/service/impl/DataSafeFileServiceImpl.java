package com.esa2000.process.service.impl;

import com.esa2000.process.entity.DataSafeFile;
import com.esa2000.process.mapper.DataSafeFileMapper;
import com.esa2000.process.service.DataSafeFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dataSafeFileService")
public class DataSafeFileServiceImpl extends BaseService<DataSafeFile> implements DataSafeFileService {

    @Resource
    DataSafeFileMapper dataSafeFileMapper;

    @Override
    public List<DataSafeFile> selectdataSafeFiles(String userId){
        Map<String,String> sqlParm = new HashMap<>();
        sqlParm.put("hasResource","Y");
        sqlParm.put("saveUser",userId);
//        Example example = new Example(DataSafeFile.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("hasResource","Y");
//        criteria.andEqualTo("createBy",userId);
//        criteria.andEqualTo("isDelete","0");
        List<DataSafeFile> dataSafeFiles = dataSafeFileMapper.queryDataSafeFile(sqlParm);
        return dataSafeFiles;
    }

    @Transactional
    @Override
    public void updataBatchDataSafeFile(List<DataSafeFile> dataSafeFiles, Map<String, String> result) {
        for (DataSafeFile dataSafeFile : dataSafeFiles) {
            int i = updateAll(dataSafeFile);
            if (i != 1){
                result.put(dataSafeFile.getSafeId(),":DATA_SAFE_FILE表处理失败");
            }
        }
    }
}
