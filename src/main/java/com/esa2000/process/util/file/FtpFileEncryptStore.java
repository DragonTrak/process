package com.esa2000.process.util.file;

import com.esa2000.process.util.certUtil.AES;
import com.esa2000.process.util.file.utils.FileUtils;
import com.esa2000.process.util.toolUtil.PropConfig;

import java.io.*;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FtpFileEncryptStore implements FileStore {
	private String ip = PropConfig.getString("ftp.ip");
	private int port = Integer.parseInt(PropConfig.getString("ftp.port"));
	private String userName = PropConfig.getString("ftp.userName");
	private String password = PropConfig.getString("ftp.password");

	@Override
	public boolean storeFile(String path, InputStream inputStream) {

		boolean success = false;
		String fileType = FileUtils.getFileType(path);
    	// 新建临时文件
		String tempFile = FileUtils.creteTempPath(fileType);

		FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
		try {
			FileUtils.writeBytesToFile(input2byte(inputStream), tempFile);
			
			byte[] encrypt = AES.encrypt(FileUtils.readBytesFromFile(new File(tempFile)));
			
			FileUtils.writeBytesToFile(encrypt, tempFile);
			
			success = ftpUtil.upload(new FileInputStream(new File(tempFile)),path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FileUtils.deleteFile(tempFile);
		ftpUtil.disConFtp();
		
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
		FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
		boolean success = false;
		try {
			success = ftpUtil.isExistFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ftpUtil.disConFtp();
		return success;
	}

	@Override
	public boolean deleteFile(String path) {
		return false;
	}

	@Override
	public boolean downloadFile(String path, String local) {
		FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
		
		boolean success = false;
		try {
			
			success = ftpUtil.download(path, local);
			// 解密文件内容
 			FileUtils.writeBytesToFile(AES.decrypt(FileUtils.readBytesFromFile(new File(local))), local) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ftpUtil.disConFtp();
		return success;
	}

	@Override
	public InputStream downloadFile(String path) {
		FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
		InputStream stream = null;
		// 新建临时文件
		String tempFile =  tempFilePath(path);
		try {
			File file = new File(tempFile);
			
        	downloadFile(path,tempFile);
        	
            stream=new FileInputStream(file);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		ftpUtil.disConFtp();
		
		return stream;
	}

	@Override
	public boolean chunkedUpload(String path, InputStream inputStream) {
		return false;
	}

	private String tempFilePath(String path) {
		String fileType  = FileUtils.getFileType(path);
    	String tempFilePath = FileUtils.creteTempPath(fileType);
		return tempFilePath;
	}
}
