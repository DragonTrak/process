package com.esa2000.process.business;

import com.esa2000.process.entity.DataSafe;
import com.esa2000.process.entity.DataSafeFile;
import com.esa2000.process.service.DataSafeFileService;
import com.esa2000.process.service.DataSafeService;
import com.esa2000.process.util.SerializeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 反序列化数据然后更新到数据库(即回滚)
 * @Date 2019/1/16 15:05
 * @Created by Administrator
 */
@Component
public class DeserializeScheduler extends BaseScheduler{

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private DataSafeService dataSafeService;

    @Autowired
    private DataSafeFileService dataSafeFileService;

    @Override
    public void apply(Map<String,String> parm)  {
        String userId = parm.get("userId");
        String fileUrl = parm.get("dataDir");
        List<DataSafe> dataSafes = null;
        List<DataSafeFile> dataSafeFiles = null;
        dataSafes = (List<DataSafe>) SerializeUtil.deserialize(fileUrl+"dataSafes"+userId+".txt");
        dataSafeFiles = (List<DataSafeFile>) SerializeUtil.deserialize(fileUrl+"dataSafeFiles"+userId+".txt");

        Map<String,String> reslut = new HashMap<>();
        Map<String,String> reslut2 = new HashMap<>();
        dataSafeService.updataBatchDataSafe(dataSafes,reslut);
        dataSafeFileService.updataBatchDataSafeFile(dataSafeFiles,reslut2);

        logger.info("data_Safe回滚失败:"+reslut.toString());
        logger.info("data_Safe_file回滚失败:"+reslut2.toString());
    }
}
