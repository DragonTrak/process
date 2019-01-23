package com.esa2000.process.business;

import com.esa2000.process.entity.DataSafeFile;
import com.esa2000.process.util.SerializeUtil;
import com.esa2000.process.util.file.FileStore;
import com.esa2000.process.util.file.FileStoreFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 删除文件
 * @Date 2019/1/16 16:45
 * @Created by Administrator
 */
@Component
public class DeleteFileTaskScheduler extends BaseScheduler{

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void apply(Map<String,String> parm) throws Exception {
        String userId = parm.get("userId");
        String fileUrl = parm.get("dataDir");
        List<DataSafeFile> dataSafeFiles = null;
        dataSafeFiles = (List<DataSafeFile>) SerializeUtil.deserialize(fileUrl+"dataSafeFiles"+userId+".txt");
        int size = dataSafeFiles.size();
        int i = 0;
        logger.info("======================开始执行删除文件任务==========================");
        logger.info("======================总文件数为:"+size+"=========================");
        FileStore fileStore = FileStoreFactory.createFileStore();
        for (DataSafeFile dataSafeFile : dataSafeFiles) {
            i ++ ;
            boolean result = fileStore.deleteFile(dataSafeFile.getFilePath());
            if (result){
                logger.info("删除||"+dataSafeFile.getFilePath()+"||成功||总数:"+size+"||剩余:"+(size-i));
            }else {
                logger.info("删除||"+dataSafeFile.getFilePath()+"||失败||总数:"+size+"||剩余:"+(size-i));
            }
        }
    }
}
