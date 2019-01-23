package com.esa2000.process.util.file;


import com.esa2000.process.util.certUtil.AES;
import com.esa2000.process.util.file.utils.FileUtils;
import com.esa2000.process.util.file.utils.OpenStorageServiceMgr;
import java.io.*;


/**
 *  集成阿里云OSS接口
 * @author Administrator
 * 20150921
 */
public class OpenServiceFileEncryptStore implements FileStore{

    @Override
    public boolean storeFile(String path, InputStream inputStream) {
    	boolean success = false;
    	String fileType = FileUtils.getFileType(path);
    	// 新建临时文件
		String tempFile = FileUtils.creteTempPath(fileType);
		
		//加密内容
		try {

			FileUtils.writeBytesToFile(input2byte(inputStream), tempFile);
		
			byte[] encrypt = AES.encrypt(FileUtils.readBytesFromFile(new File(tempFile)));

			FileUtils.writeBytesToFile(encrypt, tempFile);
			
			success = OpenStorageServiceMgr.getInstance().uploadFile(path,new File(tempFile));
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		FileUtils.deleteFile(tempFile);
    	
    	return success;
    }
    
	public static final byte[] input2byte(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

    @Override
	public boolean isFileExist(String path) {
		return  OpenStorageServiceMgr.getInstance().doesFileExist(path);
	};

    @Override
    public boolean deleteFile(String path) {
        return false;
    }

    @Override
    public boolean downloadFile(String path, String local) {
    	boolean success = false;
    	//String fileType = FileUtils.getFileType(path);
    	// 新建临时文件
		//String tempFile = CommonUtil.creteTempPath(fileType);		
        try {
        	success = OpenStorageServiceMgr.getInstance().downloadFile(path, local) ;
        	// 解密文件内容
			FileUtils.writeBytesToFile(AES.decrypt(FileUtils.readBytesFromFile(new File(local))), local) ;
        	return success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public InputStream downloadFile(String path) {
    	String tempFilePath = tempFilePath(path);
    	
        try {
        	File file = new File(tempFilePath);
        	downloadFile(path,tempFilePath);
        	FileInputStream inputStream=new FileInputStream(file);
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public boolean chunkedUpload(String path, InputStream inputStream) {
		return OpenStorageServiceMgr.getInstance().chunkedFile(path,inputStream);
	}

	private String tempFilePath(String path) {
		String fileType  = FileUtils.getFileType(path);
    	String tempFilePath = FileUtils.creteTempPath(fileType);
		return tempFilePath;
	}
}
