package com.esa2000.process.task;

import com.esa2000.process.entity.DataSafe;
import com.esa2000.process.entity.DataSafeFile;
import com.esa2000.process.service.DataSafeFileService;
import com.esa2000.process.service.DataSafeService;
import com.esa2000.process.util.securityCert.SignCertSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description TODO
 * @Date 2019/1/15 13:37
 * @Created by Administrator
 */
@Component
public class AsyncTask{
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private DataSafeService dataSafeService;

    @Autowired
    private DataSafeFileService dataSafeFileService;

    @Autowired
    private SignCertSource signCertSource;

    @Async("simpleAsync")
    public Future<Map> processDataSafe(String userId,List<DataSafe> dataSafes){
        System.out.println(Thread.currentThread());
        Map<String,String> result = new HashMap<>();

        int size = dataSafes.size();
        int i = 0;
        for (DataSafe dataSafe : dataSafes) {
            logger.info("DATA_SAFE表||总数:"+size+"||剩余:"+(size-(i++)));
            dataSafe.setSafeType("2");
            dataSafe.setSafeSize(0L);
            try {
                String signData = signCertSource.sign(dataSafe.toString().getBytes("UTF-8"));
                dataSafe.setSignValue(signData);
            } catch (GeneralSecurityException e) {
                logger.info(e);
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                logger.info(e);
                e.printStackTrace();
            }
        }

        dataSafeService.updataBatchDataSafe(dataSafes,result);
        return new AsyncResult<Map>(result);
    }

    @Async("simpleAsync")
    public Future<Map> processDataSafeFile(String userId, List<DataSafeFile> dataSafeFiles){
        System.out.println(Thread.currentThread());
        Map<String,String> result = new HashMap<>();
        int size = dataSafeFiles.size();
        int i = 0;
        for (DataSafeFile dataSafeFile : dataSafeFiles) {
            logger.info("DATA_SAFE_FILE表||总数:"+size+"||剩余:"+(size-(i++)));
            dataSafeFile.setHasResource("N");
            dataSafeFile.setSafeSize(null);
            dataSafeFile.setFilePath(null);
            try {
                String signData = signCertSource.sign(dataSafeFile.toString().getBytes("UTF-8"));
                dataSafeFile.setSignValue(signData);
            } catch (GeneralSecurityException e) {
                logger.info(e);
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                logger.info(e);
                e.printStackTrace();
            }
        }
        dataSafeFileService.updataBatchDataSafeFile(dataSafeFiles,result);
        return new AsyncResult<Map>(result);
    }

    /**
     * 文件保全 zte的DataSafeFile
     * @return
     */
    private DataSafeFile selectdataSafeFile(String dataSafeId){
        Example example = new Example(DataSafeFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("hasResource","Y");
        criteria.andEqualTo("dataSafeId",dataSafeId);
        List<DataSafeFile> dataSafeFiles = dataSafeFileService.selectByExample(example);
        if (dataSafeFiles.size() == 1){
            return dataSafeFiles.get(0);
        }else {
            return null;
        }
    }

//    private List<DataSafeFile> selectdataSafeFiles(String userId){
//        Example example = new Example(DataSafeFile.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("hasResource","Y");
//        criteria.andEqualTo("createBy",userId);
//        List<DataSafeFile> dataSafeFiles = dataSafeFileService.selectByExample(example);
//        return dataSafeFiles;
//    }

    /**
     * 查询DataSafe
     * @param
     * @return
     */
//    private List<DataSafe> selectDataSafes(String userId) {
//        Example example = new Example(DataSafe.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("saveUser",userId);
//        criteria.andEqualTo("safeType","1");//safeType
//        return dataSafeService.selectByExample(example);
//    }

//    private void updataBatchDataSafe(List<DataSafe> dataSafes,Map<String,String> result){
//        for (DataSafe dataSafe : dataSafes) {
//            int i = dataSafeService.updateAll(dataSafe);
//            if (i != 1){
//                result.put(dataSafe.getSafeId(),":DATA_SAFE表处理失败");
//            }
//        }
//    }
//    private void updataBatchDataSafeFile(List<DataSafeFile> dataSafeFiles,Map<String,String> result){
//        for (DataSafeFile dataSafeFile : dataSafeFiles) {
//            int i = dataSafeFileService.updateAll(dataSafeFile);
//            if (i != 1){
//                result.put(dataSafeFile.getSafeId(),":DATA_SAFE_FILE表处理失败");
//            }
//        }
//    }
}
