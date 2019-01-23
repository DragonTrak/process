package com.esa2000.process.util.file;

import com.esa2000.process.util.toolUtil.PropConfig;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FileStoreFactory {

	private static String OSS_WHETHER_TO_USE= PropConfig.getString("oss.whetherToUse");
	private static String DES_WHETHER_TO_USE= PropConfig.getString("des.whether.to.use");
	private static String SCP_WHETHER_TO_USE= PropConfig.getString("scp.whether.to.use");
    public static FileStore createFileStore() {
    	//默认采取FTP上传文件的方式
    	FileStore store = new FtpFileStore();
    	
    	if (OSS_WHETHER_TO_USE.equalsIgnoreCase("true"))
    	{
    		//采用 ali oss 云服务存储文件 后续添加云服务 内容加密
    		 store = new OpenServiceFileStore();
    		 
    		 if (DES_WHETHER_TO_USE.equalsIgnoreCase("true"))
    	    	{
    	    		//支持文件内容加密
    	    		 store = new OpenServiceFileEncryptStore();
    	    	}
    		 
    	}
    	else  if (DES_WHETHER_TO_USE.equalsIgnoreCase("true"))
    	{
    		//FTP 支持文件内容加密
    		 store = new FtpFileEncryptStore();
    	}
    	
    	else if(SCP_WHETHER_TO_USE.equalsIgnoreCase("true")){
    		//SCP
   		 	store = new ScpFileStore();
    	}
    	
        return store;
    }
}

