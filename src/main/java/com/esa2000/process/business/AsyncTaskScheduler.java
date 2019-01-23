package com.esa2000.process.business;

import com.esa2000.process.entity.DataSafe;
import com.esa2000.process.entity.DataSafeFile;
import com.esa2000.process.service.DataSafeFileService;
import com.esa2000.process.service.DataSafeService;
import com.esa2000.process.task.AsyncTask;
import com.esa2000.process.util.SerializeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description 用来处理文件保全转摘要保全
 * @Date 2019/1/16 13:58
 * @Created by Administrator
 */
@Component
public class AsyncTaskScheduler extends BaseScheduler {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private DataSafeService dataSafeService;

    @Autowired
    private DataSafeFileService dataSafeFileService;

    @Override
    public void apply(Map<String,String> parm) throws Exception {
        String userId = parm.get("userId");
        String fileUrl = parm.get("dataDir");
        serializable(userId,fileUrl);
        long start = System.currentTimeMillis();
        List<DataSafe> dataSafes = dataSafeService.selectDataSafes(userId);
        List<DataSafeFile> dataSafeFiles = dataSafeFileService.selectdataSafeFiles(userId);
        Future<Map> mapFuture = asyncTask.processDataSafe(userId,dataSafes);
        Future<Map> mapFuture1 = asyncTask.processDataSafeFile(userId,dataSafeFiles);
        while (true){
            if (mapFuture.isDone()&& mapFuture1.isDone()){
                logger.info("数据处理完成");
                long end = System.currentTimeMillis();
                logger.info("异步处理耗时:"+(end-start));
                logger.info("data_safe表处理失败记录:"+mapFuture.get().toString());
                logger.info("data_safe_file表处理失败记录:"+mapFuture1.get().toString());
                break;
            }
            Thread.sleep(3000);
        }
    }

    /**
     * WAL 预写日志
     * @param userId
     * @param fileUrl
     */
    public void serializable(String userId,String fileUrl){
        List<DataSafe> dataSafes = dataSafeService.selectDataSafes(userId);
        List<DataSafeFile> dataSafeFiles = dataSafeFileService.selectdataSafeFiles(userId);
        SerializeUtil.serialize(dataSafes,fileUrl+"dataSafes"+userId+".txt");
        SerializeUtil.serialize(dataSafeFiles,fileUrl+"dataSafeFiles"+userId+".txt");
    }
}
