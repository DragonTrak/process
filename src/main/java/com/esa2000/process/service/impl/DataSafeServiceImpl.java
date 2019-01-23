package com.esa2000.process.service.impl;

import com.esa2000.process.entity.DataSafe;
import com.esa2000.process.mapper.DataSafeMapper;
import com.esa2000.process.service.DataSafeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("dataSafeService")
public class DataSafeServiceImpl extends BaseService<DataSafe> implements DataSafeService {


    @Resource
    DataSafeMapper dataSafeMapper;

    @Override
    public List<DataSafe> selectDataSafes(String userId) {
        Example example = new Example(DataSafe.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("saveUser",userId);
        criteria.andEqualTo("safeType","1");//safeType：1-原文保全，2-文件摘要保全，3-日志保全
        criteria.andEqualTo("isDelete","0");
        criteria.andEqualTo("sourceType","2");//1-用户上传，2-接口上传
        return selectByExample(example);
    }

    @Transactional
    @Override
    public void updataBatchDataSafe(List<DataSafe> dataSafes,Map<String,String> result){
        for (DataSafe dataSafe : dataSafes) {
            int i = updateAll(dataSafe);
            if (i != 1){
                result.put(dataSafe.getSafeId(),":DATA_SAFE表处理失败");
            }
        }
    }
}
