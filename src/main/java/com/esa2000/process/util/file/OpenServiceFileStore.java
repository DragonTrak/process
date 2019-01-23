package com.esa2000.process.util.file;

import com.esa2000.process.util.file.utils.FileUtils;
import com.esa2000.process.util.file.utils.OpenStorageServiceMgr;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *  集成阿里云OSS接口
 * @author Administrator
 * 20150921
 */
public class OpenServiceFileStore implements FileStore{

    @Override
    public boolean storeFile(String path, InputStream inputStream) {
    	boolean success = OpenStorageServiceMgr.getInstance().uploadFile(path,inputStream);
        return success;
    }

    @Override
	public boolean isFileExist(String path) {
		return OpenStorageServiceMgr.getInstance().doesFileExist(path);
	};

    @Override
    public boolean chunkedUpload(String path, InputStream inputStream) {
        return OpenStorageServiceMgr.getInstance().chunkedFile(path,inputStream);
    }

    @Override
    public boolean deleteFile(String path) {
        return OpenStorageServiceMgr.getInstance().deleteFile(path);
    }

    @Override
    public boolean downloadFile(String path, String local) {
        try {
        	return OpenStorageServiceMgr.getInstance().downloadFile(path, local) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public InputStream downloadFile(String path) {
    	String tempFilePath = tempFilePath(path);
    	
        try {
        	downloadFile(path,tempFilePath);
        	File file = new File(tempFilePath);
        	FileInputStream inputStream=new FileInputStream(file);
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	private String tempFilePath(String path) {
		String fileType  = FileUtils.getFileType(path);
    	String tempFilePath = FileUtils.creteTempPath(fileType);
		return tempFilePath;
	}
}
